/*  Cody Morgan
    cssc0928
 */

import java.util.Iterator;
import data_structures.*;

public class PhoneNumber implements Comparable<PhoneNumber>
{
    String areaCode, prefix, number;
    String phoneNumber;
    
    public PhoneNumber(String n)
    {
        verify(n);
        phoneNumber = n;
        areaCode = phoneNumber.substring(0, 3);
        prefix = phoneNumber.substring(4, 7);
        number = phoneNumber.substring(8, 12);
    }
    
    public int compareTo(PhoneNumber n)
    {
        return phoneNumber.compareTo(n.phoneNumber);
    }

    public int hashCode()
    {
        return phoneNumber.hashCode();
    }
    
    private void verify(String n)
    {
        String regex = "(?:\\d{3}-){2}\\d{4}";
        if(!n.matches(regex))
            throw new IllegalArgumentException();
    }
       
    public String getAreaCode()
    {
        return areaCode;
    }
       
    public String getPrefix()
    {
        return prefix;
    }
       
    public String getNumber()
    {
        return number;
    }
    
    public String toString()
    {
        return phoneNumber;
    }
}           

