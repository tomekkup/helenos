/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.model.AbstractQuery', {
    extend : qx.core.Object,
    type : 'abstract',
    
    construct : function()
    {
        this.base(arguments);
    },

    properties :
    {
        consistencyLevel : {
            nullable : false, 
            init : 'ALL',
            check : 'String'
        },
        
        keyspace : {
            check : 'String',
            nullable : false,
            init : 'undefined'
        },
        
        columnFamily : {
            check : 'String',
            nullable : false,
            init : 'undefined'
        },
        
        keyClass : {
            check : 'String',
            nullable : false,
            init : 'undefined'
        },
        
        nameClass : {
            check : 'String',
            nullable : false,
            init : 'undefined'
        },
        
        valueClass : {
            check : 'String',
            nullable : false,
            init : 'undefined'
        }
    },
    
    members : {
        prepareQuery : function(cfDef, consistencyLevel) {
            this.setKeyClass(this._findParamClass(cfDef.keyValidationClass));
            if (cfDef.columnType == 'Standard') {
                this.setNameClass(this._findParamClass(cfDef.comparatorType.className));
            } else {
                this.setNameClass(this._findParamClass(cfDef.subComparatorType.className));
            }
            this.setValueClass(this._findParamClass(cfDef.defaultValidationClass));
            this.setKeyspace(cfDef.keyspaceName);
            this.setColumnFamily(cfDef.name);
            this.setConsistencyLevel(consistencyLevel);
        },
        
        _findParamClass : function(className) {
        switch (className)
            {
                case 'org.apache.cassandra.db.marshal.BytesType':
                case 'org.apache.cassandra.db.marshal.AsciiType':
                case 'org.apache.cassandra.db.marshal.UTF8Type':
                    return 'java.lang.String';
                    break;
                case 'org.apache.cassandra.db.marshal.LongType':
                case 'org.apache.cassandra.db.marshal.CounterColumnType':
                    return 'java.lang.Long';
                    break;
                case 'org.apache.cassandra.db.marshal.LexicalUUIDType':
                case 'org.apache.cassandra.db.marshal.TimeUUIDType':
                    return 'java.util.UUID';
                    break;
                default:
                    this.error('unknown class: ' + className);
                    return null;
                    break;
            }
        }
    }
});
