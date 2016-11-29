import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by nicko on 21.11.2016.
 */
public class HighscoreTest {

    private Highscore highscore;

    @Before
    public void setUp() throws SQLException {
        System.out.println("setting up");
        PlayerDaoImpl daoimpl = new PlayerDaoImpl("testing", null);
        daoimpl.recreateStructure();

        highscore = Highscore.getInsctance("testing");
    }

    @Test
    public void addPlayer() throws Exception {
        System.out.println("testing: addPlayer()");
        Player player = highscore.addPlayer("Joe");

        assertNotNull(player);
        assertEquals(new Player("Joe", 0).getName(), player.getName());
    }


    @Test
    public void addScore() throws Exception {
        System.out.println("testing: addScore()");
        Player playerBefore = highscore.addPlayer("Malte");
        Player playerAfter = highscore.addScore("Malte", 5);

        assertNotNull(playerBefore);
        assertNotNull(playerAfter);

        assertTrue(playerAfter.getScore() > playerBefore.getScore());
        assertEquals(playerBefore.getName(), playerAfter.getName());
        assertEquals(0, playerBefore.getScore());
        assertEquals(5, playerAfter.getScore());
    }

    @Test
    public void playerExists() throws Exception {
        System.out.println("testing: playerExist()");
        Player player = highscore.addPlayer("Foo");

        assertNotNull(player);

        Boolean result = highscore.playerExists("Foo");
        Boolean result2 = highscore.playerExists("Bar");

        assertTrue(result);
        assertFalse(result2);
    }

    @Test
    public void getTopTenPlayer() throws Exception {
        System.out.println("testing: getTopTenPlayer()");
        Player smith = highscore.addPlayer("Smith");
        Player jaden = highscore.addPlayer("Jaden");
        Player will = highscore.addPlayer("Will");

        assertNotNull(smith);
        assertNotNull(jaden);
        assertNotNull(will);

        highscore.addScore("Smith", 100);
        highscore.addScore("Jaden", 200);
        highscore.addScore("Will", 300);

        ArrayList<Player> players = highscore.getTopTenPlayer();

        for (int i = 0; i < players.size(); i++) {
            assertEquals(i * 100 + 100, players.get(i).getScore());
        }
    }

}