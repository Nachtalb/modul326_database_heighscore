import highscore.Highscore;
import highscore.HighscoreIntf;
import highscore.Player;
import highscore.PlayerDaoImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by nicko on 21.11.2016.
 */
public class HighscoreTest {

    private HighscoreIntf highscore;

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

        Assert.assertNotNull(player);
        Assert.assertEquals(new Player("Joe", 0).getName(), player.getName());
    }


    @Test
    public void addScore() throws Exception {
        System.out.println("testing: addScore()");
        Player playerBefore = highscore.addPlayer("Malte");
        Player playerAfter = highscore.addScore("Malte", 5);

        Assert.assertNotNull(playerBefore);
        Assert.assertNotNull(playerAfter);

        Assert.assertTrue(playerAfter.getScore() > playerBefore.getScore());
        Assert.assertEquals(playerBefore.getName(), playerAfter.getName());
        Assert.assertEquals(0, playerBefore.getScore());
        Assert.assertEquals(5, playerAfter.getScore());
    }

    @Test
    public void playerExists() throws Exception {
        System.out.println("testing: playerExist()");
        Player player = highscore.addPlayer("Foo");

        Assert.assertNotNull(player);

        Boolean result = highscore.playerExists("Foo");
        Boolean result2 = highscore.playerExists("Bar");

        Assert.assertTrue(result);
        Assert.assertFalse(result2);
    }

    @Test
    public void getTopTenPlayer() throws Exception {
        System.out.println("testing: getTopTenPlayer()");
        Player smith = highscore.addPlayer("Smith");
        Player jaden = highscore.addPlayer("Jaden");
        Player will = highscore.addPlayer("Will");

        Assert.assertNotNull(smith);
        Assert.assertNotNull(jaden);
        Assert.assertNotNull(will);

        highscore.addScore("Smith", 100);
        highscore.addScore("Jaden", 200);
        highscore.addScore("Will", 300);

        ArrayList<Player> players = highscore.getTopTenPlayer();

        for (int i = 0; i < players.size(); i++) {
            Assert.assertEquals(i * 100 + 100, players.get(i).getScore());
        }
    }

}