/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)

DEPRECATED !!!
 ************************************************************************ */
qx.Class.define("helenos.types.query.SingleColumnQuery",
{
    extend : helenos.types.query.AbstractQuery,
    
    members : {
    
        key : null,
        name : null
    },
 
    construct : function()
    {
        this.base(arguments);
    }
});
