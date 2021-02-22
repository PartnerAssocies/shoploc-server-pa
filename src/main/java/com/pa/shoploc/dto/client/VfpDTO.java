package com.pa.shoploc.dto.client;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.Date;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class VfpDTO {

    Date beginDate;
    Date endDate;
    //si superieur à 0 dans ce cas là on est pas VFP
    int nbDaysBefore;
}
