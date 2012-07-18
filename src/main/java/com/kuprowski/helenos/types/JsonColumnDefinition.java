/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kuprowski.helenos.types;

import java.nio.ByteBuffer;
import me.prettyprint.hector.api.ddl.ColumnIndexType;

/**
 *
 * @author tomek
 */
public class JsonColumnDefinition {

    public JsonColumnDefinition() {
    }
    private String name;
    private String validationClass;
    private ColumnIndexType indexType;
    private String indexName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? name : name;
    }

    public String getValidationClass() {
        return validationClass;
    }

    public void setValidationClass(String validationClass) {
        this.validationClass = validationClass;
    }

    public ColumnIndexType getIndexType() {
        return indexType;
    }

    public void setIndexType(ColumnIndexType indexType) {
        this.indexType = indexType;
    }

    public String getIndexName() {
        return indexName;
    }

    public void setIndexName(String indexName) {
        this.indexName = indexName;
    }
}
