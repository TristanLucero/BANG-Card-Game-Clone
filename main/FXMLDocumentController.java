/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_2365_project_3;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;

/**
 *
 * @author ilirtairi
 */
public class FXMLDocumentController implements Initializable {
    
    private Label label;
    @FXML
    private RadioButton numPlayers4;
    @FXML
    private RadioButton numPlayers5;
    @FXML
    private RadioButton numPlayers6;
    @FXML
    private RadioButton numPlayers7;
    @FXML
    private RadioButton numPlayers8;
    private ToggleGroup numPlayersToggleGroup;
    @FXML
    private RadioButton playNormalGameButton;
    @FXML
    private RadioButton playExpansionGameButton;
    private ToggleGroup typeGameToggleGroup;
    @FXML
    private Button onwardButton;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        numPlayersToggleGroup = new ToggleGroup();
        
        numPlayers4.setToggleGroup(numPlayersToggleGroup);
        numPlayers5.setToggleGroup(numPlayersToggleGroup);
        numPlayers6.setToggleGroup(numPlayersToggleGroup);
        numPlayers7.setToggleGroup(numPlayersToggleGroup);
        numPlayers8.setToggleGroup(numPlayersToggleGroup);
        
        typeGameToggleGroup = new ToggleGroup();
        
        playNormalGameButton.setToggleGroup(typeGameToggleGroup);
        playExpansionGameButton.setToggleGroup(typeGameToggleGroup);
        
        
        
    }    

    @FXML
    public void playNormalGame() {
        if ((numPlayersToggleGroup.getSelectedToggle() != null) && (typeGameToggleGroup.getSelectedToggle() != null)){
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GameGUI.fxml"));
            
                Parent root2 = (Parent) fxmlLoader.load();
            
                Stage gameStage = new Stage();
            
                Scene gameScene = new Scene(root2);
            
                gameStage.setScene(gameScene);
            
                gameStage.show();
                
            
            } catch (IOException ex) {
            
                System.out.println(ex.toString());
            
                System.out.println("Oops! Can't load window");
            }
        }
        
    }

    public void playAddonGame(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AddonGameGUI.fxml"));
            
            Parent root2 = (Parent) fxmlLoader.load();
            
            Stage gameStage = new Stage();
            
            Scene gameScene = new Scene(root2);
            
            gameStage.setScene(gameScene);
            
            gameStage.show();
        } catch (IOException ex) {
            System.out.println("Oops! Can't load window");
            
            return;
        }
    }

    @FXML
    private void numPlayers4(ActionEvent event) {
        GameGUIController.numberOfPlayers = 4;
    }

    @FXML
    private void numPlayers5(ActionEvent event) {
        GameGUIController.numberOfPlayers = 5;
    }

    @FXML
    private void numPlayers6(ActionEvent event) {
        GameGUIController.numberOfPlayers = 6;
    }

    @FXML
    private void numPlayers7(ActionEvent event) {
        GameGUIController.numberOfPlayers = 7;
    }

    @FXML
    private void numPlayers8(ActionEvent event) {
        GameGUIController.numberOfPlayers = 8;
    }

    @FXML
    private void gameTypeNormal(ActionEvent event) {
        GameGUIController.addonUsed = false;
    }

    @FXML
    private void gameTypeExpansion(ActionEvent event) {
        GameGUIController.addonUsed = true;
    }
    
}
