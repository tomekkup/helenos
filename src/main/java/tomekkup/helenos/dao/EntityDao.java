package tomekkup.helenos.dao;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import tomekkup.helenos.dao.model.Identifiable;

/**
 * ********************************************************
 * Copyright: 2015 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface EntityDao<T extends Identifiable<PK>, PK extends Serializable> {
    
    public void delete(final T ent);
    
    public void delete(Session session,final  T ent);
    
    public long getCount();
    
    public List<T> loadAll();
    
    public T load(final PK id);
    
    public T load(Session session,final  PK id);
    
    public T get(final PK id);
    
    public T get(Session session,final  PK id);
    
    public void persist(final T ent);
    
    public void persist(Session session,final  T ent);
    
    public void update(final T ent);
    
    public void update(Session session,final  T ent);
    
    public void saveOrUpdate(final T ent);
    
    public void saveOrUpdate(Session session,final  T ent);
    
    
}