/*  Cody Morgan
    cssc0928
 */

package data_structures;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayLinearList<E> implements LinearListADT<E> {

    private int size, maxSize, front, rear;
    private E[] list;
    
    public ArrayLinearList()
    {
        this(DEFAULT_MAX_CAPACITY);     
    }
    
    public ArrayLinearList(int max)
    {
        size = front = rear = 0;
        maxSize = max;
        list = (E[]) new Object[maxSize];
    }
    
    public boolean addFirst(E obj)
    {
        if(size == maxSize)
            return false;
        if(size == 0)
        {
            front = rear = 0;
            list[0] = obj;
            size++;
            return true;
        }
        else
        {
            --front;
            if(front < 0)
                front = maxSize - 1;
            list[front] = obj;
            size++;
            return true;
        }
    }
       
    public boolean addLast(E obj)
    {
        if(size == maxSize)
            return false;
        if(size == 0)
        {
            front = rear = 0;
            list[0] = obj;
            size++;
            return true;
        }
        else
        {
            rear++;
            if(rear == maxSize)
                rear = 0;
            
            list[rear] = obj;
            size++;
            return true;
        }
    }
    
    public E removeFirst() 
    {    
        if(size == 0)
            return null;
        
        E obj = list[front];
        front++;
        if(front == maxSize)
            front = 0;
        
        size--;
        return obj; 
    }

    public E removeLast()
    {
        if(size == 0)
            return null;
        
        E obj = list[rear];
        rear--;
        if(rear < 0)
            rear = maxSize - 1;
        
        size--;
        return obj;
    }
    
    public E remove(E obj)
    {
        if(size == 0)      
            return null;
        
        int index = front;
        
        while(((Comparable<E>)list[index]).compareTo(obj) != 0)
        {
            if(index == rear)
                return null;
            index++;
            if(index == maxSize)
                index = 0;
        }
        
        while(index != rear)
        {
            if(index == (maxSize - 1))
            {
                list[index] = list[0];
                index = 0;
            }
            else
            {
                list[index] = list[index + 1];
                index++;
            } 
        }
        size--;
        rear--;
        if(rear < 0)
            rear = maxSize - 1;
        return obj;
    }
    
    public E peekFirst()
    {
        if(size == 0)
            return null;
   
        E obj = list[front];
        return obj;
    }
    
    public E peekLast()
    {
        if(size == 0)
            return null;
     
        E obj = list[rear];
        return obj;
    }
    
    public boolean contains(E obj)
    {
        return find(obj) != null;
    }
    
    public E find(E obj)
    {
        for(E temp : this)
            if(((Comparable<E>)obj).compareTo(temp) == 0)
                return temp;
        return null;
    }
    
    public void clear()
    {
        size = front = rear = 0;
    }
    
    public boolean isEmpty()
    {
        return size == 0;
    }
 
    public boolean isFull()
    {
        return size == maxSize;
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
        private int count, index;
        
        public IteratorHelper()
        {
            index = front;
            count = 0;
        }
        
        public boolean hasNext()
        {
            return count != size;
        }
        
        public E next()
        {
            if(!hasNext())
                throw new NoSuchElementException();
            E temp = list[index++];
            if(index == maxSize)
                index = 0;
            count++;
            return temp;
        }
        
        public void remove()
        {
            throw new UnsupportedOperationException();
        }
    }
}
