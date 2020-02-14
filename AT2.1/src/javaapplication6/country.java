/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication6;

/**
 *
 * @author M213585
 */
public class country 
{   
    //private string countryname
    private String cName;   
    //create city object
    City myCity = new City();

    public country(String countryName, String cityName)
    {
        myCity.setCityName(cityName);
        cName = countryName;
    }
    public void display()
    {
        System.out.println( getCountryName() + " - "+ myCity.name);
    }
    //getter
    public String getCountryName()
    {
        return this.cName;
    }
    //setter
    public String setCountryName(String countryName)
    {
        return this.cName = countryName;   
    }
    
    //inner class
    public class City
    {
        
       private String name;
       //getter
       public String getCityName()
       {
           return this.name;
       }
       //setter
       public String setCityName(String cityName)
       {
           return this.name = cityName;
       }

    }          
}
