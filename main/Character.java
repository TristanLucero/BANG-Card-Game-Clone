package cs_2365_project_3;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Random;
import java.util.Arrays;
import java.util.ArrayList;

/**
 *
 * @author lanegmacdougall
 */
public class Character
{
     
    private int user;  // 0 for user, 1 for AI
   
    private boolean isAlive;  // True if character alive, false if character has been eliminated
   
    private boolean isZombieDead;  // True if eliminated as a zombie and permanently removed from the game; otherwise, false
   
    private boolean isZombie;  // True if character has re-entered game as a zombie (Undead or Alive expansion pack)
   
    private boolean isZombieMaster;  // True if character with Renegade role has been designated as Zombie Master (Undead or Alive expansion pack)
   
    private String name;  // Character name
   
    private int role;  // The role assigned to a character, Sherrif, Deputy, Outlaw or Renegade
   
    private int health;  // Health points for each charatcer
   
    private int maxHealth;  // Maximum health points available to a given character (set at instantiation)
   
    private int ability;  // Character special ability
   
    private int arrows;  // Number of die player does not re-roll
   
    private boolean chiefsArrow;  // Boolean value indicating whether or not the character holds the Indian Chief's arrow (expansion pack)
   
    private boolean helpedSheriff;  // Indicates whether or not the character has helped the Sheriff by providing him beer (health)
   
    private boolean shotSheriff;  // Indicates whether or not the character has shot the Sheriff
   
 
    // ArrayList contains all available game characters; Characters to be used in
    // gameplay are removed from dupeList and added to playerList
    public static ArrayList<Character> dupeList = new ArrayList<>(12);
   
    // ArrayList contains all Character objects used in gameplay
    public static ArrayList<Character> playerList = new ArrayList<>();
   
    // Constructor method - takes character health points, ability code, and the name of
    // the character as input
    // Remaining attributes are set to default values
    public Character(int healthPts, int abilityCode, String characterName){
       
        this.setUser(1);
       
        this.setLifeStatus(true);
       
        this.setZombieDead(false);
       
        this.setZombie(false);
       
        this.setZombieMaster(false);
       
        this.setHealth(healthPts);
       
        // Initial health value passed into constructor is the maximum health value that a character can have
        this.setMaxHealth(healthPts);  
       
        this.setAbility(abilityCode);
       
        this.setRole(0);
       
        this.setArrowCount(0);
       
        this.setChiefsArrow(false);
       
        this.setName(characterName);
       
        this.setHelpedSheriff(false);
       
        this.setShotSheriff(false);
       
       
    }
   
   // Sets user attribute as either 0 for program user, or 1 for AI
    public void setUser(int user){
       
        this.user = user;
       
    }
   
    // Sets isAlive attribute to either true for alive, or false for dead
    public void setLifeStatus(boolean alive){
       
        this.isAlive = alive;
       
    }
   
   
    // Sets the isZombieDead attribute, which indicates that a "zombie" character
    // has been eliminated (second elimination in current gameplay), to true
    // if the character is a zombie and has been eliminated, or false otherwise
    public void setZombieDead(boolean zombieDead){
       
        // Checks if argument is true, if yes, checks if the character is currently a zombie
        if (zombieDead == true){
            if (this.getZombieStatus() == true)
               
                // If both are true, set isZombieDead attribute to true
                this.isZombieDead = zombieDead;
           
        // Else, isZombieDead attribute is set to false
            else
                this.isZombieDead = false;
        } else
            this.isZombieDead = zombieDead;    
       
    }
   
   
    // Sets isZombie attribute according to argument
    public void setZombie(boolean zombieStatus){
       
        this.isZombie = zombieStatus;
        this.isAlive = true;
       
    }
    
    //Method that finds all the dead characters, sets them to alive, and gives
    //them the amount of health = the number of people still alive
    public static void raiseZombies() {
        int health = countAlive();
        
        for (int i = 0; i < playerList.size(); ++i) {
            if (playerList.get(i).isAlive) {
                //do nothing b/c they are already alive
            }
            //raise the dead perosn and turn into a zombie
            else {
                playerList.get(i).setZombie(true);
                playerList.get(i).setHealth(health);
            }
        }
    }
   
   
    // Sets isZombieMaster attribute according to argument
    public void setZombieMaster(boolean zombieMasterStatus){
       
        this.isZombieMaster = zombieMasterStatus;
       
    }
   
   
    // Sets Character object's health points; used during initialization to set
    // initial (and maximum) health value
    public void setHealth(int healthPts){
       
        this.health = healthPts;
       
    }
   
   
    // Sets the maximum health points a Character object can have according to the
    // health points that a Character object is initialized with
    public void setMaxHealth(int maxHealthPts){
       
       
        this.maxHealth = maxHealthPts;
       
    }
   
   
    // Sets the ability code of a Character object
    public void setAbility(int abilityCode){
       
        this.ability = abilityCode;
       
    }
   
   
    // Sets the role code of a Character object
    public void setRole(int roleType){
       
        this.role = roleType;
       
    }
   
   
    // Sets the total arrow count of a Character object according to the argument
    // passed into the function
    public void setArrowCount(int arrowCount){
       
        this.arrows = arrowCount;
       
    }
   
    //Method to be used for just changing the chiefs arrow attribute to false 
    //used for Indian attack
    public static void setChiefsAttribute(int playerIndex) {
        Character.playerList.get(playerIndex).chiefsArrow = false;
    }
    
    //Method that finds the max amount of arrows and see's if its the person 
    //holding the chiefs arrow, 
    public static int chiefsArrowIsMax() {
        int index = -1, maxArrows;
        
        //no one has the chiefs arrow
        if (findChiefsArrow(Character.playerList.size()) == -1) {
        }
        else {
            index = findChiefsArrow(Character.playerList.size());
            maxArrows = Character.playerList.get(findChiefsArrow(Character.playerList.size())).getArrowCount();
            for (int i = 0; i < Character.playerList.size(); ++i) {
                if (maxArrows == Character.playerList.get(i).getArrowCount() && 
                        i != index) {
                    index = -1;
                    break;
                }
                else if (maxArrows < Character.playerList.get(i).getArrowCount()){
                    index = -1;
                    break;
                }
                else {
                    break;
                }
            }
        }
        return index;
    }
    
    
    public void setChiefsArrow(boolean hasChiefsArrow){
       
        // If function is called with true as an argument, "give" the Character object the
        // Chief's Arrow (set attribute equal to true and add 2 arrows to the object's count
        if (hasChiefsArrow == true){
            this.chiefsArrow = hasChiefsArrow;
       
            this.changeArrows(2);
           
        // If function is called with false as an argument, "take" the Chief's Arrow
        // from the Character object (set attribute to false and subtract 2 arrows from the
        // object's count
        } else {
            if (this.hasChiefsArrow() == true){
                this.chiefsArrow = hasChiefsArrow;
               
                this.changeArrows(-2);
            }
        }
       
    }
   
   
    public static int findChiefsArrow(int numPlayers){
       
        int i = 0;
       
        boolean foundArrow = false;
       
        int location = -1; // Index of arrow; -1 if no Character object has the Chief's Arrow
       
        while (i < numPlayers && foundArrow == false){
            if (Character.playerList.get(i).hasChiefsArrow() == true){
                foundArrow = true;
                location = i;
            }
           
            i++;
        }
       
        return location;
    }
   
    // Takes number of players in the game as an argument - findChiefsArrow function must traverse playerList
    public void takeChiefsArrow(int numPlayers){
       
        // Use findChiefsArrow to find the index of the Character object with the Chief's Arrow
        // Returns -1 if no Character object has the Chief's Arrow
        int indexOfArrow = Character.findChiefsArrow(numPlayers);
       
        // If a Character object has the Chief's Arrow, remove it from that player and 
        // give it to the Character object that the method is being called on
        if (indexOfArrow != -1){
            Character.playerList.get(indexOfArrow).setChiefsArrow(false);
           
            this.setChiefsArrow(true);
        }  
    }
   
   
    public void setName(String characterName){
       
        this.name = characterName;
       
    }
   
   
    public void setHelpedSheriff(boolean didHelp){
       
        this.helpedSheriff = didHelp;
       
    }
   
   
    public void setShotSheriff(boolean didShoot){
       
        this.shotSheriff = didShoot;
       
    }
   
   
    public int getUser(){
       
        return this.user;
       
    }
   
   
    public boolean getIsAlive(){
       
        return this.isAlive;
       
    }
   
   
    public boolean getIsZombieDead(){
       
        return this.isZombieDead;
       
    }
   
   
    public boolean getZombieStatus(){
       
        return this.isZombie;
       
    }
   
   
    public boolean getZombieMasterStatus(){
       
        return this.isZombieMaster;
       
    }
    
    //Method that finds the number of alive players vs the number of zombies
    //returns -1 if zombies win, 1 if alive win, or 0 if game should continue
    public static int aliveVsZombie() {
        int alive = 0, zombies = 0, whoWins = 0;
        
        for (int i = 0; i < playerList.size(); ++i) {
            //if the player is a zombie
            if (playerList.get(i).isAlive && playerList.get(i).isZombie) {
                zombies ++;
            }
            //if player is just alive 
            else if ((playerList.get(i).isAlive && playerList.get(i).isZombie == false)
                    || (playerList.get(i).isZombieMaster && playerList.get(i).isAlive)) {
                alive ++;
            }
        }
        //for saying who wins
        if (alive == 0 && zombies > 0) {
            //zombies win
            whoWins = -1;
        }
        else if (alive > 0 && zombies == 0) {
            //alive wins
            whoWins = 1;
        }
        else if (alive > 0 && zombies > 0) {
            //game should continue
            whoWins = 0;
        }
        else {
            //everyone is dead so zombies win
            whoWins = -1;
        }
        
        return whoWins;
    }
   
   
    public String getName(){
       
        return this.name;
       
    }
   
   
    public int getRoleNum(){
       
        return this.role;
       
    }
   
   
    public int getHealth(){
       
        return this.health;
       
    }
   
   
    public int getMaxHealth(){
       
        return this.maxHealth;
       
    }
   
   
    public int getAbilityNum(){
       
        return this.ability;
       
    }
   
   
    public int getArrowCount(){
       
        return this.arrows;
       
    }
   
   
    public boolean hasChiefsArrow(){
       
        boolean hasTheArrow = false;
       
        if (this.chiefsArrow == true)
            hasTheArrow = true;
       
        return hasTheArrow;
           
       
    }
   
   
    //method that returns the index of the first person that helped the sheriff
    //or -1 if no one has helped the sheriff
    public int getHelpedSheriff(){
        int index = -1;
        for (int i = 0; i < Character.playerList.size(); ++i) {
            if (Character.playerList.get(i).helpedSheriff) {
                index = i;
                break;
            }
        }

        return index;
       
    }
   
    //Method that gets the sheriffs index in the playerlist
    public int getSheriffIndex(){
        int index = -1;
        for (int i = 0; i < Character.playerList.size(); ++i) {
            if (Character.playerList.get(i).role == 1) {
                index = i;
                break;
            }
        }
        return index;
    }
    
    //Method that returns the index of the first person that shot the sheriff
    //or -1 is no one has shot the sheriff
    public int getShotSheriff(){
        int index = -1;
        for (int i = 0; i < Character.playerList.size(); ++i) {
            if (Character.playerList.get(i).shotSheriff) {
                index = i;
                break;
            }
        }
        return index;
       
    }
   
   
   
    @Override
    public String toString(){
       
        return "Control: " + ((this.user == 0) ? "User":"CPU") + ", Name: " + this.name + ", Health: " + this.health + ", Arrow Count: " + this.arrows + ", Chiefs Arrow: " + this.chiefsArrow + ", Zombie statis: " + this.isZombie;
    }
    
    
    // Prints the String returned by the toString method and the position (in Character.playerList)
    // for all Character objects in Character.playerList
    
    public static void printAll(){
       int i;
       
        for (i = 0; i < playerList.size(); i++)
        {
            
            System.out.print(Character.playerList.get(i).toString());
            
            System.out.print(", Position: " + i + "\n");
        }
    }
   
    
    // Constructor Arguments:
    // int healthPts, int abilityCode, String characterName  
    
    public static void initializeAllCharacters(boolean expansionPack) //Initializes all player objects and assigns them to an ArrayList
    {
        Character char1 = new Character(8, 1, "Bart Cassidy");
        //Special Ability: Can take an arrow instead of losing a lifepoint only to shoot 1 or 2
       

        Character char2 = new Character(9, 5, "Paul Regret");
        //Special Ability: Never lose lifepoints to the gattling gun
       

        Character char3 = new Character(8, 6, "Pedro Ramirez");
        //Special Ability: When you lose a lifepoint, you can discard an arrow
       

        Character char4 = new Character(8, 2, "Calamity Janet");
        //Special Ability: Can use a shoot 1 as a 2 and vice versa
   

        Character char5 = new Character(9, 7, "Rose Doolan");
        //Special Ability: Can use shoot 1 and 2 for players sitting one place further
       

        Character char6 = new Character(9, 3, "Jesse Jones");
        //Special Ability: If you have 4 or less lifepoints beer heals 2 lifepoints
       

        Character char7 = new Character(7, 4, "Jourdonnais");
        //Special Ability: Never lose more than 1 lifepoint to indians
       

        Character char8 = new Character(9, 8, "Vulture Sam");
        //Special Ability: When a player is eliminated you gain 2 lifepoints
       


        Character.dupeList.add(char1);
       
        Character.dupeList.add(char2);
       
        Character.dupeList.add(char3);
       
        Character.dupeList.add(char4);
       
        Character.dupeList.add(char5);
       
        Character.dupeList.add(char6);
       
        Character.dupeList.add(char7);
       
        Character.dupeList.add(char8);
       
       
        if (expansionPack){
           
            Character charExp1 = new Character (9, 9, "Apache Kid");
           
            Character charExp2 = new Character(9, 10, "Bill Noface");
           
            Character charExp3 = new Character(8, 11, "Belle Star");
           
            Character charExp4 = new Character (7, 12, "Greg Digger");
           
           
            Character.dupeList.add(charExp1);
           
            Character.dupeList.add(charExp2);
           
            Character.dupeList.add(charExp3);
           
            Character.dupeList.add(charExp4);
           
        }
       
    }
   
   
    public static void assignCharacters(int numPlayers, boolean expansionPack) //Creates an ArrayList of randomly assigned characters, final version will pass in numPlayers
    {
       
        Random rand = new Random();
       
        int randNum;
       
        int range;
       
        if (expansionPack)
            range = 12;
        else
            range = 8;
       
        for (int i = 0; i < numPlayers; i++)
        {
            randNum = rand.nextInt(range);
           
            playerList.add(dupeList.get(randNum));
            dupeList.remove(randNum);
           
            range--;
        }
         
    }
   
   
    public static void assignRole(int numPlayers) //Randomly assigns a role to each character, final version will pass in numPlayers
    {
        //Roles:   1: Sheriff;   2: Deputy;   3: Outlaw;   4: Renegade
       
        Character player;
       
        ArrayList<Integer> roles;
       
        int range;
       
        Random rand = new Random();
       
        int randNum;
       
        if (numPlayers == 3){
            roles = new ArrayList<>(Arrays.asList(2,3,4));
           
        }else if(numPlayers == 4)
            roles = new ArrayList<>(Arrays.asList(1,3,3,4));
       
        else if(numPlayers == 5)
            roles = new ArrayList<>(Arrays.asList(1,2,3,3,4));
       
        else if(numPlayers == 6)
            roles = new ArrayList<>(Arrays.asList(1,2,3,3,3,4));
       
        else if(numPlayers == 7)
            roles = new ArrayList<>(Arrays.asList(1,2,2,3,3,3,4));
       
        else if(numPlayers == 8)
            roles = new ArrayList<>(Arrays.asList(1,2,2,3,3,3,4,4));
       
        else {
            numPlayers = 4;
            roles = new ArrayList<>(Arrays.asList(1,3,3,4));
        }
           
       
        range = numPlayers;
           
        for(int i = 0; i < numPlayers; i ++)
        {
            randNum = rand.nextInt(range);
               
            player = playerList.get(i);
               
            player.role = roles.get(randNum);
            roles.remove(randNum);
               
            range--;
               
            //Test case to display the randomly assigned charcters
            //System.out.println(player.toString());
               
        }
       
    }
   
   
    public static void assignUser(){
       
        playerList.get(0).user = 0;
       
    }
   
   
    public void addLife() //Adds life to a specific character
    {
       
        this.health ++;
       
    }
   
   
    public void loseLife() //Subtracts life from a specific charatcer
    {
       
        this.health --;
       
    }
   
   
    // Function adjusts Character object's health points so long as the object has not been eliminated
    // from the game
    // Function adjusts Character object's life status if health points decrease to 0 or less
    public void changeHealth(int amtChange){
       
        // New health value; equal to current health + change in health
        int newHealthValue;
       
        newHealthValue = this.getHealth() + amtChange;
       
        // Block executed if Character object is "alive;" see else block for code to execute if Character object
        // is a zombie
        if (this.getIsAlive() == true && this.getZombieStatus() == false && this.getIsZombieDead() == false){
           
            // If new health value is less than of equal to 0, set the object's health equal to 0
            // and set their life status as "dead"
            // The change in health has killed the Character object
            if (newHealthValue <= 0){
                this.setHealth(0);
                this.setLifeStatus(false);
           
           
            } else {
               
                // Health points cannot exceed a Character object's max points; if the amount change
                // increases the object's health points beyond the max, health is set as the max amount
                if (newHealthValue > this.getMaxHealth())
                    this.setHealth(this.getMaxHealth());
               
                // Else, set health as the new health value
                else
                    this.setHealth(newHealthValue);
            }
           
        // Block executed if Character object is a zombie
        } else if (this.getZombieStatus() == true && this.getIsZombieDead() == false){
           
            // Make sure that change in health is a decrease; zombies' health cannot increase
            if (amtChange < 0){
               
                // If change in health decreases Character object's health to 0 or less, set object as zombie dead
                // (i.e., permanently eliminated)
                if (newHealthValue <= 0){
                    this.setHealth(0);
                    this.setZombieDead(true);
                   
                // Else, set health as the new health value
                } else
                    this.setHealth(newHealthValue);
            }
        }
    }
   
   
    public boolean isAlive() //Checks to see if a player is alive
    {
       
        if (this.health == 0)
        {
            isAlive = false;
        }
        else
            isAlive = true;
       
        return isAlive;
       
    }
   
   
    public void changeArrows(int numArrows){
       
        this.arrows += numArrows;
       
    }
   
   
    public void shoot(Character shotPlayer){
       
        shotPlayer.loseLife();
       
    }
   
   
    public static double aliveToDeadRatio(){
       
        int i;
       
        int numAlive = 0;
       
        int numDead = 0;
       
        double lifeRatio;
       
        Character player;
       
        for (i = 0; i < Character.playerList.size(); i++){
           
            player = Character.playerList.get(i);
           
            if (player.getIsAlive() == true)
                numAlive += 1;
           
            else
                numDead += 1;
           
        }
       
        if (numDead == 0)
            lifeRatio = Double.MAX_VALUE;
        else
            lifeRatio = (double) numAlive/numDead;
       
       
        return lifeRatio;
       
    }
   
   
    public static int countAlive(){
       
        int i;
       
        int numAlive = 0;
       
        Character player;
       
        for (i = 0; i < Character.playerList.size(); i++){
           
            player = Character.playerList.get(i);
           
            if (player.getIsAlive() == true)
                numAlive += 1;
        }
       
        return numAlive;
       
    }
   
   
    public static int countDead(){
       
        int i;
       
        int numDead = 0;
       
        Character player;
       
        for (i = 0; i < Character.playerList.size(); i++){
           
            player = Character.playerList.get(i);
           
            if (player.getIsAlive() == false)
                numDead += 1;
        }
       
       
        return numDead;
       
    }
   
   
    public static double aliveToTotalRatio(){
       
        int i;
       
        int numAlive = 0;
       
        int numPlayers = Character.playerList.size();
       
        double lifeRatio;
       
        Character player;
       
        for (i = 0; i < numPlayers; i++){
           
            player = Character.playerList.get(i);
           
            if (player.getIsAlive() == true)
                numAlive += 1;
        }
       
        lifeRatio = (double) numAlive/numPlayers;
       
       
        return lifeRatio;
    }
   
   
    // Function follows the following steps:
    // 1. Determines how many of the alive Character objects have the "Renegade" role
    // 2. If there is only 1 Renegade, the function sets that Character object's isZombieMaster attribute
    //    to true
    // 3. If there are more than 1 Renegades, the function calculates the clockwise distance of each
    //    Character object from the current player and sets the closest Character object's 
    //    isZombieMaster attribute to true
    public static int crownTheZombieMaster(int indexOfCurrPlayer){
       
        int renegadeCount = 0;
       
        int indexOfMaster = -1;
       
        // ArrayList holds the playerList indices of any Renegades in gameplay
        ArrayList <Integer> renegadePositions = new ArrayList<>();
       
        Character player;
       
        int i;
       
        // Check each Character object in playerList; if the Character object is 
        // (1) alive, and
        // (2) has a role ID of 4 (Renegade),
        // increment renegadeCount and add the index of the Character object in playerList to 
        // the renegadePositions ArrayList
        // If the Renegade is the first one encountered, set that Character object's index as
        // the indexOfMaster; if this is the only Renegade in gameplay, this variable can be returned 
        // without any further checks
        for (i = 0; i < Character.playerList.size(); i++){
            player = Character.playerList.get(i);
            if (player.getIsAlive() == true && player.getRoleNum() == 4){
               
                renegadeCount += 1;
               
                if (renegadeCount == 1)
                    indexOfMaster = i;
               
                renegadePositions.add(i);
               
            }
        }
       
       // If there are two Renegades (max possible), the clockwise distance of each from the
       // current player is calculated
       // The index of the closer of the two is saved as in indexOfMaster
        if (renegadeCount == 2){
           
            int numDist1, numDist2;
           
            int trueDist1, trueDist2;
           
           
            // Calculate the absolute value of the difference in the indices of the current player
            // and the first Renegade
            numDist1 = Math.abs(indexOfCurrPlayer - renegadePositions.get(0));
           
            // If that number is less than or equal to 4 (total players(8) / 2), then this value 
            // is the clockwise distance of the Renegade from the current player
            if (numDist1 <= 4)
                trueDist1 = numDist1;
            
            // If numDist1 is greater than 4, then the clockwise distance is 8 minus this value
            else
                trueDist1 = 8 - numDist1;
           
            
            // Calculate the absolute value of the difference in the indices of the current player
            // and the second Renegade
            numDist2 = Math.abs(indexOfCurrPlayer - renegadePositions.get(1));
           
           
            // If that number is less than or equal to 4 (total players(8) / 2), then this value 
            // is the clockwise distance of the Renegade from the current player
            if (numDist2 <= 4)
                trueDist2 = numDist2;
            
            // If numDist2 is greater than 4, then the clockwise distance is 8 minus this value
            else
                trueDist2 = 8 - numDist2;
           
           
            // If Renegade 1 is closer to or the same distance from the current player as Renegade 2, 
            // Renegade 1 is set as the Zombie Master
            // Else, Renegade 2 is set as the Zombie Master
            if ((trueDist1 < trueDist2) || (trueDist1 == trueDist2))
                indexOfMaster = renegadePositions.get(0);
            else
                indexOfMaster = renegadePositions.get(1);
        }
       
        // Find the Character object at indexOfMaster in playerList and designate it as the
        // Zombie Master ONLY if renegadeCount is greater than 0 
        // (i.e., at least one Renegade is in gameplay)
        if (renegadeCount > 0){
       
            player = Character.playerList.get(indexOfMaster);
           
            player.setZombieMaster(true);
           
        }
       
        return indexOfMaster;
       
    }
   
    // Function determines whether or not the character "Vulture Sam" is currently
    // in gameplay
    // Returns a boolean value indicating the presence of Vulture Same
    // Used in the process of awarding Vulture Sam a life point whenever a different player
    // is eliminated from the game
    public static int isVultureSamPresent(){
        
        int i, index;
        
        boolean vultureSamInGame = false;
        
        // Iterate through all Character objects in playerList
        // If Character
        // (1) Alive, and
        // (2) has name attribute equal to "Vulture Sam,"
        // set vultureSamInGame variable to true
        for (i = 0; i < Character.playerList.size(); i++){
            if (Character.playerList.get(i).getIsAlive() == true && Character.playerList.get(i).getName().equals("Vulture Sam")) {
                vultureSamInGame = true;
                break;
            }
                
        }
        
        if (vultureSamInGame) {
            index = i;
        }
        else {
            index = -1;
        }
        
        return index;
    }

   
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) //Driver for testing the class
    {
        int i;
       
        int numPlayers = 8;
       
        Character player;
       
        Character.initializeAllCharacters(true);
         
        Character.assignCharacters(numPlayers, true);
       
        Character.assignRole(numPlayers);
       
        Character.assignUser();
        
        Character.printAll();
        
        System.out.println(Character.isVultureSamPresent());
       
        /*Character.playerList.get(1).setZombie(true);
       
        Character.playerList.get(2).setLifeStatus(false);
       
        Character.playerList.get(2).setZombie(true);
       
        Character.playerList.get(2).setZombieDead(true);
       
        Character.playerList.get(3).setLifeStatus(false);
       
        Character.playerList.get(3).setZombie(true);
       
        Character.playerList.get(5).setLifeStatus(false);
       
       
        int maxHealth = Character.playerList.get(6).getMaxHealth();
       
        System.out.println("Max health: " + maxHealth);
       
        Character.playerList.get(6).changeHealth(-10);
       
        System.out.println(Character.playerList.get(6).getHealth());
       
        System.out.println(Character.playerList.get(6).getIsAlive());
       
        Character.playerList.get(6).changeHealth(100);
       
        System.out.println(Character.playerList.get(6).getHealth());
       
        System.out.println(Character.playerList.get(6).getIsAlive());
       
        System.out.println(Character.playerList.get(3).getHealth());
       
        Character.playerList.get(3).changeHealth(2);
       
        System.out.println(Character.playerList.get(3).getHealth());
       
        System.out.println(Character.playerList.get(3).getIsZombieDead());
       
        Character.playerList.get(3).changeHealth(-20);
       
        System.out.println(Character.playerList.get(3).getHealth());
       
        System.out.println(Character.playerList.get(3).getIsZombieDead());
       
        System.out.println(Character.playerList.get(5).getHealth());
       
        Character.playerList.get(5).changeHealth(2);
       
        System.out.println(Character.playerList.get(5).getHealth());
       
        Character.playerList.get(5).changeHealth(-3);
       
        System.out.println(Character.playerList.get(5).getHealth());
       */

    }

}