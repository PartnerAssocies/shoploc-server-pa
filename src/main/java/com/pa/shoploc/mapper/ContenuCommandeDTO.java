package com.pa.shoploc.mapper;

import java.util.List;

public class ContenuCommandeDTO {
    private List<ProduitCommandeDTO> produits;
    private int cid;

    public List<ProduitCommandeDTO> getProduits() {
        return produits;
    }

    public void setProduits(List<ProduitCommandeDTO> produits) {
        this.produits = produits;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public static class ProduitCommandeDTO {

        private int pid;
        private String libelle;
        private int quantite;
        private String image;
        private double prix;
        private float fidelitePointsRequis;

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

        public int getQuantite() {
            return quantite;
        }

        public void setQuantite(int quantite) {
            this.quantite = quantite;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public float getFidelitePointsRequis() {
            return fidelitePointsRequis;
        }

        public void setFidelitePointsRequis(float fidelitePointsRequis) {
            this.fidelitePointsRequis = fidelitePointsRequis;
        }

        public double getPrix() {
            return prix;
        }

        public void setPrix(double prix) {
            this.prix = prix;
        }
    }
}

