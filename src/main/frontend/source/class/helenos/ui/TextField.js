/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.TextField",
{
    extend : qx.ui.form.TextField,
    
    statics : {
        classDefs : {
            'org.apache.cassandra.db.marshal.BytesType' : {
                placeholder : 'bytes',
                width : 190
            },
            'org.apache.cassandra.db.marshal.AsciiType' : {
                placeholder : 'ascii',
                width : 190
            },
            'org.apache.cassandra.db.marshal.UTF8Type' : {
                placeholder : 'utf-8',
                width : 190
            },
            'org.apache.cassandra.db.marshal.LongType' : {
                placeholder : 'long',
                width : 120,
                filter : /[0-9]/
            },
            'org.apache.cassandra.db.marshal.LexicalUUIDType' : {
                placeholder : 'lexical uuid',
                maxLength : 36,
                width : 260,
                filter : /[a-fA-F0-9\-]/
            },
            'org.apache.cassandra.db.marshal.TimeUUIDType' : {
                placeholder : 'time uuid',
                maxLength : 36,
                width : 260,
                filter : /[a-fA-F0-9\-]/
            }
        }
    },

    construct : function(clazz)
    {
        this.base(arguments);
        
        var cd = this.self(arguments).classDefs[clazz];
        if (cd != undefined) {
            this.set(cd );
        } else {
            if (clazz.substring(0,45) == 'org.apache.cassandra.db.marshal.CompositeType') {
                this.error('Composite type not supported yet');
            } else {
                this.error('type not known')
            }
            
        }
    }
});
