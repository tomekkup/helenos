/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.components.Footer",
{
    extend : qx.ui.container.Composite,

    construct : function()
    {
        this.base(arguments);
        this.set({
            layout : new qx.ui.layout.HBox(8).set({
                alignX : 'right',
                alignY : 'middle'
            }),
            padding : 2,
            appearance : 'app-footer-dark'
        });
        
        this.__createComponents();
        this.__initScheduledJob();
    },
    
    members : {
        __liveAtom : null,
        
        __downAtom : null,
        
        __suspendedAtom : null,
        
        __timerManager : null,
        
        __initScheduledJob : function() {
            this._timerManager = qx.util.TimerManager.getInstance();
            this._timerManager.start(function(userData, timerId) {
	        	this.__checkConnectionHealth();
	        }, 10000, this, null, 1);

        },
        
        __checkConnectionHealth : function() {
            var status = helenos.util.RpcActionsProvider.getConnectionStatus();
            if (status != undefined) {
                this.__liveAtom.setLabel('' + status['LIVE'].length);
                this.__downAtom.setLabel('' + status['DOWN'].length);
                this.__suspendedAtom.setLabel('' + status['SUSPENDED'].length);

                this.__liveAtom.setToolTipText(this.__getToolTipText(status['LIVE']));
                this.__downAtom.setToolTipText(this.__getToolTipText(status['DOWN']));
                this.__suspendedAtom.setToolTipText(this.__getToolTipText(status['SUSPENDED']));
            } else {
                this.__liveAtom.setLabel(this.tr('error'));
                this.__downAtom.setLabel(this.tr('error'));
                this.__suspendedAtom.setLabel(this.tr('error'));
            }
        },
        
        __getToolTipText : function(hostsArray) {
            var ttt = (hostsArray.length == 0) ? 'none' : '';
            for (var i = 0; i < hostsArray.length; i++) {
                ttt += hostsArray[i].name + '<br/>';
            }
            return ttt;
        },
        
        __createComponents : function() {
            this.__liveAtom = new qx.ui.basic.Atom('', 'helenos/green-light.png');
            this.__downAtom = new qx.ui.basic.Atom('', 'helenos/red-light.png');
            this.__suspendedAtom = new qx.ui.basic.Atom('', 'helenos/yellow-light.png');
        
            this.add(new qx.ui.basic.Label('Connection state:'));
            this.add(this.__liveAtom);
            this.add(this.__downAtom);
            this.add(this.__suspendedAtom);
        }
    }
});
