package tomekkup.helenos.dao.model;

import java.io.Serializable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * ********************************************************
 * Copyright: 2015 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
@MappedSuperclass
public abstract class AbstractEntity<PK extends Serializable> implements Identifiable<PK> {
    
    private PK id;

    @Id 
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    public PK getId() {
        return id;
    }

    public void setId(PK id) {
        this.id = id;
    }
}

