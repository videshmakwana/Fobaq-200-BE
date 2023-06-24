package com.brilworks.accounts.dto;

import com.brilworks.accounts.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResponseDto {

    private String type;

    private String message;

    public ResponseDto(String type, String message) {
        this.message = message;
        this.type = type;
    }

    public static ResponseDto of(String type, String message) {
        ResponseDto dto = new ResponseDto();
        dto.setMessage(message);
        dto.setType(type);
        return dto;
    }

    public static ResponseDto success(String message) {
        ResponseDto dto = new ResponseDto();
        dto.setMessage(message);
        dto.setType(Constants.SUCCESS);
        return dto;
    }

    public static ResponseDto update(String message) {
        ResponseDto dto = new ResponseDto();
        dto.setMessage(message);
        dto.setType(Constants.UPDATED);
        return dto;
    }

    public static ResponseDto delete(String message) {
        ResponseDto dto = new ResponseDto();
        dto.setMessage(message);
        dto.setType(Constants.DELETED);
        return dto;
    }

    public static ResponseDto success() {
        return success("");
    }

    public static ResponseDto saved() {
        return success(Constants.SAVED);
    }

    public static ResponseDto updated() {
        return update(Constants.UPDATED);
    }

    public static ResponseDto deleted() {
        return delete(Constants.DELETED);
    }
}
