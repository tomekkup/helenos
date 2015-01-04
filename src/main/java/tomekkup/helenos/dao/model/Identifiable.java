package tomekkup.helenos.dao.model;

import java.io.Serializable;

/**
 * ********************************************************
 * Copyright: 2015 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface Identifiable<PK extends Serializable> {
    
    public PK getId();
    
    public void setId(PK id);
}
