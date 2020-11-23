package com.pa.shoploc.dto.register;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;



@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RegisterClientRequestDTO extends RegisterUserRequestDTO{

    String nom;
    String prenom;

}
