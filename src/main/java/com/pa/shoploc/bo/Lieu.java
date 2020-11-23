package com.pa.shoploc.bo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Lieu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int lid;
    String adresse;
    String ville;
    double coordx;
    double coordy;

}
