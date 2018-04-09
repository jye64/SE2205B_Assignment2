/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package birds;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author Abdelkader
 */
public class BirdsController implements Initializable {

    @FXML
    private MenuBar mainMenu;
    
    @FXML
    private Pane birdImageView;
    
    @FXML
    private ComboBox birdSize;
    
    @FXML
    private TextField birdName;
          
    @FXML
    private Label birdNameDisplayed;
    
    @FXML
    private Label birdText;
    
    @FXML
    Button find;
    @FXML
    Button first;
    @FXML
    Button next;
    @FXML
    Button previous;
    @FXML
    Button last;
    @FXML
    Button delete;
    @FXML
    Button play;
    @FXML
    Button stop;
     
    private final OrderedDictionary birdDictionary = new OrderedDictionary();
    private BirdRecord birdRecord = new BirdRecord();   
    private Media birdSound;
    private MediaPlayer player;
    String dictionaryFileName = "BirdsDataBase.txt";  
    
    @FXML
    public void loadDictionary(){
        Scanner input;
        try{
            int bird_Size;
            String bird_Name;
            String bird_About;
            String bird_Image;
            String bird_Sound;
            
            input = new Scanner(new File(dictionaryFileName));
            while(input.hasNextLine()){
                for (int i=0; i<3; i++){
                    String temp = input.nextLine();
                    if(temp.length()<=0)
                        continue;
                    bird_Size = Integer.parseInt(temp);
                    bird_Name = input.nextLine();
                    bird_About = input.nextLine();
                    bird_Image = "src/images/"+bird_Name+".jpg";
                    bird_Sound = "src/sounds/"+bird_Name+".mp3";
                    DataKey newKey = new DataKey(bird_Name, bird_Size);
                    BirdRecord newRecord = new BirdRecord(newKey,bird_About,bird_Sound,bird_Image);
                    try{
                        birdDictionary.insert(newRecord);
                    }catch(DictionaryException ex){
                        displayAlert(ex.getMessage());
                    }
                }
            }
            first();
 
        }catch(IOException ex){
            displayAlert(ex.getMessage());
        }
         
    }
    
    @FXML
    public void first(){
        try{
            birdRecord = birdDictionary.smallest();
            update();
        }catch (DictionaryException ex){
            displayAlert(ex.getMessage());
        }   
    }
    
    @FXML
    public void last(){
        try{
            birdRecord = birdDictionary.largest();
            update();
        }catch(DictionaryException ex){
            displayAlert(ex.getMessage());
        }
    }
    
    @FXML
    public void next() throws DictionaryException{
        try{
            birdRecord = birdDictionary.successor(birdRecord.getDataKey());
            update();
        }catch (DictionaryException ex){
            displayAlert(ex.getMessage());
        }
        stop();
    }
    
    @FXML
    public void previous() throws DictionaryException{
        try{
            birdRecord = birdDictionary.predecessor(birdRecord.getDataKey());
            update();
        }catch(DictionaryException ex){
            displayAlert(ex.getMessage());
        }
        stop();
    }

    @FXML
    public void delete() throws DictionaryException{
        try{
            birdDictionary.remove(birdRecord.getDataKey());
            if(birdDictionary.isEmpty()){
                birdNameDisplayed.setText("");
                birdText.setText("");
                birdImageView.getChildren().clear();
                displayAlert("No more birds in the database to show.");
            }else{
                next();
            }
        }catch(DictionaryException ex){
            displayAlert(ex.getMessage());
        }
    }
    
    @FXML
    public void find(){
        DataKey newKey = new DataKey(birdName.getText(), readCombo());
        try{
            birdRecord = birdDictionary.find(newKey);
            update();
        }catch(DictionaryException ex){
            displayAlert(ex.getMessage());
        }
    }
    
    @FXML
    public void play(){
        birdSound = new Media(new File(birdRecord.getSound()).toURI().toString());
        player = new MediaPlayer(birdSound);
        System.out.println(new File(birdRecord.getSound()).toURI().toString());
        player.play();  
        play.setStyle("-fx-background-color: #90EE90");
        stop.setStyle("-fx-background-color: #008000");
    }
    
    @FXML
    public void stop(){
        if(player!=null)
            player.stop();
        stop.setStyle("-fx-background-color: #90EE90");
        play.setStyle("-fx-background-color: #008000");
    }
    
    private void update(){
        birdNameDisplayed.setText(birdRecord.getDataKey().getBirdName());
        birdText.setText(birdRecord.getAbout());
        birdImageView.getChildren().add(new ImageView(new Image(new File(birdRecord.getImage()).toURI().toString())));
    }
    
    private int readCombo(){
        if("Small".equals(birdSize.getValue().toString()))  return 1;
        else if("Medium".equals(birdSize.getValue().toString()))  return 2;
        else if("Large".equals(birdSize.getValue().toString()))  return 3;
        else return 0;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        birdSize.getItems().addAll("Small","Medium","Large");
        delete.setStyle("-fx-background-color: #ff0000; ");
        first.setStyle("-fx-background-color: #90EE90");
        next.setStyle("-fx-background-color: #90EE90");
        previous.setStyle("-fx-background-color: #90EE90");
        last.setStyle("-fx-background-color: #90EE90");
        find.setStyle("-fx-background-color: #87CEFA");
        play.setStyle("-fx-background-color: #008000");
        stop.setStyle("-fx-background-color: #90EE90");
        
    }

    @FXML
    public void exit() {
        Stage stage = (Stage)mainMenu.getScene().getWindow();
        stage.close();
    }
    
    private void displayAlert(String msg) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Alert.fxml"));
            Parent ERROR = loader.load();
            AlertController controller = (AlertController) loader.getController();

            Scene scene = new Scene(ERROR);
            Stage stage = new Stage();
            stage.setTitle("Dictionary Exception");
            stage.setScene(scene);

            stage.getIcons().add(new Image("file:src/birds/WesternLogo.png"));
            controller.setAlertText(msg);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();

        } catch (IOException ex) {

        }
    }
    
 
}
