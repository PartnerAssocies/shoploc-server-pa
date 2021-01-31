package com.pa.shoploc.dto.commande;

import com.pa.shoploc.enumeration.CommandeEtat;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CommandeDTO {
    int cid;
    Date date;
    double total;
    CommandeEtat etat;
    int note;
    boolean estPayeEnFidelite;
    boolean creeParClickAndCollect;
    String client;
    String commercant;


}
