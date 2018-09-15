package wordcount;

import java.util.Map;

public interface WordCount {
    Map<String, Integer> getCountWords(String fileName);
}
