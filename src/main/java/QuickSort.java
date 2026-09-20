import java.util.Random;

public class QuickSort {
    private static final Random RAND = new Random();

    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length <= 1) return;
        metrics.startTimer();
        sort(arr, 0, arr.length - 1, metrics, 1);
        metrics.stopTimer();
    }

    private static void sort(int[] arr, int lo, int hi, Metrics metrics, int depth) {
        if (lo >= hi) return;
        metrics.updateDepth(depth);
        int p = partition(arr, lo, hi, metrics);
        sort(arr, lo, p - 1, metrics, depth + 1);
        sort(arr, p + 1, hi, metrics, depth + 1);
    }

    private static int partition(int[] arr, int lo, int hi, Metrics metrics) {
        int pivotIndex = lo + RAND.nextInt(hi - lo + 1);
        swap(arr, pivotIndex, hi);
        int pivot = arr[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            metrics.addComparison();
            if (arr[j] < pivot) {
                swap(arr, i, j);
                i++;
            }
        }
        swap(arr, i, hi);
        return i;
    }

    private static void swap(int[] arr, int i, int j) {
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}