package com.company;

import org.junit.Test;

import static org.junit.Assert.*;

public class BinaryTree
{
    Node root;

    class Node
    {
        String value;
        Node left;
        Node right;

        Node(String value)
        {
            this.value = value;
            right = null;
            left = null;
        }

    }

    private Node addRecursive(Node current, String value) {
        if (current == null)
        {
            return new Node(value);
        }
        if (value.compareTo(current.value) < 0)
        {
            current.left = addRecursive(current.left, value);
        }
        else if(value.compareTo(current.value) > 0)
        {
            current.right = addRecursive(current.right, value);
        }
        return current;
    }

    public void add(String value)
    {
        root = addRecursive(root, value);
    }

    public void traverseInOrder(Node node)
    {
        if (node != null)
        {
            traverseInOrder(node.left);
            System.out.print(" " + node.value);
            traverseInOrder(node.right);
        }
    }
    public void delete(String value)
    {
        root = deleteRecursive(root, value);
    }

    private Node deleteRecursive(Node current, String value)
    {
        if (current == null)
        {
            return null;
        }

        if (value == current.value)
        {
            // Case 1: no children
            if (current.left == null && current.right == null)
            {
                return null;
            }
            // Case 2: only 1 child
            if (current.right == null)
            {
                return current.left;
            }

            if (current.left == null)
            {
                return current.right;
            }

            // Case 3: 2 children
            String smallestValue = (findSmallestValue(current.right));
            current.value = smallestValue;
            current.right = deleteRecursive(current.right, smallestValue);
            return current;
        }
        if (value.compareTo(current.value) < 0)
        {
            current.left = deleteRecursive(current.left, value);
            return current;
        }

        current.right = deleteRecursive(current.right, value);
        return current;
    }
    //finds the smallest value
    private String findSmallestValue(Node root)
    {
        if (root.left == null)
        {
            return root.value;
        }
        else
            {
            return findSmallestValue(root.left);
        }

    }
    //check the nodes to return if a value is contained
    private boolean containsNodeRecursive(Node current, String value) {
        if (current == null)
        {
            return false;
        }
        if (value == current.value)
        {
            return true;
        }
        if (value.compareTo(current.value) < 0)
        {
            return containsNodeRecursive(current.left, value);
        }
        else
            {
            return containsNodeRecursive(current.right, value);
        }

    }
    //call the containsNodeRecursive method and return the value
    public boolean containsNode(String value)
    {
        System.out.println(containsNodeRecursive(root, value));
        return containsNodeRecursive(root, value);
    }
    @Test
    public void givenABinaryTree_WhenAddingElements_ThenTreeContainsThoseElements() {
        BinaryTree bt =  createBinaryTree();

        assertTrue(bt.containsNode("Nut"));
        assertTrue(bt.containsNode("Gear"));
        assertFalse(bt.containsNode("Java Sucks"));
    }
    //created for Junit Testing
    private BinaryTree createBinaryTree() {

        BinaryTree bt = new BinaryTree();

        bt.add("hello");
        bt.add("how");
        bt.add("are");
        bt.add("you");
        bt.add("doing");
        bt.add("today");
        bt.add("friend");

        return bt;
    }

}
