package com.pa.shoploc.dto.produit;

public class ProduitDTO {

    private String libelle;
    private float prix;
    private int stock;
    private int fidelitePointsRequis;
    private String image;
    private String commercantId;


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

    public int getFidelitePointsRequis() {
        return fidelitePointsRequis;
    }

    public void setFidelitePointsRequis(int fidelitePointsRequis) {
        this.fidelitePointsRequis = fidelitePointsRequis;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCommercantId() {
        return commercantId;
    }

    public void setCommercantId(String commercantId) {
        this.commercantId = commercantId;
    }
}
