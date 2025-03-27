import java.io.*;
import java.util.*;

public class bai4ThongKeBayes {
    private static final String FILE_PATH = "src/UIT-ViOCD.txt"; // Đường dẫn file dữ liệu
    private static final int MIN_WORD_COUNT = 5; // Ngưỡng lọc từ ít xuất hiện

    private static Map<String, Integer> wordCount = new HashMap<>();
    private static Map<String, Map<String, Integer>> bigramCount = new HashMap<>();
    private static Set<String> vocabulary = new HashSet<>();

    public static void main(String[] args) {
        // Bước 1: Đọc dữ liệu và xây dựng mô hình
        loadData();

        // Bước 2: Nhập từ bắt đầu từ người dùng
        Scanner scanner = new Scanner(System.in);
        System.out.print("Nhập từ bắt đầu: ");
        String startWord = scanner.next().toLowerCase();  // Gán giá trị trước khi sử dụng

        // Bước 3: Sinh câu dựa trên mô hình Bayes
        System.out.println("Từ bắt đầu: " + startWord);
        String generatedSentence = generateSentence(startWord, 5);
        System.out.println("Câu sinh ra: " + generatedSentence);

        scanner.close();
    }


    // 📌 Đọc dữ liệu từ file
    private static void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                processLine(line);
            }
        } catch (IOException e) {
            System.err.println("Lỗi khi đọc file: " + e.getMessage());
        }

        // Lọc từ vựng chỉ giữ các từ xuất hiện ít nhất MIN_WORD_COUNT lần
        vocabulary.removeIf(word -> wordCount.get(word) < MIN_WORD_COUNT);
    }

    // 📌 Xử lý từng dòng văn bản để thống kê tần suất từ và cặp từ
    private static void processLine(String line) {
        String[] words = line.toLowerCase().split("\\s+"); // Chia tách thành từ
        for (int i = 0; i < words.length; i++) {
            String word = words[i].replaceAll("[^a-zA-ZÀ-Ỷà-ỹ]", ""); // Xóa dấu câu

            if (word.isEmpty()) continue;
            wordCount.put(word, wordCount.getOrDefault(word, 0) + 1);
            vocabulary.add(word);

            if (i > 0) {
                String prevWord = words[i - 1].replaceAll("[^a-zA-ZÀ-Ỷà-ỹ]", "");
                bigramCount.putIfAbsent(prevWord, new HashMap<>());
                bigramCount.get(prevWord).put(word, bigramCount.get(prevWord).getOrDefault(word, 0) + 1);
            }
        }
    }

    // 📌 Sinh câu dựa trên mô hình Bayes
    private static String generateSentence(String startWord, int maxWords) {
        StringBuilder sentence = new StringBuilder(startWord);
        String currentWord = startWord;

        for (int i = 1; i < maxWords; i++) {
            String nextWord = getNextWord(currentWord);
            if (nextWord == null) break;
            sentence.append(" ").append(nextWord);
            currentWord = nextWord;
        }

        return sentence.toString();
    }

    // 📌 Tìm từ tiếp theo theo xác suất Bayes
    private static String getNextWord(String currentWord) {
        Map<String, Integer> nextWords = bigramCount.getOrDefault(currentWord, new HashMap<>());
        if (nextWords.isEmpty()) return null;

        // Tính xác suất P(w2 | w1) = P(w1, w2) / P(w1)
        double totalBigrams = nextWords.values().stream().mapToInt(Integer::intValue).sum();
        TreeMap<Double, String> probabilityMap = new TreeMap<>();
        double cumulativeProbability = 0.0;

        for (Map.Entry<String, Integer> entry : nextWords.entrySet()) {
            String nextWord = entry.getKey();
            double probability = entry.getValue() / totalBigrams;
            cumulativeProbability += probability;
            probabilityMap.put(cumulativeProbability, nextWord);
        }

        // Chọn từ tiếp theo theo phân bố xác suất
        double randValue = Math.random();
        return probabilityMap.higherEntry(randValue).getValue();
    }
}
