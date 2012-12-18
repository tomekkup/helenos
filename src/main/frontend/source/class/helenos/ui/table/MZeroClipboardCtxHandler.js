/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Mixin.define("helenos.ui.table.MZeroClipboardCtxHandler",
{
    members : {
        contextMenuHandler : function(col, row, table, dataModel, contextMenu) {
            var copyBtn = new qx.ui.menu.Button('Copy to clipboard', 'helenos/clipboard.png');
            var clip = new ZeroClipboard.Client();
            
            clip.addEventListener('complete', function (client, text) {
                client.destroy();
            },this);
            if (!table.getSelectionModel().isSelectionEmpty()) {
                var text = dataModel.getValue(col, row);
                if (qx.lang.Type.isObject(text)) {
                    text = text.label
                }
                clip.setText(text);
            }
            
            copyBtn.addListener("appear", function(e) {
                clip.glue(e.getTarget().getContentElement().getDomElement());
            }, this);
            
            contextMenu.add(copyBtn);
            return true;
        }
    }
});
