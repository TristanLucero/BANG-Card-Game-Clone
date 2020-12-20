/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cs_2365_project_3;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;

/**
 * FXML Controller class
 *
 * @author lanegmacdougall
 */
public class GameGUIController implements Initializable {

    @FXML
    private TextArea menuText;
    public static TextArea referenceKeyPublic;
    @FXML
    private Label character1;
    @FXML 
    private Label role1;
    @FXML
    private Label health_curr_1;
    @FXML
    private Label arrow_count_1;
    @FXML
    private Label character3;
    @FXML
    private Label role3;
    @FXML
    private Label health_curr_3;
    @FXML
    private Label arrow_count_3;
    @FXML
    private Label character5;
    @FXML
    private Label role5;
    @FXML
    private Label health_curr_5;
    @FXML
    private Label arrow_count_5;
    @FXML
    private Label character7;
    @FXML
    private Label role7;
    @FXML
    private Label health_curr_7;
    @FXML
    private Label arrow_count_7;
    @FXML
    private Label character2;
    @FXML
    private Label role2;
    @FXML
    private Label health_curr_2;
    @FXML
    private Label arrow_count_2;
    @FXML
    private Label character4;
    @FXML
    private Label role4;
    @FXML
    private Label health_curr_4;
    @FXML
    private Label arrow_count_4;
    @FXML
    private Label character6;
    @FXML
    private Label role6;
    @FXML
    private Label health_curr_6;
    @FXML
    private Label arrow_count_6;
    @FXML
    private Label character8;
    @FXML
    private Label role8;
    @FXML
    private Label health_curr_8;
    @FXML
    private Label arrow_count_8;
    private ToggleButton toggle1;
    private ToggleButton toggle3;
    private ToggleButton toggle2;
    private ToggleButton toggle4;
    @FXML
    private ImageView bangLogo;
    @FXML
    private ImageView characterPic1;
    @FXML
    private ImageView characterPic3;
    @FXML
    private ImageView characterPic5;
    @FXML
    private ImageView characterPic7;
    @FXML
    private ImageView characterPic2;
    @FXML
    private ImageView characterPic4;
    @FXML
    private ImageView characterPic6;
    @FXML
    private ImageView characterPic8;

    private static int loadGameButtonPressed = 0;
    
    @FXML
    private Button loadGame;
    @FXML
    private Button Roll;
    @FXML
    private ToggleButton rerollCheckedDice;
    @FXML
    private MenuItem noExpansionDice;
    @FXML
    private MenuItem expansionDice0;
    @FXML
    private MenuItem expansionDice1;
    @FXML
    private MenuItem expansionDice2;
    @FXML
    private MenuItem expansionDice3;
    @FXML
    private CheckBox reroll0;
    @FXML
    private CheckBox reroll1;
    @FXML
    private CheckBox reroll2;
    @FXML
    private CheckBox reroll3;
    @FXML
    private CheckBox reroll4;
    @FXML
    private ImageView diceImage0;
    @FXML
    private ImageView diceImage1;
    @FXML
    private ImageView diceImage2;
    @FXML
    private ImageView diceImage3;
    @FXML
    private ImageView diceImage4;

    /**
     * Initializes the controller class.
     */
    
    //Variables used for the game class
    Game currGame = new Game();
    int currPlayer = 0, numPlayers = 0, currChar, sheriffIndex = 0; 
    boolean endGame = false;
    
    static private boolean diceToReroll[] = new boolean[5]; 
    static private int expansionDiceUse;
    static private boolean initRollUsed = false;
    static public boolean addonUsed;
    static public int numberOfPlayers;
    @FXML
    private MenuButton expansionDiceSelection;
    @FXML
    private Label lifeStatusLabel1;
    @FXML
    private Label lifeStatusLabel3;
    @FXML
    private Label lifeStatusLabel5;
    @FXML
    private Label lifeStatusLabel7;
    @FXML
    private Label lifeStatusLabel2;
    @FXML
    private Label lifeStatusLabel4;
    @FXML
    private Label lifeStatusLabel6;
    @FXML
    private Label lifeStatusLabel8;
    @FXML
    private Button nextTurn;
    @FXML
    private RadioButton RadioButton8;
    @FXML
    private RadioButton RadioButton7;
    @FXML
    private RadioButton RadioButton6;
    @FXML
    private RadioButton RadioButton5;
    @FXML
    private RadioButton RadioButton4;
    @FXML
    private RadioButton RadioButton3;
    @FXML
    private RadioButton RadioButton2;
    @FXML
    private RadioButton RadioButton1;
    private ToggleGroup numberToggleGroup = new ToggleGroup();;
    public static int radioButtonValue;
    @FXML
    private Button loadAction;
    @FXML
    private Button resolveTurn;
    @FXML
    private Button executeAction;
    
    
    public void initialize(URL url, ResourceBundle rb) {
        // TODO 
        //menuText.setEditable(false);
        GameGUIController.referenceKeyPublic = menuText;
        
        RadioButton1.setToggleGroup(numberToggleGroup);
        RadioButton2.setToggleGroup(numberToggleGroup);
        RadioButton3.setToggleGroup(numberToggleGroup);
        RadioButton4.setToggleGroup(numberToggleGroup);
        RadioButton5.setToggleGroup(numberToggleGroup);
        RadioButton6.setToggleGroup(numberToggleGroup);
        RadioButton7.setToggleGroup(numberToggleGroup);
        RadioButton8.setToggleGroup(numberToggleGroup); 
        
        //Call game here
        //Initalizes the game 
        numPlayers = currGame.StartGame();
        
        // if-statement ensures that the game is only loaded up one time - load procedures
        //      carried out only if button has not yet been pressed (tracked by loadGameButtonPressed static attribute)
        if (GameGUIController.loadGameButtonPressed == 0){
        
        // Arrays of character labels - enables iteration 
        Label[] characterLabels = new Label[]{character1, character2, character3, character4, character5, character6, character7, character8};
        
        Label[] roleLabels = new Label[]{role1, role2, role3, role4, role5, role6, role7, role8};
        
        Label[] currHealthLabels = new Label[]{health_curr_1, health_curr_2, health_curr_3, health_curr_4, health_curr_5, health_curr_6, health_curr_7, health_curr_8};
        
        Label[] arrowLabels = new Label[]{arrow_count_1, arrow_count_2, arrow_count_3, arrow_count_4, arrow_count_5, arrow_count_6, arrow_count_7, arrow_count_8}; 
        
        ImageView[] characterPictures = new ImageView[]{characterPic1, characterPic2, characterPic3, characterPic4, characterPic5, characterPic6, characterPic7, characterPic8};
        
        // Iterate through all of the Character objects in Character.playerList ArrayList and set labels according
        //      to Character object's "personal" information
        for (int i = 0; i < Character.playerList.size(); i++){
            
            // Set the Character object's character label to the name of the character
            characterLabels[i].setText(Character.playerList.get(i).getName());
            
            // Unless the Character object is the Sheriff, set the role label to "Unknown"
            if (Character.playerList.get(i).getRoleNum() == 1)
                roleLabels[i].setText("Sheriff");
            else
                roleLabels[i].setText("Unknown");
            
            // Cannot write an integer to the GUI label; convert current health of character into String 
            
            String currHealthString = String.valueOf(Character.playerList.get(i).getHealth());

            // Cannot write an integer to the GUI label; convert max health of character into String 
            String maxHealthString = String.valueOf(Character.playerList.get(i).getMaxHealth());
            
            currHealthLabels[i].setText(currHealthString + " / " + maxHealthString);
            
            
            // Cannot write an integer to the GUI label; convert arrow count of character into String 
            String arrowCountString = String.valueOf(Character.playerList.get(i).getArrowCount());
            arrowLabels[i].setText(arrowCountString);
            
            // Set character photo according to the character at a given index in Character.playerList attribute
            if (Character.playerList.get(i).getName().equals("Bart Cassidy")){
                Image characterImage = new Image(getClass().getResourceAsStream("Bart Cassidy.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName() == "Apache Kid"){
                Image characterImage = new Image(getClass().getResourceAsStream("Apache Kid.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName().equals("Calamity Janet")){
                Image characterImage = new Image(getClass().getResourceAsStream("Calamity Janet.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName() == "Bill Noface"){
                Image characterImage = new Image(getClass().getResourceAsStream("Bill Noface.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName().equals("Jourdonnais")){
                Image characterImage = new Image(getClass().getResourceAsStream("JOURDONNAIS.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName().equals("Jesse Jones")){
                Image characterImage = new Image(getClass().getResourceAsStream("Jesse Jones.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName() == "Belle Star"){
                Image characterImage = new Image(getClass().getResourceAsStream("Belle Star.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName() == "Greg Digger"){
                Image characterImage = new Image(getClass().getResourceAsStream("Greg Digger.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName().equals("Paul Regret")){
                Image characterImage = new Image(getClass().getResourceAsStream("Paul Regret.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName().equals("Pedro Ramirez")){
                Image characterImage = new Image(getClass().getResourceAsStream("Pedro Ramirez.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName().equals("Rose Doolan")){
                Image characterImage = new Image(getClass().getResourceAsStream("Rose Doolan.png"));
                characterPictures[i].setImage(characterImage);
                
            } else if (Character.playerList.get(i).getName().equals("Vulture Sam")){
                Image characterImage = new Image(getClass().getResourceAsStream("Vulture Sam.png"));
                characterPictures[i].setImage(characterImage);
            }     
        }
        
        // Increments loadButtonPressed by one - game will not be loaded again
        GameGUIController.loadGameButtonPressed += 1;
        
        }
        
        //Used to start gameplay with the sheriff
        for (int i = 0; i < numPlayers; ++i) {
            if (Character.playerList.get(i).getRoleNum() == 1) {
                currPlayer = i;
                sheriffIndex = i;
                break;
            }
        }
        System.out.println(currPlayer);
        //Human player goes first
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            this.setMenuText("Press the roll button to start your turn");
        }
        //AI is first so game runs on next turn button
        else {
            this.setMenuText("AI is sheriff, the game will start when you press next turn button");
        }
        //Tells the user to do start the game based off of what the curr player is 
    }   
    
    public static void setMenuText(String myString){
        referenceKeyPublic.setText(myString);
    }
 
    /*
    Function changes the text on selected menu buttons using the ToggleButton class' 
        .isSelected() and .setText() methods
    */
    public void handleToggle(ActionEvent event){
        
        if (event.getSource() == toggle1){
            if (toggle1.isSelected())
                toggle1.setText("Selected!");
            else
                toggle1.setText("Select");
        } else if (event.getSource() == toggle3)
            if (toggle3.isSelected())
                toggle3.setText("Selected!");
            else
                toggle3.setText("Select");
        else if (event.getSource() == toggle2)
            if (toggle2.isSelected())
                toggle2.setText("True!");
            else
                toggle2.setText("Select For True");
        else if (event.getSource() == toggle4)
            if (toggle4.isSelected())
                toggle4.setText("True!");
            else
                toggle4.setText("Select For True");
    }
    
    /*
    Function "loads" the scene with characters' names, images, roles (if sheriff), health, and arrow counts
        Called when the "Load Game" button on the GUI is pressed
    */
    @FXML
    public void loadGame(){
        
        //Dont need this button i dont think 
        
        
        //Runs the players turn 
        nextTurn();
        
    }
    
    
    /*
    Function sets the text of a label across characters' images if they are:
        (1) a zombie
        (2) the zombie master
        (3) eliminated (dead)
    */
    public void updateLifeStatusLabels(){
        
        Character player; // Temporary Character object reference used in traversal
        
        int i;
        
        // Array of lifeStatus... labels in GUI
        Label[] lifeStatusLabels = new Label[]{lifeStatusLabel1, lifeStatusLabel2, lifeStatusLabel3, lifeStatusLabel4, lifeStatusLabel5, lifeStatusLabel6, lifeStatusLabel7, lifeStatusLabel8};
        
        for (i = 0; i < Character.playerList.size(); i++){
            player = Character.playerList.get(i);
            
            // Set bold label in front of character picture to indicate if the Character object is
            //      a zombie, the zombie master, or eliminated
            //      *Note: label is not visible if none of these conditions are met
            if (player.getZombieStatus() == true) {
                lifeStatusLabels[i].setText("ZOMBIE");
            } else if (player.getZombieMasterStatus() == true) {
                lifeStatusLabels[i].setText("ZOMBIE MASTER");
            } else if ((player.getIsAlive() == false) || (player.getIsZombieDead() == true)) {
                lifeStatusLabels[i].setText("ELIMINATED");  
            }
        }
    }

    public void endGame(Game currGame){
       
        loadGame.setDisable(true);
        Roll.setDisable(true);
        nextTurn.setDisable(true);
        resolveTurn.setDisable(true);
        loadAction.setDisable(true);
        executeAction.setDisable(true);
        rerollCheckedDice.setDisable(true);
        reroll0.setDisable(true);
        reroll1.setDisable(true);
        reroll2.setDisable(true);
        reroll3.setDisable(true);
        reroll4.setDisable(true);
        expansionDiceSelection.setDisable(true);
        RadioButton1.setDisable(true);
        RadioButton1.setDisable(true);
        RadioButton2.setDisable(true);
        RadioButton3.setDisable(true);
        RadioButton4.setDisable(true);
        RadioButton5.setDisable(true);
        RadioButton6.setDisable(true);
        RadioButton7.setDisable(true);
        RadioButton8.setDisable(true);
        //TODO: Disable additional buttons using the above syntax
       
       
        //Used to decide who the winner is based off the zombie outbreak
        //conditions for if there is a zombie outbreak
        
        if (currGame.zombieOutbreak) {
            if (Character.aliveVsZombie() == 1) {
                //humans win
                //FIXME - comment out
                System.out.println("Humans win");
               
                // Output to GUI TextArea
                this.setMenuText("Humans win");
            }
            else if (Character.aliveVsZombie() == -1){
                //zombies win
                //FIXME - comment out
                System.out.println("Zombies win");
               
                // Output to GUI TextArea
                this.setMenuText("Zombies win");
            }
            else {
                //should never be any other output
            }
        }
        //conditions for if there isnt a zombie outbreak
        else {
            //Used for showing who the winner is
            if (currGame.alive[0] == 0 && currGame.alive[1] == 0) {
                if (currGame.alive[2] == 0 && currGame.alive[3] == 1) {
                    //renegade wins since there is only one renegade alive
                    //FIXEME - Comment out
                    System.out.println("Renegade's win");
                   
                    // Output to GUI TextArea
                    this.setMenuText("Renegade's win");
                }
                else {
                    //FIXEME - Comment out
                    System.out.println("Outlaw's win");
                    //outlaw wins since either sheriff is dead and there are outlaws
                    //or sheriff is dead and there is more than one renegade left
                   
                    // Output to GUI TextArea
                    this.setMenuText("Outlaw's win");
                }
            }
            else if(currGame.alive[0] == 0 && currGame.alive[2] != 0) {
                //Outlaws win
                System.out.println("Outlaws win");
               
                // Output to GUI TextArea
                this.setMenuText("Outlaw's win");
            }
            else {
                //sheriff & deputy win since others are dead
                //FIXEME - Comment out
                System.out.println("Sheriff & Deputies win");
               
                // Output to GUI TextArea
                this.setMenuText("Sheriff & Deputies win");
            }
        }

    }
    
    @FXML
    private void initRoll(ActionEvent event) {
        if(initRollUsed == false){
            Dice.rollAllDice();
            updateDice();
            System.out.println("Dice counts: ");
            System.out.println(Dice.gatlingCount);
            System.out.println(Dice.dynamiteCount);
            System.out.println(Dice.dualingCount);
            System.out.println(Dice.bullseye1Count);
            System.out.println(Dice.bullseye2Count);
            System.out.println(Dice.beerCount);
            System.out.println(Dice.brokenArrowCount);
            System.out.println(Dice.bulletCount);
            System.out.println(Dice.whiskeyCount);
            System.out.println(Dice.arrowCount);
            System.out.println("-------------------------");
            
            System.out.println("Dice Array");
            System.out.println(Arrays.toString(Dice.diceArray));
            initRollUsed = true;
            
            //Where inital roll is resolved
            currGame.RollDice(currPlayer);
            updateGUI();
            
            //wont let them reroll if the dynamite was triggered
            if (currGame.dynamiteTriggered) {
                this.setMenuText("Dynamite was triggered! Hit Resolve Turn to continue.");
            }
            //they can still reroll 
            else {
                this.setMenuText("Enter Resolve Turn to end your turn, or check the dice you want to \n re-roll and hit re-roll checked dice");
            }
        }
        //They cant re-roll 
        else {
            this.setMenuText("You can't roll anymore, press Resolve Turn to continue.");
        }
    }

    @FXML
    private void rerollCheckedDice(ActionEvent event) {
        if(initRollUsed == true && currGame.dynamiteTriggered == false && Dice.rerollsPlayer < 2){
            Dice.rerollDice(diceToReroll);
            updateDice();
            
            System.out.println("Dice Array");
            System.out.println(Arrays.toString(Dice.diceArray));
            System.out.println("Dice counts: ");
            System.out.println(Dice.gatlingCount);
            System.out.println(Dice.dynamiteCount);
            System.out.println(Dice.dualingCount);
            System.out.println(Dice.bullseye1Count);
            System.out.println(Dice.bullseye2Count);
            System.out.println(Dice.beerCount);
            System.out.println(Dice.brokenArrowCount);
            System.out.println(Dice.bulletCount);
            System.out.println(Dice.whiskeyCount);
            System.out.println("-------------------------");
            ////Where re-roll is resolved
            currGame.RollDice(currPlayer);
            updateGUI();
            
            //wont let them reroll if the dynamite was triggered
            if (currGame.dynamiteTriggered) {
                this.setMenuText("Dynamite was triggered! Hit Resolve Turn to continue.");
            }
            //they can still reroll 
            else {
                this.setMenuText("Enter Resolve Turn to end your turn, or check the dice you want to \n re-roll and hit re-roll checked dice");
            }
        }
        //They cant re-roll 
        else {
            this.setMenuText("You can't re-roll anymore, press Resolve Turn to continue.");
        }
    }

    @FXML
    private void setExpansionDiceNone(ActionEvent event) {
        if(initRollUsed == false)    
            Dice.dontUseExpansion();
    }

    @FXML
    private void setExpansionDice0(ActionEvent event) {
        if(initRollUsed == false)
            Dice.useExpansion(0);
    }

    @FXML
    private void setExpansionDice1(ActionEvent event) {
        if(initRollUsed == false)
            Dice.useExpansion(1);
    }

    @FXML
    private void setExpansionDice2(ActionEvent event) {
        if(initRollUsed == false)   
            Dice.useExpansion(2);
    }

    @FXML
    private void setExpansionDice3(ActionEvent event) {
        if(initRollUsed == false)
            Dice.useExpansion(3);
    }

    @FXML
    private void rerollDice0(ActionEvent event) {
        if(reroll0.isSelected())
            diceToReroll[0] = true;
        else
            diceToReroll[0] = false;
    }

    @FXML
    private void rerollDice1(ActionEvent event) {
        if(reroll1.isSelected())
            diceToReroll[1] = true;
        else
            diceToReroll[1] = false;
    }

    @FXML
    private void rerollDice2(ActionEvent event) {
        if(reroll2.isSelected())
            diceToReroll[2] = true;
        else
            diceToReroll[2] = false;
    }

    @FXML
    private void rerollDice3(ActionEvent event) {
        if(reroll3.isSelected())
            diceToReroll[3] = true;
        else
            diceToReroll[3] = false;
    }

    @FXML
    private void rerollDice4(ActionEvent event) {
        if(reroll4.isSelected())
            diceToReroll[4] = true;
        else
            diceToReroll[4] = false;
    }
    
    public void updateDice(){
        ImageView diceImageArr[] = new ImageView[] {diceImage0, diceImage1, diceImage2, diceImage3, diceImage4};
        for(int i = 0; i < 5; i++){
            if(Dice.saloonDice[i] == true && i == 1){
                if(Dice.diceArray[i] == 0){
                    Image image = new Image(getClass().getResourceAsStream("brokenArrowDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 1){
                    Image image = new Image(getClass().getResourceAsStream("bulletDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 2){
                    Image image = new Image(getClass().getResourceAsStream("bullseye1DoubleDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 3){
                    Image image = new Image(getClass().getResourceAsStream("bullseye2DoubleDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 4){
                    Image image = new Image(getClass().getResourceAsStream("dynamiteDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 5){
                    Image image = new Image(getClass().getResourceAsStream("gatlingDoubleDice.png"));
                    diceImageArr[i].setImage(image);
                }
            }
            else if(Dice.saloonDice[i] == true && i == 2){
                if(Dice.diceArray[i] == 0){
                    Image image = new Image(getClass().getResourceAsStream("brokenArrowDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 1){
                    Image image = new Image(getClass().getResourceAsStream("beerDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 2){
                    Image image = new Image(getClass().getResourceAsStream("bullseye1Dice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 3){
                    Image image = new Image(getClass().getResourceAsStream("bullseye2Dice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 4){
                    Image image = new Image(getClass().getResourceAsStream("dynamiteDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 5){
                    Image image = new Image(getClass().getResourceAsStream("beerDoubleDice.png"));
                    diceImageArr[i].setImage(image);
                }
            }
            else if(Dice.deadOrAliveDice[i] == true){
                if(Dice.diceArray[i] == 0){
                    Image image = new Image(getClass().getResourceAsStream("arrowDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 1){
                    Image image = new Image(getClass().getResourceAsStream("dynamiteDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 2){
                    Image image = new Image(getClass().getResourceAsStream("whiskeyDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 3){
                    Image image = new Image(getClass().getResourceAsStream("dualDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 4){
                    Image image = new Image(getClass().getResourceAsStream("dualDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 5){
                    Image image = new Image(getClass().getResourceAsStream("gatlingDice.png"));
                    diceImageArr[i].setImage(image);
                }
            }
            else{
                if(Dice.diceArray[i] == 0){
                    Image image = new Image(getClass().getResourceAsStream("arrowDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 1){
                    Image image = new Image(getClass().getResourceAsStream("dynamiteDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 2){
                    Image image = new Image(getClass().getResourceAsStream("bullseye1Dice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 3){
                    Image image = new Image(getClass().getResourceAsStream("bullseye2Dice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 4){
                    Image image = new Image(getClass().getResourceAsStream("beerDice.png"));
                    diceImageArr[i].setImage(image);
                }
                else if(Dice.diceArray[i] == 5){
                    Image image = new Image(getClass().getResourceAsStream("gatlingDice.png"));
                    diceImageArr[i].setImage(image);
                }
            }
        }
    }

    @FXML
    private void nextTurn() {
        //Checks for win status
        
        //for checking the zombie outbreak conditions for winners
        if (currGame.zombieOutbreak) {
            if (Character.aliveVsZombie() == 0) {
                //game goes on
            }
            else if (Character.aliveVsZombie() == 1) {
                //humans win
                endGame(currGame);
            }
            else {
                //zombies win
                endGame(currGame);
            }
        }
        //for normal game play end game conditions
        else {
            //Check if only the sheriff and/or Deputy are alive to end the game
            currGame.WhoAlive();
            if ((currGame.alive[0] >= 1 || currGame.alive[1] >= 1) && 
                    (currGame.alive[2] == 0 && currGame.alive[3] == 0)) {
                endGame(currGame);
            }
            else if (currGame.alive[0] == 0) {
                //sheriff is dead 
                endGame(currGame);
            }

        }
        
        //End game conditions were checked, so now tell them to roll to start their turn
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            this.setMenuText("Press the roll button to start your turn");
        }
        
        //It's an AI's turn so it cycles through their whole turn
        else {
        
            //For playing the characters tern if an AI 
            currChar = Character.playerList.get(currPlayer).getAbilityNum();
            //FIXME - comment out
            System.out.println("Player: " + currPlayer + "'s turn");
            System.out.println("Sheriff is player: " + sheriffIndex);
            System.out.println();

            //After each turn will call the update GUI function and pass in the String to
            //print what functions were used 
            switch(currChar) {
                case 1:     //Bart Cassidy
                    currGame.whatHappenedText = "";
                    currGame.BartCassidy(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 2:     //Calamity Janet
                    currGame.whatHappenedText = "";
                    currGame.CalamityJanet(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 3:     //Jesse Jones
                    currGame.whatHappenedText = "";
                    currGame.JesseJones(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 4:     //Jourdonnais
                    currGame.whatHappenedText = "";
                    currGame.Jourdonnais(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 5:     //Paul Regret
                    currGame.whatHappenedText = "";
                    currGame.PaulRegret(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 6:     //Pedro Ramirez
                    currGame.whatHappenedText = "";
                    currGame.PedroRamirez(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 7:     //Rose Doolan
                    currGame.whatHappenedText = "";
                    currGame.RoseDoolan(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 8:     //Vulture Sam
                    currGame.whatHappenedText = "";
                    currGame.VultureSam(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 9:     //Apache Kid
                    currGame.whatHappenedText = "";
                    currGame.ApacheKid(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 10:    //Bill Noface
                    currGame.whatHappenedText = "";
                    currGame.BillNoface(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 11:    //Belle Star
                    currGame.whatHappenedText = "";
                    currGame.BelleStar(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;

                case 12:    //Greg Digger
                    currGame.whatHappenedText = "";
                    currGame.GregDigger(currPlayer);
                    updateGUI();
                    this.setMenuText("AI's actions");
                    break;   
            }

            //Add this part to the exiquite action to do if the count is = 0
            //to update the curr player if human player


            //will be here for the AI part to update it at the end of the AI turn 
            //used to cycle through the player list 
            while (true) {
                if (currPlayer == (numPlayers - 1)) {
                    currPlayer = 0;
                }
                else {
                    currPlayer ++;
                }

                //For playing with the zombie outbreaks
                if (currGame.pack) {
                    //Used to check if the player is dead or alive
                    if (Character.playerList.get(currPlayer).isAlive() &&
                            Character.playerList.get(currPlayer).getZombieStatus() == false) {
                        break;
                    }
                    //if there is a zombie outbreak, play the turn as a zombie
                    else if (currGame.zombieOutbreak &&
                            Character.playerList.get(currPlayer).getZombieStatus() &&
                            Character.playerList.get(currPlayer).isAlive()) {
                        //call the simplified zombie character turn then go to next player
                        currGame.whatHappenedText = "";
                        currGame.Zombie(currPlayer);
                        updateGUI();
                    }
                    //they are really dead
                    else {
                        //Check if a boneyard card should be drawn 
                        currGame.BoneyardCardCheck(currPlayer);
                        if (currGame.zombieOutbreak && 
                                Character.playerList.get(currPlayer).isAlive()) {
                            //there was a zombie outbreak after the dead person drew a card
                            //play a turn as the zombie then go to next player 
                            currGame.whatHappenedText = "";
                            currGame.Zombie(currPlayer);
                            updateGUI();
                        }
                    }
                }
                //for playing a normal game
                else {
                    //Used to check if the player is dead or alive
                    if (Character.playerList.get(currPlayer).isAlive()) {
                        break;
                    }
                    else {
                        //player is dead so increase player count
                    }
                }
            }
            //AI turn is done and currPlayer has been increased, so output to hit next turn
            currGame.whatHappenedText += "AI Turn played, hit next turn to continue";
            this.setMenuText(currGame.whatHappenedText);
            currGame.whatHappenedText = "";
        }
    }

    @FXML
    private void RadioButton8(ActionEvent event) {
        radioButtonValue = 7;
    }

    @FXML
    private void RadioButton7(ActionEvent event) {
        radioButtonValue = 6;
    }

    @FXML
    private void RadioButton6(ActionEvent event) {
        radioButtonValue = 5;
    }

    @FXML
    private void RadioButton5(ActionEvent event) {
        radioButtonValue = 4;
    }

    @FXML
    private void RadioButton4(ActionEvent event) {
        radioButtonValue = 3;
    }

    @FXML
    private void RadioButton3(ActionEvent event) {
        radioButtonValue = 2;
    }

    @FXML
    private void RadioButton2(ActionEvent event) {
        radioButtonValue = 1;
    }

    @FXML
    private void RadioButton1(ActionEvent event) {
        radioButtonValue = 0;
    }

    @FXML
    private void loadAction(ActionEvent event) {
        currGame.LoadAction(currPlayer);
        
    }

    @FXML
    private void resolveTurn(ActionEvent event) {
        currGame.ResolveTurn(currPlayer);
    }

    @FXML
    private void executeAction(ActionEvent event) {
        currGame.ExecuteAction(currPlayer);
        
        if (currGame.resolveCount == 0) {
            this.setMenuText("No more actions resolve, press next turn to continue");
            updateGUI();
        }
        else {
            this.setMenuText("There are more actions to resolve, press load actions to continue.");
            updateGUI();
            initRollUsed = false;
            //used to cycle through the player list 
            while (true) {
                if (currPlayer == (numPlayers - 1)) {
                    currPlayer = 0;
                }
                else {
                    currPlayer ++;
                }
                //Used to check if the player is dead or alive
                if (Character.playerList.get(currPlayer).isAlive()) {
                    break;
                }
                else {
                    //player is dead so increase player count
                } 
            }
        }
    }

    private void menuText(String myString) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /*
    Function updates character-related labels during gameplay
        Does NOT update character images
    */
    public void updateGUI(){
       
        // Arrays of all character-related labels that need to be updated according to characters' stats
        Label[] characterLabels = new Label[]{character1, character2, character3, character4, character5, character6, character7, character8};
       
        Label[] roleLabels = new Label[]{role1, role2, role3, role4, role5, role6, role7, role8};
       
        Label[] currHealthLabels = new Label[]{health_curr_1, health_curr_2, health_curr_3, health_curr_4, health_curr_5, health_curr_6, health_curr_7, health_curr_8};
       
        Label[] arrowLabels = new Label[]{arrow_count_1, arrow_count_2, arrow_count_3, arrow_count_4, arrow_count_5, arrow_count_6, arrow_count_7, arrow_count_8};
       
        // Array of lifeStatusLabels in GUI - Only visible if killed, a zombie, or the zombie master
        Label[] lifeStatusLabels = new Label[]{lifeStatusLabel1, lifeStatusLabel2, lifeStatusLabel3, lifeStatusLabel4, lifeStatusLabel5, lifeStatusLabel6, lifeStatusLabel7, lifeStatusLabel8};
       
        int i;
       
        // Iterate through all characters
        for (i = 0; i < Character.playerList.size(); i++){
           
            // Set character label according to name
            characterLabels[i].setText(Character.playerList.get(i).getName());
           
            // Always display role of character assigned Sheriff role
            if (Character.playerList.get(i).getRoleNum() == 1)
                roleLabels[i].setText("Sheriff");
            else {
                // If character is alive, set role label as zombie (if a zombie), zombie master (if the zombie master)
                //      or unknown (otherwise)
                if (Character.playerList.get(i).getIsAlive() == true){
                    if (Character.playerList.get(i).getZombieStatus() == true)
                        roleLabels[i].setText("Zombie");
                    else if (Character.playerList.get(i).getZombieMasterStatus() == true)
                        roleLabels[i].setText("Zombie Master");
                    else
                        roleLabels[i].setText("Unknown");
                // If character is eliminated, display role if character was not a zombie or the zombie master
                } else {
                    if (Character.playerList.get(i).getZombieStatus() == false && Character.playerList.get(i).getZombieMasterStatus() == false){
                        if (Character.playerList.get(i).getRoleNum() == 2)
                            roleLabels[i].setText("Deputy");
                        else if (Character.playerList.get(i).getRoleNum() == 3)
                            roleLabels[i].setText("Outlaw");
                        else if (Character.playerList.get(i).getRoleNum() == 4)
                            roleLabels[i].setText("Renegade");
                        else
                            roleLabels[i].setText("Unknown");
                    // Keep role labels as zombie or zombie master, even after character is eliminated
                    } else {
                        if (Character.playerList.get(i).getZombieStatus() == true)
                            roleLabels[i].setText("Zombie");
                        else if (Character.playerList.get(i).getZombieMasterStatus() == true)
                            roleLabels[i].setText("Zombie Master");
                        else
                            roleLabels[i].setText("Unknown");
                    }
                }      
            }
           
           
            // Cannot write an integer to the GUI label; convert current health of character into String
           
            String currHealthString = String.valueOf(Character.playerList.get(i).getHealth());

            // Cannot write an integer to the GUI label; convert max health of character into String
            String maxHealthString = String.valueOf(Character.playerList.get(i).getMaxHealth());
           
            currHealthLabels[i].setText(currHealthString + " / " + maxHealthString);
           
            // Cannot write an integer to the GUI label; convert arrow count of character into String
            String arrowCountString = String.valueOf(Character.playerList.get(i).getArrowCount());
            arrowLabels[i].setText(arrowCountString);

           
            // Set bold label in front of character picture to indicate if the Character object is
            //      a zombie, the zombie master, or eliminated
            //      *Note: label is not visible if none of these conditions are met
            if ((Character.playerList.get(i).getIsAlive() == false) || (Character.playerList.get(i).getIsZombieDead() == true)) {
                lifeStatusLabels[i].setText("ELIMINATED");  
            } else if (Character.playerList.get(i).getZombieStatus() == true) {
                lifeStatusLabels[i].setText("ZOMBIE");
            } else if (Character.playerList.get(i).getZombieMasterStatus() == true) {
                lifeStatusLabels[i].setText("ZOMBIE MASTER");
            }
        }
    }
    
}
