package trie;

import com.search.autocomplete.trie.TST;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class TSTTest {

    private TST tst;

    @Before
    public void setup() {
        tst = new TST(5, "\t");
    }

    @Test
    public void testInsert() {
        Assert.assertTrue(tst.query("a").isEmpty());
        Assert.assertTrue(tst.query("").isEmpty());

        String movie1 = "2008\tUS\tStarship Troopers";
        String movie2 = "2008\tUK\tNaming Pluto";
        String movie3 = "2007\tDE\tAudi Star Talk";
        String movie4 = "2007\tDE\tAudi Star Talk";
        String movie5 = "2008\tDE\tNaming Pluto";

        // add movie1
        tst.insert("Starship Troopers", movie1);
        tst.insert("Troopers", movie1);

        // add movie2
        tst.insert("Naming Pluto", movie2);
        tst.insert("Pluto", movie2);

        List<String> results = tst.query("Star");
        Assert.assertEquals(results.get(0), movie1);

        results = tst.query("Na");
        Assert.assertEquals(results.get(0), movie2);

        // add movie3
        tst.insert("Audi Star Talk", movie3);
        tst.insert("Star Talk", movie3);
        tst.insert("Talk", movie3);


        results = tst.query("Star");
        Assert.assertEquals(results.get(0), movie3);
        Assert.assertEquals(results.get(1), movie1);

        results = tst.query("T");
        Assert.assertEquals(results.get(0), movie3);
        Assert.assertEquals(results.get(1), movie1);

        //add movie4 - same year, same country same name
        tst.insert("Audi Star Talk", movie4);
        tst.insert("Star Talk", movie4);
        tst.insert("Talk", movie4);

        results = tst.query("Star");
        Assert.assertEquals(results.get(0), movie3);
        Assert.assertEquals(results.get(1), movie4);
        Assert.assertEquals(results.get(2), movie1);

        // add movie5 -  same year, same name, different country
        tst.insert("Naming Pluto", movie5);
        tst.insert("Pluto", movie5);
        results = tst.query("Plu");
        Assert.assertEquals(results.get(0), movie2);
        Assert.assertEquals(results.get(1), movie5);

        // test for substring, results should be empty
        results = tst.query("tar");// tar is substring of star
        Assert.assertTrue(results.isEmpty());

        // test for valid string with space
        results = tst.query("Naming ");
        Assert.assertEquals(results.get(0), movie2);

    }

    @Test(expected = NullPointerException.class)
    public void testInsertNullKey() {
        tst.insert(null, "some value");
    }

    @Test(expected = NullPointerException.class)
    public void testInsertNullValue() {
        tst.insert("key", null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertInvalidKey() {
        tst.insert("", "some value");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInsertInvalidValue() {
        tst.insert("key", "");
    }
}
