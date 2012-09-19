/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(qx/icon/${qx.icontheme}/16/devices/network-wired.png)
*/
qx.Class.define("helenos.components.Header",
{
    extend : qx.ui.container.Composite,

    construct : function()
    {
        this.base(arguments);
        this.set({
            layout : new qx.ui.layout.HBox().set({
                alignY : "middle"
            }),
            
            appearance : 'app-header-black'
        });
        this.add(this.__getLogo());
        this.add(this.__getIconsBox(), {flex: 1});
    },
    
    members : {
        __getLogo : function() {
            return new qx.ui.basic.Image('helenos/logo.png');
        },
        
        __getIconsBox : function() {
            var boxLayout = new qx.ui.layout.HBox(5);
            boxLayout.setAlignX('right');
            var box = new qx.ui.container.Composite(boxLayout);
            
            var editClusterConfigButton = new qx.ui.form.Button(null, 'icon/16/devices/network-wired.png');
            editClusterConfigButton.setPadding(1);
            editClusterConfigButton.setToolTipText('Manage connections');
            editClusterConfigButton.addListener('execute', this.__onSomething);
            
            box.add(editClusterConfigButton);
            return box;
        },
        
        __onSomething : function(e) {
            helenos.util.GuiObserver.showConnectionEditTab();
        }
    }
});
