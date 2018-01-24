import java.util.Random;
/**
 * Contains all the main methods that control the object (the game)
 * that is 'Shut The Box'
 *
 * @author (Tucker)
 * @version (1/24/18)
 */
public class ShutTheBox
{
    int[] numUp, numDown, allNums;
    int points, roll;
    boolean continueGame;
    
    public ShutTheBox()
    {
        numDown = new int[9]; //initialises the number down as all nothing
        for(int i=1; i<10; i++){ // initialises the numbers up as what is up
            numUp[i] = i;
            allNums[i] = i;
        }
        points = 0; // starts with nothing
        roll = 0;
        continueGame = true; // game will be initialised as being able to continue
    }
    
    public int rollDice()// may have to change this because cant roll a zero....
    { // also it may not be able to produce a 12
        Random dice = new Random(13);
        do{
            roll = dice.nextInt();
        }while(roll==0);
        return roll;
    }
    
    public void curNumUp()
    {
        for(int i = 0; i<9; i++){
            if(numDown[i]==0){numUp[i]=0;}
        }
    }
    
    public void curNumDown()
    {
    }
    
    public void points()
    {
        for(int i=0; i<numUp.length; i++)
        {
            points+=numUp[i];
        }
    }
    
    public void possibilities()
    {
        int countDown = roll;
        while(countDown!=0&&countDown>0){
            for(int i=numUp.length-1; i>=0;i++){
                if(numUp[i]!=0){countDown-=numUp[i];}
            }
        }
        if(countDown>=0){continueGame}
    }
}

