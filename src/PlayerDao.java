import java.sql.SQLException;
import java.util.List;

/**
 * Created by nicko on 15.11.2016.
 */
public interface PlayerDao {
    public List<Player> getTopTenPlayer() throws SQLException;

    public Player addPlayer(String name) throws SQLException;

    public Player getPlayer(String name) throws SQLException;

    public Player updatePlayer(String name, int score) throws SQLException;

    public void deletePlayer(String name) throws SQLException;
}
