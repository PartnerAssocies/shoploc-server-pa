package com.pa.shoploc.bo;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;

@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
public class Client extends User{
    String nom;
    String prenom;
    double argent;
    @Column(columnDefinition="BOOLEAN DEFAULT false")
    boolean estVfp;
    int pointsFidelites;
}
