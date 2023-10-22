package com.codelogic.cityconnect.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.gson.Gson;
import jakarta.validation.ValidationException;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Data
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomException {

    private String timestamp;
    private int status;
    private String message;
    private List<ValidationException> erros;

    public CustomException(int status, List<ValidationException> erros){
        this.timestamp = createTimestamp();
        this.status = status;
        this.erros = erros;
    }

    public CustomException(String message, int status){
        this.timestamp = createTimestamp();
        this.message = message;
        this.status = status;
    }

    public String createTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSZZ");
        String date = formatter.format(LocalDateTime.now().atZone(ZoneOffset.UTC));
        return date.replace("+0000", "+00:00");
    }

    public String toJson() {
        Gson json = new Gson();
        return json.toJson(this);
    }
}
