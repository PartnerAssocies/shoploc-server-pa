package com.pa.shoploc.bo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.Constraint;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@Getter
@Setter
@IdClass(value=ContientPk.class)
public class Contient {

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(unique=false,name = "pid")
    Produit pid;


    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(unique=false,name = "cid")
    Commande cid;

    @Column
    int quantite=0;

    @Column
    int nbProduitsEnFidelite=0;


}
