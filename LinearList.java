/*  Cody Morgan
    cssc0928
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.ConcurrentModificationException;

public class LinearList<E extends Comparable<E>> implements LinearListADT<E>
{
    private int size;
    private Node<E> head, tail;
    private long modCounter;
    
    class Node<T>
    {
        T data;
        Node<T> previous, next;
        
        Node (T obj)
        {
            data = obj;
            previous = next = null;
        }
        
    }
    
    public boolean addFirst(E obj)
    {
        Node<E> newNode = new Node<E>(obj);
        
        if(head == null)
            head = tail = newNode;
        else
        {
            newNode.next = head;
            head.previous = newNode;
            head = newNode;
        }
        size++;
        modCounter++;
        return true;
    }
    
    public boolean addLast(E obj)
    {
        Node<E> newNode = new Node<E>(obj);
        
        if(tail == null)
            head = tail = newNode;
        else
        {
            newNode.previous = tail;
            tail.next = newNode;
            tail = newNode;
        }
        size++;
        modCounter++;
        return true;
    }

    public E removeFirst()
    {
        if(head == null)
            return null;
        if(head == tail)
        {
            E obj = head.data;
            head = tail = null;
            size--;
            modCounter++;
            return obj;
        }
        
        E obj = head.data;
        head = head.next;
        head.previous = null;
        size--;
        modCounter++;
        return obj;
    }

    public E removeLast()
    {
        if(tail == null)
            return null;
        if(head == tail)
        {
            E obj = head.data;
            head = tail = null;
            size--;
            modCounter++;
            return obj;
        }
        
        E obj = tail.data;
        tail = tail.previous;
        tail.next = null;
        size--;
        modCounter++;
        return obj;
    }

    public E remove(E obj)
    {
        if(head == null)
            return null;
        
        Node<E> current = head, previous = null;
        
        while(obj.compareTo(current.data) != 0)
        {
            if(current.next == null)
                return null;
            previous = current;
            current = current.next;
        }
        
        if(head == tail)
        {
            head = tail = null;
            size--;
            modCounter++;
            return obj;
        }
            
        if(current.previous == null)
        {
            head = head.next;
            head.previous = null;
            size--;
            modCounter++;
            return obj;
        }
            
        if(current.next == null)
        {
            tail = tail.previous;
            tail.next = null;
            size--;
            modCounter++;
            return obj;
        }
            
        previous.next = current.next;
        previous = current;
        current = current.next;
        current.previous = previous.previous;
        size--;
        modCounter++;
        return obj; 
    }
    
    public E peekFirst()
    {
        if(head == null)
            return null;
        
        return head.data;
    }

    public E peekLast()
    {
        if(tail == null)
            return null;
        
        return tail.data;
    }

    public boolean contains(E obj)
    {
        return find(obj) != null;
    }

    public E find(E obj)
    {
        for(E temp : this)
            if((obj).compareTo(temp) == 0)
                return temp;
        return null;
        
    }
    
    public void clear()
    {
        head = tail = null;
        size = 0;
        modCounter = 0;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    public boolean isFull()
    {
        return false;
    }

    public int size()
    {
        return size;
    }

    public Iterator<E> iterator()
    {
        return new IteratorHelper();
    }
    
    class IteratorHelper implements Iterator<E>
    {
        private Node<E> iterPtr;
        private long modCheck;
        
        public IteratorHelper()
        {
            modCheck = modCounter;
            iterPtr = head;
        }
        
        public boolean hasNext()
        {
            if(modCheck != modCounter)
                throw new ConcurrentModificationException();
            return iterPtr != null;
        }
        
        public E next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            E obj = iterPtr.data;
	    iterPtr = iterPtr.next;
            return obj;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
