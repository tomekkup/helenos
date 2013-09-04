/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.model.AbstractColumnQuery', {
    extend : helenos.model.AbstractQuery,
    type : 'abstract',
    
    construct : function()
    {
        this.base(arguments);
    },

    properties :
    {
        columnNames : {
            check : 'Array',
            nullable : true
        },
        
        nameStart : {},
        
        nameEnd : {},
        
        limit : {
            init : 10,
            check : 'Integer'
        },
        
        reversed : {
            init : false,
            check : 'Boolean'
        }
    }
});
