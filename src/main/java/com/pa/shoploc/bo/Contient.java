package com.pa.shoploc.bo;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;

@Entity
@FieldDefaults(level = AccessLevel.PRIVATE)
@IdClass(value=ContientPk.class)
public class Contient {

    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(unique=false,name = "pid")
    private Produit pid;


    @Id
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(unique=false,name = "cid")
    private Commande cid;

    @Column
    private int quantite;

    public Produit getPid() {
        return pid;
    }

    public void setPid(Produit pid) {
        this.pid = pid;
    }

    public Commande getCid() {
        return cid;
    }

    public void setCid(Commande cid) {
        this.cid = cid;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }
}
