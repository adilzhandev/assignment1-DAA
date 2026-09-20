import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;
import java.util.Random;

public class Benchmark {
    private static final int[] SIZES = {1000, 10000, 100000, 1000000};
    private static final String[] TYPES = {"random", "sorted", "duplicates"};
    private static final int RUNS = 5;

    public static void main(String[] args) throws IOException {
        try (FileWriter writer = new FileWriter("results.csv")) {
            writer.write("algorithm,input,n,time_ms,comparisons,max_depth\n");

            for (int n : SIZES) {
                for (String type : TYPES) {
                    runAlgorithm(writer, "MergeSort", n, type);
                    runAlgorithm(writer, "QuickSort", n, type);
                    runAlgorithm(writer, "QuickSelect", n, type);
                }
            }
        }
        System.out.println("results.csv готов");
    }

    private static void runAlgorithm(FileWriter writer, String algo, int n, String type) throws IOException {
        double[] times = new double[RUNS];
        Metrics[] runs = new Metrics[RUNS];

        for (int i = 0; i < RUNS; i++) {
            int[] arr = generateArray(n, type);
            runs[i] = new Metrics();

            switch (algo) {
                case "MergeSort" -> MergeSort.sort(arr, runs[i]);
                case "QuickSort" -> QuickSort.sort(arr, runs[i]);
                case "QuickSelect" -> QuickSelect.select(arr, n / 2, runs[i]);
            }
            times[i] = runs[i].getTimeMs();
        }

        int medianIndex = medianIndexOf(times);
        Metrics median = runs[medianIndex];

        writer.write(String.format(Locale.US, "%s,%s,%d,%.3f,%d,%d%n",
                algo, type, n, times[medianIndex], median.getComparisons(), median.getMaxDepth()));

        System.out.printf(Locale.US, "%-12s %-11s n=%-8d %.3f ms%n", algo, type, n, times[medianIndex]);
    }

    /** Индекс прогона, чьё время оказалось медианным среди RUNS прогонов. */
    private static int medianIndexOf(double[] times) {
        double[] sorted = times.clone();
        Arrays.sort(sorted);
        double medianValue = sorted[times.length / 2];
        for (int i = 0; i < times.length; i++) {
            if (times[i] == medianValue) return i;
        }
        return 0;
    }

    private static int[] generateArray(int n, String type) {
        int[] arr = new int[n];
        Random rand = new Random();
        if (type.equals("random")) {
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt();
        } else if (type.equals("sorted")) {
            for (int i = 0; i < n; i++) arr[i] = i;
        } else if (type.equals("duplicates")) {
            for (int i = 0; i < n; i++) arr[i] = rand.nextInt(10);
        }
        return arr;
    }
}
