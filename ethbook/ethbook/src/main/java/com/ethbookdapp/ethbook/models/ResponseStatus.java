package com.ethbookdapp.ethbook.models;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResponseStatus {
    private String message;
    private Integer statusCode;
    private String description; // in terms of authentication this field will contain jwt authentication token
}