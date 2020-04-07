package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class Salary implements Comparable<Salary>
{
    //variables
    int Number;
    //getter, setter
    Salary(int Number)
    {
        this.Number = Number;
    }
    //compare to method
    @Override
    public int compareTo(Salary sa)
    {
        if(Number == sa.Number)
            return 0;

        else if(Number > sa.Number)
            return 1;

        else
            return -1;
    }



}
