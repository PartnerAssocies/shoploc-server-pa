package com.pa.shoploc.service;

import java.util.List;

public interface DtoService<T,R>{

     R toDTO(T t);
     List<R> toDTOList(List<T> list);


}
