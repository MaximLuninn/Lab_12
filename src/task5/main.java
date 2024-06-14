package task5;

import java.util.concurrent.*;

public class main {

    public static int findMax(int[] array) {
        int numThreads = Runtime.getRuntime().availableProcessors();
        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Future<Integer>[] futures = new Future[numThreads];

        int chunkSize = array.length / numThreads;
        int startIndex = 0;

        for (int i = 0; i < numThreads; i++) {
            int endIndex = i == numThreads - 1 ? array.length : startIndex + chunkSize;
            int[] chunk = new int[endIndex - startIndex];
            System.arraycopy(array, startIndex, chunk, 0, chunk.length);

            final int index = i;
            futures[i] = executor.submit(() -> {
                int max = Integer.MIN_VALUE;
                for (int num : chunk) {
                    if (num > max) {
                        max = num;
                    }
                }
                return max;
            });

            startIndex = endIndex;
        }

        int globalMax = Integer.MIN_VALUE;
        for (int i = 0; i < numThreads; i++) {
            try {
                int localMax = futures[i].get();
                if (localMax > globalMax) {
                    globalMax = localMax;
                }
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        executor.shutdown();

        return globalMax;
    }

    public static void main(String[] args) {
        int[] array = {5, 3, 8, 2, 9, 1, 4, 7, 6};
        int max = findMax(array);
        System.out.println("Max element in the array: " + max);
    }
}