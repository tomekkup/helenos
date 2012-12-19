package tomekkup.helenos.types.qx.query;

import java.util.List;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class SubRangeQuery<K,SN,N> extends RangeQuery<K, N> {

    private Class<SN> sNameClass;
    private String sName;
    

    public SubRangeQuery() {
        super();
    }

    public SubRangeQuery(Class<K> keyClass, Class<SN> sNameClass, Class<N> nameClass, String keyspace, String columnFamily, String keyFrom, String keyTo, List<String> columnNames, String sName, String nameStart, String nameEnd) {
        super(keyClass, nameClass, keyspace, columnFamily, keyFrom, keyTo, columnNames, nameStart, nameEnd);
        setsNameClass(sNameClass);
        setsName(sName);;
    }

    public Class<SN> getsNameClass() {
        return sNameClass;
    }

    public void setsNameClass(Class<SN> sNameClass) {
        this.sNameClass = sNameClass;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    
}
