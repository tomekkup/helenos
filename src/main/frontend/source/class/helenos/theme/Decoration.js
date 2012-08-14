/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Theme.define('helenos.theme.Decoration',
{
    extend : qx.theme.modern.Decoration,

    decorations :
    {
        'app-header-black':
        {
            decorator : qx.ui.decoration.Background,
            style : {
                backgroundImage : 'helenos/background.jpg',
                backgroundRepeat : 'repeat-x'
            }
        } 
    }
});