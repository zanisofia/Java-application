/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;
import java.util.LinkedList;
/**
 *
 * @author sofia zani
 */
public class Queue {
    private LinkedList<Integer> queue = new LinkedList<Integer> ();

    public Queue(){
        this.queue= new LinkedList<Integer> ();
    }
    
    public Queue(Integer min){  
        this.queue= new LinkedList<Integer> ();   
        queue.addLast(min);
    }
    
    public void enqueue(Integer u){
        queue.addLast(u); 
    }
    
    public boolean dequeue(){
        if(!queue.isEmpty()){ 
            queue.removeFirst(); 
            return true;
        }
        else return false;
    }
    
     public Integer getvalue(){
        return queue.getFirst();
     }
   
    public void print(){ 
        System.out.println("\n coda "+queue);
    }
    
     public boolean empty (){ 
        return this.queue.isEmpty();
    }
            
}
