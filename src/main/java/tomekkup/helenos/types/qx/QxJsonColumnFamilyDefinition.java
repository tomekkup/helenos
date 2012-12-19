package tomekkup.helenos.types.qx;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class QxJsonColumnFamilyDefinition {

    public QxJsonColumnFamilyDefinition() {
    }
    
    private String keyspaceName;
    private String name;
    private String columnType;
    private String comparatorType;
    private String subComparatorType;
    private String keyValidationclass;
    private String defaultValidationclass;
    private String comment;
    private int gcGraceSeconds;

    public int getGcGraceSeconds() {
        return gcGraceSeconds;
    }

    public void setGcGraceSeconds(int gcGraceSeconds) {
        this.gcGraceSeconds = gcGraceSeconds;
    }
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getComparatorType() {
        return comparatorType;
    }

    public void setComparatorType(String comparatorType) {
        this.comparatorType = comparatorType;
    }

    public String getSubComparatorType() {
        return subComparatorType;
    }

    public void setSubComparatorType(String subComparatorType) {
        this.subComparatorType = subComparatorType;
    }

    public String getKeyValidationclass() {
        return keyValidationclass;
    }

    public void setKeyValidationclass(String keyValidationclass) {
        this.keyValidationclass = keyValidationclass;
    }

    public String getDefaultValidationclass() {
        return defaultValidationclass;
    }

    public void setDefaultValidationclass(String defaultValidationclass) {
        this.defaultValidationclass = defaultValidationclass;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getKeyspaceName() {
        return keyspaceName;
    }

    public void setKeyspaceName(String keyspaceName) {
        this.keyspaceName = keyspaceName;
    }
}
