package com.brilworks.accounts.services.impl;

import com.brilworks.accounts.controller.AuthValidator;
import com.brilworks.accounts.dto.LinkedInDTO;
import com.brilworks.accounts.dto.LinkedInMediaType;
import com.brilworks.accounts.dto.LinkedInPostRequestDto;
import com.brilworks.accounts.entity.LinkedInAccessToken;
import com.brilworks.accounts.entity.LinkedInState;
import com.brilworks.accounts.entity.User;
import com.brilworks.accounts.repository.LinkedInAccessTokenRepository;
import com.brilworks.accounts.repository.LinkedInStateRepository;
import com.brilworks.accounts.services.LinkedInService;
import com.google.gson.Gson;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;

import javax.transaction.Transactional;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.UUID;

@Service
public class LinkedInServiceImpl implements LinkedInService {
    private static final Logger log = LoggerFactory.getLogger(LinkedInServiceImpl.class);
    @Autowired
    private AuthValidator authValidator;
    @Autowired
    private LinkedInStateRepository linkedInStateRepository;
    @Autowired
    private LinkedInAccessTokenRepository linkedInAccessTokenRepository;
    @Value("${linkedin.access.toke.api}")
    private String ACCESS_TOKEN_API;
    @Value("${linkedin.client.id}")
    private String CLIENT_ID;
    @Value("${linkedin.client.secret}")
    private String CLIENT_SECRET;
    @Value("${linkedin.redirect.uri}")
    private String REDIRECT_URI;
    @Value("${linkedin.uri}")
    private String LINKEDIN_URI;
    @Value("${linkedin.user.profile.api}")
    private String LINKEDIN_USER_PROFILE;
    @Value("${linkedin.url.text.post}")
    private String LINKEDIN_TEXT_POST;

    @Override
    @Transactional
    public String getLinkedInUri(Authentication auth) {
        User user = authValidator.authUser(auth);
        LinkedInState linkedInState = linkedInStateRepository.findByUserId(user.getId());
        if(linkedInState == null){
            linkedInState = new LinkedInState();
            linkedInState.setUserId(user.getId());
        }
        linkedInState.setState(UUID.randomUUID().toString());
        linkedInStateRepository.save(linkedInState);
        String finalLinkedUrl = LINKEDIN_URI+"?response_type=code&client_id="+CLIENT_ID+
                "&redirect_uri="+REDIRECT_URI+"&state="+linkedInState.getState()+
                "&scope=r_liteprofile%20r_emailaddress%20w_member_social";
        return finalLinkedUrl;
    }
    @Override
    @Transactional
    public void getAccessToken(String code,String state) {
        LinkedInState linkedInState = linkedInStateRepository.findByState(state);
        if(linkedInState == null) {
            log.error("linkedInState not found by state");
            return;
        }
        String callUrl = ACCESS_TOKEN_API + "?code="+code+"&grant_type=authorization_code&client_id="+CLIENT_ID+"&client_secret="+CLIENT_SECRET+"&redirect_uri="+REDIRECT_URI;
        try {
            URL apiUrl = new URL(callUrl);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            connection.setDoOutput(true);

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//            outputStream.writeBytes(parameters);
            outputStream.flush();
            outputStream.close();

            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            System.out.println("Response Code: " + responseCode);
            System.out.println("Response Body: " + response.toString());
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(String.valueOf(response));
            if(jsonObject != null && jsonObject.containsKey("access_token")){
                String accessToken = (String) jsonObject.get("access_token");
                String scope = (String) jsonObject.get("scope");
                Long expiry = (Long) jsonObject.get("expires_in");
                LinkedInAccessToken linkedInAccessToken = linkedInAccessTokenRepository.findByUserId(linkedInState.getUserId());
                if(linkedInAccessToken == null) {
                    linkedInAccessToken = new LinkedInAccessToken();
                    linkedInAccessToken.setUserId(linkedInState.getUserId());
                }
                linkedInAccessToken.setAccessToken(accessToken);
                linkedInAccessToken.setScope(scope);
                linkedInAccessToken.setExpiresIn(expiry);
                linkedInAccessTokenRepository.save(linkedInAccessToken);
                linkedInStateRepository.delete(linkedInState);
                getUserProfile(linkedInAccessToken);

            }
        } catch (Exception e) {
            log.error("error in getAccessToken linkedin ",e);
        }
    }
    @Transactional
    private void getUserProfile(LinkedInAccessToken linkedInAccessToken) {
        try {
            URL apiUrl = new URL(LINKEDIN_USER_PROFILE);
            HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization","Bearer "+linkedInAccessToken.getAccessToken());
            connection.setDoOutput(true);

            int responseCode = connection.getResponseCode();

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            StringBuilder response = new StringBuilder();

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            System.out.println(responseCode);
            System.out.println(response);
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(String.valueOf(response));
            if(jsonObject.containsKey("id")){
                String linkedInId = (String) jsonObject.get("id");
                String firstName = jsonObject.containsKey("localizedFirstName") ? (String) jsonObject.get("localizedFirstName") : null;
                String lastName = jsonObject.containsKey("localizedLastName") ? (String) jsonObject.get("localizedLastName") : null;
                linkedInAccessToken.setUserUrn(linkedInId);
                linkedInAccessToken.setFirstName(firstName);
                linkedInAccessToken.setLastName(lastName);
                linkedInAccessToken.setUserResponse(response.toString());
                linkedInAccessTokenRepository.save(linkedInAccessToken);
            }
        } catch (Exception e) {
            log.error("error in getUserProfile ",e);
        }
    }

    @Override
    public void postToLinkedIn(Authentication auth, LinkedInPostRequestDto linkedInPostRequestDto) {
        User user = authValidator.authUser(auth);
        LinkedInAccessToken linkedInAccessToken = linkedInAccessTokenRepository.findByUserId(user.getId());
        if(linkedInAccessToken == null) return;
        linkedInPostRequestDto.setAuthor("urn:li:person:"+linkedInAccessToken.getUserUrn());
        prepareToPost(linkedInPostRequestDto,linkedInAccessToken.getAccessToken());

    }
    private void prepareToPost(LinkedInPostRequestDto linkedInPostRequestDto, String accessToken) {
        try{
            if(LinkedInMediaType.NONE.equals(linkedInPostRequestDto.getLinkedInMediaType())){
                LinkedInDTO linkedInDTO = new LinkedInDTO(linkedInPostRequestDto.getAuthor(), linkedInPostRequestDto.getCaption(), linkedInPostRequestDto.getLinkedInMediaType(), linkedInPostRequestDto.getLinkedInVisibilty());
//                String request = linkedInDTO.toJson();
                Gson gson = new Gson();
                String request = gson.toJson(linkedInDTO);
                request = request.replace("shareContent","com.linkedin.ugc.ShareContent");
                request = request.replace("networkVisibility","com.linkedin.ugc.MemberNetworkVisibility");
                System.out.println(request);
                String response = createHttpPostReq(LINKEDIN_TEXT_POST,request,accessToken);
                System.out.println(response);
            }
        } catch (Exception e) {
            log.error("error in prepare to post ",e);
        }

    }
    private String createHttpPostReq(String url, String requestStr, String accessToken) throws Exception{

        OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, requestStr);
        Request request = new Request.Builder()
                .url(url)
                .method("POST", body)
                .addHeader("Authorization", "Bearer "+accessToken)
                .addHeader("Content-Type", "application/json")
//                .addHeader("Cookie", "lidc=\"b=VB18:s=V:r=V:a=V:p=V:g=4592:u=1083:x=1:i=1687604792:t=1687632846:v=2:sig=AQEnoQMR7U9v4pI4Q23gnWZdEaUM4abj\"; bcookie=\"v=2&c827b49f-4452-4229-8010-8d41552e2722\"; lang=v=2&lang=en-us; lidc=\"b=VB19:s=V:r=V:a=V:p=V:g=4114:u=1:x=1:i=1687604282:t=1687690682:v=2:sig=AQEsF5bdzCngJu13HK5tu4H7onRZuM0P\"")
                .build();
        Response response = client.newCall(request).execute();
        System.out.println(response);
        return response.toString();

//        URL apiUrl = new URL(url);
//        HttpURLConnection connection = (HttpURLConnection) apiUrl.openConnection();
//        connection.setRequestMethod("POST");
//        connection.setRequestProperty("Authorization","Bearer "+ accessToken);
////        connection.setRequestProperty("Content-Length", String.valueOf(request.length()));
//        connection.setRequestProperty("Content-Type","application/json");
//        connection.setDoOutput(true);
//
//        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
//        outputStream.writeBytes(request);
//        outputStream.flush();
//        outputStream.close();
//
//        int responseCode = connection.getResponseCode();
//
//        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//        String line;
//        StringBuilder response = new StringBuilder();
//
//        while ((line = reader.readLine()) != null) {
//            response.append(line);
//        }
//        reader.close();
//
//        System.out.println("Response Code: " + responseCode);
//        System.out.println("Response Body: " + response.toString());
//        return response.toString();
    }
}
