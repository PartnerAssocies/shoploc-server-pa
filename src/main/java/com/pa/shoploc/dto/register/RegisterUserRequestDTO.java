package com.pa.shoploc.dto.register;

import com.pa.shoploc.enumeration.Role;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class RegisterUserRequestDTO {
    String username;
    String password;
    Role role;
    int lieu;
}
