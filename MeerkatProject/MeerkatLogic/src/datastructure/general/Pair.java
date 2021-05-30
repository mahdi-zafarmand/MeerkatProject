/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datastructure.general;

/**
 *
 * @author aabnar
 * @param <K>
 * @param <V>
 */
public class Pair<K,V> {
    K key;
    V value;
    
    /**
     *
     */
    public Pair() {
        
    }
    
    /**
     *
     * @param ptKey
     * @param ptValue
     */
    public Pair(K ptKey, V ptValue) {
        this.key = ptKey;
        this.value = ptValue;
    }
    
    /**
     *
     * @param key
     * @param value
     */
    public void set (K key, V value) {
        this.key = key;
        this.value = value;
    }
    
    /**
     *
     * @return
     */
    public K getKey() {
        return key;
    }
    
    /**
     *
     * @return
     */
    public V getValue() {
        return value;
    }
    
}
