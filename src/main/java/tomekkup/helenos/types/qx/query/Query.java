/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package tomekkup.helenos.types.qx.query;

/**
 *
 * @author tomek
 */
public interface Query<K,N,V> {
    
    String getConsistencyLevel();
    String getKeyspace();
}
