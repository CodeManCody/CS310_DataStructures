/*  Cody Morgan
    cssc0928
 */

import data_structures.*;
import java.util.Iterator;
import java.io.*;

public class PhoneBook
{
    DictionaryADT<String, String> hashList;
    int maxSize;

    public PhoneBook(int maxSize)
    {
        this.maxSize = maxSize;
        hashList = new Hashtable<String, String>(this.maxSize);
    }
    
    private void shellSort(String[] array)
    {
        String[] n = array;
        String temp;
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
    
    public void load(String file)
    {
        String line = null;
        
        try {
            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            while((line = bufferedReader.readLine()) != null)
            {
                if(line.contains("="))
                {
                    String[] parts = line.split("=");
                    String key = parts[0];
                    String value = parts[1];
                    hashList.add(key, value);
                }
                else
                    throw new IllegalArgumentException("Entry " + line 
                            + " does not contain ="); 
            }   

            bufferedReader.close();         
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        }
    }
           
    public String numberLookup(PhoneNumber number)
    {
        return hashList.getValue(number.phoneNumber);
    }
    
    public PhoneNumber nameLookup(String name)
    {
        String key = hashList.getKey(name);
        if(key == null)
            return null;
        return new PhoneNumber(key);
    }
 
    public boolean addEntry(PhoneNumber number, String name)
    {
        return hashList.add(number.phoneNumber, name);
    }
       
    public boolean deleteEntry(PhoneNumber number)
    {
        return hashList.delete(number.phoneNumber) == true;
    }
       
    public void printAll()
    {
        Iterator<String> keys = hashList.keys();
        Iterator<String> values = hashList.values();
        while(keys.hasNext())
            System.out.println(keys.next() + "=" + values.next());
    }
       
    public void printByAreaCode(String code)
    {
        String regex = "(?:\\d{3})";
        if(!code.matches(regex))
            throw new IllegalArgumentException();
        else
        {
            Iterator<String> keys = hashList.keys();
            Iterator<String> values = hashList.values();
            
            while(values.hasNext())
            {
                String keyTemp = keys.next();
                String valueTemp = values.next();
                if(keyTemp.startsWith(code))
                    System.out.println(keyTemp + "=" + valueTemp);
            }
        }
    }
   
    public void printNames()
    {
        Iterator<String> keys = hashList.keys();
        Iterator<String> values = hashList.values();
        
        String[] names = new String[hashList.size()];
            
        for(int i = 0; i < hashList.size(); i++)
            names[i] = values.next();
        
        shellSort(names);
        
        for(int i = 0; i < hashList.size(); i++)
            System.out.println(names[i]);
    }
}