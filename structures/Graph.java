/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package structures;
import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 * @author sofia zani
 */
public class Graph {
    public LinkedHashMap<Integer, LinkedHashMap<Integer,Integer>> vertici; //struttura grafo
    public Integer capacity;
	
    public Graph() {
        this.vertici = new LinkedHashMap<Integer, LinkedHashMap<Integer,Integer>>();
        capacity=0;
    }
        
    public boolean insertNode(Integer u) { 
        if (controlNode(u)){
            if (!this.vertici.containsKey(u)){
                if (this.vertici.size()>15){
                    System.out.println("non si possono inserire ulteriori nodi\n");
                    return false;
                }
                else{ 
                    this.vertici.put(u, new LinkedHashMap<Integer,Integer>()); 
                    System.out.println("è stato inserito il nuovo nodo "+u+"\n");
                    return true;
                }
            }
            else{
                System.out.println("nodo "+u+" già inserito\n");
                return true;
            }
        }
        else return false;
    }
    
    public boolean insertEdge (Integer u, Integer v, Integer w){ //nodo di partenza u -nodo di arrivo v - peso dell'arco di collegamento w
        if (controlNode(u) && controlNode(v) && controlW(w)){
            if (!this.vertici.get(u).containsKey(v)){ 
                if (!insertNode(v)) return false;
                else
                    System.out.println("Si procede all'inserimento dell'arco\n");
            }
           
            if (this.vertici.get(u).get(v) != null){
                this.vertici.get(u).put(v,w); capacity++;
                System.out.print("Modifica del peso dell'arco da nodo "+u+" a nodo "+v+", nuovo peso "+vertici.get(u).get(v)+"\n");
                return true;
            }
            else{
                this.vertici.get(u).put(v,w); capacity++;
                System.out.print("Nuovo arco da nodo "+u+" a nodo "+v+" inserito con peso "+vertici.get(u).get(v)+"\n");
                return true;
            }
        }
        else return false;
    }
        
    public boolean reverseEdge (Integer u, Integer v){ 
        if(controlNode(u) && controlNode(v)){
            if (!this.vertici.get(u).containsKey(v)){ 
                System.out.println("Non è presente questo arco\n");
                return false;
            }
            else{
                Integer n = this.vertici.get(u).get(v);
                deleteEdge(u,v);
                this.vertici.get(v).put(u,n);
                return true;
            }
        }
        else return false;
    }
      
       
    public  boolean controlLink(Integer u,Integer v){
        if(this.vertici.get(u).containsKey(v)) 
            return true;
        else 
            return false;
    }
        
    public boolean controlNode(Integer n){
        if(0>n || 14<n) {System.out.println("valore nodo "+ n +" scorretto\n"); return false;}
        else return true;
    }
        
    public boolean controlW(Integer n){
        if(-25>n || 25<n) {System.out.println("valore peso "+n+" scorretto\n"); return false;}
        else return true;
    }
         
    public void deleteemptyNode(Integer u) {
        this.vertici.remove(u); 
    }
    
    public boolean deleteNode(Integer u) {
        if  (controlNode(u)){   
            if (!vertici.isEmpty()){
                if(this.vertici.containsKey(u)){
                    Integer n=this.vertici.get(u).size(); capacity=capacity-n;
                    for (int i=0; i<getnNode();i++) 
                        deleteEdge(i,u);
                    this.vertici.remove(u); 
                    return true;
                } 
                else{
                    System.out.println("il nodo "+u+" non è presente");
                    return false;
                }     
            }
            else{
                System.out.println("impossibile togliere il nodo lista vuota");
                return false;
            } 
        }    
        else 
            return false;
    }
        
    public boolean deleteEdge(Integer u,Integer v) {
        if  (controlNode(u) && controlNode(v)){ 
            if (!vertici.isEmpty()){
                if(this.vertici.containsKey(u) && controlLink(u,v)){
                    capacity--; 
                    this.vertici.get(u).remove(v);
                    return true;
                }
                else{
                    System.out.println("il nodo non è presente");
                    return false;
                } 
            }
            else{
                System.out.println("impossibile togliere il nodo lista vuota");
                return false;
            } 
        }
        else 
            return false;
    }
        
    public void printNode (Integer u){
        if(this.vertici.get(u) != null)
            System.out.print("nodo "+ u +" nodi collegati e peso "+this.vertici.get(u)+" a nodo \n");
    }
        
    public void printEdge (Integer u, Integer v){
        if(this.vertici.get(u).get(v) != null)
            System.out.print("arco da nodo "+u+" a nodo "+v+" ha peso "+vertici.get(u).get(v)+"\n");
        else
            System.out.print("non esiste arco da nodo "+u+" a nodo "+v+" \n");
    }
        
    public void printAll (){
        System.out.print("tutti gli elementi "+this.vertici.values()+"\n");
    }
     
    public Integer getW(Integer u, Integer i){
        if (this.vertici.get(u).containsKey(i))
            return this.vertici.get(u).get(i);
        else 
            return null;
    }
    
    public Integer getnNode(){  
        return  this.vertici.size();
    }
        
    public Integer getsizeNode(Integer u){    
       return this.vertici.get(u).size();
    }
    
    public Integer[] fillindex(){    
      return this.vertici.keySet().toArray(new Integer[this.vertici.keySet().size()]);
    }
}

