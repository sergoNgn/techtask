package wordcount;

import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class WordCountImplTest {

    private WordCount wordCount = new WordCountImpl();

    @Test
    public void countWordsShouldReturnEmptyMapForNull() {
        assertNotNull(wordCount.getCountWords(null));
    }

    @Test
    public void countWordsShouldReturnEmptyMapForEmptyString() {
        assertNotNull(wordCount.getCountWords(""));
    }

    @Test
    public void countWordsAllWordsAreDoubled() {
        String[] linesArray = "This is the test text file for word count task.".split("\\W");

        Map<String, Integer> wordsCountMap =
                wordCount.getCountWords("src/test/resources/AllWordsDoubledFile.txt");

        for (String word : linesArray) {
            assertEquals(wordsCountMap.get(word.toLowerCase()), Integer.valueOf(2));
        }
    }

    @Test
    public void countWordsUniqueWords() {
        String[] linesArray = "All words are unique".split("\\W");

        Map<String, Integer> wordsCountMap =
                wordCount.getCountWords("src/test/resources/UniqueWordsFile.txt");

        for (String word : linesArray) {
            assertEquals(wordsCountMap.get(word.toLowerCase()), Integer.valueOf(1));
        }
    }

    @Test
    public void countWordsEmptyFile() {
        Map<String, Integer> wordsCountMap =
                wordCount.getCountWords("src/test/resources/EmptyFile.txt");

        assertTrue(wordsCountMap.isEmpty());
    }

    @Test
    public void countWordsFromTechTaskFile() {
        Map<String, Integer> wordsCountMap =
                wordCount.getCountWords("src/test/resources/TechTaskFile.txt");

        assertEquals(wordsCountMap.get("a"), Integer.valueOf(19)); // I counted 19 a/A words
        assertEquals(wordsCountMap.get("the"), Integer.valueOf(14));
    }
}