public class QuickSelect {
    public static int select(int[] arr, int k, Metrics metrics) {
        if (arr == null) {
            throw new IllegalArgumentException("array must not be null");
        }
        if (arr.length == 0) {
            throw new IllegalArgumentException("array must not be empty");
        }
        if (k < 0 || k >= arr.length) {
            throw new IllegalArgumentException(
                    "k must be in [0, " + (arr.length - 1) + "], got " + k);
        }

        metrics.startTimer();
        int lo = 0, hi = arr.length - 1;

        // Алгоритм итеративный: он использует один кадр стека независимо от n.
        metrics.updateDepth(1);

        while (lo <= hi) {
            int[] p = QuickSort.partition(arr, lo, hi, metrics);
            if (k >= p[0] && k <= p[1]) {
                metrics.stopTimer();
                return arr[k];
            }
            if (k < p[0]) hi = p[0] - 1;
            else lo = p[1] + 1;
        }
        metrics.stopTimer();
        return arr[k];
    }
}