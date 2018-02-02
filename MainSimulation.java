
/**
 * Runs the main simulation for the game, so it contains a main method which
 * constructs and then calls runSimulation
 *
 * @author (Tucker Tavarone)
 * @version (1/27/18)
 */
public class MainSimulation
{
    public static void main(String[] args)
    {
        ShutTheBox newGame = new ShutTheBox(); //new ShutTheBox object
        newGame.runSimulation(); //starts a new game and runs the simulation
    }
}
