/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/Tango/16/status/dialog-information.png)
#asset(qx/icon/Tango/64/status/dialog-information.png)
*/
qx.Class.define("helenos.components.tab.KeyspaceInfoPage",
{
    extend : helenos.components.tab.AbstractCloseablePage,

    /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */
 
    construct : function(ksDef)
    {
        this.base(arguments);
        set({
            title: ksDef.name,
            icon: 'qx/icon/Tango/16/status/dialog-information.png',
            layout: new qx.ui.layout.VBox()
        });
        
        this.__showInformation(ksDef);
    },

    members :
    {
        __showInformation : function(ksDef) {
            this.__addNameAtom(ksDef);
            this.__addBasicInfoGroup(ksDef);
        },
        
        __addNameAtom : function(ksDef) {
            var atom = new qx.ui.basic.Atom(ksDef.name, "qx/icon/Tango/64/status/dialog-information.png");
            atom.setFont(new qx.bom.Font(22, ["OpenSansRegular", "Helvetica Neue", "Helvetica", "Arial", "sans-serif"]));
            this.add(atom);
        },
        
        __addBasicInfoGroup : function(ksDef) {
            var gb = new qx.ui.groupbox.GroupBox('Info');
            gb.setLayout(new qx.ui.layout.VBox());
            
            var a1 = new helenos.ui.RichAtom('Strategy class: <b>' + ksDef.strategyClass + '</b>');
            var a2 = new helenos.ui.RichAtom('Replication factor: <b>' + ksDef.replicationFactor + '</b>');
            
            gb.add(a1);
            gb.add(a2);
            this.add(gb);
        }
        
    }
});
