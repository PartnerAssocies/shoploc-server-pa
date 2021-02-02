package com.pa.shoploc.bo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@EqualsAndHashCode
@Getter
@Setter
public class ContientPk implements Serializable {

    public ContientPk() {
    }

    public ContientPk(int pid, int cid) {
        this.pid = pid;
        this.cid = cid;
    }

    private int pid;
    private int cid;



}
