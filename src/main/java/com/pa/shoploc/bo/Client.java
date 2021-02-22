package com.pa.shoploc.bo;



import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import java.util.Date;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Client extends User {

    private String nom;
    private String prenom;
    private double argent;
    @Column(columnDefinition = "integer DEFAULT 5")
    private int dayBeforeVfp;
    private Date vfpBeginDate;
    private Date vfpEndDate;
    private int pointsFidelites;


}
