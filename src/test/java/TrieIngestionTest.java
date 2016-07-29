import com.search.autocomplete.TrieIngestion;
import com.search.autocomplete.trie.TST;
import com.search.autocomplete.trie.Trie;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class TrieIngestionTest {

    @Test
    public void testGetMovieNameKeys() {
        Trie trie = new TST(10, "\t");
        TrieIngestion trieIngestion = new TrieIngestion(trie, new String[]{"test"}, "\t", " ");
        List<String> keys = trieIngestion.getMovieNameKeys("Star Troopers");
        Assert.assertTrue(keys.size() == 2);
        Assert.assertEquals(keys.get(0), "Star Troopers");
        Assert.assertEquals(keys.get(1), "Troopers");

        keys = trieIngestion.getMovieNameKeys("Swades");
        Assert.assertTrue(keys.size() == 1);
        Assert.assertEquals(keys.get(0), "Swades");

        keys = trieIngestion.getMovieNameKeys(" ");
        Assert.assertTrue(keys.size() == 0);

        keys = trieIngestion.getMovieNameKeys("");
        Assert.assertTrue(keys.size() == 0);

        keys = trieIngestion.getMovieNameKeys(null);
        Assert.assertTrue(keys.size() == 0);
    }
}
