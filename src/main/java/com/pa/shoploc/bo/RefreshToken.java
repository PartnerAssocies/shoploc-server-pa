package com.pa.shoploc.bo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.Id;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter
@Entity
public class RefreshToken {
    @Id
    String refreshToken;
    String username;

}
