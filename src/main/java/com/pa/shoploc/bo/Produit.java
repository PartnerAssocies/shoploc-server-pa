package com.pa.shoploc.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@SequenceGenerator(name = "seqProduit",initialValue = 1)
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seqProduit")
    private int pid;
    @Column
    private String libelle;
    @Column
    private float prix;
    @Column
    private int stock;
    @Column(name="fidelite_points_requis")
    private float fidelitePointsRequis;
    @Column
    private String image;
    @ManyToOne
    @JoinColumn(name="username")
    private Commercant cid;
    @JsonIgnore
    @OneToMany(mappedBy = "pid", cascade = CascadeType.REMOVE )
    private Set<Contient> lines = new HashSet<Contient>();

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getLibelle() {
        return libelle;
    }

    public void setLibelle(String libelle) {
        this.libelle = libelle;
    }

    public float getPrix() {
        return prix;
    }

    public void setPrix(float prix) {
        this.prix = prix;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public float getFidelitePointsRequis() {
        return fidelitePointsRequis;
    }

    public void setFidelitePointsRequis(float fidelitePointsRequis) {
        this.fidelitePointsRequis = fidelitePointsRequis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Commercant getCid() {
        return cid;
    }

    public void setCid(Commercant cid) {
        this.cid = cid;
    }
}
