package tomekkup.helenos.types.qx.query;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public interface SubQuery<SN> {
 
    Class<SN> getsNameClass();

    void setsNameClass(Class<SN> sNameClass);

    SN getsName();

    void setsName(String sName);
}
