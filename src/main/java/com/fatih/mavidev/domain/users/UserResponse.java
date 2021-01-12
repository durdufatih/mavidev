package com.fatih.mavidev.domain.users;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserResponse {

    private Integer id;
    private String name;
    private String surname;
    private String email;
    private Integer age;
}
