package cs_2365_project_3;

import java.util.*;

public class Dice {
    static int diceArray[] = new int[5]; //each dice in the diceArray holds the value of the symbols
    /*0 = arrow
      1 = dynamite
      2 = bull's eye "1"
      3 = bull's eye "2"
      4 = beer
      5 = gatling
    */
    static boolean diceRerolled[] = new boolean[5]; //gets updated everytime a dice is rolled, so dice can't be rolled again
    public static int gatlingCount = 0; //how many times gatling has been rolled, default zero
    static public int rerollsPlayer = 0; //rerolls player has done in total
    public static int dynamiteCount = 0;

    static public boolean saloonDice[] = new boolean[5];
    static public boolean deadOrAliveDice[] = new boolean[5];
    static private boolean expansionUsed = false;
    static public boolean gatlingUsed = false;

    static public int arrowCount = 0;
    static private boolean dynamitePresent = false;

    public static int dualingCount = 0;
    
    //new counts
    static public int bullseye1Count = 0;
    static public int bullseye2Count = 0;
    static public int beerCount = 0;
    static public int brokenArrowCount = 0;
    static public int bulletCount = 0;
    static public int whiskeyCount = 0;


    public void dice(){
    }

    //rolls all the dice in the dice array
    static public void rollAllDice(){
        Random rand = new Random();
        for (int i = 0; i < diceArray.length; i++) {
            diceArray[i] = rand.nextInt(6);

            if(saloonDice[i] == false) //adds to arrow count if it runs into arrow
                if(diceArray[i] == 0)
                    arrowCount++;

        }
        calcDiceCounts();
    }

    //rolls dice 0-2 for zombie characters
    static public void zombieRollAllDice(){
        Dice.dontUseExpansion();
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            diceArray[i] = rand.nextInt(6);
            if(saloonDice[i] == false) //adds to arrow count if it runs into arrow
                if(diceArray[i] == 0)
                    arrowCount++;
        }
        calcDiceCounts();
    }

    static public boolean dualDiceReroll(){
        Random rand = new Random();
        diceArray[4] = rand.nextInt(6);
        if(diceArray[4] == 3 || diceArray[4] == 4)
            return true;
        else
            return false;
    }

    //only rolls 1 dice, doesn't need array just uses index.
    static public void rerollOneDice(int diceIndex){
        if (canBeRerolled(diceIndex)){
            Random rand = new Random();
            diceArray[diceIndex] = rand.nextInt(6);
            diceRerolled[diceIndex] = true;
            rerollsPlayer++;

            if(saloonDice[diceIndex] == false) //adds to arrow count if it runs into arrow
                if(diceArray[diceIndex] == 0)
                    arrowCount++;
        }
        calcDiceCounts();
    }

    //Rerolled dice, up to the full amount of dice. Array values set to true will be rerolled if they can be.
    static public void rerollDice(boolean diceToReroll[]){
        Random rand = new Random();
        boolean diceHaveBeenRerolled = false; //will be set to true if any dice is rerolled

        if(diceToReroll[0] == true && canBeRerolled(0)){
            diceArray[0] = rand.nextInt(6);
            diceRerolled[0] = true;
            diceHaveBeenRerolled = true;
            if(saloonDice[0] == false) //adds to arrow count if it runs into arrow
                if(diceArray[0] == 0)
                    arrowCount++;
        }
        if(diceToReroll[1] == true && canBeRerolled(1)){
            diceArray[1] = rand.nextInt(6);
            diceRerolled[1] = true;
            diceHaveBeenRerolled = true;
            if(saloonDice[1] == false) //adds to arrow count if it runs into arrow
                if(diceArray[1] == 0)
                    arrowCount++;
        }
        if(diceToReroll[2] == true && canBeRerolled(2)){
            diceArray[2] = rand.nextInt(6);
            diceRerolled[2] = true;
            diceHaveBeenRerolled = true;
            if(saloonDice[2] == false) //adds to arrow count if it runs into arrow
                if(diceArray[2] == 0)
                    arrowCount++;
        }
        if(diceToReroll[3] == true && canBeRerolled(3)){
            diceArray[3] = rand.nextInt(6);
            diceRerolled[3] = true;
            diceHaveBeenRerolled = true;
            if(saloonDice[3] == false) //adds to arrow count if it runs into arrow
                if(diceArray[3] == 0)
                    arrowCount++;
        }
        if(diceToReroll[4] == true && canBeRerolled(4)){
            diceArray[4] = rand.nextInt(6);
            diceRerolled[4] = true;
            diceHaveBeenRerolled = true;
            if(saloonDice[4] == false) //adds to arrow count if it runs into arrow
                if(diceArray[4] == 0)
                    arrowCount++;
        }
        if (diceHaveBeenRerolled == true) {
            rerollsPlayer++;
            calcDiceCounts();
        }
    }

    //checks to see if dice can be rerolled or not at the index provided
    static public boolean canBeRerolled(int diceIndex){
        if(rerollsPlayer >= 2)
            return false;
        if(diceRerolled[diceIndex] == true) //if dice is rerolled, you can't reroll it again
            return false;
        //-----------------------------------------CHANGE FOR EXPANSION-----------------------
        if(saloonDice[diceIndex] == true) //no dynamite with saloon, can always reroll
            return true;
        else if(deadOrAliveDice[diceIndex] == true) { //if dice is dynamite, can't reroll
            if (diceArray[diceIndex] == 1)
                return false;
        }
        else {
            if(diceArray[diceIndex] == 1) //if dice is dynamite, can't reroll
                return false;
        }
        return true;
    }

    static public String toString(int diceNum, int symbolIndex){ //stopped here lately
        String string = new String("");
        if(saloonDice[diceNum] == true){
            if(symbolIndex == 0)
                string = "Broken Arrow";
            else if(symbolIndex == 1)
                string = "Bullet";
            else if(symbolIndex == 2)
                string = "Double Bull's Eye \"1\"";
            else if(symbolIndex == 3)
                string = "Double Bull's Eye \"2\"";
            else if(symbolIndex == 4)
                string = "Double Beer";
            else if(symbolIndex == 5)
                string = "Double Gatling";
        }
        else if(deadOrAliveDice[diceNum] == true){
            if(symbolIndex == 0)
                string = "Indian Arrow";
            else if(symbolIndex == 1)
                string = "Dynamite";
            else if(symbolIndex == 2)
                string = "Whiskey Bottle";
            else if(symbolIndex == 3)
                string = "Fight a Dual";
            else if(symbolIndex == 4)
                string = "Fight a Dual";
            else if(symbolIndex == 5)
                string = "Gatling";
        }
        else{
            if(symbolIndex == 0)
                string = "Indian Arrow";
            else if(symbolIndex == 1)
                string = "Dynamite";
            else if(symbolIndex == 2)
                string = "Bull's Eye \"1\"";
            else if(symbolIndex == 3)
                string = "Bull's Eye \"2\"";
            else if(symbolIndex == 4)
                string = "Beer";
            else if(symbolIndex == 5)
                string = "Gatling";
        }
        return string;
    }

    //if gatling is >= 3 returns true
    static public boolean gatlingReady(){
        if(gatlingCount >= 3) {
            gatlingCount = 0;
            gatlingUsed = true;
            return true;
        }
        else
            return false;
    }

    //if dynamite is >= 3 returns true
    static public boolean dynamiteReady(){
        if(dynamiteCount >= 3) {
            dynamiteCount = 0;
            return true;
        }
        else
            return false;
    }

    //calculate gatling and dynamite counts for the current dice in hand

    static public void calcDiceCounts(){
        gatlingCount = 0;
        dynamiteCount = 0;
        dualingCount = 0;
        
        bullseye1Count = 0;
        bullseye2Count = 0;
        beerCount = 0;
        brokenArrowCount = 0;
        bulletCount = 0;
        whiskeyCount = 0;
        arrowCount = 0;
        for(int i = 0; i < diceArray.length; i++){
            if(saloonDice[i] == true){
                if(i == 1){
                    if(diceArray[i] == 0)
                        brokenArrowCount++;
                    else if(diceArray[i] == 1)
                        bulletCount++;
                    else if(diceArray[i] == 2)
                        bullseye1Count += 2;
                    else if(diceArray[i] == 3)
                        bullseye2Count += 2;
                    else if(diceArray[i] == 4)
                        dynamiteCount++;
                    else if(diceArray[i] == 5)
                        gatlingCount += 2;
                }
                else if(i == 2){
                    if(diceArray[i] == 0)
                        brokenArrowCount++;
                    else if(diceArray[i] == 1)
                        beerCount++;
                    else if(diceArray[i] == 2)
                        bullseye1Count++;
                    else if(diceArray[i] == 3)
                        bullseye2Count++;
                    else if(diceArray[i] == 4)
                        dynamiteCount++;
                    else if(diceArray[i] == 5)
                        beerCount += 2;
                }
            }
            if(deadOrAliveDice[i] == true){
                if(diceArray[i] == 0)
                    arrowCount++;
                if(diceArray[i] == 1)
                    dynamiteCount++;
                if(diceArray[i] == 2)
                    whiskeyCount++;
                if(diceArray[i] == 3 || diceArray[i] == 4)
                    dualingCount++;
                if(diceArray[i] == 5)
                    gatlingCount++;
            }
            else{
                if (diceArray[i] == 0)
                    arrowCount++;
                if (diceArray[i] == 1)
                    dynamiteCount++;
                if (diceArray[i] == 2)
                    bullseye1Count++;
                if (diceArray[i] == 3)
                    bullseye2Count++;
                if (diceArray[i] == 4)
                    beerCount++;
                if (diceArray[i] == 5)
                    gatlingCount++;
                
            }
        }
        if (dynamiteCount > 0)
            dynamitePresent = true;
        else
            dynamitePresent = false;
    }

    //Call the function to use expansion in dice class,
    // pass the number of dice you want to use from saloon expansion, either 1 or 2 or them.
    //2 deadOrAliveDice / black dice used automatically
    public static void useExpansion(int saloonDiceToUse){
        expansionUsed = true;
        deadOrAliveDice[3] = true;
        deadOrAliveDice[4] = true;
        saloonDice[1] = false;
        saloonDice[2] = false;
        if(saloonDiceToUse == 1){ //loudmouth
            saloonDice[1] = true;
            saloonDice[2] = false;
        }
        else if(saloonDiceToUse == 2){//coward
            saloonDice[2] = true;
            saloonDice[1] = false;
        }
        else if(saloonDiceToUse == 3){
            saloonDice[1] = true;
            saloonDice[2] = true;
        }
    }

    //sets expansion to not be used, sets all dice to default dice
    public static void dontUseExpansion(){
        expansionUsed = false;
        for (int i = 0; i < diceArray.length; i++) {
            deadOrAliveDice[i] = false;
            saloonDice[i] = false;
        }
    }

    public static void dynamiteToGatling(){
        boolean converted = false;
        for(int i = 0; i < 5 && converted == false; i++){
            if (saloonDice[i] == false) {
                if (diceArray[i] == 0) {
                    diceArray[i] = 5;
                    converted = true;
                }
            }
        }
        calcDiceCounts();
    }

    //tests dice class
    private static void testDiceClass(){
        System.out.println("----------------TESTING DICE CLASS----------------");
        System.out.println("Testing diceArray[] blank values, expected values should be all zero.");
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
        System.out.println("\n");


        System.out.println("Testing rollAllDice(), should be random dice values displayed between 0-5");
        rollAllDice();
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
        System.out.println("\n");

        System.out.println("Testing arrowCount, should be updated based on number of arrow in the last hand (gets reset by game class only)");
        System.out.println("Arrow Count: " + arrowCount + "\n");

        System.out.println("Testing zombieRollAllDice(), only first 3 should be rolled dice from 0-5");
        zombieRollAllDice();
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
        System.out.println("\n");

        System.out.println("Testing rollOneDice(), only the 5th dice should be rolled/rerolled if it's not dynamite (1)");
        rerollOneDice(4);
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
        System.out.println("\n");

        System.out.println("Testing rerollDice(), should reroll all the dice except for 5 which has already been rerolled");
        boolean[] myBoolArr = new boolean[] {true, true, true, true, true};
        rerollDice(myBoolArr);
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
        System.out.println("\n");

        System.out.println("Testing toString(), should return string name the 1st dice in the array at index 0");
        System.out.println(Dice.toString(0, diceArray[0]) + "\n");

        System.out.println("Testing gatlingReady(), should print 'true' is >= 3 gatling dice, which is index 5 on normal dice");
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
        System.out.println(gatlingReady());
        System.out.println();


        System.out.println("Testing dynamiteReady(), should print 'true' is >= 3 dynamite dice, which is index 1 on normal dice");
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
        System.out.println(dynamiteReady() + "\n");

        System.out.println("Testing calcDiceCounts(), should update gatlingCount & dynamiteCount variables to current dynamite (index 1) and gatling (index 5) in hand");
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
        calcDiceCounts();
        System.out.println();
        System.out.println("Gatling count: " + gatlingCount);
        System.out.println("Dynamite count: " + dynamiteCount + "\n");

        System.out.println("Testing useExpansion(), should update deadOrAliveDice[3] and deadOrAliveDice[4] " +
                "to be true, and saloonDice[2] and saloonDice[1] to be true since 2 is passed");
        useExpansion(2);
        System.out.print("deadOfAliveDice[] array printed: ");
        for(int i = 0; i < 5; i++)
            System.out.print(deadOrAliveDice[i] + " ");
        System.out.println();
        System.out.print("saloonDice[] array printed: ");
        for(int i = 0; i < 5; i++)
            System.out.print(saloonDice[i] + " ");
        System.out.println("\n");

        System.out.println("Testing dontUseExpansion(), should update deadOrAliveDice[] to be full of false values, and saloonDice[] to be full of false values");
        dontUseExpansion();
        System.out.print("deadOfAliveDice[] array printed: ");
        for(int i = 0; i < 5; i++)
            System.out.print(deadOrAliveDice[i] + " ");
        System.out.println();
        System.out.print("saloonDice[] array printed: ");
        for(int i = 0; i < 5; i++)
            System.out.print(saloonDice[i] + " ");
        System.out.println("\n");

        System.out.println("Testing dynamiteToGatling(), should change first 1 to a 5 in dice array if there is one");
        System.out.print("Original dice: ");
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
        dynamiteToGatling();
        System.out.println("");
        System.out.print("After dynamiteToGatling() dice: ");
        for(int i = 0; i < 5; i++)
            System.out.print(diceArray[i] + " ");
    }

    public static void main(String[] args) {
        // write your code here
    }
}