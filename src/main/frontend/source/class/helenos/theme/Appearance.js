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
    extend : qx.theme.simple.Appearance,
    title : 'Helenos app theme',

    appearances : {
        'app-header-black' : {
            style : function(states) {
                return {
                    font : "bold",
                    textColor : "#E8E8E8",
                    padding : [ 6 ],
                    decorator : "app-header-black"
                };
            }
        },
        
        'app-footer-dark' : {
            style : function(states) {
                return {
                    backgroundColor : "#6c727b",
                    textColor : "#FFFFFF",
                    padding : [ 2, 2, 2, 2]
                };
            }
        },
        
        'criteria-pane' : {
            style : function(states) {
                return {
                    backgroundColor : '#E0ECFF'
                }
            }
        },
        
        'cql-textarea' : {
            style : function(states) {
                return {
                    textColor : '#265515',
                    font : 'cql'
                }
            }
        },
        
        'groupbox/frame' : {
            style : function(states) {
                return {
                    backgroundColor : 'background',
                    padding : [6, 9],
                    margin: [18, 2, 2, 2],
                    decorator  : "white-box"
                }
            }
        }
    } 
});