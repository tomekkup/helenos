package tomekkup.helenos.cassandra.model;

import me.prettyprint.cassandra.service.OperationType;
import me.prettyprint.hector.api.ConsistencyLevelPolicy;
import me.prettyprint.hector.api.HConsistencyLevel;

/**
 * ********************************************************
 * Copyright: 2012 Tomek Kuprowski
 *
 * License: GPLv2: http://www.gnu.org/licences/gpl.html
 *
 * @author Tomek Kuprowski (tomekkuprowski at gmail dot com)
 * *******************************************************
 */
public class AllConsistencyLevelPolicy implements ConsistencyLevelPolicy {

    private HConsistencyLevel level;

    private AllConsistencyLevelPolicy(HConsistencyLevel level) {
        this.level = level;
    }

    public static AllConsistencyLevelPolicy getInstance(HConsistencyLevel level) {
        return new AllConsistencyLevelPolicy(level);
    }

    @Override
    public HConsistencyLevel get(OperationType op) {
        return this.level;
    }

    @Override
    public HConsistencyLevel get(OperationType op, String cfName) {
        return this.level;
    }
}
