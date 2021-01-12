package com.fatih.mavidev.domain.users;

import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;


@Getter
@Setter
public class CreateUserRequest {

    @NonNull
    private String name;

    @NonNull
    private String surname;

    @NonNull
    private String email;

    private Integer age;

    public UserEntity toEntity() {
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(this.age);
        userEntity.setEmail(this.email);
        userEntity.setName(this.name);
        userEntity.setSurname(this.surname);
        return userEntity;
    }

}
