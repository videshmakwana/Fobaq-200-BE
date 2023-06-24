package com.brilworks.accounts.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LinkedInDTO {
    @JsonProperty("author")
    private String author;
    private String lifecycleState = "PUBLISHED";
    private SpecificContent specificContent;
    private Visibility visibility;

    public LinkedInDTO(String author, String caption, LinkedInMediaType linkedInMediaType, LinkedInVisibilty linkedInVisibilty) {
        this.author = author;
        this.specificContent = new SpecificContent(caption,linkedInMediaType);
        this.visibility = new Visibility(linkedInVisibilty);
    }

    class SpecificContent{
        @JsonProperty(value="com.linkedin.ugc.ShareContent")
        private ShareContent shareContent;


        public SpecificContent(String caption, LinkedInMediaType shareMediaCategory) {
            this.shareContent = new ShareContent(caption,shareMediaCategory);

        }
    }
    class Visibility{
        @JsonProperty("com.linkedin.ugc.MemberNetworkVisibility")
        private LinkedInVisibilty networkVisibility;

        public Visibility(LinkedInVisibilty networkVisibility) {
            this.networkVisibility = networkVisibility;
        }
    }
    class ShareContent{
        private ShareCommentary shareCommentary;
        private LinkedInMediaType shareMediaCategory;

        public ShareContent(String caption, LinkedInMediaType shareMediaCategory) {
            this.shareCommentary = new ShareCommentary(caption);
            this.shareMediaCategory = shareMediaCategory;
        }
    }
    class ShareCommentary{
        private String text;

        public ShareCommentary(String text) {
            this.text = text;
        }
    }
    public String toJson() throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.WRAP_ROOT_VALUE);

        return mapper.writeValueAsString(this);
    }
}
