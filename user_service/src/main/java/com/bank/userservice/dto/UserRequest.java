package com.bank.userservice.dto;

import lombok.*;

@Getter
@Setter
public class UserRequest {

    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private String address;

}
