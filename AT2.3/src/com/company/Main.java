package com.company;

import org.junit.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Main
{

    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        Randomizer randomizer = new Randomizer();
        //Insert sort
        Insertion insertion = new Insertion();
        //selection sort
        Selection selection = new Selection();
        //create array list
        ArrayList<Salary> sa = new ArrayList<Salary>();

        //create 100000 arraylist items
        for(int i = 0; i < 100000; i ++)
        {
            sa.add(new Salary(Randomizer.generate(1 , 1000000)));
        }
        //shuffle
        Collections.shuffle(sa);

        System.out.println("Insertion sort: This may take a while please be patient...");
        long startTime1 = System.nanoTime();
        Insertion.insertionSort(sa);
        long endTime1 = System.nanoTime();
        for(Salary s:sa)
        {
            System.out.println("Random Integers: "+s.Number);
        }
        System.out.println("Insertion Sort runtime (Nanoseconds): " + (endTime1 - startTime1));
        System.out.println("Type anything and press ENTER to continue to the next Sort...");
        scanner.nextLine();
        Collections.shuffle(sa);

        System.out.println("Collection sort: This may take a while please be patient...");
        long startTime2 = System.nanoTime();
        Collections.sort(sa);
        long endTime2 = System.nanoTime();
        for(Salary s:sa)
        {
            System.out.println("Random Integers: "+s.Number);
        }
        System.out.println("Collection Sort runtime (Nanoseconds): " + (endTime2 - startTime2));
        System.out.println("Type anything and press ENTER to continue to the next Sort... (there is about a minute delay on this)");
        scanner.nextLine();
        Collections.shuffle(sa);

        selection.selectionSort(sa);
        System.out.println("Selection sort: This may take a while please be patient...");
        long startTime3 = System.nanoTime();
        selection.selectionSort(sa);
        long endTime3 = System.nanoTime();
        for(Salary s:sa)
        {
            System.out.println("Random Integers: "+s.Number);
        }
        System.out.println("Collection Sort runtime (Nanoseconds): " + (endTime3 - startTime3));

    }
    @Test
    public void testInsertionSort()
    {
        ArrayList<Salary> sa = new ArrayList<Salary>();
        Insertion insertion = new Insertion();

        for(int i = 0; i < 100000; i ++)
        {
            assertTrue(sa.add(new Salary(Randomizer.generate(1 , 1000000))));
        }
        Insertion.insertionSort(sa);
        assertTrue(sa.containsAll(sa));

    }

}
