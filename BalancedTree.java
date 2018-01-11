/*  Cody Morgan
    cssc0928
 */

package data_structures;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

public class BalancedTree<K extends Comparable<K>, V>
        implements DictionaryADT<K, V>
{
    TreeMap<K, V> map = new TreeMap<K, V>();
    
    public boolean contains(K key)
    {
        return map.containsKey(key);
    }

    public boolean add(K key, V value)
    {
        if(contains(key))
            return false;
        
        map.put(key, value);
        return true;
    }

    public boolean delete(K key)
    {
        return map.remove(key) != null;
    }

    public V getValue(K key)
    {
        return (V) map.get(key);
    }

    public K getKey(V value) 
    {
        for (Map.Entry entry : map.entrySet())
        {
           if (((Comparable<V>)value).compareTo((V) entry.getValue()) == 0)
               return (K) entry.getKey();
        }
        return null;  
    }

    public int size()
    {
        return map.size();
    }

    public boolean isFull()
    {
        return false;
    }

    public boolean isEmpty()
    {
        return map.size() == 0;
    }

    public void clear()
    {
        map.clear();
    }

    public Iterator keys()
    {
        return map.keySet().iterator();
    }

    public Iterator values()
    {
        return map.values().iterator();
    }
}
