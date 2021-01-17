package com.pa.shoploc.bo;

import lombok.Builder;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
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
