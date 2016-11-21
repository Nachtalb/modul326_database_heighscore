import java.sql.*;
import java.util.ArrayList;

/**
 * Created by nicko on 15.11.2016.
 */
public class PlayerDaoImpl implements PlayerDao {

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_NAME = "bombermann";
    private static final String DB_LOCATION = "./";
    private static final String DB_CONNECTION = "jdbc:h2:" + DB_LOCATION + DB_NAME;
    private static final String DB_USER = "bomberman";
    private static final String DB_PASSWORD = "";

    private static Connection getDBConnection() {
        Connection dbConnection = null;

        try {

            Class.forName(DB_DRIVER);

        } catch (ClassNotFoundException e) {

            System.out.println(e.getMessage());

        }

        try {

            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER,
                    DB_PASSWORD);
            return dbConnection;

        } catch (SQLException e) {

            System.out.println(e.getMessage());

        }

        return dbConnection;

    }

    @Override
    public ArrayList<Player> getTopTenPlayer() throws SQLException {
        Connection con = getDBConnection();

        Statement stmt = null;
        ArrayList<Player> players = new ArrayList<Player>();
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player ORDER BY score DESC LIMIT 10");

            while (rs.next()) {
                players.add(new Player(rs.getString("name"), rs.getInt("score")));
            }

            stmt.close();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return players;
        }

    }

    @Override
    public Player addPlayer(String name) throws SQLException {
        Connection con = getDBConnection();


        PreparedStatement stmt = null;
        Player player = null;

        String query = "INSERT INTO player(name, score) VALUES(?,?)";
        try {
            con.setAutoCommit(false);

            stmt = con.prepareStatement(query);

            stmt.setString(1, name);
            stmt.setInt(2, 0);

            stmt.executeUpdate();
            stmt.close();

            con.commit();

            player = new Player(name, 0);
        } catch (SQLException e) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return player;
        }

    }

    @Override
    public Player getPlayer(String name) throws SQLException {
        Connection con = getDBConnection();

        Statement stmt = null;
        Player player = null;
        try {
            con.setAutoCommit(false);
            stmt = con.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM player WHERE name='" + name + "';");

            if (rs.isBeforeFirst()) {
                while (rs.next()) {
                    player = new Player(rs.getString("name"), rs.getInt("score"));
                }
            }

            stmt.close();
            con.commit();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return player;
        }

    }

    @Override
    public Player updatePlayer(String name, int score) throws SQLException {
        Connection con = getDBConnection();

        PreparedStatement stmt = null;

        String query = "UPDATE player SET name=?, score=? WHERE name=? ";
        try {
            con.setAutoCommit(false);

            stmt = con.prepareStatement(query);

            stmt.setString(1, name);
            stmt.setInt(2, score);
            stmt.setString(3, name);

            stmt.executeUpdate();
            stmt.close();

            con.commit();

        } catch (SQLException e) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
            return null;
        }

    }

    @Override
    public void deletePlayer(String name) throws SQLException {
        Connection con = getDBConnection();

        PreparedStatement stmt = null;

        String query = "DELETE FROM player WHERE name=?";
        try {
            con.setAutoCommit(false);

            stmt = con.prepareStatement(query);

            stmt.setString(1, name);

            stmt.executeUpdate();
            stmt.close();

            con.commit();
        } catch (SQLException e) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }
    }

    public void createStructure() throws SQLException {
        Connection con = getDBConnection();

        PreparedStatement stmt = null;
        Player player = null;

        String query = "CREATE TABLE player (name VARCHAR(32), score INT );";
        try {
            con.setAutoCommit(false);

            stmt = con.prepareStatement(query);

            stmt.executeUpdate();
            stmt.close();

            con.commit();

        } catch (SQLException e) {

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            con.close();
        }

    }
}
