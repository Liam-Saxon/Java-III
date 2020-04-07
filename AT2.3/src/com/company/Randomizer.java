package com.company;

import java.util.ArrayList;

public class Randomizer
{
    //random number gen
    public static int generate(int min,int max)
    {
        return min + (int)(Math.random() * ((max - min) + 1));
    }
}
