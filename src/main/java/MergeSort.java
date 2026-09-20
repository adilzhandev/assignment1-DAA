public class MergeSort {
    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length <= 1) return;
        metrics.startTimer();
        sort(arr, 0, arr.length - 1, metrics, 1);
        metrics.stopTimer();
    }

    private static void sort(int[] arr, int lo, int hi, Metrics metrics, int depth) {
        metrics.updateDepth(depth);
        if (hi <= lo) return;
        int mid = lo + (hi - lo) / 2;
        sort(arr, lo, mid, metrics, depth + 1);
        sort(arr, mid + 1, hi, metrics, depth + 1);

        int[] aux = new int[arr.length]; 
        System.arraycopy(arr, lo, aux, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) arr[k] = aux[j++];
            else if (j > hi) arr[k] = aux[i++];
            else {
                metrics.addComparison();
                if (aux[j] < aux[i]) arr[k] = aux[j++];
                else arr[k] = aux[i++];
            }
        }
    }
}