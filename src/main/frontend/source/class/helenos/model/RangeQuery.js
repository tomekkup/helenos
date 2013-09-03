/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.model.RangeQuery', {
    extend : helenos.model.AbstractColumnQuery,
    
    construct : function(cfDef, consistencyLevel)
    {
        this.base(arguments, cfDef, consistencyLevel);
    },

    properties :
    {
        keyFrom : { },
        
        keyTo : { },
        
        rowCount : {
            check : 'Integer',
            init : 10
        }
    }
});
