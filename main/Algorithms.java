/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.*;
import structures.*;


import java.io.File;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author Matilde Rodolfi
 */
public class Algorithms{
    public Graph g;
    public Array warray;
    public Array parray;
    public ArrayBool barray;
    public Queue wayqueue;
    Integer[] index;
    int nodoI;
    
    private static BufferedReader br;
     
    public Algorithms(){
        g = new Graph();
    }
    
    /**
    *
    * @author Erica Bertuzzi
     * @param numNodi numero di nodi del grafo da creare
    */
    public void randomGraph (int numNodi){
        g = new Graph();
        
        for (int i = 0; i<numNodi; i++) g.insertNode(i);
        if (numNodi !=1) {
            for (int i = 0; i <(int)(numNodi*2); i++) { 
                //genera (numNodes*1.5) archi tra nodi casuali
                Random randomGenerator = new Random();
                int randomInt;
                int randomS;
                int randomD;

                randomD = randomGenerator.nextInt(numNodi);
                randomS = randomGenerator.nextInt(numNodi);
                while (randomD==randomS){
                    randomS = randomGenerator.nextInt(numNodi);
                }
                randomInt = randomGenerator.nextInt(25)+1;
                if(randomGenerator.nextInt(2) == 0) randomInt= randomInt * (-1);

                g.insertEdge(randomS, randomD, randomInt);
            }
        }
    }
    
    public void importGraph (Stage stage){
        final FileChooser fileChooser = new FileChooser();
        //aggiungiamo il filtro all'importazione, di modo che il file da importare sia necessariamente un file di testo txt.
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        fileChooser.getExtensionFilters().add(extFilter);
        File file = fileChooser.showOpenDialog(stage);
        if (file != null) {
            try {
                System.out.println("Loading... try");
                FileReader in = null;
                in = new FileReader(file);
	         
	        br = new BufferedReader(in);

                String sCurrentLine;
		g = new Graph();
		sCurrentLine = br.readLine();
                while ((sCurrentLine) != null) {
                    if(sCurrentLine!="\r\n") parse(sCurrentLine, g);
                    sCurrentLine = br.readLine();
	        }
            } catch (IOException ex) {
                Logger.getLogger(
                    Algorithms.class.getName()
                ).log(
                    Level.SEVERE, null, ex
                );
            }
        }
    }
    
    //@SuppressWarnings({ "rawtypes", "unchecked" })
    static void parse(String line, Graph g) {
        /*esempio di grafico .txt importabile:
        1 -> 2[weight="0.2"];
        1 -> 3[weight="0.4"];
        3 -> 2[weight="0.6"];
        3 -> 5[weight="0.6"];
        5 -> 4[weight="0.1"];
        5 -> 2[weight="0.7"];
        5 -> 2[weight="17"]; 				<-esempio. NB. I numeri razionali non sono contemplati come peso.
        00000000001111111
        01234567890123456
	*/
        int iCorrettivo = 0; 				//>0 se l'istruzione non parte dalla posizione 0
        int jCorr = 0; 						//1 se il nodo sorgente ha due cifre
        int jCorrE = 0; 					//1 se il nodo destinazione ha due cifre
	
        if( (!line.isEmpty()) && (isNumber(line.charAt(iCorrettivo)) )) {
            if(isNumber(line.charAt(iCorrettivo+1))) 
                jCorr=1;
		
            if(isNumber(line.charAt(iCorrettivo+6+jCorr)))
                jCorrE=1;

            if ((isNumber(line.charAt(iCorrettivo))) && (isNumber(line.charAt(iCorrettivo+5+jCorr))) && (isNumber(line.charAt(iCorrettivo+15+jCorr+jCorrE)))) {
		int sauce = line.charAt(iCorrettivo) - '0';
		if (jCorr==1) 
                    sauce=(sauce*10) + (line.charAt(iCorrettivo+jCorr) - '0');
		g.insertNode(sauce);
		int des = (line.charAt(iCorrettivo+jCorr+5) - '0'); // destinazione
		if (jCorrE==1) 
                    des=(des*10) + (line.charAt(iCorrettivo+jCorr+jCorrE) - '0');
		g.insertNode(des);
		double weight = 0.1;
		int sign = 1; 			//segno del peso
		iCorrettivo=+ jCorr+jCorrE;
		if(line.charAt(iCorrettivo+15+jCorr+jCorrE)== '-') {
                    sign = -1;
                    iCorrettivo++; 			//il valore del peso è scalato di uno, per via del segno -
		}
		for (int i = iCorrettivo+15; isNumber(line.charAt(i)); i++){ //le condizioni sono che il peso sia un numero	
                    weight*=10;
                    weight=+ (line.charAt(i) - '0');	
		}
		g.insertEdge(sauce, des, (int)(weight*sign));
            }
	}
    }
    
    static Boolean isNumber (char a) {
	int a1 = a - '0';
        return (a1 >= 0 && a1 <=9);
    }
    
    
    
    static String s = new String(); //stringa per l'export
    public void ExportGraph(Graph g, final Stage primaryStage, Array parray, Array warray){// throws FileNotFoundException{
        int numNodes = g.getnNode();
        s = new String();
	final FileChooser outFile = new FileChooser();
    	for ( int iOut = 0; iOut!=numNodes; iOut++) {
            for (int jOut = 0; jOut!=numNodes; jOut++) {
                if (g.getW(iOut, jOut)!=null){
                    s = s + iOut + " -> " + jOut + "[weight=\"" + g.getW(iOut, jOut) +"\"]\r\n";
                }
            }
        }
        
        //Imposta il filtro "File .txt" per il file che si sta esportando
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        outFile.getExtensionFilters().add(extFilter);

        //Mostra la finestra per salvare il file
        File file = outFile.showSaveDialog(primaryStage);

        if (file != null) {
            saveTextToFile(s, file);
        }
    }
    
    public void ExportShortEdge(Graph g, final Stage primaryStage, Array parray, Array warray){
        int numNodes = g.getnNode();
        s = new String();
	final FileChooser outFile = new FileChooser();
        Integer [] nodiName = g.fillindex();
        for ( int iOut = 0; iOut!=numNodes; iOut++) {
                s = s + "\r\n" + parray.getvalue(iOut) + " -> " + nodiName[iOut] + "[weight=\"" + warray.getvalue(iOut) + "\"]";
            // es. output:
            // 1 -> 2[weight="3"];
    	}
        
    	//Imposta il filtro "File .txt" per il file che si sta esportando
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TXT files (*.txt)", "*.txt");
        outFile.getExtensionFilters().add(extFilter);

        //Mostra la finestra per salvare il file
        File file = outFile.showSaveDialog(primaryStage);

        if (file != null) {
            saveTextToFile(s, file);
        }
    }

  private static void saveTextToFile(String content, File file) {
      try {
          PrintWriter writer;
          writer = new PrintWriter(file);
          writer.println(content);
          writer.close();
      } catch (IOException ex) {
          Logger.getLogger(Algorithms.class.getName()).log(Level.SEVERE, null, ex);
      }
  }
    
    public void shortestPathInitialization(int nodoI){
        if (g.vertici.containsKey(nodoI)){
            this.nodoI=nodoI;
            int numNodi = g.getnNode();
            warray = new Array(numNodi); //vettore contenente i pesi di distanza da min nodo di partenza
            parray = new Array(numNodi); //vettore contenente i padri 
            barray = new ArrayBool(numNodi, nodoI); //vettore controllo della presenza nella coda nodi del nodo       

            wayqueue = new Queue(nodoI); // se si inserisce il nodo iniziale da cui si inizia il riempimento di Queue

            index = g.fillindex();
            
            warray.autofillw(nodoI);
            parray.autofillp(); 
        }
        else{
            int numNodi = g.getnNode();
            warray = new Array(numNodi); //vettore contenente i pesi di distanza da min nodo di partenza
            parray = new Array(numNodi); //vettore contenente i padri 
            barray = new ArrayBool(numNodi, nodoI); //vettore controllo della presenza nella coda nodi del nodo       

            wayqueue = new Queue(); // se si inserisce il nodo iniziale da cui si inizia il riempimento di Queue

            index = g.fillindex();
            
            warray.autofillw(0);
            parray.autofillp(); 
        }
    }
    
    public void shortestPathOneStep(){
        
        int numNodi = g.getnNode();
        
        while (!wayqueue.empty()){
            Integer j=wayqueue.getvalue(); 
            int a=0;
            while(index[a]!=nodoI || a==numNodi) a++;
            Integer linkj=g.getsizeNode(j);
            wayqueue.dequeue(); 
            barray.setvalue(a,false);
            System.out.print("\n nodo estratto "+j);
            int k=0;
            while(linkj!=0){   
                boolean flag=false;
                System.out.print("\n numero collegamenti "+j+ " e' "+linkj);
                System.out.print("\n collegamento "+ index[k]);
                if (g.controlLink(j,index[k])){
                    flag=true;
                    Integer weight=g.getW(j,index[k]);
                    System.out.print("\n trovato ");
                    if (warray.getvalue(k)>warray.getvalue(a)+weight){  
                        if(barray.getvalue(k)==false){ //controllo che il nodo di arrivo sia all'interno dell'array 
                            wayqueue.enqueue(index[k]); barray.setvalue(k,true); 
                            wayqueue.print();
                        }
                    if(k!=nodoI){
                            parray.setvalue(k,j); 
                            warray.setvalue(k,weight+warray.getvalue(a));}

                    }
                    else{ //corrispondente all'if della disuguaglianza di bellman
                         System.out.print("\nnon entra ");
                    }
                }
                if (flag==true) linkj--;
                k++;
            }  
        }
    }
    
    /**
    *
    * @author Sofia Zani
     * @param nodoI nodo di partenza
    */
    public void shortestPath(int nodoI){
        if (g.vertici.containsKey(nodoI)){
            int numNodi = g.getnNode();
            warray = new Array(numNodi); //vettore contenente i pesi di distanza da min nodo di partenza
            parray = new Array(numNodi); //vettore contenente i padri 
            barray = new ArrayBool(numNodi, nodoI); //vettore controllo della presenza nella coda nodi del nodo       

            wayqueue = new Queue(nodoI); // se si inserisce il nodo iniziale da cui si inizia il riempimento di Queue

            index = g.fillindex();
            
            warray.autofillw(nodoI);
            parray.autofillp(); 

            do{
                Integer j=wayqueue.getvalue(); 
                int a=0;
                while(index[a]!=nodoI || a==numNodi) a++;
                Integer linkj=g.getsizeNode(j);
                wayqueue.dequeue(); 
                barray.setvalue(a,false);
                System.out.print("\n nodo estratto "+j);
                int k=0;
                while(linkj!=0){   
                    boolean flag=false;
                    System.out.print("\n numero collegamenti "+j+ " e' "+linkj);
                    System.out.print("\n collegamento "+ index[k]);
                    if (g.controlLink(j,index[k])){
                        flag=true;
                        Integer weight=g.getW(j,index[k]);
                        System.out.print("\n trovato ");
                        if (warray.getvalue(k)>warray.getvalue(a)+weight){  
                            if(barray.getvalue(k)==false){ //controllo che il nodo di arrivo sia all'interno dell'array 
                                wayqueue.enqueue(index[k]); barray.setvalue(k,true); 
                                wayqueue.print();
                            }
                        if(k!=nodoI){
                            parray.setvalue(k,j); 
                            warray.setvalue(k,weight+warray.getvalue(a));}
                        
                        }
                        else{ //corrispondente all'if della disuguaglianza di bellman
                             System.out.print("\nnon entra ");
                        }
                    }
                    if (flag==true) linkj--;
                    k++;
                }  
            }while (!wayqueue.empty());
        }
    }
}
