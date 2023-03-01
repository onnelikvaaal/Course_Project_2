package functionaltask;

import java.util.*;
import java.util.stream.Collectors;

public class FPTask {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        System.out.println("Введите слова одной строкой через пробел:");
        String inputString = sc.nextLine();

        List<String> words = Arrays.stream(inputString.split(" ")).map(String::toLowerCase).collect(Collectors.toList());

        System.out.println("В тексте " + words.size() + " слов");

        TreeMap<String, Integer> sortedWords = new TreeMap<>();
        for (int i = 0; i < words.size(); i++) {
            if (!sortedWords.containsKey(words.get(i))) {
                sortedWords.put(words.get(i), 1);
            } else {
                Integer value = sortedWords.get(words.get(i));
                sortedWords.put(words.get(i), value + 1);
            }
        }

        Map<String, Integer> sortedByTimes = sortedWords.entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));

        sortedByTimes.entrySet().forEach(System.out::println);

    }
}
