package com.brilworks.accounts.role_permissions.dto;

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
        dto.setType("SUCCESS");
        return dto;
    }

    public static ResponseDto update(String message) {
        ResponseDto dto = new ResponseDto();
        dto.setMessage(message);
        dto.setType("UPDATED");
        return dto;
    }

    public static ResponseDto delete(String message) {
        ResponseDto dto = new ResponseDto();
        dto.setMessage(message);
        dto.setType("DELETED");
        return dto;
    }

    public static ResponseDto success() {
        return success("");
    }

    public static ResponseDto saved() {
        return success("SAVED");
    }

    public static ResponseDto updated() {
        return update("UPDATED");
    }

    public static ResponseDto deleted() {
        return delete("DELETED");
    }
}
