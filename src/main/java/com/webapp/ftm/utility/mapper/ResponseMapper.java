package com.webapp.ftm.utility.mapper;


import org.springframework.stereotype.Component;

import java.util.List;

@Component
public interface ResponseMapper<T,S> {
    public T mapper (S entity);
    public default List<T> listMapper(List<S> entities) {
        return entities.stream().map((S entity) -> mapper(entity)).toList();
    }
}
