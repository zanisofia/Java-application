/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafica;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import main.Algorithms;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Tooltip;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author Matilde Rodolfi
 */
public class Grafica{
    
    private Stage primaryStage;

    //testi grafica
    private final String txtTitolo = "ALGRAPH";
    
    //varibili contenitori grafici 
    private final BorderPane layout = new BorderPane(); //divide la pagina in 5 sezioni: top, left, center, right, bottom
    private final Scene scene = new Scene(layout, 1700, 900);
    private final VBox boxAlgoritmo = new VBox();
    private final HBox boxGrafo = new HBox();
    private final VBox boxArray = new VBox();
    private final VBox boxCentro = new VBox();
    
    //informazioni grafo
    private Algorithms a = new Algorithms();
    
    //varibili grafiche visualizzazione grafo
    private Circle[] nodo;
    private Text[] nodoTesto;
    private Arrow[] arco;
    private final Pane pane = new Pane();
    private final int raggioNodo = 17; 
    private final double centroX = 80;
    private final double centroY = 265;
    private final double distanza = 225;
    
    //testo dell'algoritmo di Bellman-Ford
    private Text[] txtAlgoritmo;
    
    //vairiabili array algoritmo Bellman-Ford
    private final Text[] valDistanza = new Text[raggioNodo];
    private final Text[] valRaggiungibile = new Text[raggioNodo];
    private final Text[] valPredecessore = new Text[raggioNodo];
    private TextField txtNodoI;
    
    //Handler per animazione bottoni
    EventHandler<MouseEvent> btnMousePressed = (MouseEvent event) -> {
        HBox source = (HBox) event.getSource();
        source.getStyleClass().clear();
        source.getStyleClass().add("buttonOver");
    };  
    EventHandler<MouseEvent> btnMouseRealased = (MouseEvent event) -> {
        HBox source = (HBox) event.getSource();
        source.getStyleClass().clear();
        source.getStyleClass().add("button");
    };
    
    /*---VISUALIZZAZIONE GRAFO - data la struttura del grafo e lo visualizza all'utente ---*/
    private void visualizzaGrafo(){
        boxGrafo.getChildren().clear();
        pane.getChildren().clear();
        
        Integer [] nodiName = a.g.fillindex();
        
        int numNodi = a.g.getnNode();
               
        nodo = new Circle[numNodi];
        nodoTesto = new Text[numNodi];
        for (int i = 0; i<numNodi; i++) {
            
            double angolo = 2*Math.PI*i / numNodi ;

            nodo[i] = new Circle(centroX+distanza*Math.cos(angolo), centroY+distanza*Math.sin(angolo), raggioNodo, Color.BLUE);
            nodo[i].setCursor(Cursor.OPEN_HAND);
            
            int xAgg = 3;
            if (i>9) xAgg=8;
            
            nodoTesto[i] = new Text(centroX+distanza*Math.cos(angolo)-xAgg, centroY+distanza*Math.sin(angolo)+5, ""+nodiName[i]);
            nodoTesto[i].setFill(Color.WHITE);
            nodoTesto[i].setCursor(Cursor.OPEN_HAND);            
        }        
        
        if (numNodi>0){
            arco = new Arrow[a.g.capacity];
            int c=0;
            for (int i=0; i<numNodi; i++){
                for (int j=0; j<numNodi; j++){
                    if (a.g.vertici.get(nodiName[i]).get(nodiName[j]) != null){
                        if (i>j)
                            arco[c] = new Arrow(nodo[i].getCenterX()+10, nodo[i].getCenterY()+10, nodo[j].getCenterX(), nodo[j].getCenterY());
                        else 
                            arco[c] = new Arrow(nodo[i].getCenterX()-10, nodo[i].getCenterY()-10, nodo[j].getCenterX(), nodo[j].getCenterY());
                        pane.getChildren().add(arco[c]);
                        
                        double angleLine = Math.atan2((nodo[j].getCenterY() - nodo[i].getCenterY()), (nodo[j].getCenterX() - nodo[i].getCenterX()));
                        double Y=nodo[j].getCenterY()-Math.sin(angleLine)*80;
                        double X=nodo[j].getCenterX()-Math.cos(angleLine)*80;
                        
                        pane.getChildren().add(new Text(X,Y, ""+a.g.getW(nodiName[i],nodiName[j])));
                        
                        c++;
                    }
                }
            }
            
            pane.getChildren().addAll(nodo);
            pane.getChildren().addAll(nodoTesto);
        }
        
        boxGrafo.getStyleClass().add("conteiner");
        boxGrafo.getChildren().add(pane);
        
        visualizzaPulsantiGrafo();
    }
    
    private void visualizzaAlgoritmo(){
        txtAlgoritmo = new Text[26];
        
        txtAlgoritmo[0] = new Text("Algoritmo Bellman-Ford (grafo, nodoIniziale){");
        txtAlgoritmo[1] = new Text("   int[] distanza; boolean[] raggiungibile; string[] predecessore;");
        txtAlgoritmo[2] = new Text("   for each (nodo∈grafo.nodi()){");
        txtAlgoritmo[3] = new Text("      distanza[nodo] = ∞;");
        txtAlgoritmo[4] = new Text("      raggiungibile[nodo] = false;");
        txtAlgoritmo[5] = new Text("      predecessore5[nodo] = NULL;");
        txtAlgoritmo[6] = new Text("   }");
        txtAlgoritmo[7] = new Text("   distanza[nodoIniziale] = 0;");
        txtAlgoritmo[8] = new Text("   raggiungibile[nodoIniziale] = true;");
        txtAlgoritmo[9] = new Text("   coda controllo = new coda();");
        txtAlgoritmo[10] = new Text("   controllo.aggiungi(nodoIniziale);");
        txtAlgoritmo[11] = new Text("   while not controllo.vuoto() do{");
        txtAlgoritmo[12] = new Text("      nodoControllo = controllo.estrai();");
        txtAlgoritmo[13] = new Text("      raggiungibile[nodoControllo] = false;");
        txtAlgoritmo[14] = new Text("      for each (nodoArrivo∈grafo.nodiAdiacenti(nodoControllo)){");
        txtAlgoritmo[15] = new Text("         if distanza[nodoControllo] + costoArco(nodoControllo, nodoArrivo) < distanza[nodoArrivo] {");
        txtAlgoritmo[16] = new Text("            if not raggiungibile[nodoArrivo]{");
        txtAlgoritmo[17] = new Text("               controllo.aggiungi(nodoArrivo);");
        txtAlgoritmo[18] = new Text("               raggiungibile[nodoArrivo] = true;");
        txtAlgoritmo[19] = new Text("            }");
        txtAlgoritmo[20] = new Text("            predecessore[nodoArrivo] = nodoControllo;");
        txtAlgoritmo[21] = new Text("            distanza[nodoArrivo] = distanza[nodoControllo] + costoArco(nodoControllo, nodoArrivo);");
        txtAlgoritmo[22] = new Text("         }");
        txtAlgoritmo[23] = new Text("      }");
        txtAlgoritmo[24] = new Text("   }");
        txtAlgoritmo[25] = new Text("}");
        
        for (Text tA : txtAlgoritmo){
            tA.getStyleClass().add("text");
            boxAlgoritmo.getChildren().add(tA);
        }
        
        boxAlgoritmo.setSpacing(10);
        boxAlgoritmo.getStyleClass().add("conteiner");
        layout.setLeft(boxAlgoritmo);
        layout.setAlignment(boxAlgoritmo, Pos.TOP_RIGHT);
    }
    
    private void visualizzaPulsantiEsecuzione(){
        
        HBox boxBtn = new HBox();
        
        ImageView imgPlay = new ImageView(new Image("/images/play.png", 50, 50, true, true));
        imgPlay.getStyleClass().add("imgButton");
        HBox btnPlay = new HBox();
        btnPlay.getChildren().add(imgPlay);
        Tooltip tooltipPlay = new Tooltip("Esegui tutto l'algoritmo");
        Tooltip.install(btnPlay, tooltipPlay);
        btnPlay.getStyleClass().add("button");
        boxBtn.getChildren().add(btnPlay);
        
        btnPlay.setOnMousePressed(btnMousePressed);
        btnPlay.setOnMouseReleased(btnMouseRealased);
        
        EventHandler<MouseEvent> play = (MouseEvent event) -> {
            a.shortestPath(Integer.parseInt(txtNodoI.getText()));
            visualizzaArray();
        }; 
        btnPlay.setOnMouseClicked(play);
        
        ImageView imgPlayOne = new ImageView(new Image("/images/playOne.png", 50, 50, true, true));
        imgPlayOne.getStyleClass().add("imgButton");
        HBox btnPlayOne = new HBox();
        btnPlayOne.getChildren().add(imgPlayOne);
        Tooltip tooltipPlayOne = new Tooltip("Esegui un solo passo dell'algoritmo");
        Tooltip.install(btnPlayOne, tooltipPlayOne);
        btnPlayOne.getStyleClass().add("button");
        boxBtn.getChildren().add(btnPlayOne);
        btnPlayOne.setOnMousePressed(btnMousePressed);
        btnPlayOne.setOnMouseReleased(btnMouseRealased);
        EventHandler<MouseEvent> playOne = (MouseEvent event) -> {
            if (a.wayqueue.empty())
                a.shortestPathInitialization(Integer.parseInt(txtNodoI.getText()));
            else
                a.shortestPathOneStep();
            visualizzaArray();
        }; 
        btnPlayOne.setOnMouseClicked(playOne);
        
        ImageView imgStop = new ImageView(new Image("/images/stop.png", 50, 50, true, true));
        imgStop.getStyleClass().add("imgButton");
        HBox btnStop = new HBox();
        btnStop.getChildren().add(imgStop);
        Tooltip tooltipStop = new Tooltip("Riazzera l'algoritmo");
        Tooltip.install(btnStop, tooltipStop);
        btnStop.getStyleClass().add("button");
        boxBtn.getChildren().add(btnStop);
        
        btnStop.setOnMousePressed(btnMousePressed);
        btnStop.setOnMouseReleased(btnMouseRealased);
        
        EventHandler<MouseEvent> stop = (MouseEvent event) -> {
            a.shortestPathInitialization(Integer.parseInt(txtNodoI.getText()));
            visualizzaArray();
        }; 
        btnStop.setOnMouseClicked(stop);
        
        boxBtn.setAlignment(Pos.CENTER_RIGHT);
        boxAlgoritmo.getChildren().add(boxBtn);
    }
    
    private void visualizzaPulsantiGrafo(){
        VBox btnGrado = new VBox();

        //bottone nuovo nodo     
        ImageView imgNuovoNodo = new ImageView(new Image("/images/nuovoNodo.png", 1, 1, true, true));        
        Text txtNuovoNodo = new Text("Nuovo Nodo");
        imgNuovoNodo.getStyleClass().add("imgButton");
        txtNuovoNodo.getStyleClass().add("txtButton");
        HBox btnNuovoNodo = new HBox();
        btnNuovoNodo.getChildren().addAll(imgNuovoNodo, txtNuovoNodo);
        btnNuovoNodo.setAlignment(Pos.CENTER_LEFT);
        Tooltip tooltipNuovoNodo = new Tooltip("Aggiungi un nuovo nodo al grafo");
        Tooltip.install(btnNuovoNodo, tooltipNuovoNodo);
        btnNuovoNodo.getStyleClass().add("button");
        btnGrado.getChildren().add(btnNuovoNodo);
        
        btnNuovoNodo.setOnMousePressed(btnMousePressed);
        btnNuovoNodo.setOnMouseReleased(btnMouseRealased);
        
        EventHandler<MouseEvent> newNodo = (MouseEvent event) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Aggiungi Nodo");
            alert.setContentText("Sei sicuro di voler aggiungere un nodo?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                a.g.insertNode(a.g.getnNode());
            }
            
            visualizzaGrafo();
            a.shortestPathInitialization(-1);
            visualizzaArray();
        }; 
        btnNuovoNodo.setOnMouseClicked(newNodo);
        
        //bottone modifica nodo     
        ImageView imgModNodo = new ImageView(new Image("/images/modNodo.png", 50, 50, true, true));        
        Text txtModNodo = new Text("Elimina Nodo");
        imgModNodo.getStyleClass().add("imgButton");
        txtModNodo.getStyleClass().add("txtButton");
        HBox btnModNodo = new HBox();
        btnModNodo.getChildren().addAll(imgModNodo, txtModNodo);
        btnModNodo.setAlignment(Pos.CENTER_LEFT);
        Tooltip tooltipModNodo = new Tooltip("Elimina un nodo già presente nel grafo");
        Tooltip.install(btnModNodo, tooltipModNodo);
        btnModNodo.getStyleClass().add("button");
        btnGrado.getChildren().add(btnModNodo);
        
        btnModNodo.setOnMousePressed(btnMousePressed);
        btnModNodo.setOnMouseReleased(btnMouseRealased);
        
        EventHandler<MouseEvent> modNodo = (MouseEvent event) -> {
            
            Integer [] nodiName = a.g.fillindex();
            
            String [] arrayData = new String[a.g.getnNode()];
            for (int i=0; i<a.g.getnNode(); i++){
                arrayData[i]=""+nodiName[i];
            }
            
            List<String> dialogData;
            
            dialogData = Arrays.asList(arrayData);

            ChoiceDialog dialogNP = new ChoiceDialog(dialogData.get(0), dialogData);
            dialogNP.setTitle("Elimina nodo");
            dialogNP.setHeaderText("Seleziona il nodo da eliminare");

            Optional<String> optNP = dialogNP.showAndWait();
            String nodoPartenza = "";
            if (optNP.isPresent()) {
                nodoPartenza = optNP.get();
            }
            
            a.g.deleteNode(Integer.parseInt(nodoPartenza));
            
            visualizzaGrafo();
            a.shortestPathInitialization(-1);
            visualizzaArray();
        }; 
        btnModNodo.setOnMouseClicked(modNodo);
        
        //bottone modifica arco     
        ImageView imgModArco = new ImageView(new Image("/images/modArco.png", 50, 50, true, true));        
        Text txtModArco = new Text("Gestisci Arco");
        imgModArco.getStyleClass().add("imgButton");
        txtModArco.getStyleClass().add("txtButton");
        HBox btnModArco = new HBox();
        btnModArco.getChildren().addAll(imgModArco, txtModArco);
        btnModArco.setAlignment(Pos.CENTER_LEFT);
        Tooltip tooltipModArco = new Tooltip("Crea/Modifica peso/elimina un nodo già nel grafo");
        Tooltip.install(btnModArco, tooltipModArco);
        btnModArco.getStyleClass().add("button");
        btnGrado.getChildren().add(btnModArco);
        
        btnModArco.setOnMousePressed(btnMousePressed);
        btnModArco.setOnMouseReleased(btnMouseRealased);
        
        EventHandler<MouseEvent> modArco = (MouseEvent event) -> {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Modifica/Elimina arco");
            alert.setContentText("Vuoi eliminare un arco?");

            Integer [] nodiName = a.g.fillindex();
            
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                String [] arrayData = new String[a.g.getnNode()];
                for (int i=0; i<a.g.getnNode(); i++){
                    arrayData[i]=""+nodiName[i];
                }

                List<String> dialogData;

                dialogData = Arrays.asList(arrayData);

                ChoiceDialog dialogNP = new ChoiceDialog(dialogData.get(0), dialogData);
                dialogNP.setTitle("Elimina Arco - Nodo Partenza");
                dialogNP.setHeaderText("Seleziona il nodo di partenza");

                Optional<String> optNP = dialogNP.showAndWait();
                String nodoPartenza = "";
                if (optNP.isPresent()) {
                    nodoPartenza = optNP.get();
                }

                ChoiceDialog dialogNA = new ChoiceDialog(dialogData.get(0), dialogData);
                dialogNA.setTitle("Elimina Arco - Nodo Arrivo");
                dialogNA.setHeaderText("Seleziona il nodo di arrivo");

                Optional<String> optNA = dialogNA.showAndWait();
                String nodoArrivo = "";
                if (optNA.isPresent()) {
                    nodoArrivo = optNA.get();
                }

                if (nodoPartenza!=nodoArrivo){
                    a.g.deleteEdge(Integer.parseInt(nodoPartenza), Integer.parseInt(nodoArrivo));
                }
            }
            else{
                String [] arrayData = new String[a.g.getnNode()];
                for (int i=0; i<a.g.getnNode(); i++){
                    arrayData[i]=""+nodiName[i];
                }

                List<String> dialogData;

                dialogData = Arrays.asList(arrayData);

                ChoiceDialog dialogNP = new ChoiceDialog(dialogData.get(0), dialogData);
                dialogNP.setTitle("Arco - Nodo Partenza");
                dialogNP.setHeaderText("Seleziona il nodo di partenza dell'arco che vuoi creare/modificare");

                Optional<String> optNP = dialogNP.showAndWait();
                String nodoPartenza = "";
                if (optNP.isPresent()) {
                    nodoPartenza = optNP.get();
                }

                ChoiceDialog dialogNA = new ChoiceDialog(dialogData.get(0), dialogData);
                dialogNA.setTitle("Arco - Nodo Arrivo");
                dialogNA.setHeaderText("Seleziona il nodo di arrivo");

                Optional<String> optNA = dialogNA.showAndWait();
                String nodoArrivo = "";
                if (optNA.isPresent()) {
                    nodoArrivo = optNA.get();
                }

                TextInputDialog dialogV = new TextInputDialog("5");
                dialogV.setTitle("Arco - Valore Arco");
                dialogV.setHeaderText("Inseririsci il valore dell'arco");

                Optional<String> optV = dialogV.showAndWait();
                String valore = "";

                if (optV.isPresent()) {
                    valore = optV.get();
                }

                if (nodoPartenza!=nodoArrivo){
                    a.g.insertEdge(Integer.parseInt(nodoPartenza), Integer.parseInt(nodoArrivo), Integer.parseInt(valore));
                }
            }
            
            visualizzaGrafo();
            a.shortestPathInitialization(-1);
            visualizzaArray();
        }; 
        btnModArco.setOnMouseClicked(modArco);
        
        Text divisore = new Text("---------------------------");
        btnGrado.getChildren().add(divisore);
        
        //bottone input da file     
        ImageView imgLoad = new ImageView(new Image("/images/load.png", 50, 50, true, true));        
        Text txtLoad = new Text("Carica Grafo");
        imgLoad.getStyleClass().add("imgButton");
        txtLoad.getStyleClass().add("txtButton");
        HBox btnLoad = new HBox();
        btnLoad.getChildren().addAll(imgLoad, txtLoad);
        btnLoad.setAlignment(Pos.CENTER_LEFT);
        Tooltip tooltipLoad = new Tooltip("Carica il grafo da file esterno");
        Tooltip.install(btnLoad, tooltipLoad);
        btnLoad.getStyleClass().add("button");
        btnGrado.getChildren().add(btnLoad);
        
        btnLoad.setOnMousePressed(btnMousePressed);
        btnLoad.setOnMouseReleased(btnMouseRealased);
        
        EventHandler<MouseEvent> Load = (MouseEvent event) -> {
            a.importGraph(primaryStage);
            
            visualizzaGrafo();
            a.shortestPathInitialization(-1);
            visualizzaArray();
        }; 
        btnLoad.setOnMouseClicked(Load);
        
        //bottone salva su file     
        ImageView imgSave = new ImageView(new Image("/images/save.png", 50, 50, true, true));        
        Text txtSave = new Text("Salva Grafo");
        imgSave.getStyleClass().add("imgButton");
        txtSave.getStyleClass().add("txtButton");
        HBox btnSave = new HBox();
        btnSave.getChildren().addAll(imgSave, txtSave);
        btnSave.setAlignment(Pos.CENTER_LEFT);
        Tooltip tooltipSave = new Tooltip("Salva il grafo in un file esterno");
        Tooltip.install(btnSave, tooltipSave);
        btnSave.getStyleClass().add("button");
        btnGrado.getChildren().add(btnSave);
        
        btnSave.setOnMousePressed(btnMousePressed);
        btnSave.setOnMouseReleased(btnMouseRealased);
        
        EventHandler<MouseEvent> Save = (MouseEvent event) -> {
            a.ExportGraph(a.g, primaryStage, a.parray, a.warray);
        }; 
        btnSave.setOnMouseClicked(Save);
        
        //bottone salva array su file    
        ImageView imgSaveArray = new ImageView(new Image("/images/save.png", 50, 50, true, true));        
        Text txtSaveArray = new Text("Salva Array");
        imgSaveArray.getStyleClass().add("imgButton");
        txtSaveArray.getStyleClass().add("txtButton");
        HBox btnSaveArray = new HBox();
        btnSaveArray.getChildren().addAll(imgSaveArray, txtSaveArray);
        btnSaveArray.setAlignment(Pos.CENTER_LEFT);
        Tooltip tooltipSaveArray = new Tooltip("Salva gli array in un file esterno");
        Tooltip.install(btnSaveArray, tooltipSaveArray);
        btnSaveArray.getStyleClass().add("button");
        btnGrado.getChildren().add(btnSaveArray);
        
        btnSaveArray.setOnMousePressed(btnMousePressed);
        btnSaveArray.setOnMouseReleased(btnMouseRealased);
        
        EventHandler<MouseEvent> SaveArray = (MouseEvent event) -> {
            a.ExportShortEdge(a.g, primaryStage, a.parray, a.warray);
        }; 
        btnSaveArray.setOnMouseClicked(SaveArray);
        
        Text divisore2 = new Text("---------------------------");
        btnGrado.getChildren().add(divisore2);
        
        //bottone grafo random     
        ImageView imgRandom = new ImageView(new Image("/images/random.png", 50, 50, true, true));        
        Text txtRandom = new Text("Grafo Casuale");
        imgRandom.getStyleClass().add("imgButton");
        txtRandom.getStyleClass().add("txtButton");
        HBox btnRandom = new HBox();
        btnRandom.getChildren().addAll(imgRandom, txtRandom);
        btnRandom.setAlignment(Pos.CENTER_LEFT);
        Tooltip tooltipRandom = new Tooltip("Genera un grafo casualmente");
        Tooltip.install(btnRandom, tooltipRandom);
        btnRandom.getStyleClass().add("button");
        btnGrado.getChildren().add(btnRandom);
        
        btnRandom.setOnMousePressed(btnMousePressed);
        btnRandom.setOnMouseReleased(btnMouseRealased);
        
        EventHandler<MouseEvent> random = (MouseEvent event) -> {
            String [] arrayData = new String[15];
            for (int i=1; i<=15; i++){
                arrayData[i-1]=""+i;
            }

            List<String> dialogData;

            dialogData = Arrays.asList(arrayData);

            ChoiceDialog dialogN = new ChoiceDialog(dialogData.get(0), dialogData);
            dialogN.setTitle("Grafo Casuale");
            dialogN.setHeaderText("Seleziona il numero di nodi che deve avere il grafo");

            Optional<String> optN = dialogN.showAndWait();
            if (optN.isPresent()) {
                a.randomGraph(Integer.parseInt(optN.get()));
            }
            
            visualizzaGrafo();
            a.shortestPathInitialization(-1);
            visualizzaArray();
        }; 
        btnRandom.setOnMouseClicked(random);
        
        btnGrado.setStyle("-fx-padding: 17;");
        boxGrafo.getChildren().add(btnGrado);
        boxGrafo.setAlignment(Pos.TOP_RIGHT);
    }
    
    private void visualizzaArray(){
        int numNodi = a.g.getnNode();
        
        boxArray.getChildren().clear();
        
        
        
        txtNodoI = new TextField ();
        HBox boxNodoI = new HBox();
        boxNodoI.getChildren().add(new Text("Nodo di partenza:"));
        boxNodoI.getChildren().add(txtNodoI);
        boxNodoI.setSpacing(10);
        boxArray.getChildren().add(boxNodoI);
        
        if (a.g.getnNode()>0){
            Integer [] nodiName = a.g.fillindex();
            txtNodoI.setText(""+nodiName[0]);
        }
        
        boxArray.setMinHeight(255);
        boxArray.getChildren().add(new Text("-------------------------------------------------------"));
        
        /*HBox BoxCoda = new HBox();
        BoxCoda.getChildren().add(new Text("Coda:                    "));
        for (int i=0; i<numNodi; i++){
            StackPane box = new StackPane();
            box.getChildren().add(a.wayqueue.);
            box.getStyleClass().add("array");
            BoxCoda.getChildren().add(box);
        }
        boxArray.getChildren().add(BoxCoda);
        boxArray.getChildren().add(new Text(""));*/
        
        HBox BoxNomi = new HBox();
        BoxNomi.getChildren().add(new Text("Nodi:                    "));
        for (int i=0; i<numNodi; i++){
            StackPane box = new StackPane();
            
            box.getChildren().add(new Text(""+a.g.fillindex()[i]));
            box.getStyleClass().add("array");
            BoxNomi.getChildren().add(box);
        }
        boxArray.getChildren().add(BoxNomi);
        boxArray.getChildren().add(new Text(""));
        
        HBox BoxDistanza = new HBox();
        BoxDistanza.getChildren().add(new Text("Distanza Minima:  "));
        for (int i=0; i<numNodi; i++){
            StackPane box = new StackPane();
            box.setPrefWidth(30);
            box.getChildren().add(new Text(""+a.warray.getvalue(i)));
            box.getStyleClass().add("array");
            BoxDistanza.getChildren().add(box);
        }
        boxArray.getChildren().add(BoxDistanza);
        boxArray.getChildren().add(new Text(""));
        
        HBox BoxRaggiungibile = new HBox();
        BoxRaggiungibile.getChildren().add(new Text("Raggiungibile:      "));
        for (int i=0; i<numNodi; i++){
            StackPane box = new StackPane();
            box.setPrefWidth(30);
            if (a.barray.getvalue(i))
                box.getChildren().add(new Text("Vero"));
            else
                box.getChildren().add(new Text("Falso"));
            box.getStyleClass().add("array");
            BoxRaggiungibile.getChildren().add(box);
        }
        boxArray.getChildren().add(BoxRaggiungibile);
        boxArray.getChildren().add(new Text(""));
        
        HBox BoxPredecessore = new HBox();
        BoxPredecessore.getChildren().add(new Text("Predecessore:       "));
        for (int i=0; i<numNodi; i++){
            StackPane box = new StackPane();
            box.setPrefWidth(30);
            box.getChildren().add(new Text(""+a.parray.getvalue(i)));
            box.getStyleClass().add("array");
            BoxPredecessore.getChildren().add(box);
        }
        boxArray.getChildren().add(BoxPredecessore);
        
        boxArray.getStyleClass().add("conteiner");
    }
    
    public Grafica(Stage primaryStage){
        this.primaryStage = primaryStage;
        scene.getStylesheets().add("style/scene.css");
        layout.getStyleClass().add("scene");
        
        //Nella sezione top metto il nome del e lo slogan
        InnerShadow shadow = new InnerShadow();
        shadow.setOffsetX(4);
        shadow.setOffsetY(4);
        
        Text titolo = new Text(txtTitolo);
        //titolo.setFont(fontH1);
        //titolo.setTextAlignment(TextAlignment.CENTER);
        
        titolo.setEffect(shadow);
        titolo.getStyleClass().add("title");
        
        layout.setTop(titolo);
        layout.setAlignment(titolo, Pos.CENTER);
        
        //nella barra laterale sx metto l'algoritmo e i pulsanti per l'esecuzione (sia passo a passo che totale)
        visualizzaAlgoritmo();
        visualizzaPulsantiEsecuzione();
        
        //suddivido lo spazio centrale in due zone: la prima in cui viene visualizzato il grafo e i pulsanti per modificarlo, nell'altro i vettori di appoggio dell'algoritmo
        boxCentro.getChildren().addAll(boxGrafo, boxArray);
        layout.setCenter(boxCentro);
        visualizzaGrafo();
        visualizzaArray();
        
        primaryStage.setTitle("ALGRAPH");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
