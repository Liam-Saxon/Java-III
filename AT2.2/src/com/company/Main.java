package com.company;

import org.junit.Test;
import org.w3c.dom.Node;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class Main
{
    public static void main(String[] args)
    {

        BinaryTree bt = new BinaryTree();

        bt.add("Drive Shaft");
        bt.add("Spline");
        bt.add("Bearing");
        bt.add("Gear");
        bt.add("Belt");
        bt.add("Clutch");
        bt.add("Gear Train");
        bt.add("Nut");
        bt.add("Linchpin");
        bt.add("Clutch");

        //traverse the tree in order
        bt.traverseInOrder(bt.root);
        bt.delete("Clutch");
        System.out.println();
        bt.traverseInOrder(bt.root);
        System.out.println();
        System.out.print("Value found - ");
        bt.containsNode("Java Sucks");


    }
}
