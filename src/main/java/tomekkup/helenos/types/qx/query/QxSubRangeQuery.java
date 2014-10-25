package tomekkup.helenos.types.qx.query;

import tomekkup.helenos.utils.Converter;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class QxSubRangeQuery<K, SN, N, V> extends QxRangeQuery<K, N, V> implements SubQuery<SN> {

    private Class<SN> sNameClass;
    private String sName;

    public QxSubRangeQuery() {
        super();
    }

    @Override
    public Class<SN> getsNameClass() {
        return sNameClass;
    }

    @Override
    public void setsNameClass(Class<SN> sNameClass) {
        this.sNameClass = sNameClass;
    }

    @Override
    public SN getsName() {
        return Converter.toValue(sName, sNameClass);
    }

    @Override
    public void setsName(String sName) {
        this.sName = sName;
    }
}
