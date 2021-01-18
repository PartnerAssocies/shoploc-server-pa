package com.pa.shoploc.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pa.shoploc.enumeration.CommandeEtat;

import java.util.HashSet;
import java.util.List;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;


@Entity
@SequenceGenerator(name="seqCommande", initialValue=1)
public class Commande {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seqCommande")
    private int cid;

    @Column
    private Date date;

    @Column
    private double total;

    @NotNull
    @Enumerated(EnumType.STRING)
    private CommandeEtat etat;

    @Column
    @Min(value = 0)
    @Max(value = 5)
    private int note;

    @Column
    private boolean estPayeEnFidelite;

    @Column
    private boolean creeParClickAndCollect;

    @ManyToOne
    @JoinColumn(referencedColumnName="username")
    private Client client;

    @ManyToOne
    @JoinColumn(referencedColumnName="username")
    private Commercant commercant;


    @JsonIgnore
    @OneToMany(mappedBy = "cid", cascade = CascadeType.REMOVE )
    private Set<Contient> contenu=new HashSet<Contient>();


    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }



    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public CommandeEtat getEtat() {
        return etat;
    }

    public void setEtat(CommandeEtat etat) {
        this.etat = etat;
    }

    public int getNote() {
        return note;
    }

    public void setNote(int note) {
        this.note = note;
    }

    public boolean isEstPayeEnFidelite() {
        return estPayeEnFidelite;
    }

    public void setEstPayeEnFidelite(boolean estPayeEnFidelite) {
        this.estPayeEnFidelite = estPayeEnFidelite;
    }

    public boolean isCreeParClickAndCollect() {
        return creeParClickAndCollect;
    }

    public void setCreeParClickAndCollect(boolean creeParClickAndCollect) {
        this.creeParClickAndCollect = creeParClickAndCollect;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Commercant getCommercant() {
        return commercant;
    }

    public void setCommercant(Commercant commercant) {
        this.commercant = commercant;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Set<Contient> getContenu() {
        return contenu;
    }

    public void setContenu(Set<Contient> contenu) {
        this.contenu = contenu;
    }
}
