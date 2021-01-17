package com.pa.shoploc.bo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;

@Getter @Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SequenceGenerator(initialValue = 1,name = "seqLieu")
public class Lieu {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seqLieu")
    int lid;
    String adresse;
    String ville;
    double coordx;
    double coordy;

}
