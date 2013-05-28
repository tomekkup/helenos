/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Class.define("helenos.ui.RoleAwareButton",
{
    extend : qx.ui.form.Button,

    construct : function(label, icon, role)
    {
        this.base(arguments, label, icon);
        this.setEnabled(helenos.util.CredentialsProvider.hasRole(role));
    }
});
