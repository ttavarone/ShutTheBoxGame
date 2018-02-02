import java.util.Random;
import java.util.Scanner;
/**
 * Contains all the main methods that control the object (the game)
 * that is 'Shut The Box'
 *
 * @author (Tucker Tavarone)
 * @version (1/24/18)
 */
public class ShutTheBox
{
    private int[] numUp;
    private int points=0, roll=0, errNum=0;
    private boolean continueGame=true, numError=false;
    private Random r = new Random();

    /**
     * Main constructor, initialises all the variables to default values
     */
    public ShutTheBox()
    {
        numUp = new int[10]; //contains all the numbers that are still up and 0's for the ones that are down
        for(int i=0; i<9; i++){ // initialises the numbers up as what is up
            numUp[i] = i+1;
        }
        points = 0; // starts with nothing
        roll = 0;
        continueGame = true; // game will be initialised as being able to continue
        errNum = 0;
        numError = false;
    }

    /**
     * A dice roll simulator, simulates rolling two dice and summing it up
     * so values can be anywhere from 1-9
     * 
     * @return int that contains the sum of rolling 'two' dice
     */
    public double rollDice()
    {
        int max = 10;
        int min = 1;
        roll = min + r.nextInt(max);
        return roll;
    }

    /**
     * Runs through the array of numbers down and if it is a 0, then numbers up gets
     * a 0 in that position, if not it stays as the number it should be
     * 
     * It is only run in this class after numbers down, to update what is still up
     */
    private void curNumUp(int numIn)
    {
        for(int i = 0; i<numUp.length-1; i++){
            if(numUp[i]==numIn){
                numUp[i]=0;
            }
        }
    }

    /**
     * Run after game is finished will be called almost last in the runSimulation class 
     * returns sets the points variable to the remaining numbers up
     */
    public void points()
    {
        for(int i=0; i<numUp.length; i++)
        {
            points+=numUp[i];
        }
    }

    /**
     * Returns the value in the points variable
     * 
     * @param int that contains how many points were accumulated
     */
    public int getPoints()
    {
        return points;
    }

    /**
     * This class checks for two things, the first is whether or not the game can continue
     * based on the available options left, the second is if all the numbers are down.... it is run 
     * at the end of the loop in run simulation and the loop breaks if this decides game over
     * 
     */
    public void possibilities()
    {
        int anyUp = 0;
        for(int i = 0; i<numUp.length; i++){
            anyUp += numUp[i];
        }
        if(anyUp==0){continueGame=false;}

        continueGame = sumOfSubset(numUp, numUp.length, roll);
    }

    /**
     * Tests whether or not the game can continue by testing all elements of the set (in the array)
     * and finding a subset that may add up to the roll, thus allowing the game to continue
     */
    private boolean sumOfSubset(int numUpIn[], int n, int sum) 
    { 
        if(sum==0){
            return true;
        }
        if(n==0&&sum!=0){
            return false;
        }
        if(numUpIn[n-1]>sum){
            return sumOfSubset(numUpIn, n-1, sum);
        }

        return sumOfSubset(numUpIn, n-1, sum)||sumOfSubset(numUpIn, n-1, sum-numUpIn[n-1]);

    }

    /**
     * Method that ultimately tests the string that is input by the user and puts down the numbers
     * corresponding to what the user selected. It also generates error messages if the user tries
     * to put numbers down that have already been used.
     * 
     * @param s the string that contains what the scanner picked up for user input (it will be parsed
     *        for ints via an array that contains char
     * @return String that contains a message if it is applicable, otherwise the string is empty
     *         and useless
     */
    public String checker(String s)
    {
        s.trim(); // clean up the string before sending to char array
        char[] nums = s.toCharArray(); // make a char array so we can iterate through and parse nums
        String msg = new String(); // outputs a msg regardless of input, see other msg
        for(int i = 0; i<nums.length; i++){
            int workingValue = nums[i];
            int numberValue = Character.getNumericValue(workingValue);
            if(workingValue==32)continue;
            if(numberValue>0 && numberValue<10){
                if(numUp[numberValue-1]==0){
                    msg = new String("Cannot use that number, you lose."); //ends game
                }
                else{
                    curNumUp(numberValue);
                    numError = false;
                    roll -= numberValue;
                    if(roll>0){continue;}
                    else if(roll==0){break;}
                }
            }
            else{
                //error message if user puts a number down that should not be put down
                errNum = numberValue;
                msg = new String(errNum+" is already down."); //new msg if there is an error
                numError = true;
                break;

            }
        }
        return msg;
    }

    /**
     * Prints out the numUp array so that the user can visualize it
     * 
     */
    public void curNumString()
    {
        for(int i = 0; i<numUp.length-1; i++){
            if(numUp[i]!=0){
                System.out.print(numUp[i]+" ");
            }
        }
        System.out.println();
    }

    /**
     * Main Simulation class that will be called to create new simulations from main
     * 
     * Also manages the interactive text part of the game, the user interface
     */
    public void runSimulation()
    {
        Scanner in = new Scanner(System.in);
        while(continueGame){
            System.out.println("Current Numbers Up ");
            System.out.println("*****************");
            curNumString();
            System.out.println("*****************");
            System.out.println("Your roll is: < "+rollDice()+" >");
            continueGame = sumOfSubset(numUp, numUp.length, roll);
            if(!continueGame){break;}
            System.out.println("Enter your numbers");
            String input = in.nextLine();
            System.out.println(checker(input));
            possibilities();

        }
        System.out.println("Game Over.");
        points();
        System.out.println("You scored "+getPoints());
    }
}

