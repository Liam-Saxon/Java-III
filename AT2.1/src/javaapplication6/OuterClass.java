//Author: Liam Saxon
//Student ID: M213585
//Summary: Nested classes with a doubly linked list.
package javaapplication6;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;



public class OuterClass<element>
{   


    //global Variables
    private Node max;
    private Node min;
    private int size;
     
    public OuterClass() 
    {
        size = 0;
    }
    //this class keeps track of each elements information
    private class Node 
    {
        element userElement;
        Node next;
        Node prev;
 
        public Node(element userelement, Node next, Node prev) 
        {
            this.userElement = userelement;
            this.next = next;
            this.prev = prev;
        }
    }
    /**
     * returns the size of the linked list
     * @return
     */
    public int size() { return size; }
     
    /**
     * return whether the list is empty or not
     * @return
     */
    public boolean isEmpty() { return size == 0; }
     
    /**
     * adds userElement at the starting of the linked list
     * @param userelement
     */
    public void addFirst(element userelement) 
    {
        Node tmp = new Node(userelement, max, null);
        if(max != null ) {max.prev = tmp;}
        max = tmp;
        if(min == null) { min = tmp;}
        size++;
        //System.out.println("adding: "+userelement);
        ((country)userelement).display();
    }
     
    /**
     * adds userElement at the end of the linked list
     * @param userelement
     */
    public void addLast(element userelement) 
    {
         
        Node tmp = new Node(userelement, null, min);
        if(min != null) {min.next = tmp;}
        min = tmp;
        if(max == null) { max = tmp;}
        size++;
        //System.out.println("adding: "+userelement);
        ((country)userelement).display();
    }
     
    /**
     * this method walks forward through the linked list
     */
    public void iterateForward()
    {
         
        System.out.println("iterating forward..");
        Node tmp = max;
        while(tmp != null)
        {
//            System.out.println(tmp.userElement);
            ((country)tmp.userElement).display();
            tmp = tmp.next;
        }
    }
     
    /**
     * this method walks backward through the linked list
     */
    public void iterateBackward()
    {
         
        System.out.println("iterating backword..");
        Node tmp = min;
        while(tmp != null)
        {
//            System.out.println(tmp.userElement);
             ((country)tmp.userElement).display();
            tmp = tmp.prev;
        }
    }
     
    /**
     * this method removes userElement from the start of the linked list
     * @return
     */
    public element removeFirst() 
    {
        if (size == 0) throw new NoSuchElementException();
        Node tmp = max;
        max = max.next;
        max.prev = null;
        size--;
//        System.out.println("deleted: "+tmp.userElement);
        ((country)tmp.userElement).display();
        return tmp.userElement;
    }
     
    /**
     * this method removes userElement from the end of the linked list
     * @return
     */
    public element removeLast() 
    {
        if (size == 0) throw new NoSuchElementException();
        Node tmp = min;
        min = min.prev;
        min.next = null;
        size--;
        //System.out.println("deleted: "+tmp.userElement);
        ((country)tmp.userElement).display();
        return tmp.userElement;
    } 
    
    static void test()
    {

    }
   
    public static void main(String[] args) 
    {
        //create doubly linked list
        OuterClass<country> countries = new OuterClass<country>();
        //create country
        country australia = new country("Australia", "Sydney");
        country.City sydney = australia.new City();
        countries.addFirst(australia);
        
        country brazil = new country("Brazil", "Rio");
        country.City rio = brazil.new City();
        countries.addLast(brazil);
        
        country japan = new country("Japan", "Tokyo");
        country.City tokyo = japan.new City();
        countries.addLast(japan);
        
        country germany = new country("Germany", "Berlin");
        country.City berlin = germany.new City();
        countries.addLast(germany);
        
        countries.iterateForward();
        countries.iterateBackward();
        countries.removeFirst();
        countries.removeLast();
        countries.iterateForward();
        countries.iterateBackward();
        
        country country1 = new country("aus", "Perth");
        assertTrue(country1.getCountryName().compareTo("aus")== 0); 
        
        country country2 = new country("brazil", "rio");
        assertTrue(country2.getCountryName().compareTo("brazil")== 0);

    } 
}