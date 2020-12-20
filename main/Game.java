//CS 2365 OOP Spring 202 Section 002
//Peter Wharton
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package cs_2365_project_3;

import java.util.*;

/**
 *
 * @author Peter Wharton
 */
public class Game {

    public int arrowDeck = 9;
    public boolean chiefsArrow = true, pack = false, zombieOutbreak = false;
    public boolean dynamiteTriggered = false;
    public int [] boneyardDeck = {0, 0, 1, 1, 1, 1, 1, 1, 2, 2, 2};
    public int numPlayers, numBeers, targetOne, targetTwo, arrows, beforeAlive;
    public int currAlive, playerChoice, boneyardCount, boneyardCard, resolveCount;
    public int action, previousRC;
    public int [] alive = new int [] {1, 0, 0, 0};
    public Scanner scan = new Scanner(System.in);
    String whatHappenedText = new String("");
    
    /**
     * Method to start the game off
     * @return returns the number of players in the game
     */
    public int StartGame() {
        //GameGUIController currGUI = new GameGUIController();
        
        //FIXME - Get the number of players for the game from the GUI
        System.out.println("Enter number of players from 4 - 8");
        //numPlayers = scan.nextInt();
        numPlayers = GameGUIController.numberOfPlayers;
        
       
        
        //FIXME - Get if they want to play with the expansion packs or not from GUI
        System.out.println("Enter true if you want to play with expansions " + 
                "or false if you dont want to.");
        //scan.nextLine();
        //pack = scan.nextBoolean();
        pack = GameGUIController.addonUsed;
        
        //Creates all 16 different characters possible
        Character.initializeAllCharacters(pack);
        
        //Picks a character for each player 
        Character.assignCharacters(numPlayers, pack);
        
        //Picks a role for each player 
        Character.assignRole(numPlayers);
        
        //Assigns the user to a player 
        Character.assignUser();
        
        //currGUI.setMenuText("Testoutput");
        
        //FIXME - Get a start game command from GUI or something here
        //FIXEME - comment out
        System.out.println("Game Started");
        System.out.println();
        
        return numPlayers;
    }
    
    /**
     *Method that gets the number of arrows left in the deck, GUI can use to update this
     * @return the number of arrows in the arrow deck 
     */
    public int GetArrowDeckNum () {
        //FIXME - comment out 
        System.out.println("Arrod Deck: " + arrowDeck);
        return arrowDeck;
    }
    
    /**
     *
     * @return a boolean for if the chiefs arrow is in the deck or not
     */
    public boolean CheckChiefsArrow() {
        //FIXME - comment out
        System.out.println("Cheiefs Arrow in deck: " + chiefsArrow);
        return chiefsArrow;
    }
    
    /**
     * Method for checking who is still alive 
     */
    public void WhoAlive() {
        alive[0] = 0; alive[1] = 0; alive[2] = 0; alive[3] = 0;
        // index 0 = sheriff, 1 = deputy, 2 = outlaw, 3 = renegade
        // if value is 0 means dead, 1 if alive, > 1 more than one alive
        
        for (int i = 0; i < numPlayers; ++i) {
            
            switch(Character.playerList.get(i).getRoleNum()) {
                case 4: 
                    if (Character.playerList.get(i).isAlive()) {
                        alive[3] ++;
                    }
                    break;
                case 3:
                    if (Character.playerList.get(i).isAlive()) {
                        alive[2] ++;
                    }
                    break;
                case 2:
                    if (Character.playerList.get(i).isAlive()) {
                        alive[1] ++;
                    }
                    break;
                case 1: 
                    if (Character.playerList.get(i).isAlive()) {
                        alive[0] ++;
                    }
                    break;
            }
        }
        //FIXME - comment out
        System.out.print("Who's still alive: ");
        for (int i = 0; i < 4; ++i) {
            System.out.print(alive[i] + " ");
        }
        System.out.println();
        System.out.println("0 = Sheriff, 1 = Deputy, 2 = Outlaw, 3 = Renegade");
        System.out.println();
        
    }
    
    /**
     * Method for working through the dice, called by the roll / re - roll button 
     * in the GUI  
     * @param currPlayer The current players index 
     */
    public void RollDice(int currPlayer) {
        //Runs through the dice portion of the turn for both AI and human 
        //Will have specific roll dice methods for if there are specials that effect 
        //the rolling progression 
        
        //For if the player is a zombie
        if (Character.playerList.get(currPlayer).getZombieStatus()) {
            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                //If expansions are being used 
                //For human option to take the chiefs arrow
                if (pack) {
                    //For dealing with the chiefs arrow 
                    ChiefsArrow(currPlayer);
                }

                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                dynamiteTriggered = true;
            }
        }
        //For Apache Kid rolling secquence - special == 9
        else if (Character.playerList.get(currPlayer).getAbilityNum() == 9) {
            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            
            //for special
            else if (Character.playerList.get(currPlayer).hasChiefsArrow()) {
                //nothing happens b/c he already has it
            }
            //currPlayer doesnt have the arrow so he always takes it
            else if (Character.playerList.get(currPlayer).hasChiefsArrow() == false) {
                if (chiefsArrow) {
                    Character.playerList.get(currPlayer).setChiefsArrow(true);
                    chiefsArrow = false;
                }
                else {
                    Character.playerList.get(currPlayer).takeChiefsArrow(numPlayers);
                }
            }
            else {
                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If expansions are being used 
            //For dealing with the broken arrow and bulles dice
            if (pack) {
                //For checking the broken arrow 
                if (Dice.brokenArrowCount != 0) {
                    BrokenArrow(currPlayer);
                }

                //For dealing with the bullet 
                if (Dice.bulletCount != 0) {
                    Bullet(currPlayer);
                }
                
                //For dealing with the whiskey bottle
                if (Dice.whiskeyCount != 0) {
                    //FIXME - comment out
                    System.out.println("Whiskey Applied");
                    Character.playerList.get(currPlayer).changeHealth(Dice.whiskeyCount);
                    Dice.whiskeyCount = 0;
                }
            }
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                dynamiteTriggered = true;
            }
        }
        
        //For Bill Noface rolling process - 10
        else if (Character.playerList.get(currPlayer).getAbilityNum() == 9) {
            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //If expansions are being used 
            //For dealing with the broken arrow and bulles dice
            if (pack) {
                //For checking the broken arrow 
                if (Dice.brokenArrowCount != 0) {
                    BrokenArrow(currPlayer);
                }

                //For dealing with the bullet 
                if (Dice.bulletCount != 0) {
                    Bullet(currPlayer);
                }
                
                //For dealing with the whiskey bottle
                if (Dice.whiskeyCount != 0) {
                    //FIXME - comment out
                    System.out.println("Whiskey Applied");
                    Character.playerList.get(currPlayer).changeHealth(Dice.whiskeyCount);
                    Dice.whiskeyCount = 0;
                }
            }
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                dynamiteTriggered = true;
            }
        }
        
        //For Belle Star rolling progress - 11
        else if (Character.playerList.get(currPlayer).getAbilityNum() == 9) {
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                //If expansions are being used 
                //For human option to take the chiefs arrow
                if (pack) {
                    //For dealing with the chiefs arrow 
                    ChiefsArrow(currPlayer);
                }

                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If expansions are being used 
            //For dealing with the broken arrow and bulles dice
            if (pack) {
                //For checking the broken arrow 
                if (Dice.brokenArrowCount != 0) {
                    BrokenArrow(currPlayer);
                }

                //For dealing with the bullet 
                if (Dice.bulletCount != 0) {
                    Bullet(currPlayer);
                }
                
                //For dealing with the whiskey bottle
                if (Dice.whiskeyCount != 0) {
                    //FIXME - comment out
                    System.out.println("Whiskey Applied");
                    Character.playerList.get(currPlayer).changeHealth(Dice.whiskeyCount);
                    Dice.whiskeyCount = 0;
                }
            }
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                dynamiteTriggered = true;
            }
            //For special and changing one dynamite to gat gun
            else if (Dice.dynamiteCount > 0) {
                Dice.dynamiteToGatling();
                //GameGUIController.updateDice();         //FIXME -  
            }
            
            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
        }
        //For Greg Digger player menu/running - 12
        else if (Character.playerList.get(currPlayer).getAbilityNum() == 12) {
            //For galting gun 
            if (Dice.gatlingReady() && Dice.gatlingUsed == false) {
                GatlingAttack(currPlayer);
                Dice.gatlingUsed = true;
                //Sets arrow count to zero if gatling gun goes off
                arrows = Character.playerList.get(currPlayer).getArrowCount();
                this.arrowDeck += arrows;
                Character.playerList.get(currPlayer).setArrowCount(-1 * arrows);
                arrows = 0;
            }
            
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                //If expansions are being used 
                //For human option to take the chiefs arrow
                if (pack) {
                    //For dealing with the chiefs arrow 
                    ChiefsArrow(currPlayer);
                }

                if (arrows == arrowDeck) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                }
                else if (arrows > arrowDeck) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
            }
            arrows = 0;
            Dice.arrowCount = 0;
            
            //If expansions are being used 
            //For dealing with the broken arrow and bulles dice
            if (pack) {
                //For checking the broken arrow 
                if (Dice.brokenArrowCount != 0) {
                    BrokenArrow(currPlayer);
                }

                //For dealing with the bullet 
                if (Dice.bulletCount != 0) {
                    Bullet(currPlayer);
                }
                
                //For dealing with the whiskey bottle including special 
                if (Dice.whiskeyCount != 0) {
                    //FIXME - comment out
                    System.out.println("Whiskey Applied");
                    Character.playerList.get(currPlayer).changeHealth( 2 * Dice.whiskeyCount);
                    Dice.whiskeyCount = 0;
                }
            }
            
            //If there are three or more dynamites rolled, players turn ends 
            //They still count their cards though but not if they died 
            if (Dice.dynamiteReady()) {
                DynamiteAttack(currPlayer);
                dynamiteTriggered = true;
            }
        }
        //if character special doesnt effect the rolls
        else {
            //For arrows and Indian attack
            arrows = Dice.arrowCount;
            if (arrows == 0) {
            }
            else {
                //If expansions are being used 
                if (pack && Character.playerList.get(currPlayer).isAlive()) {
                    //For dealing with the chiefs arrow 
                    ChiefsArrow(currPlayer);                //user option removed 
                }

                if (arrows == arrowDeck && Character.playerList.get(currPlayer).isAlive()) {
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                    IndianAttack();
                }
                else if (arrows > arrowDeck && Character.playerList.get(currPlayer).isAlive()) {
                    int tempArrows = arrowDeck;
                    Character.playerList.get(currPlayer).changeArrows(tempArrows);
                    arrowDeck -= tempArrows;
                    arrows -= tempArrows; 
                    IndianAttack();
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else if (Character.playerList.get(currPlayer).isAlive()){
                    Character.playerList.get(currPlayer).changeArrows(arrows);
                    arrowDeck -= arrows;
                }
                else {
                    //player is dead so nothing happens
                }

                arrows = 0;
                Dice.arrowCount = 0;

                //If expansions are being used 
                //For dealing with the broken arrow and bulles dice
                if (pack && Character.playerList.get(currPlayer).isAlive()) {
                    //For checking the broken arrow 
                    if (Dice.brokenArrowCount != 0) {
                        BrokenArrow(currPlayer);
                    }

                    //For dealing with the bullet 
                    if (Dice.bulletCount != 0) {
                        Bullet(currPlayer);
                    }

                    //For dealing with the whiskey bottle
                    if (Dice.whiskeyCount != 0) {
                        //FIXME - comment out
                        System.out.println("Whiskey Applied");
                        Character.playerList.get(currPlayer).changeHealth(Dice.whiskeyCount);
                        Dice.whiskeyCount = 0;
                    }
                }
                //If there are three or more dynamites rolled, players turn ends 
                //They still count their cards though but not if they died 
                if (Dice.dynamiteReady()) {
                    DynamiteAttack(currPlayer);
                    dynamiteTriggered = true;
                }
            }
        }
    }
    
    /**
     * Method that is called when resolve turn button is pressed
     * Will add up all the counts for beers, targets, and duels so the user can select
     * who they want to attack
     * @param currPlayer The current players index
     */
    public void ResolveTurn(int currPlayer) {
        //Check to make sure they arent dead
        resolveCount = 0;
        if (Character.playerList.get(currPlayer).isAlive()) {
            numBeers = Dice.beerCount;
            resolveCount += numBeers;
            
            targetOne = Dice.bullseye1Count;
            resolveCount += targetOne;
            
            targetTwo = Dice.bullseye2Count;
            resolveCount += targetTwo;
            
            resolveCount += Dice.dualingCount;
        }
        //Print statement telling the user to click the load action button for 
        //first option if any, or hit next turn b/c the count is == 0
        if (resolveCount == 0) {
            //Nothing to resolve, hit next turn button
            GameGUIController.setMenuText("No actions are required, press Next Turn to continue.");
        }
        else {
            //resolveCount + " things to resolve, hit load action buttont to start
            GameGUIController.setMenuText("There are actions to resolve, press load action to resolve them.");
        }
    }
    
    /** 
     * Method that runs through loading one action and gets the players choice from it
     * @param currPlayer The current index of the player 
     */
    public void LoadAction(int currPlayer) {
        //if there are still more things to resolve at the end of their turn
        System.out.println(currPlayer);
        if (resolveCount != 0) {
            
            //cycle through who they want to hit with target one first 
            //so gets their selection of who they want to attack using target one
            //takes into account the player specials 
            if (targetOne != 0) {
                //Calamity Janet specials
                action = 1;
                if (Character.playerList.get(currPlayer).getAbilityNum() == 2) { 
                    CalamityJanetSpecialOne(currPlayer);
                    //output their options and what buttons to choose 
                }
                //Rose Doolan specials
                else if (Character.playerList.get(currPlayer).getAbilityNum() == 7){
                    RoseDoolanSpecialOne(currPlayer);
                    //output their options and what buttons to choose
                }
                //No special effecting it
                else {
                    NormalRangeOne(currPlayer);
                    //output their options and what buttons to choose
                }
                targetOne --;
                resolveCount --;
            }
            
            //cycle through who they want to hit with target two first
            //so gets their selction of who they want to attack using target thwo 
            //takes into account the player specials 
            else if (targetTwo != 0) {
                action = 2;
                //Calamity Janet specials
                if (Character.playerList.get(currPlayer).getAbilityNum() == 2) { 
                    CalamityJanetSpecialTwo(currPlayer);
                    //output their options and what buttons to choose 
                }
                //Rose Doolan specials
                else if (Character.playerList.get(currPlayer).getAbilityNum() == 7){
                    RoseDoolanSpecialTwo(currPlayer);
                    //output their options and what buttons to choose 
                }
                //No special effecting it
                else {
                    NormalRangeTwo(currPlayer);
                    //output their options and what buttons to choose 
                }
                targetTwo --;
                resolveCount --;
            }
            
            //Cycles through who they want to heal with the beers
            //so gets the index of who they want to heal from the buttons
            else if (numBeers != 0) {
                action = 3;
                WhoToHeal(currPlayer);
                //output their options and what buttons to choose 
                numBeers -= 1;
                resolveCount --;
            }
            
            //Cycles through who they want to duel 
            else {
                action = 4;
                WhoToDuel(currPlayer);
                //output their options and what buttons to choose 
                resolveCount --;
            }
        }
    }
    
    /** 
     * Method that runs through loading one action and gets the players choice from it
     * @param currPlayer The current index of the player 
     */
    public void ExecuteAction(int currPlayer) {
        
        //based off what action to do calls that method
        switch(action) {
            case 1:
                //target one
                //for human player
                if (Character.playerList.get(currPlayer).getUser() == 0) {
                    TargetOne(GameGUIController.radioButtonValue);
                }
                //for AI player
                else {
                    TargetOneAi(currPlayer);
                }
                break;
                
            case 2:
                //target two
                //for human player
                if (Character.playerList.get(currPlayer).getUser() == 0) {
                    TargetTwo(GameGUIController.radioButtonValue);
                }
                //for AI player
                else {
                    TargetTwoAi(currPlayer);
                }
                break;
                
            case 3:
                //beers
                //for human player
                if (Character.playerList.get(currPlayer).getUser() == 0) {
                    ApplyBeers(currPlayer, GameGUIController.radioButtonValue);
                }
                //for AI player
                else {
                    ApplyBeers(currPlayer, 0);
                }
                break;
                
            case 4:
                //duels
                //for human player
                if (Character.playerList.get(currPlayer).getUser() == 0) {
                    Duel(currPlayer, GameGUIController.radioButtonValue, 1);
                }
                //for AI player
                else {
                    Duel(currPlayer, 0, Dice.dualingCount);
                }
                break;
                
            default:
                //Nothing else to do 
        }
        
    }
    /**
     * Method that shuffles the boneyardDeck
     */
    public void ShuffleBoneyard() {
        Random rand = new Random();
        
        for (int i = 0; i < 11; ++i) {
            int index = i + rand.nextInt(11 - i);
            
            int temp = boneyardDeck[index];
            boneyardDeck[index] = boneyardDeck[i];
            boneyardDeck[i] = temp;
        }
    }
    
    /**
     * Bart Cassidy player menu/running - 1
     * @param currPlayer The current players index 
     */
    public void BartCassidy(int currPlayer) {
        //speical of taking an arrow instead of losing health
            //except for indian or dynamite attack, and cant be last arrow
            
        System.out.println("Bart Cassidy played");
        whatHappenedText = whatHappenedText + "Bart Cassidy played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Calamity Janet player menu/running - 2
     * @param currPlayer The current players index 
     */
    public void CalamityJanet(int currPlayer) {
        //Can use a 1 target as a 2, or a 2 target as 1
        
        System.out.println("Calamity Janet played");
        whatHappenedText = whatHappenedText + "Calamity Janet played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Jesse Jones player menu/running - 3
     * @param currPlayer The current players index 
     */
    public void JesseJones(int currPlayer) {
        //4 or less health you can heal for 2 life per bear rolled  
         
        System.out.println("Jesse Jones played");
        whatHappenedText = whatHappenedText + "Jesse Jones played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Jourdonnais player menu/running - 4
     * @param currPlayer The current players index 
     */
    public void Jourdonnais(int currPlayer) {
        //only lose one health from indians (special done in Indian Attack)
                
        System.out.println("Jourdonnais played");
        whatHappenedText = whatHappenedText + "Jourdonnais played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Paul Regret player menu/running - 5
     * @param currPlayer The current players index 
     */
    public void PaulRegret(int currPlayer) {
        //never lose health from gatling Gun (Done in GatlingAttack)
                
        System.out.println("Paul Regret played");
        whatHappenedText = whatHappenedText + "Jourdonnais played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Pedro Ramirez player menu/running - 6
     * @param currPlayer The current players index 
     */
    public void PedroRamirez(int currPlayer) {
        //if you lose life, you can dicard an arrow
                
        System.out.println("Pedro Ramirez played");
        whatHappenedText = whatHappenedText + "Pedro Ramirez played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Rose Doolan player menu/running - 7
     * @param currPlayer The current players index 
     */
    public void RoseDoolan(int currPlayer) {
        //target can go one space farther 
                
        System.out.println("Rose Doolan played");
        whatHappenedText = whatHappenedText + "Rose Doolan played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Vulture Sam player menu/running - 8
     * @param currPlayer The current players index 
     */
    public void VultureSam(int currPlayer) {
        //when someone dies you gain two life points (special where health is lost) 
        
        System.out.println("Vulture Same played");
        whatHappenedText = whatHappenedText + "Vulture Same played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Apache Kid player menu/running - 9
     * @param currPlayer The current players index 
     */
    public void ApacheKid(int currPlayer) {
        //if you roll arrow you can take the chief's arrow from another player
                
        System.out.println("Apache Kid played");
        whatHappenedText = whatHappenedText + "Apache Kid played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Bill Noface player menu/running - 10
     * @param currPlayer The current players index 
     */
    public void BillNoface(int currPlayer) {
        //apply arrow results only after your last roll
                
        System.out.println("Bill Noface played");
        whatHappenedText = whatHappenedText + "Bill Noface played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //For special, arrows happens at the end of the turn
                //For arrows and Indian attack
                arrows = Dice.arrowCount;
                if (arrows == 0) {
                }
                else {
                    //If expansions are being used 
                    //For human option to take the chiefs arrow
                    if (pack) {
                        //For dealing with the chiefs arrow 
                        ChiefsArrow(currPlayer);
                    }

                    if (arrows == arrowDeck) {
                        Character.playerList.get(currPlayer).changeArrows(arrows);
                        arrowDeck -= arrows;
                        IndianAttack();
                    }
                    else if (arrows > arrowDeck) {
                        int tempArrows = arrowDeck;
                        Character.playerList.get(currPlayer).changeArrows(tempArrows);
                        arrowDeck -= tempArrows;
                        arrows -= tempArrows; 
                        IndianAttack();
                        if (Character.playerList.get(currPlayer).isAlive() != true) {
                        }
                        else {
                            Character.playerList.get(currPlayer).changeArrows(arrows);
                            arrowDeck -= arrows;
                        }

                    }
                    else {
                        Character.playerList.get(currPlayer).changeArrows(arrows);
                        arrowDeck -= arrows;
                    }
                }
                arrows = 0;
                Dice.arrowCount = 0;
                
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Belle Star player menu/running - 11
     * @param currPlayer The current players index 
     */
    public void BelleStar(int currPlayer) {
        //after each roll you can change one dynamite to gatting gun
                    //not if you roll 3 or more dynamite
                
        System.out.println("Belle Star played");
        whatHappenedText = whatHappenedText + "Belle Star played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Greg Digger player menu/running - 12
     * @param currPlayer The current players index 
     */
    public void GregDigger(int currPlayer) {
        //you can use the whiskey bottle twice for each time you roll it 
                
        System.out.println("Greg Digger played");
        whatHappenedText = whatHappenedText + "Greg Digger played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            if (pack) {
                Dice.useExpansion(1);
                Dice.rollAllDice();
            }
            else {
                Dice.dontUseExpansion();
                Dice.rollAllDice();
            }
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;

                //for resolving the dueling guns 
                if (pack) {
                    if (Dice.dualingCount > 0) {
                        Duel(currPlayer, 0, Dice.dualingCount);
                    }
                }
            }

            //Check if a boneyard card should be drawn 
            BoneyardCardCheck(currPlayer);
        }
    }
    
    /**
     * Method for playing a zombie character
     * @param currPlayer The current players index
     */
    public void Zombie(int currPlayer) {
        //no specials, only roll 3 main dice, they cant be healed 
        
        System.out.println("Zombie played");
        whatHappenedText = whatHappenedText + "Zombie played \n";
        //for checking the options that were checked on a roll/ reroll
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //Human player should never get into these so nothing happens
        }
        
        //runs through the whole game if it's an AI player
        if (Character.playerList.get(currPlayer).getUser() == 1) {
            Dice.dontUseExpansion();
            Dice.rollAllDice();
            RollDice(currPlayer);
            //Checks if they died in the middle of their turn 
            if (Character.playerList.get(currPlayer).isAlive() != true) {
                }
            else {
                //gets the number of beers for finishing turn
                numBeers = Dice.beerCount;
                if (numBeers > 0) {
                    //Applies beers as wanted
                    ApplyBeers(currPlayer, 0);
                }

                //check target one's and applies those changes 
                targetOne = Dice.bullseye1Count;
                while (targetOne != 0) {
                    TargetOneAi(currPlayer);
                }

                //check target two's and applies those changes
                targetTwo = Dice.bullseye2Count;
                while (targetTwo != 0) {
                    TargetTwoAi(currPlayer);
                }
                Dice.gatlingUsed = false;
            }
        }
    }
     
    /**
     * For when Bart Cassidy takes damage from other than Indian attack or dynamite
     * @param playerType For human or AI
     * @param playerIndex The current players index
     */
    public void BartCassidySpecial(int playerType, int playerIndex) {
        //FIXME - commnet out
        System.out.println("BartCassidySpecial");
        whatHappenedText = whatHappenedText + "Bart Cassidy Special \n";
        
        if (playerType == 1 || playerType == 0) { //AI || Human
            if (arrowDeck > 1) {
                Character.playerList.get(playerIndex).changeArrows(1);
                arrowDeck --;
            }
            else {
                Character.playerList.get(playerIndex).changeHealth(-1);
            }
        } 
    }
    
    /**
     * Calamity Janet Special selection of who can be attacked and if they want to use
     * one as one or two
     * @param currPlayer The index of the current player
     */
    public void CalamityJanetSpecialOne(int currPlayer) {
        int [] options = new int[6];    
        //index 0 and 1 are for player options if they want to use one as a one
        //index 2 and 3 are for player options if they want to use one as a two 
        
        //below is what the output text should say 
        //System.out.println("Enter a 1 if you want to use 1 as a 1, enter 2 if you want to use 1 as a 2.");
        //First output text option to be called so index 4 is 1
        options[4] = 1;
        
        //A target one action so index 5 is a one
        options[5] = 1;
        
        //they want to use one as a one these are the two options                   
        //Finds starting index for options
        if (currPlayer == numPlayers - 1) {
            options[0] = 0;
        }
        else {
            options[0] = currPlayer + 1;
        }

        if (currPlayer == 0) {
            options[1] = numPlayers - 1;
        }
        else {
            options[1] = currPlayer - 1;
        }

        //To find the first person they can attack
        while (Character.playerList.get(options[0]).isAlive() == false) {
            options[0] ++;
            if (options[0] >= numPlayers) {
                options[0] = 0;
            }
        }
        //To find the second person they can attack
        while (Character.playerList.get(options[1]).isAlive() == false) {
            options[1] --;
            if (options[1] <= 0) {
                options[1] = numPlayers - 1;
            }
        } 
        
        //They want to use one as a two
        //Finds starting index for options
        if (currPlayer == numPlayers - 1) {
            options[2] = 1;
        }
        else if (currPlayer == numPlayers - 2) {
            options[2] = 0;
        }
        else {
            options[2] = currPlayer + 2;
        }

        if (currPlayer == 0) {
            options[3] = numPlayers - 2;
        }
        else if (currPlayer == 1) {
            options[3] = numPlayers - 1;
        }
        else {
            options[3] = currPlayer - 2;
        }

        //To find the first person they can attack
        while (Character.playerList.get(options[2]).isAlive() == false) {
            options[2] ++;
            if (options[2] >= numPlayers) {
                options[2] = 0;
            }
        }
        //To find the second person they can attack
        while (Character.playerList.get(options[3]).isAlive() == false) {
            options[3] --;
            if (options[3] <= 0) {
                options[3] = numPlayers - 1;
            }
        }
        
        String s = "Select the player you want to attack, your options are: " + Integer.toString(options[0] + 1) 
                + ", " + Integer.toString(options[1] + 1) + ", " + Integer.toString(options[2] + 1) + ", " + Integer.toString(options[3] + 1);
        
        GameGUIController.setMenuText(s);
    }
    
    /**
     * Rose Doolan Special selection of who can be attacked and if they want to use
     * one as one or two
     * @param currPlayer The index of the current player
     */
    public void RoseDoolanSpecialOne(int currPlayer) {
        int [] options = new int[6];        
        //Options index 1 and 2 are if they want to use 1 as a 1
        //Options index 3 and 4 are if they want to use 1 as a 2
        
        //What the output should say as far as their choice so index 4 is their choice
        //System.out.println("Enter a 1 if you want to use 1 as a 1, enter 2 if you want to use 1 as a 2.");
        options[4] = 1;
        
        //A target one action so index 5 is a one
        options[5] = 1;
        
        //they want to use one as a one                 
        //Finds starting index for options
        if (currPlayer == numPlayers - 1) {
            options[0] = 0;
        }
        else {
            options[0] = currPlayer + 1;
        }

        if (currPlayer == 0) {
            options[1] = numPlayers - 1;
        }
        else {
            options[1] = currPlayer - 1;
        }

        //For no special effecting choice
        //To find the first person they can attack
        while (Character.playerList.get(options[0]).isAlive() == false) {
            options[0] ++;
            if (options[0] >= numPlayers) {
                options[0] = 0;
            }
        }
        //To find the second person they can attack
        while (Character.playerList.get(options[1]).isAlive() == false) {
            options[1] --;
            if (options[1] <= 0) {
                options[1] = numPlayers - 1;
            }
        }
        
        //They want to use one as a two
        //Finds starting index for options
        if (currPlayer == numPlayers - 1) {
            options[2] = 1;
        }
        else if (currPlayer == numPlayers - 2) {
            options[2] = 0;
        }
        else {
            options[2] = currPlayer + 2;
        }

        if (currPlayer == 0) {
            options[3] = numPlayers - 2;
        }
        else if (currPlayer == 1) {
            options[3] = numPlayers - 1;
        }
        else {
            options[3] = currPlayer - 2;
        }

        //To find the first person they can attack
        while (Character.playerList.get(options[2]).isAlive() == false) {
            options[2] ++;
            if (options[2] >= numPlayers) {
                options[2] = 0;
            }
        }
        //To find the second person they can attack
        while (Character.playerList.get(options[3]).isAlive() == false) {
            options[3] --;
            if (options[3] <= 0) {
                options[3] = numPlayers - 1;
            }
        } 
        
        String s = "Select the player you want to attack, your options are: " + Integer.toString(options[0] + 1) 
                + ", " + Integer.toString(options[1] + 1) + ", " + Integer.toString(options[2] + 1) + ", " + Integer.toString(options[3] + 1);
        
        GameGUIController.setMenuText(s);
    }
    
    /**
     * For when Pedro Ramirez takes damage
     * @param playerIndex The current players index
     */
    public void PedroRamirezSpecial(int playerIndex) {
        //FIXME - commnet out
        System.out.println("PedroRamirezSpecial");
        whatHappenedText = whatHappenedText + "Pedro Ramirez Special \n";
        
        if (Character.findChiefsArrow(numPlayers) == playerIndex) {
            if (Character.playerList.get(playerIndex).getArrowCount() > 2) {
            Character.playerList.get(playerIndex).changeArrows(-1);
            arrowDeck ++;
            }
        }
        else {
            if (Character.playerList.get(playerIndex).getArrowCount() > 0) {
            Character.playerList.get(playerIndex).changeArrows(-1);
            arrowDeck ++;
            }
        }
        
    }
    
    /**
     * For no special, selection of who can be attacked for one
     * @param currPlayer The index of the current player
     */
    public void NormalRangeOne(int currPlayer) {
        int [] options = new int [6];
        //Options index 1 and 2 are their two options
        //Options index 3 and 4 are just zeros b/c they arent options
        options[2] = 0;
        options[3] = 0;
        
        //What the output should say as far as their choice so index 4 is their choice
        //System.out.println("you can attack");
        options[4] = 4;
        
        //A target one action so index 5 is a one
        options[5] = 1;
        
        //Finds starting index for options
            if (currPlayer == numPlayers - 1) {
                options[0] = 0;
            }
            else {
                options[0] = currPlayer + 1;
            }

            if (currPlayer == 0) {
                options[1] = numPlayers - 1;
            }
            else {
                options[1] = currPlayer - 1;
            }

            //For no special effecting choice
            //To find the first person they can attack
            while (Character.playerList.get(options[0]).isAlive() == false) {
                options[0] ++;
                if (options[0] >= numPlayers) {
                    options[0] = 0;
                }
            }
            //To find the second person they can attack
            while (Character.playerList.get(options[1]).isAlive() == false) {
                options[1] --;
                if (options[1] <= 0) {
                    options[1] = numPlayers - 1;
                }
            } 
            
        String s = "Select the player you want to attack, your options are: " + Integer.toString(options[0] + 1) 
                + ", " + Integer.toString(options[1] + 1);
        
        GameGUIController.setMenuText(s);
    }
    
    /**
     * Method that will shoot the target one, takes in the amount of targets, 
     * and the player to hit
     * @param playerChoice For the target player that was chosen 
     */
    public void TargetOne(int playerChoice) {
        //FIXME - commnet out
        System.out.println("TargetOne");
        
        
        if (Character.isVultureSamPresent() != -1) {
            //For special to count alive 
            beforeAlive = Character.countAlive();
        }
        
        //For attacking the player chosen 
        
        //Bart Cassidy Special for human
        if (Character.playerList.get(playerChoice).getAbilityNum() == 1 &&
                Character.playerList.get(playerChoice).getUser() == 0) {  
            BartCassidySpecial(0, playerChoice);
        }
        //Pedro Ramirez Special for both human and AI 
        else if (Character.playerList.get(playerChoice).getAbilityNum() == 6) {
            PedroRamirezSpecial(playerChoice);
            Character.playerList.get(playerChoice).changeHealth(-1);
        }
        else {
            Character.playerList.get(playerChoice).changeHealth(-1);
        }
        
        //For Vulture Sam's special to count alive
        if (Character.isVultureSamPresent() != -1) {
            int samsIndex = Character.isVultureSamPresent();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        
        //updates GUI for health, alive/dead
    }
    
    /**
     * Method for shoot Target One if AI is playing
     * @param currPlayer The current players index
     */
    public void TargetOneAi(int currPlayer) {
        //FIXME - commnet out
        System.out.println("TargetOneAi");
        whatHappenedText = whatHappenedText + "Target One Ai \n";
        
        int optionOne;
        
        //Just shoots first person they can right now
        //Can update to have strategy in it as well
        
        if (Character.isVultureSamPresent() != -1) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        
        //Finds starting index for options
        if (currPlayer == numPlayers - 1) {
            optionOne = 0;
        }
        else {
            optionOne = currPlayer + 1;
        }

        //For no special effecting choice
        //To find the first person they can attack
        while (Character.playerList.get(optionOne).isAlive() == false) {
            optionOne ++;
            if (optionOne >= numPlayers) {
                optionOne = 0;
            }
        }

        //Bart Cassidy Special for AI
        if (Character.playerList.get(optionOne).getAbilityNum() == 1 &&
                Character.playerList.get(optionOne).getUser() == 1) { 
            BartCassidySpecial(1, optionOne);
            targetOne -= 1;
        }
        
        //Pedro Ramirez Special for both human and AI 
        else if (Character.playerList.get(optionOne).getAbilityNum() == 6) {
            PedroRamirezSpecial(optionOne);
            Character.playerList.get(optionOne).changeHealth(-1);
            targetOne -= 1;
        }
        
        else {
            Character.playerList.get(optionOne).changeHealth(-1);
            targetOne -= 1;
        }
        
        //For Vulture Sam's special to count alive
        if (Character.isVultureSamPresent() != -1) {
            int samsIndex = Character.isVultureSamPresent();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        
        //updates GUI for health, alive/dead
        
    }
    
    /**
     * Calamity Janet Special selection of who can be attacked and if they want to use
     * two as a one
     * @param currPlayer The index of the current player
     */
    public void CalamityJanetSpecialTwo(int currPlayer){
        int [] options = new int [6];
        //index 0 and 1 are for player options if they want to use two as a one
        //index 2 and 3 are for player options if they want to use two as a two 
        
        //below is what the output text should say 
        //System.out.println("Enter a 1 if you want to use 2 as a 1, enter 2 if you want to use 2 as a 2.");
        //First output text option to be called so index 4 is 7
        options[4] = 7;
        
        //A target two action so index 5 is a two
        options[5] = 2;
                          
        //Finds starting index for options for 2 as a 1
        if (currPlayer == numPlayers - 1) {
            options[0] = 0;
        }
        else {
            options[0] = currPlayer + 1;
        }

        if (currPlayer == 0) {
            options[1] = numPlayers - 1;
        }
        else {
            options[1] = currPlayer - 1;
        }

        //For no special effecting choice
        //To find the first person they can attack
        while (Character.playerList.get(options[0]).isAlive() == false) {
            options[0] ++;
            if (options[0] >= numPlayers) {
                options[0] = 0;
            }
        }
        //To find the second person they can attack
        while (Character.playerList.get(options[1]).isAlive() == false) {
            options[1] --;
            if (options[1] <= 0) {
                options[1] = numPlayers - 1;
            }
        } 
        
        //They want to use two as a two
        //Finds starting index for options
        if (currPlayer == numPlayers - 1) {
            options[2] = 1;
        }
        else if (currPlayer == numPlayers - 2) {
            options[2] = 0;
        }
        else {
            options[2] = currPlayer + 2;
        }

        if (currPlayer == 0) {
            options[3] = numPlayers - 2;
        }
        else if (currPlayer == 1) {
            options[3] = numPlayers - 1;
        }
        else {
            options[3] = currPlayer - 2;
        }

        //To find the first person they can attack
        while (Character.playerList.get(options[2]).isAlive() == false) {
            options[2] ++;
            if (options[2] >= numPlayers) {
                options[2] = 0;
            }
        }
        //To find the second person they can attack
        while (Character.playerList.get(options[3]).isAlive() == false) {
            options[3] --;
            if (options[3] <= 0) {
                options[3] = numPlayers - 1;
            }
        } 
        
        String s = "Select the player you want to attack, your options are: " + Integer.toString(options[0] + 1) 
                + ", " + Integer.toString(options[1] + 1) + ", " + Integer.toString(options[2] + 1) + ", " + Integer.toString(options[3] + 1);
        
        GameGUIController.setMenuText(s);
    }
    
    /**
     * Rose Doolan Special selection of who can be attacked and if they want to use
     * two as two or three
     * @param currPlayer The index of the current player
     */
    public void RoseDoolanSpecialTwo(int currPlayer) {
        int [] options = new int[6];
        //index 0 and 1 are for player options if they want to use two as a three
        //index 2 and 3 are for player options if they want to use two as a two 
        
        //below is what the output text should say 
        //System.out.println("Enter a 1 if you want to use 2 as a 3, enter 2 if you want to use 2 as a 2.");
        //First output text option to be called so index 4 is 8
        options[4] = 8;
        
        //A target two action so index 5 is a two
        options[5] = 2;
                         
        //Finds starting index for options for 2 as a 3
        if (currPlayer == numPlayers - 1) {
            options[0] = 2;
        }
        else if (currPlayer == numPlayers -2) {
            options[0] = 1;
        }
        else if (currPlayer == numPlayers -3) {
            options[0] = 0;
        }
        else {
            options[0] = currPlayer + 3;
        }

        if (currPlayer == 0) {
            options[1] = numPlayers - 3;
        }
        else if (currPlayer == 1) {
            options[1] = numPlayers - 2;
        }
        else if (currPlayer == 2) {
            options[1] = numPlayers - 1;
        }
        else {
            options[1] = currPlayer - 3;
        }

        //For no special effecting choice
        //To find the first person they can attack
        while (Character.playerList.get(options[0]).isAlive() == false) {
            options[0] ++;
            if (options[0] >= numPlayers) {
                options[0] = 0;
            }
        }
        //To find the second person they can attack
        while (Character.playerList.get(options[1]).isAlive() == false) {
            options[1] --;
            if (options[1] <= 0) {
                options[1] = numPlayers - 1;
            }
        } 
  
        //They want to use two as a two
        //Finds starting index for options
        if (currPlayer == numPlayers - 1) {
            options[2] = 1;
        }
        else if (currPlayer == numPlayers - 2) {
            options[2] = 0;
        }
        else {
            options[2] = currPlayer + 2;
        }

        if (currPlayer == 0) {
            options[3] = numPlayers - 2;
        }
        else if (currPlayer == 1) {
            options[3] = numPlayers - 1;
        }
        else {
            options[3] = currPlayer - 2;
        }

        //To find the first person they can attack
        while (Character.playerList.get(options[2]).isAlive() == false) {
            options[2] ++;
            if (options[2] >= numPlayers) {
                options[2] = 0;
            }
        }
        //To find the second person they can attack
        while (Character.playerList.get(options[3]).isAlive() == false) {
            options[3] --;
            if (options[3] <= 0) {
                options[3] = numPlayers - 1;
            }
        } 
        
        String s = "Select the player you want to attack, your options are: " + Integer.toString(options[0] + 1) 
                + ", " + Integer.toString(options[1] + 1) + ", " + Integer.toString(options[2] + 1) + ", " + Integer.toString(options[3] + 1);
        
        GameGUIController.setMenuText(s);
    }
    
    /**
     * For no special, selection of who can be attacked for two 
     * @param currPlayer The index of the current player
     */
    public void NormalRangeTwo(int currPlayer) {
        int [] options = new int[6];
        //Options index 1 and 2 are their two options
        //Options index 3 and 4 are just zeros b/c they arent options
        options[2] = 0;
        options[3] = 0;
        
        //What the output should say as far as their choice so index 4 is their choice
        //System.out.println("you can attack");
        options[4] = 4;
        
        //A target two action so index 5 is a two
        options[5] = 2;
        
        //Finds starting index for options
        if (currPlayer == numPlayers - 1) {
            options[0] = 1;
        }
        else if (currPlayer == numPlayers - 2) {
            options[0] = 0;
        }
        else {
            options[0] = currPlayer + 2;
        }

        if (currPlayer == 0) {
            options[1] = numPlayers - 2;
        }
        else if (currPlayer == 1) {
            options[1] = numPlayers - 1;
        }
        else {
            options[1] = currPlayer - 2;
        }

        //To find the first person they can attack
        while (Character.playerList.get(options[0]).isAlive() == false) {
            options[0] ++;
            if (options[0] >= numPlayers) {
                options[0] = 0;
            }
        }
        //To find the second person they can attack
        while (Character.playerList.get(options[1]).isAlive() == false) {
            options[1] --;
            if (options[1] <= 0) {
                options[1] = numPlayers - 1;
            }

        }
        String s = "Select the player you want to attack, your options are: " + Integer.toString(options[0] + 1) 
                + ", " + Integer.toString(options[1] + 1);
        
        GameGUIController.setMenuText(s);
    }
    
    /**
     * Method that will shoot the target two, takes in the amount of targets, 
     * and the player to hit
     * @param playerChoice For if the target one is effected by the players special
     */
    public void TargetTwo(int playerChoice) {
        //FIXME - commnet out
        System.out.println("TargetTwo");
        if (Character.isVultureSamPresent() != -1) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        
        //For attacking the chosen player
        
        //Bart Cassidy Special for human
        if (Character.playerList.get(playerChoice).getAbilityNum() == 1 &&
                Character.playerList.get(playerChoice).getUser() == 0) {  
            BartCassidySpecial(0, playerChoice);
        }
        //Pedro Ramirez Special for both human and AI 
        else if (Character.playerList.get(playerChoice).getAbilityNum() == 6) {
            PedroRamirezSpecial(playerChoice);
            Character.playerList.get(playerChoice).changeHealth(-1);
        }
        else {
            Character.playerList.get(playerChoice).changeHealth(-1);
        }
        
        //For Vulture Sam's special to count alive
        if (Character.isVultureSamPresent() != -1) {
            int samsIndex = Character.isVultureSamPresent();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        //updates GUI for health, alive/dead
        
    }
    
    /**
     * Method for shoot Target Two if AI is playing
     * @param currPlayer The current players index
     */
    public void TargetTwoAi(int currPlayer) {
        //FIXME - commnet out
        System.out.println("TargetTwoAi");
        whatHappenedText = whatHappenedText + "Target Two Ai \n";
        int optionOne;
        
        if (Character.isVultureSamPresent() != -1) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        
        //Finds starting index for options
        if (currPlayer == numPlayers - 1) {
            optionOne = 1;
        }
        else if (currPlayer == numPlayers - 2) {
            optionOne = 0;
        }
        else {
            optionOne = currPlayer + 2;
        }

        //To find the first person they can attack
        while (Character.playerList.get(optionOne).isAlive() == false) {
            optionOne ++;
            if (optionOne >= numPlayers) {
                optionOne = 0;
            }
        }

        //Bart Cassidy Special for AI
        if (Character.playerList.get(optionOne).getAbilityNum() == 1 &&
                Character.playerList.get(optionOne).getUser() == 1) { 
            BartCassidySpecial(1, optionOne);
            targetTwo -= 1;
        }
        //Pedro Ramirez Special for both human and AI 
        else if (Character.playerList.get(optionOne).getAbilityNum() == 6) {
            PedroRamirezSpecial(optionOne);
            Character.playerList.get(optionOne).changeHealth(-1);
            targetTwo -= 1;
        }
        else {
            Character.playerList.get(optionOne).changeHealth(-1);
            targetTwo -= 1;
        }
        
        //For Vulture Sam's special to count alive
        if (Character.isVultureSamPresent() != -1) {
            int samsIndex = Character.isVultureSamPresent();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        
        //updates GUI for health, alive/dead
        
    }
    
    /**
     * Method that gets the index of who the human wants to heal with their beer
     * @param currPlayer The index of the current player
     */
    public void WhoToHeal(int currPlayer) {
        int [] options = new int [6];
        //Just need to tell the human player what to do so all index's are zero 
        // execpt the last one
        options[0] = 0;
        options[1] = 0;
        options[2] = 0;
        options[3] = 0;
        options[4] = 9;
        
        //A beer action so index 5 is a 3
        options[5] = 3;
        
        String s = "Select the player you want to heal. ";
        
        GameGUIController.setMenuText(s);
    }
    
    /**
     * Method that heals a player, take in the player number (index) of who they want to heal
     * @param playerIndex The current players index
     */
    public void Beer(int playerIndex) {
        //FIXME - commnet out
        System.out.println("Beer");
        whatHappenedText = whatHappenedText + "Beer \n";
        //For Jesse Jones special 
        if (Character.playerList.get(playerIndex).getAbilityNum() == 3 &&
                Character.playerList.get(playerIndex).getHealth() <= 4) {
            Character.playerList.get(playerIndex).changeHealth(2);
        }
        else {
            Character.playerList.get(playerIndex).changeHealth(1);
        }
        
        //updates GUI after each time it's called
        
    }
    
    /**
     * Method that applies the beers as human or AI wants them to be applied, takes
     * in the current player index
     * @param currPlayer The current players index
     * @param playerChoice The index that the human player chooses;
     */
    public void ApplyBeers(int currPlayer, int playerChoice) {
        //FIXME - comment out
        System.out.println("ApplyBeers"); 
        whatHappenedText = whatHappenedText + "Apply Beers \n";
        //Applies beers as wanted

        //For human option
        if (Character.playerList.get(currPlayer).getUser() == 0){
            Beer(playerChoice);
            if (playerChoice == Character.playerList.get(currPlayer).getSheriffIndex()){
                Character.playerList.get(currPlayer).setHelpedSheriff(true);
            }
        }
        //For AI option
        else {
            //For sheriff AI role
            if (Character.playerList.get(currPlayer).getRoleNum() == 1){
                //Sheriffs health is below full, heals himself
                if (Character.playerList.get(currPlayer).getHealth() < 
                        Character.playerList.get(currPlayer).getMaxHealth()) {
                    Beer(currPlayer);
                }
                //Sheriffs health is full, heal someone who's helped him
                else if (Character.playerList.get(currPlayer).getHelpedSheriff() != -1) {
                    Beer(Character.playerList.get(currPlayer).getHelpedSheriff());
                    Character.playerList.get(currPlayer).setHelpedSheriff(true);
                }
                //No one has helped the sheriff
                else {
                    Beer(currPlayer);
                }
            }
            //For Deputy AI role
            else if (Character.playerList.get(currPlayer).getRoleNum() == 2) {
                //If deputies health is less than the sheriffs
                if (Character.playerList.get(currPlayer).getHealth() <= 
                        Character.playerList.get(Character.playerList.get(currPlayer).getSheriffIndex()).getHealth()) {
                    Beer(currPlayer);
                }
                //If sheriffs health is lower than deputies
                else if (Character.playerList.get(currPlayer).getHealth() > 
                        Character.playerList.get(Character.playerList.get(currPlayer).getSheriffIndex()).getHealth()) {
                    Beer(Character.playerList.get(currPlayer).getSheriffIndex());
                }
                else {
                    Beer(currPlayer);
                }

            }
            //For Outlaw AI role
            else if (Character.playerList.get(currPlayer).getRoleNum() == 3) {
                //Heal himself if less than max health
                if (Character.playerList.get(currPlayer).getHealth() < 
                        Character.playerList.get(currPlayer).getMaxHealth()) {
                    Beer(currPlayer);
                }
                else if (Character.playerList.get(currPlayer).getShotSheriff() != -1){
                    Beer(Character.playerList.get(currPlayer).getShotSheriff());
                }
                else {
                    Beer(currPlayer);
                }
            }
            //For Renegade AI role
            else if (Character.playerList.get(currPlayer).getRoleNum() == 4) {
                //always heals himself
                Beer(currPlayer);
            }
            //Add zombie stuff maybe?
            else {

            }
        }
    }
    
    /**
     * Method to use when the dynamite goes off
     * @param playerIndex The current players index
     */
    public void DynamiteAttack(int playerIndex) {
        //FIXME - comment out
        System.out.println("Dynamite Attack");
        whatHappenedText = whatHappenedText + "Dynamite Attack \n";
        if (Character.isVultureSamPresent() != -1) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }

        //Pedro Ramirez Special for both human and AI 
        if (Character.playerList.get(playerIndex).getAbilityNum() == 6) {
            PedroRamirezSpecial(playerIndex);
            Character.playerList.get(playerIndex).changeHealth(-1);
        }
        else {
            Character.playerList.get(playerIndex).changeHealth(-1);
        }
        
        //For Vulture Sam's special to count alive
        if (Character.isVultureSamPresent() != -1) {
            int samsIndex = Character.isVultureSamPresent();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        
        //Tell GUI to update health, and player alive/dead
        
    }
    
    /**
     * When there are no more arrow cards in the deck runs through and reduces 
     * everyones health for the given amount of arrow cards they have
     */
    public void IndianAttack() {
        //FIXME - comment out
        System.out.println("Indian Attack");
        whatHappenedText = whatHappenedText + "Indian Attack \n";
        
        if (Character.isVultureSamPresent() != -1) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        
        //For if the expansion packs are being used 
        if (pack) {
            //For replacing the chiefs arrow
            if (chiefsArrow) {
                //nothing happens b/c the chiefs arrow isnt assigned
            }
            //if character with max arrows has the chiefs arrow they dont lose health
            else {
                if (Character.chiefsArrowIsMax() != -1) {
                    Character.playerList.get(Character.findChiefsArrow(numPlayers)).changeArrows(-2);
                    int arrowCount = Character.playerList.get(Character.findChiefsArrow(numPlayers)).getArrowCount();
                    arrowDeck += arrowCount;
                    Character.playerList.get(Character.findChiefsArrow(numPlayers)).changeArrows(arrowCount * -1);
                    Character.setChiefsAttribute(Character.findChiefsArrow(numPlayers));
                    chiefsArrow = true;
                }
                //if they have the chiefs arrow but not the max arrows 
                else {
                    Character.playerList.get(Character.findChiefsArrow(numPlayers)).changeArrows(-2);
                    Character.playerList.get(Character.findChiefsArrow(numPlayers)).changeHealth(-2);
                    Character.setChiefsAttribute(Character.findChiefsArrow(numPlayers));
                    chiefsArrow = true;
                }
            }
        }
        
        for (int i = 0; i < numPlayers; ++i) {
            if (Character.playerList.get(i).isAlive() == false) {
                //skip the dead players 
            }
            //Jourdonnais Special 
            else if (Character.playerList.get(i).getAbilityNum() == 4) {
                int arrowCount = Character.playerList.get(i).getArrowCount();
                arrowDeck += arrowCount;
                if (arrowCount != 0) {
                    Character.playerList.get(i).changeHealth(-1);
                    Character.playerList.get(i).changeArrows(arrowCount * -1);
                }
            }
            else {
                int arrowCount = Character.playerList.get(i).getArrowCount();
                arrowDeck += arrowCount;
                Character.playerList.get(i).changeHealth(arrowCount * -1);
                Character.playerList.get(i).changeArrows(arrowCount * -1);
            }
        }
        
        //For Vulture Sam's special to count alive
        if (Character.isVultureSamPresent() != -1) {
            int samsIndex = Character.isVultureSamPresent();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }

        //Tells the GUI to update here for health, and player alive/dead , and arrow count
        
    }
    
    /**
     * When there are 3 or more gatling guns this is called and drops everyone health
     * unless they have a special to stop it or get an option to stop it
     * @param playerPos The current players index
     */
    public void GatlingAttack(int playerPos) {
        //FIXME - commnet out
        System.out.println("Gatling Attack");
        whatHappenedText = whatHappenedText + "Gatling Attack \n";
        if (Character.isVultureSamPresent() != -1) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        for (int i = 0; i < numPlayers; ++i) {
            
            if (i == playerPos) {
                //no health is taken from current player
            }
            else if (Character.playerList.get(i).isAlive() == false) {
                //skip dead player 
            }
            //Paul Regret Special
            else if (Character.playerList.get(i).getAbilityNum() == 5){
                //doesnt lose health
            }
            //If Bart Cassidy special and human to give option
            else if (Character.playerList.get(i).getAbilityNum() == 1 && 
                    Character.playerList.get(i).getUser() == 0) {
                BartCassidySpecial(0, i);
            }
            //If Bart Cassidy special and AI
            else if (Character.playerList.get(i).getAbilityNum() == 1 && 
                    Character.playerList.get(i).getUser() == 1){
                BartCassidySpecial(1, i);
            }
            //Pedro Ramirez Special for both human and AI 
            else if (Character.playerList.get(i).getAbilityNum() == 6) {
                PedroRamirezSpecial(i);
                Character.playerList.get(i).changeHealth(-1);
            }
            else {
                Character.playerList.get(i).changeHealth(-1);
            }
        }
        
        //For Vulture Sam's special to count alive
        if (Character.isVultureSamPresent() != -1) {
            int samsIndex = Character.isVultureSamPresent();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        //Tells the GUI to update here for health, and player alive/dead 
        
    }
    
    /**
     * For dealing with the chiefs arrow in the character methods, both for human and AI
     * @param currPlayer The current players index
     */
    public void ChiefsArrow(int currPlayer) {
        //FIXEME - comment out
        System.out.println("ChiefsArrow");
        whatHappenedText = whatHappenedText + "Chiefs Arrow \n";
        
        if(chiefsArrow) {
            Character.playerList.get(currPlayer).setChiefsArrow(true);
            chiefsArrow = false;
        }
        
    }
    
    /**
     * For dealing with the broken arrow dice
     * @param currPlayer The current players index
     */
    public void BrokenArrow(int currPlayer) {
        //FIXME - comment out
        System.out.println("BrokenArrow");
        whatHappenedText = whatHappenedText + "Broken Arrow \n";
        
        //for human player
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            
            playerChoice = currPlayer;
            if (Character.playerList.get(playerChoice).getArrowCount() == 0) {
                //nothing happens b/c they dont have arrows 
            }
            else {
                Character.playerList.get(playerChoice).changeArrows(-1);
                arrowDeck ++;
            }
        }
        //for AI he only takes arrows away from himself right now
        else {
            if (Character.playerList.get(currPlayer).getArrowCount() == 0) {
                //nothing happens b/c AI doesnt have any arrows 
            }
            else {
                Character.playerList.get(currPlayer).changeArrows(-1);
                arrowDeck ++;
            }
        }
    }
    
    /**
     * For dealing with the bullet dice
     * @param currPlayer The current players index
     */
    public void Bullet(int currPlayer) {
        //FIXME - comment out
        System.out.println("Bullet");
        whatHappenedText = whatHappenedText + "Bullet \n";
        if (Character.isVultureSamPresent() != -1) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        
        Character.playerList.get(currPlayer).changeHealth(-1);
        
        //For Vulture Sam's special to count alive
        if (Character.isVultureSamPresent() != -1) {
            int samsIndex = Character.isVultureSamPresent();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        
        //Tells the GUI to update here for health, and player alive/dead 
        
    }
    
    /**
     * Method for getting who the human wants to duel 
     * @param currPlayer The current players index
     * @return options What tells the GUI to print to the text screen
     */
    public int [] WhoToDuel(int currPlayer) {
        int [] options = new int[6];
        //Since we just want to get what the player wants to attack and there 
        //arent any options to pass, all but 4 index are 0
        options[0] = 0;
        options[1] = 0;
        options[2] = 0;
        options[3] = 0;
        options[4] = 10;
        
        //A duel action so index 5 is a 4
        options[5] = 4;
        
        return options;
    }
    
    /**
     * For dealing with the dueling dice
     * @param currPlayer The current players index
     * @param playerChoice The player the attacker wants to duel
     * @param numDuels The number of duels the AI should cycle through 
     */
    public void Duel(int currPlayer, int playerChoice, int numDuels) {
        boolean attacker = true, defender = true;
        int count = 1; 
        //FIXME - comment out
        System.out.println("Duel");
        whatHappenedText = whatHappenedText + "Duel \n";
        if (Character.isVultureSamPresent() != -1) {
                //For special to count alive 
                beforeAlive = Character.countAlive();
            }
        
        //For human player
        if (Character.playerList.get(currPlayer).getUser() == 0) {
            //takes turn rolling based off of the count 
            //if count is odd = defneder's roll 
            //if count is even = attackers roll
            while (attacker && defender) {
                //for defenders roll
                if (count % 2 != 0) {
                    if (Dice.dualDiceReroll()) {
                        //they rolled a duel guns so nothing happens, and attacker rolls
                    }
                    else {
                        defender = false;
                    }
                }
                //for the attacker
                else {
                    if (Dice.dualDiceReroll()) {
                        //they rolled a duel guns so nothing happens, and defender rolls
                    }
                    else {
                        defender = false;
                    }
                }
                count ++;
            }
            //Who losed the duel 
            if (attacker) {
                //defender loses health b/c they lost
                Character.playerList.get(playerChoice).changeHealth(-1);
            }

            else {
                //attacker loses health b/c they lost 
                Character.playerList.get(currPlayer).changeHealth(-1);
            }
        }
        //For AI player
        else {
            //cycles throw the total number of duels avalable 
            playerChoice = numPlayers - 1;
            count = numPlayers;
            for (int i = 0; i < numDuels; ++i) {
                //selects who to duel 
                while (count < 0) {
                    if (playerChoice != currPlayer &&
                            Character.playerList.get(playerChoice).isAlive()) {
                        count --;
                        break;
                    }
                    else {
                        playerChoice --;
                        count --;
                    }
                }
                
                //takes turn rolling based off of the count 
                //if count is odd = defneder's roll 
                //if count is even = attackers roll
                while (attacker && defender) {
                    //for defenders roll
                    if (count % 2 != 0) {
                        if (Dice.dualDiceReroll()) {
                            //they rolled a duel guns so nothing happens, and attacker rolls
                        }
                        else {
                            defender = false;
                        }
                    }
                    //for the attacker
                    else {
                        if (Dice.dualDiceReroll()) {
                            //they rolled a duel guns so nothing happens, and defender rolls
                        }
                        else {
                            defender = false;
                        }
                    }
                    count ++;
                }
                //Who losed the duel 
                if (attacker) {
                    //defender loses health b/c they lost
                    Character.playerList.get(playerChoice).changeHealth(-1);
                }
                 
                else {
                    //attacker loses health b/c they lost 
                    Character.playerList.get(currPlayer).changeHealth(-1);
                }
            }
        }
        
        //For Vulture Sam's special to count alive
        if (Character.isVultureSamPresent() != -1) {
            int samsIndex = Character.isVultureSamPresent();
            currAlive = Character.countAlive();
            if (beforeAlive - currAlive != 0 && 
                    Character.playerList.get(samsIndex).isAlive()) {
                Character.playerList.get(samsIndex).changeHealth(2);
            }
        }
        
        //Tells the GUI to update here for health, and player alive/dead 
        
    }
    

    /**
     * Method for checking if a boneyard card should be drawn after an alive person's
     * turn, and sets the zombie outbreak to true if there is an outbreak
     * @param currPlayer The current players index
     */
    public void BoneyardCardCheck(int currPlayer) {
        //doesnt play zombies if expansion isnt used
        if (!pack) {
            
        }
        else {
            //no zombie outbreak yet, and someone is dead draw a card
            if (zombieOutbreak == false && Character.countAlive() < numPlayers) {
                int count = 0;
                boneyardCard = boneyardDeck[count];
                while (boneyardCard == -1) {
                    count ++;
                    boneyardCard = boneyardDeck[count];
                }
                if (boneyardCard == 0) {
                    //no outbreak
                    //shuffle the deck to keep zero in it
                    ShuffleBoneyard();
                }
                //card isnt a zero so see if number of alive is great than count
                //if not there is a zombie outbreak
                else {
                    boneyardCount += boneyardCard;
                    boneyardDeck[count] = -1;
                    if (boneyardCount > Character.countAlive()) {
                        zombieOutbreak = true;
                        boneyardCount = 0;
                        Character.crownTheZombieMaster(currPlayer);
                        //all dead players get set to alive, and gain health = to 
                        //amount of alive people left 
                        Character.raiseZombies();
                    }
                }
            }
            else {
                //nothing happens b/c a card shouldnt be drawn
            }
        }
    }
    
    /**
     * Prints all the game stats
     */
    public void PrintStats() {
        System.out.println("Arrow Deck count is: " + arrowDeck);
        System.out.println("Chiefs arrow status is: " + chiefsArrow);
        System.out.println("Beer count status is: " + numBeers);
        System.out.println("Target One status is: " + targetOne);
        System.out.println("Target Two status is: " + targetTwo);
        System.out.println("Zombie Outbreak: " + zombieOutbreak);
        System.out.println("Boneyard count is: " + boneyardCount);
    }
    
    /**
     * Main Class that runs the game 
     * @param args
     */
    
    public static void main(String[] args) {
       /*
        Game currGame = new Game();
        int currPlayer = 0, numPlayers, currChar, sheriffIndex = 0; 
        boolean endGame = false;
        
        //Initalizes the game 
        numPlayers = currGame.StartGame();
      
        //Used to start gameplay with the sheriff
        for (int i = 0; i < numPlayers; ++i) {
            if (Character.playerList.get(i).getRoleNum() == 1) {
                currPlayer = i;
                sheriffIndex = i;
                break;
            }
        }
        //Starts the loop for playing 
        while(endGame != true) {
            
            currChar = Character.playerList.get(currPlayer).getAbilityNum();
            //FIXME - comment out
            System.out.println("Player: " + currPlayer + "'s turn");
            System.out.println("Sheriff is player: " + sheriffIndex);
            System.out.println();
            
            switch(currChar) {
                case 1:     //Bart Cassidy
                    currGame.BartCassidy(currPlayer);
                    break;
                    
                case 2:     //Calamity Janet
                    currGame.CalamityJanet(currPlayer);
                    break;
                    
                case 3:     //Jesse Jones
                    currGame.JesseJones(currPlayer);
                    break;
                    
                case 4:     //Jourdonnais
                    currGame.Jourdonnais(currPlayer);
                    break;
                    
                case 5:     //Paul Regret
                    currGame.PaulRegret(currPlayer);
                    break;
                    
                case 6:     //Pedro Ramirez
                    currGame.PedroRamirez(currPlayer);
                    break;
                    
                case 7:     //Rose Doolan
                    currGame.RoseDoolan(currPlayer);
                    break;
                    
                case 8:     //Vulture Sam
                    currGame.VultureSam(currPlayer);
                    break;
                    
                case 9:     //Apache Kid
                    currGame.ApacheKid(currPlayer);
                    break;
                    
                case 10:    //Bill Noface
                    currGame.BillNoface(currPlayer);
                    break;
                    
                case 11:    //Belle Star
                    currGame.BelleStar(currPlayer);
                    break;
                    
                case 12:    //Greg Digger
                    currGame.GregDigger(currPlayer);
                    break;   
            }
            
            //for checking the zombie outbreak conditions for winners
            if (currGame.zombieOutbreak) {
                if (Character.aliveVsZombie() == 0) {
                    //game goes on
                }
                else if (Character.aliveVsZombie() == 1) {
                    //humans win
                    break;
                }
                else {
                    //zombies win
                    break;
                }
            }
            //for normal game play end game conditions
            else {
                //Check if only the sheriff and/or Deputy are alive to end the game
                currGame.WhoAlive();
                if ((currGame.alive[0] >= 1 || currGame.alive[1] >= 1) && 
                        (currGame.alive[2] == 0 && currGame.alive[3] == 0)) {
                    break;
                }
                else if (currGame.alive[0] == 0) {
                    //sheriff is dead 
                    break;
                }
                    
            }
            
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
                        currGame.Zombie(currPlayer);
                    }
                    //they are really dead
                    else {
                        //Check if a boneyard card should be drawn 
                        currGame.BoneyardCardCheck(currPlayer);
                        if (currGame.zombieOutbreak && 
                                Character.playerList.get(currPlayer).isAlive()) {
                            //there was a zombie outbreak after the dead person drew a card
                            //play a turn as the zombie then go to next player 
                            currGame.Zombie(currPlayer);
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
            
        }
        //Used to decide who the winner is based off the zombie outbreak
        //conditions for if there is a zombie outbreak
        if (currGame.zombieOutbreak) {
            if (Character.aliveVsZombie() == 1) {
                //humans win
                //FIXME - comment out
                System.out.println("Alive wins");
            }
            else if (Character.aliveVsZombie() == -1){
                //zombies win
                //FIXME - comment out
                System.out.println("Zombies wins");
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
                } 
                else {
                    //FIXEME - Comment out
                    System.out.println("Outlaw's win");
                    //outlaw wins since either sheriff is dead and there are outlaws
                    //or sheriff is dead and there is more than one renegade left
                }
            }
            else if(currGame.alive[0] == 0 && currGame.alive[2] != 0) {
                //Outlaws win
                System.out.println("Outlaws win");
            }
            else {
                //sheriff & deputy win since others are dead 
                //FIXEME - Comment out
                System.out.println("Sheriff's/ Deputies win");
            }
        }*/
    }
}
