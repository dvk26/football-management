package com.webapp.ftm.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatusCode;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestResponse<K>  {
    private int statusCode;
    private String error;

    // message có thể là string, hoặc arrayList
    private Object message;
    private K data;
}
