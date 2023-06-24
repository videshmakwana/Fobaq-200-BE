package com.brilworks.accounts.dto;


import com.brilworks.accounts.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userName;
    private String password;
    private String firstName;
    private String lastName;
    private String nickName;
    private String gender;
    private String email;
    private String profileImage;
    private String token;
    private Date timeZone;
    private Long roleId;


    public UserDto entityToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUserName(user.getUserName());
        userDto.setPassword(user.getPassword());
        userDto.setNickName(user.getNickName());
        userDto.setGender(user.getGender());
        userDto.setEmail(user.getEmail());
        userDto.setProfileImage(user.getProfileImage());
        userDto.setToken(user.getToken());
        userDto.setTimeZone(user.getTimeZone());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());

        return userDto;
    }

    public User dtoToEntity(UserDto userDto) {
        User user = new User();
        user.setId(userDto.getId());
        user.setUserName(userDto.getUserName());
        user.setPassword(userDto.getPassword());
        user.setNickName(userDto.getNickName());
        user.setGender(userDto.getGender());
        user.setEmail(userDto.getEmail());
        user.setProfileImage(userDto.getProfileImage());
        user.setToken(userDto.getToken());
        user.setTimeZone(userDto.getTimeZone());
        return user;
    }

    public UserDto(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.nickName = user.getNickName();
        this.gender = user.getGender();
        this.email = user.getEmail();
    }
}
