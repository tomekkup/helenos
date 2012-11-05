/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)

DEPRECATED !!!
 ************************************************************************ */
qx.Class.define("helenos.types.query.AbstractQuery",
{
        
    members : {
        keyspace : null,
        columnFamily : null,
        keyClass : null,
        nameClass : null
    },
   
    construct : function()
    {
        this.base(arguments);
    }
});
