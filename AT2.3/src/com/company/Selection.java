package com.company;

import java.util.ArrayList;
import java.util.Comparator;

public class Selection
{
    //selection sort
    public void selectionSort(ArrayList<Salary> items) {
        //if index is less than salary size, increment.
        for (int index = 0; index < items.size(); index++)
        {
            //subindex is = to the index if its less that salary size increment
            for (int subIndex = index; subIndex < items.size(); subIndex++)
            {
                //if the compared sub index is compared to the index and is less than, then swap
                if (items.get(subIndex).compareTo(items.get(index)) < 0)
                {
                    Comparable temp = items.get(index);
                    items.set(index, items.get(subIndex));
                    items.set(subIndex, (Salary) temp);
                }
            }
        }
    }
}
