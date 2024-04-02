import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static AtomicInteger coolNickWithLength3 = new AtomicInteger(0);
    public static AtomicInteger coolNickWithLength4 = new AtomicInteger(0);
    public static AtomicInteger coolNickWithLength5 = new AtomicInteger(0);
    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        List<Thread> threadList = new ArrayList<>();

        threadList.add(new Thread(() -> {
            for(String text: texts) {
                if (!checkIsSameLetters(text)) continue;
                incrementCount(text);
            }
        }));

        threadList.add(new Thread(() -> {
            for(String text: texts) {
                if (!checkisPalindrome(text)) continue;
                incrementCount(text);
            }
        }));

        threadList.add(new Thread(() -> {
            for(String text: texts) {
                if (!checkLettersInAlphabeticalOrder(text)) continue;
                incrementCount(text);
            }
        }));

        for(Thread thread: threadList) {
            thread.start();
            thread.join();
        }

        System.out.println("Красивых слов с длиной 3: " + coolNickWithLength3.get());
        System.out.println("Красивых слов с длиной 4: " + coolNickWithLength4.get());
        System.out.println("Красивых слов с длиной 5: " + coolNickWithLength5.get());
    }
    public static void incrementCount(String text) {
        switch (text.length()) {
            case 3:
                coolNickWithLength3.getAndIncrement();
                break;
            case 4:
                coolNickWithLength4.getAndIncrement();
                break;
            case 5:
                coolNickWithLength5.getAndIncrement();
                break;
        }
    }
    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean checkIsSameLetters(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (i == 0) continue;
            if (word.charAt(i) != word.charAt(i -1)) return false;
        }
        return true;
    }

    public static boolean checkLettersInAlphabeticalOrder(String word) {
        for (int i = 0; i < word.length(); i++) {
            if (i == 0) continue;
            if (word.charAt(i) < word.charAt(i -1)) return false;
        }
        return true;
    }

    public static boolean checkisPalindrome(String word) {
        int i = 0, j = word.length() - 1;
        while (i < j) {
            if (word.charAt(i) != word.charAt(j)) {
                return false;
            }
            i++;
            j--;
        }

        return true;
    }
}
