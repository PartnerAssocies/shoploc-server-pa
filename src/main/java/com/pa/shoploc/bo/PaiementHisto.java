package com.pa.shoploc.bo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pa.shoploc.enumeration.CommandeEtat;
import com.pa.shoploc.enumeration.Transaction;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@SequenceGenerator(name = "seqPaiementHisto",initialValue = 1)
public class PaiementHisto {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "seqPaiementHisto")
    int phid;
    @Column
    @NotNull
    Date date;
    @NotNull
    @Enumerated(EnumType.STRING)
    Transaction transaction;
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="username")
    Client user;

    float money;


}
