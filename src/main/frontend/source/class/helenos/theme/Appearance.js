/* ************************************************************************
Copyright:
  2012 Tomek Kuprowski
License:
  GPLv2: http://www.gnu.org/licences/gpl.html
Authors:
  Tomek Kuprowski (tomekkuprowski at gmail dot com)
 ************************************************************************ */
qx.Theme.define("helenos.theme.Appearance",
{
    extend : qx.theme.modern.Appearance,
    title : 'Helenos app theme',

    appearances : {
        'app-header-black' : {
            style : function(states) {
                return {
                    font : "bold",
                    textColor : "#E8E8E8",
                    padding : [ 8, 12 ],
                    decorator : "app-header-black"
                };
            }
        }

    } 
});