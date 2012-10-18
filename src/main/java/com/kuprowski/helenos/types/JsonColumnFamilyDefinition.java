package com.kuprowski.helenos.types;

import java.util.List;
import java.util.Map;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class JsonColumnFamilyDefinition {

    public JsonColumnFamilyDefinition() {
    }
    private String name;
    private String keyspaceName;
    private String comment;
    private String columnType;
    private JsonComparatorType comparatorType;
    private JsonComparatorType subComparatorType;
    private double rowCacheSize;
    private int rowCacheSavePeriodInSeconds;
    private double keyCacheSize;
    private double readRepairChance;
    private List<JsonColumnDefinition> columnMetadata;
    private int gcGraceSeconds;
    private int keyCacheSavePeriodInSeconds;
    private String rowCacheProvider;
    private int rowCacheKeysToSave;
    private String keyValidationClass;
    private String defaultValidationClass;
    private int id;
    private int maxCompactionThreshold;
    private int minCompactionThreshold;
    private double memtableOperationsInMillions;
    private int memtableThroughputInMb;
    private int memtableFlushAfterMins;
    private boolean replicateOnWrite;
    private String compactionStrategy;
    private Map<String, String> compactionStrategyOptions;
    private Map<String, String> compressionOptions;
    private double mergeShardsChance;
    private String keyAlias;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setKeyAlias(String keyAlias) {
        this.keyAlias = keyAlias;
    }

    public String getKeyAlias() {
        return keyAlias;
    }
    
    public String getKeyspaceName() {
        return keyspaceName;
    }

    public void setKeyspaceName(String keyspaceName) {
        this.keyspaceName = keyspaceName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setColumnType(String columnType) {
        this.columnType = columnType;
    }

    public String getColumnType() {
        return columnType;
    }

    public void setComparatorType(JsonComparatorType comparatorType) {
        this.comparatorType = comparatorType;
    }

    public JsonComparatorType getComparatorType() {
        return comparatorType;
    }

    public void setSubComparatorType(JsonComparatorType subComparatorType) {
        this.subComparatorType = subComparatorType;
    }

    public JsonComparatorType getSubComparatorType() {
        return subComparatorType;
    }

    public double getRowCacheSize() {
        return rowCacheSize;
    }

    public void setRowCacheSize(double rowCacheSize) {
        this.rowCacheSize = rowCacheSize;
    }

    public int getRowCacheSavePeriodInSeconds() {
        return rowCacheSavePeriodInSeconds;
    }

    public void setRowCacheSavePeriodInSeconds(int rowCacheSavePeriodInSeconds) {
        this.rowCacheSavePeriodInSeconds = rowCacheSavePeriodInSeconds;
    }

    public double getKeyCacheSize() {
        return keyCacheSize;
    }

    public void setKeyCacheSize(double keyCacheSize) {
        this.keyCacheSize = keyCacheSize;
    }

    public double getReadRepairChance() {
        return readRepairChance;
    }

    public void setReadRepairChance(double readRepairChance) {
        this.readRepairChance = readRepairChance;
    }

    public List<JsonColumnDefinition> getColumnMetadata() {
        return columnMetadata;
    }

    public void setColumnMetadata(List<JsonColumnDefinition> columnMetadata) {
        this.columnMetadata = columnMetadata;
    }

    public int getGcGraceSeconds() {
        return gcGraceSeconds;
    }

    public void setGcGraceSeconds(int gcGraceSeconds) {
        this.gcGraceSeconds = gcGraceSeconds;
    }

    public String getKeyValidationClass() {
        return keyValidationClass;
    }

    public void setKeyValidationClass(String keyValidationClass) {
        this.keyValidationClass = keyValidationClass;
    }

    public String getDefaultValidationClass() {
        return defaultValidationClass;
    }

    public void setDefaultValidationClass(String defaultValidationClass) {
        this.defaultValidationClass = defaultValidationClass;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaxCompactionThreshold() {
        return maxCompactionThreshold;
    }

    public void setMaxCompactionThreshold(int maxCompactionThreshold) {
        this.maxCompactionThreshold = maxCompactionThreshold;
    }

    public int getMinCompactionThreshold() {
        return minCompactionThreshold;
    }

    public void setMinCompactionThreshold(int minCompactionThreshold) {
        this.minCompactionThreshold = minCompactionThreshold;
    }

    public double getMemtableOperationsInMillions() {
        return memtableOperationsInMillions;
    }

    public void setMemtableOperationsInMillions(double memtableOperationsInMillions) {
        this.memtableOperationsInMillions = memtableOperationsInMillions;
    }

    public int getMemtableThroughputInMb() {
        return memtableThroughputInMb;
    }

    public void setMemtableThroughputInMb(int memtableThroughputInMb) {
        this.memtableThroughputInMb = memtableThroughputInMb;
    }

    public int getMemtableFlushAfterMins() {
        return memtableFlushAfterMins;
    }

    public void setMemtableFlushAfterMins(int memtableFlushAfterMins) {
        this.memtableFlushAfterMins = memtableFlushAfterMins;
    }

    public int getKeyCacheSavePeriodInSeconds() {
        return keyCacheSavePeriodInSeconds;
    }

    public void setKeyCacheSavePeriodInSeconds(int keyCacheSavePeriodInSeconds) {
        this.keyCacheSavePeriodInSeconds = keyCacheSavePeriodInSeconds;
    }

    public boolean isReplicateOnWrite() {
        return replicateOnWrite;
    }

    public void setReplicateOnWrite(boolean replicateOnWrite) {
        this.replicateOnWrite = replicateOnWrite;
    }

    public String getCompactionStrategy() {
        return compactionStrategy;
    }

    public void setCompactionStrategy(String compactionStrategy) {
        this.compactionStrategy = compactionStrategy;
    }

    public Map<String, String> getCompactionStrategyOptions() {
        return compactionStrategyOptions;
    }

    public void setCompactionStrategyOptions(Map<String, String> compactionStrategyOptions) {
        this.compactionStrategyOptions = compactionStrategyOptions;
    }

    public Map<String, String> getCompressionOptions() {
        return compressionOptions;
    }

    public void setCompressionOptions(Map<String, String> compressionOptions) {
        this.compressionOptions = compressionOptions;
    }

    public double getMergeShardsChance() {
        return mergeShardsChance;
    }

    public void setMergeShardsChance(double mergeShardsChance) {
        this.mergeShardsChance = mergeShardsChance;
    }

    public String getRowCacheProvider() {
        return rowCacheProvider;
    }

    public void setRowCacheProvider(String rowCacheProvider) {
        this.rowCacheProvider = rowCacheProvider;
    }

    public int getRowCacheKeysToSave() {
        return rowCacheKeysToSave;
    }

    public void setRowCacheKeysToSave(int rowCacheKeysToSave) {
        this.rowCacheKeysToSave = rowCacheKeysToSave;
    }
}
