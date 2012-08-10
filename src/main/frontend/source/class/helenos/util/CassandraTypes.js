/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.util.CassandraTypes', {
        
    statics : {
        
        comparatorTypes : [
        {
            'label' : "Ascii", 
            'value' : "org.apache.cassandra.db.marshal.AsciiType"
        }, 
        {
            'label' : "Bytes", 
            'value' : "org.apache.cassandra.db.marshal.BytesType"
        },
        {
            'label' : "Integer", 
            'value' : "org.apache.cassandra.db.marshal.IntegerType"
        },
        {
            'label' : "Lexical UUID", 
            'value' : "org.apache.cassandra.db.marshal.LexicalUUIDType"
        },
        {
            'label' : "Local by partitioner", 
            'value' : "org.apache.cassandra.db.marshal.LocalByPartionerType"
        },
        {
            'label' : "Long", 
            'value' : "org.apache.cassandra.db.marshal.LongType"
        },
        {
            'label' : "Time UUID", 
            'value' : "org.apache.cassandra.db.marshal.TimeUUIDType"
        },
        {
            'label' : "UTF-8", 
            'value' : "org.apache.cassandra.db.marshal.UTF8Type"
        },
        {
            'label' : "Composite", 
            'value' : "org.apache.cassandra.db.marshal.CompositeType"
        },
        {
            'label' : "Dynamic composite", 
            'value' : "org.apache.cassandra.db.marshal.DynamicCompositeType"
        },
        {
            'label' : "UUID", 
            'value' : "org.apache.cassandra.db.marshal.UUIDType"
        },
        {
            'label' : "Counter column", 
            'value' : "org.apache.cassandra.db.marshal.CounterColumnType"
        }
        ],
        
        strategyClasses : [
            {
                'label' : "Simple", 
                'value' : "org.apache.cassandra.locator.SimpleStrategy"
            },
            {
                'label' : "Network topology", 
                'value' : "org.apache.cassandra.locator.NetworkTopologyStrategy"
            }
        ],
                   
        columnTypes : [
        {
            'label' : "Standard", 
            'value' : "Standard"
        }, 
        {
            'label' : "Super", 
            'value' : "Super"
        }
        ],
        
        validationClasses : [
        {
            'label' : "BytesType", 
            'value' : "BytesType"
        },
        {
            'label' : "AsciiType", 
            'value' : "AsciiType"
        },
        {
            'label' : "UTF8Type", 
            'value' : "UTF8Type"
        },
        {
            'label' : "LongType", 
            'value' : "LongType"
        },
        {
            'label' : "LexicalUUIDType", 
            'value' : "LexicalUUIDType"
        },
        {
            'label' : "TimeUUIDType", 
            'value' : "TimeUUIDType"
        }
        ]
    }
});
