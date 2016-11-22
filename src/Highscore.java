import java.sql.SQLException;
import java.util.ArrayList;


public class Highscore implements HighscoreIntf {

    private static Highscore instance = null;

    private PlayerDaoImpl playerDaoImpl;

    private Highscore() {
        playerDaoImpl = new PlayerDaoImpl("bomberman", "./");
    }

    /**
     * Returns the Highscore object
     *
     * @return the Highscore object
     */
    static Highscore getInsctance() {
        if (instance == null)
            instance = new Highscore();
        return instance;
    }

    /**
     * Adds given score to the player
     *
     * @param name  of the player
     * @param score which will be added to the player
     * @return Player which was updated
     * @throws SQLException if there is an error with the database
     */
    public Player addScore(String name, int score) throws SQLException {
        if (playerExists(name)) {
            Player player = playerDaoImpl.getPlayer(name);
            int newScore = player.getScore() + score;
            return playerDaoImpl.updatePlayer(name, newScore);
        } else {
            return null;
        }
    }

    /**
     * Adds a new player
     *
     * @param name of the new player
     * @return Player which was created
     * @throws SQLException if there is an error with the database
     */
    public Player addPlayer(String name) throws SQLException {
        if (!playerExists(name)) {
            return playerDaoImpl.addPlayer(name);
        } else {
            return null;
        }
    }

    /**
     * Returns true if the player already exists
     *
     * @param name of the player
     * @return true if player exists
     * @throws SQLException if there is an error with the database
     */
    public boolean playerExists(String name) throws SQLException {
        return (playerDaoImpl.getPlayer(name) != null);
    }

    /**
     * Gives you a player if he exists
     *
     * @param name of the player
     * @return player if exists otherwise null
     * @throws SQLException if there is an error with the database
     */
    public Player getPlayer(String name) throws SQLException {
        return playerDaoImpl.getPlayer(name);
    }

    /**
     * Get the top ten players
     *
     * @throws SQLException if there is an error with the database
     */
    public ArrayList<Player> getTopTenPlayer() throws SQLException {
        return playerDaoImpl.getTopTenPlayer();
    }

}
