
package com.service1.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;

/**
 *
 * @author Ravindra
 * @param <T>
 */
public class BaseDAOImpl<T,ID extends Serializable> {
    
    public Class<T> persistentClass;
    
    public BaseDAOImpl() {
        this.persistentClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }
    
}
