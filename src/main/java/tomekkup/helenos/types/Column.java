package tomekkup.helenos.types;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class Column<N> {

    private N name;
    private String value;
    private long clock;
    private int ttl;

    public Column() {
    }

    public N getName() {
        return name;
    }

    public void setName(N name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getClock() {
        return clock;
    }

    public void setClock(long clock) {
        this.clock = clock;
    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
    }
}
