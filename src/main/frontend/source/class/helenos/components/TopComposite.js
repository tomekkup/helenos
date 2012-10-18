/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */

qx.Class.define("helenos.components.TopComposite",
{
    extend : qx.ui.container.Composite,

    construct : function()
    {
        this.base(arguments);
        this.set({
            layout : new qx.ui.layout.VBox()
        });

        this.add(this.__getHeader());
        this.add(this.__getMainContainer(), {
            flex : 1
        });
        this.add(this.__getFooter());
    },

    members :
    {
        __getHeader : function() {
            return new helenos.components.Header();
        },
        
        __getMainContainer : function() {
            return new helenos.components.MainPane();
        },
        
        __getFooter : function() {
            return new helenos.components.Footer();
        }
    }
});
