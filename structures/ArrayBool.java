/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;

/**
 *
 * @author sofia zani
 */
public class ArrayBool {
    boolean[] arrayb;
    Integer capacity;
    
    public ArrayBool(Integer c, Integer min){
        arrayb= new boolean[c] ;
        for (int i=0; i<c; i++) {
            arrayb[i]=false; 
            if (i==min)  
                arrayb[i]=true;
        }
        capacity=c;  
    }
    
    public boolean getvalue(Integer j){
        return arrayb[j];
    }
   
    public void setvalue(Integer j, boolean v){
        arrayb[j]=v;
    }
     
    public void print (){ 
        System.out.println("\n array bool "+arrayb);
    }
}
