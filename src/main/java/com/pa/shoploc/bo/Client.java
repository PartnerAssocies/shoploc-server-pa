package com.pa.shoploc.bo;


import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Client extends User {

    private String nom;
    private String prenom;
    private double argent;
    @Column(columnDefinition = "BOOLEAN DEFAULT false")
    private boolean estVfp;
    private int pointsFidelites;

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public double getArgent() {
        return argent;
    }

    public void setArgent(double argent) {
        this.argent = argent;
    }

    public boolean isEstVfp() {
        return estVfp;
    }

    public void setEstVfp(boolean estVfp) {
        this.estVfp = estVfp;
    }

    public int getPointsFidelites() {
        return pointsFidelites;
    }

    public void setPointsFidelites(int pointsFidelites) {
        this.pointsFidelites = pointsFidelites;
    }
}
