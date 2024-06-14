package task6;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.List;
import java.util.ArrayList;

public class main {

    public static int parallelSum(int[] numbers) {
        int cores = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(cores);
        List<Future<Integer>> futures = new ArrayList<>();

        int partLength = numbers.length / cores;
        for (int i = 0; i < cores; i++) {
            final int start = i * partLength;
            final int end = (i == cores - 1) ? numbers.length : (start + partLength);
            futures.add(executor.submit(() -> {
                int sum = 0;
                for (int j = start; j < end; j++) {
                    sum += numbers[j];
                }
                return sum;
            }));
        }

        int totalSum = 0;
        // Сбор результатов
        for (Future<Integer> future : futures) {
            try {
                totalSum += future.get();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();
        return totalSum;
    }

    public static void main(String[] args) {
        int[] numbers = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int sum = parallelSum(numbers);
        System.out.println("Сумма элементов массива: " + sum);
    }
}