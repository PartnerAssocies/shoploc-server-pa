package com.pa.shoploc.bo;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;


@Entity
public class Commercant extends User {

    @NotNull
    @Column
    private String libelleMagasin;
    @Column
    private String image;
    @Column
    private String description;
    @Column
    private String siret;

    public String getLibelleMagasin() {
        return libelleMagasin;
    }

    public void setLibelleMagasin(String libelleMagasin) {
        this.libelleMagasin = libelleMagasin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getSiret() {
        return siret;
    }

    public void setSiret(String siret) {
        this.siret = siret;
    }
}
