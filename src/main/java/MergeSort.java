public class MergeSort {
    private static final int CUTOFF = 15;

    public static void sort(int[] arr, Metrics metrics) {
        if (arr == null || arr.length <= 1) return;
        metrics.startTimer();
        int[] aux = new int[arr.length];
        sort(arr, aux, 0, arr.length - 1, metrics, 1);
        metrics.stopTimer();
    }

    private static void sort(int[] arr, int[] aux, int lo, int hi, Metrics metrics, int depth) {
        metrics.updateDepth(depth);
        if (hi - lo + 1 <= CUTOFF) {
            insertionSort(arr, lo, hi, metrics);
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(arr, aux, lo, mid, metrics, depth + 1);
        sort(arr, aux, mid + 1, hi, metrics, depth + 1);
        merge(arr, aux, lo, mid, hi, metrics);
    }

    private static void merge(int[] arr, int[] aux, int lo, int mid, int hi, Metrics metrics) {
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

    private static void insertionSort(int[] arr, int lo, int hi, Metrics metrics) {
        for (int i = lo + 1; i <= hi; i++) {
            int key = arr[i];
            int j = i - 1;
            while (j >= lo) {
                metrics.addComparison();
                if (arr[j] > key) {
                    arr[j + 1] = arr[j];
                    j--;
                } else break;
            }
            arr[j + 1] = key;
        }
    }
}