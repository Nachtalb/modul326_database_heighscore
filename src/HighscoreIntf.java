import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nicko on 22.11.2016.
 */
public interface HighscoreIntf {

    public Player addScore(String name, int score) throws SQLException;

    public Player addPlayer(String name) throws SQLException;

    public boolean playerExists(String name) throws SQLException;

    public Player getPlayer(String name) throws SQLException;

    public ArrayList<Player> getTopTenPlayer() throws SQLException;
}
