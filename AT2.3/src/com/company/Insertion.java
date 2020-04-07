package com.company;

import java.util.ArrayList;

public class Insertion
{
    public static void insertionSort(ArrayList<Salary> list)
    {
        for (int j = 1; j < list.size(); j++)
        {
            Salary current = list.get(j);

            int i = j-1;

            while ((i > -1) && ((list.get(i).compareTo(current)) == 1))
            {
                list.set(i+1, list.get(i));
                i--;
            }
            list.set(i+1, current);
        }
    }
}
