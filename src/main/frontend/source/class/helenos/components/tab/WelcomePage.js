/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
/*
#asset(welcome/*)
#asset(qx/icon/${qx.icontheme}/16/places/user-home.png)
 */
qx.Class.define("helenos.components.tab.WelcomePage",
{
    extend : helenos.components.tab.AbstractCloseablePage,

    /*
  *****************************************************************************
     MEMBERS
  *****************************************************************************
  */
 
    construct : function()
    {
        this.base(arguments);
        this.set({
            label: 'Start',
            icon: 'icon/16/places/user-home.png',
            layout: new qx.ui.layout.VBox(3, 'top')
        });
        
        var iframe = new qx.ui.embed.ThemedIframe('resource/welcome/index.html?t=' + new Date().getTime());
        this.add(iframe, {flex : 1});
        this.add(this.__getVersionInfoBox());
    },
    
    members : {
        __getVersionInfoBox : function() {
            return new qx.ui.basic.Label('Version ' + qx.core.Environment.get('qx.app.version'));
        }
    }
});
