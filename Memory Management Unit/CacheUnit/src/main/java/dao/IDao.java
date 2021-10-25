package dao;

import dm.DataModel;

public interface IDao <ID extends java.io.Serializable,T>{

    void delete(T t);

    void save(T entity);

    T find(Long id);


}

