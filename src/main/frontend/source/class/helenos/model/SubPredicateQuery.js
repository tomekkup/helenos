/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define('helenos.model.SubPredicateQuery', {
    extend : helenos.model.PredicateQuery,
    include : [helenos.model.MSubQuery],
    
    construct : function()
    {
        this.base(arguments);
    },
    
    members : {
        prepareQuery : function(cfDef, consistencyLevel) {
            this.base(arguments, cfDef, consistencyLevel);
            this.setSNameClass(this._findParamClass(cfDef.subComparatorType.className));
        }
    }
});
