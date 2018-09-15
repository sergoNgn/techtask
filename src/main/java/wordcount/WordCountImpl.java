package wordcount;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class WordCountImpl implements WordCount {

    @Override
    public Map<String, Integer> getCountWords(String fileName) {
        if (Objects.isNull(fileName) || fileName.isEmpty()) {
            return Collections.emptyMap();
        }
        Map<String, Integer> resultMap = new HashMap<>();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                countWords(line.split("\\W"), resultMap);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return resultMap;
    }

    private void countWords(String[] words, Map<String, Integer> resultMap) {
        for (String word : words) {
            if (!word.isEmpty()) {
                resultMap.merge(word.toLowerCase(), 1, (oldValue, newValue) -> oldValue + newValue);
            }
        }
    }
}
