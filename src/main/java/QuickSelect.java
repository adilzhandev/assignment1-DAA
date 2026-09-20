public class QuickSelect {
    public static int select(int[] arr, int k, Metrics metrics) {
        metrics.startTimer();
        int lo = 0, hi = arr.length - 1;
        int depth = 1;

        while (lo <= hi) {
            metrics.updateDepth(depth++);
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