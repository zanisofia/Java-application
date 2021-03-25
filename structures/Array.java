/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

import java.util.ArrayList;

/**
 *
 * @author sofia zani
 */
public class Array {
    private ArrayList<Integer> array = new ArrayList<Integer> ();
    private Integer capacity;
    
    public Array(Integer c){  
        capacity=c; 
        this.array= new ArrayList<Integer> ();   
    }
    
    public void autofillw(Integer n){
        for (int i=0; i<capacity; i++)
        { if (i==n) array.add(0);
          else  array.add(100);}
    }
    
    public void autofillp(){
        for (int i=0; i<capacity; i++)
            array.add(null);
    }
    
     public Integer getvalue(Integer j){
         return array.get(j);
     }
     
      public void setvalue(Integer j, Integer v){
         array.set(j,v);
     }
    
    public void print (){ 
        System.out.println("\n array "+array);
    }
}
