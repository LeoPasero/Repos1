package assignemnt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

class Sentence {
    String text;
    int wordCount;

    Sentence(String text) {
        this.text = text;
        this.wordCount = text.split("\\s+").length;
    }
}

public class intern {
    public static void main(String[] args) {
        String filePath = "C:\\Users\\paser\\OneDrive\\Documenti\\Java\\L_2019152EN.01004501.xml.html"; 
        List<Sentence> sentences = extractSentences(filePath);
        Map<Integer, Integer> sentenceLengthDistribution = new HashMap<>();
        List<Double> similarities = new ArrayList<>();

        for (Sentence sentence : sentences) {
            sentenceLengthDistribution.put(sentence.wordCount, sentenceLengthDistribution.getOrDefault(sentence.wordCount, 0) + 1);
        }

        for (int i = 0; i < sentences.size(); i++) {
            for (int j = i + 1; j < sentences.size(); j++) {
                double similarity = calculateSimilarity(sentences.get(i), sentences.get(j));
                similarities.add(similarity);
            }
        }

        System.out.println("Sentence Length Distribution:");
        sentenceLengthDistribution.forEach((length, count) -> System.out.println("Length: " + length + " words, Count: " + count));
    }

    private static List<Sentence> extractSentences(String filePath) {
        List<Sentence> sentences = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String content = reader.lines().reduce("", (acc, line) -> acc + " " + line);
            String[] splitSentences = content.split("[.!?]");
            for (String sentenceText : splitSentences) {
                if (!sentenceText.trim().isEmpty()) {
                    sentences.add(new Sentence(sentenceText.trim()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sentences;
    }

    private static double calculateSimilarity(Sentence s1, Sentence s2) {
        Set<String> set1 = new HashSet<>(Arrays.asList(s1.text.toLowerCase().split("\\s+")));
        Set<String> set2 = new HashSet<>(Arrays.asList(s2.text.toLowerCase().split("\\s+")));
        Set<String> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        Set<String> union = new HashSet<>(set1);
        union.addAll(set2);
        return (double) intersection.size() / union.size();
    }
}
