package com.ethbookdapp.ethbook.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FormData {
    private String name;
    private String email;
    private String userId;
    private String password;
}