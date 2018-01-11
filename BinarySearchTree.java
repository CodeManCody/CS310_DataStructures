/*  Cody Morgan
    cssc0928
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class BinarySearchTree<K extends Comparable<K>, V> 
        implements DictionaryADT<K, V>
{
    private class TreeNode<K, V>
    {
        private K key;
        private V value;
        private TreeNode<K, V> leftChild;
        private TreeNode<K, V> rightChild;
        
        public TreeNode(K k, V v)
        {
            key = k;
            value = v;
            leftChild = rightChild = null;
        }
    }
    
    private TreeNode<K, V> root;
    private int currentSize;
    private long modCounter;
    private K keySave;
    
    public BinarySearchTree()
    {
        root = null;
        currentSize = 0;
        modCounter = 0;
    }
    
    public boolean contains(K key)
    {
        return find(key, root) != null;
    }
    
    private V find(K key, TreeNode<K, V> current)
    {
        if(root == null) return null;
        if(current == null) return null;
        
        int compare = key.compareTo(current.key);
        if(compare < 0)
            return find(key, current.leftChild);
        else if(compare > 0)
            return find(key, current.rightChild);
        else return current.value;
    }

    public boolean add(K key, V value)
    {
        if(contains(key)) return false;
        
        if(root == null)
            root = new TreeNode<K, V>(key, value);
        else
            insert(key, value, root, null, false);
        
        currentSize++;
        modCounter++;
        return true;
    }
    
    private void insert(K k, V v, TreeNode<K, V> current, TreeNode<K, V> parent
            , boolean wentLeft)
    {
        if(current == null)
        {
            if(wentLeft) parent.leftChild = new TreeNode<K, V>(k, v);
            else parent.rightChild = new TreeNode<K, V>(k, v);
        }
        else if(((Comparable<K>)k).compareTo((K)current.key) < 0)
            insert(k, v, current.leftChild, current, true);     // go left
        else
            insert(k, v, current.rightChild, current, false);   // go right
    }

    public boolean delete(K key)
    {
        if(isEmpty()) return false;
        
        if(!delete(key, root, null, false)) return false;
        
        currentSize--;
        modCounter++;
        return true;
    }
    
    private boolean delete(K k, TreeNode<K, V> current, TreeNode<K, V> parent
            , boolean wentLeft)
    {
        if(!contains(k)) return false;
        
        int compare = k.compareTo(current.key);
        
        if(compare < 0)
            return delete(k, current.leftChild, current, true);
        else if(compare > 0)
            return delete(k, current.rightChild, current, false);
        else
        {
            if(current.leftChild == null && current.rightChild == null)
            {                                   // no child
                if(parent == null) root = null;
                else if(wentLeft) parent.leftChild = null;
                else parent.rightChild = null;
                return true;
            }
            else if(current.leftChild == null)  // has right child
            {
                if(parent == null) root = current.rightChild;
                else if(wentLeft) parent.leftChild = current.rightChild;
                else parent.rightChild = current.rightChild;
                return true;
            }
            else if(current.rightChild == null) // has left child
            {
                if(parent == null) root = current.leftChild;
                else if(wentLeft) parent.leftChild = current.leftChild;
                else parent.rightChild = current.leftChild;
                return true;
            }
            else if(current.rightChild != null && current.leftChild != null)
            {                                   // has two children
                TreeNode<K, V> temp = getSuccessor(current, parent);
                current.key = temp.key;
                current.value = temp.value;
                return true;
            }
        }
        return false;
    }
    
    private TreeNode<K, V> getSuccessor(TreeNode<K, V> current
            , TreeNode<K, V> parent)
    {
        TreeNode<K, V> tempCurrent = current.rightChild;
        parent = current;
        
        if(tempCurrent.leftChild == null)
        {
            current = parent.rightChild;
            parent.rightChild = tempCurrent.rightChild;
            return current;
        }
        
        while(tempCurrent.leftChild != null)
        {
            parent = tempCurrent;
            tempCurrent = tempCurrent.leftChild;
        }
        
        current = parent.leftChild;
        parent.leftChild = tempCurrent.rightChild;
        return current;
    }

    public V getValue(K key)
    {
        return find(key, root);  
    }
    
    public K getKey(V value)
    {
        keySave = null;
        walk(value, root);
        return keySave;
    }
    
    private void walk(V v, TreeNode<K, V> current)
    {
        if(keySave != null) return;
        
        if(current == null) return;
        walk(v, current.leftChild);
            if(((Comparable<V>)v).compareTo((V)current.value) == 0)
                keySave = current.key;
        walk(v, current.rightChild);
    }

    public int size()
    {
        return currentSize;
    }

    public boolean isFull()
    {
        return false;
    }

    public boolean isEmpty()
    {
        return currentSize == 0;
    }

    public void clear()
    {
        root = null;
        currentSize = 0;
        modCounter = 0;
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
        protected TreeNode<K, V> [] nodes;
        protected int index;
        protected int i = 0;
        protected long modCheck;
        
        private void walk(TreeNode<K, V> current)
        {
            if(current == null) return;
            walk(current.leftChild);
                nodes[i++] = current;
            walk(current.rightChild);
        }
        
        public IteratorHelper()
        {
            nodes = new TreeNode[currentSize];
            index = 0;
            int j = 0;
            modCheck = modCounter;
            
            walk(root);
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
