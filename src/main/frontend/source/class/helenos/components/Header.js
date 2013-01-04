/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
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
            
            box.add(new helenos.ui.BoldLabel('User: ' + helenos.util.CredentialsProvider.getLoggedUser()));
            box.add(new qx.ui.core.Spacer(10));
            
            var editClusterConfigButton = new helenos.ui.HeaderButton('helenos/connections.png', 'Manage connections');
            editClusterConfigButton.addListener('execute', this.__onEditCC);
            
            var editAccountsButton = new helenos.ui.HeaderButton('helenos/users.png', 'Manage accounts');
            editAccountsButton.addListener('execute', this.__onEditAccounts);
            
            var logoutButton = new helenos.ui.HeaderButton('helenos/logout.png', 'Logout');
            logoutButton.addListener('execute', this.__onLogout);
            
            box.add(editAccountsButton);
            box.add(editClusterConfigButton);
            box.add(logoutButton);
            return box;
        },
        
        __onLogout : function(e) {
            var req = new qx.io.remote.Request(helenos.util.UriHelper.getRemoteUri('/j_spring_security_logout'), 'GET', 'application/json');
            req.set({
                parseJson : true, 
                prohibitCaching : true
            });
            req.addListener("completed", function(e) {
                helenos.util.GuiObserver.shutdownApp();
            }, this);
            
            req.send();
        },
        
        __onEditCC : function(e) {
            helenos.util.GuiObserver.showConnectionEditTab();
        },
        
        __onEditAccounts : function(e) {
            helenos.util.GuiObserver.showAccountsEditTab();
        }
    }
});
