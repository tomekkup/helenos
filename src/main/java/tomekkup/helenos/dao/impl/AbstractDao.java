package tomekkup.helenos.dao.impl;

import java.io.Serializable;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tomekkup.helenos.dao.EntityDao;
import tomekkup.helenos.dao.model.AbstractEntity;
import tomekkup.helenos.dao.model.ClusterConfiguration;
import tomekkup.helenos.dao.model.Identifiable;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
@Transactional(propagation = Propagation.SUPPORTS)
public abstract class AbstractDao<T extends Identifiable<PK>, PK extends Serializable> extends HibernateDaoSupport implements EntityDao<T, PK> {
    
    private final Class<T> type;

    public AbstractDao(Class<T> type) {
        this.type = type;
    }
    
    protected Session openSession() {
        return getSessionFactory().getCurrentSession();
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void delete(final T ent) {
        delete(openSession(), ent);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void delete(Session session, final T ent) {
        session.delete(ent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void persist(final T ent) {
        persist(openSession(), ent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void persist(Session session, final T ent) {
        session.persist(ent);
    }

    @Override
    public T load(final PK id) {
        return load(openSession(), id);
    }

    @Override
    public final T load(Session session, final PK id) {
        return (T) session.load(type, id);
    }
    
    @Override
    public final T get(final PK id) {
        return get(openSession(), id);
    }
    
    @Override
    public final T get(Session session, final PK id) {
        return (T) session.get(type, id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void update(final T ent) {
        update(openSession(), ent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void update(Session session, final T ent) {
        session.update(ent);
    }
    
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void saveOrUpdate(final T ent) {
        saveOrUpdate(openSession(), ent);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public final void saveOrUpdate(Session session, final T ent) {
        session.saveOrUpdate(ent);
    }
    
    @Override
    public long getCount() {
        Session session = openSession();
        return ((Number) session.createCriteria(type).setProjection(Projections.rowCount()).uniqueResult()).longValue();
    }
    
    @Override
    public List<T> loadAll() {
        return getHibernateTemplate().loadAll(type);
    }
}