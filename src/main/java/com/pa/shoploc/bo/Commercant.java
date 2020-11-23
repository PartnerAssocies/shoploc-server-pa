package com.pa.shoploc.bo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;

@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter @Setter
@Entity
public class Commercant extends User{
    String libelleMagasin;

}
