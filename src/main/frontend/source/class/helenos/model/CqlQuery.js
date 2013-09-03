/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.model.CqlQuery', {
    extend : helenos.model.AbstractQuery,
    
    construct : function(cfDef, consistencyLevel)
    {
        this.base(arguments, cfDef, consistencyLevel);
    },

    properties :
    {
        query : {
            check : 'String'
        }
    }
});
