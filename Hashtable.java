/*  Cody Morgan
    cssc0928
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class Hashtable<K extends Comparable<K>, V>
        implements DictionaryADT<K, V>
{
    private LinearListADT<HashNode<K, V>> [] list;
    private long modCounter;
    private int currentSize, maxSize, tableSize;
    
    private class HashNode<K, V> implements Comparable<HashNode<K, V>>
    {
        K key;
        V value;
        
        public HashNode(K key, V value)
        {
            this.key = key;
            this.value = value;
        }
        
        public int compareTo(HashNode<K, V> node)
        {
            return ((Comparable<K>) key).compareTo((K) node.key);
        }
    }
    
    public Hashtable(int max)
    {
        currentSize = 0;
        modCounter = 0;
        maxSize = max;
        tableSize = (int) (maxSize * 1.3f);
        list = new LinearList[tableSize];
        
        for(int i = 0; i < tableSize; i++)
        {
            list[i] = new LinearList<HashNode<K, V>>();
        }
    }
    
    public int getIndex(K key)
    {
        return (key.hashCode() & 0x7FFFFFFF) % tableSize;
    }
    
    public boolean contains(K key)
    {
        return list[getIndex(key)].find(new HashNode<K,V>(key,null)) != null;
    }

    public boolean add(K key, V value)
    {
        if(contains(key) || isFull())
            return false;
           
	HashNode<K, V> newNode = new HashNode<K, V>(key, value);
	list[getIndex(key)].addLast(newNode);
        currentSize++;
        modCounter++;
        return true;
    }

    public boolean delete(K key)
    {
        HashNode<K, V> temp = list[getIndex(key)]
                .remove(new HashNode<K, V>(key, null));
        if(temp == null) return false;
        currentSize--;
        modCounter++;
        return true;
    }

    public V getValue(K key)
    {
        HashNode<K, V> temp = 
                list[getIndex(key)].find(new HashNode<K, V>(key, null));
	if(temp == null) return null;
	return temp.value;
    }

    public K getKey(V value)
    {
        for(int i = 0; i < tableSize; i++)
            for(HashNode<K, V> tempNode : list[i])
                if(((Comparable<K>) value).compareTo((K) tempNode.value) == 0)
                    return tempNode.key;
        return null;
    }

    public int size()
    {
        return currentSize;
    }

    public boolean isFull()
    {
        return currentSize == maxSize;
    }

    public boolean isEmpty()
    {
        return currentSize == 0;
    }

    public void clear()
    {
        currentSize = 0;
        modCounter = 0;
        
        for(int i = 0; i < tableSize; i++)
        {
            list[i].clear();
        }
    }

    public Iterator keys()
    {
        return new KeyIteratorHelper<K>();
    }

    public Iterator values()
    {
        return new ValueIteratorHelper<V>();
    }
    
    
    abstract class IteratorHelper<E> implements Iterator<E> 
    {
        protected HashNode<K, V> [] nodes;
        protected int index;
        protected long modCheck;
        
        private void shellSort(HashNode<K, V> [] nodes)
        {
            HashNode<K, V> [] n = nodes;
            HashNode<K, V> temp;
            int in, out, h = 1;
            int size = n.length;
            
            while(h <= size/3)
                h = h*3+1;
            
            while(h > 0)
            {
                for(out=h; out < size; out++)
                {
                    temp = n[out];
                    in = out;
                    
                    while(in > h-1 && n[in-h].compareTo(temp) >= 0)
                    {
                        n[in] = n[in-h];
                        in-=h;
                    }
                    n[in] = temp;
                }
                h = (h-1)/3;
            }
        }
        
        public IteratorHelper()
        {
            nodes = new HashNode[currentSize];
            index = 0;
            int j = 0;
            modCheck = modCounter;
            
            for(int i = 0; i < tableSize; i++)
                for(HashNode n : list[i])
                    nodes[j++] = n;
                
            shellSort(nodes);
        }
        
        public boolean hasNext()
        {
            if(modCheck != modCounter)
                throw new ConcurrentModificationException();
            return index < currentSize;
        }
        
        public abstract E next();
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
    
    class KeyIteratorHelper<K> extends IteratorHelper<K>
    {
        public KeyIteratorHelper()
        {
            super();
        }
        
        public K next()
        {
            return (K) nodes[index++].key;
        }
    }
    
    class ValueIteratorHelper<V> extends IteratorHelper<V>
    {
        public ValueIteratorHelper()
        {
            super();
        }
        
        public V next()
        {
            return (V) nodes[index++].value;
        }
    }
}
