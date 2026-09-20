import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class QuickSortTest {
    private final Random rand = new Random();

    @Test
    public void testQuickSortCorrectness() {
        for (int i = 0; i < 100; i++) {
            int[] arr = generateRandomArray(1000);
            int[] expected = arr.clone();
            Arrays.sort(expected);

            QuickSort.sort(arr, new Metrics());
            assertArrayEquals(expected, arr);
        }
    }

    @Test
    public void testQuickSortDepthOnSorted() {
        int n = 100000;
        int[] sortedArr = new int[n];
        for (int i = 0; i < n; i++) sortedArr[i] = i;

        Metrics metrics = new Metrics();
        QuickSort.sort(sortedArr, metrics);

        int maxAllowedDepth = 2 * (int) (Math.log(n) / Math.log(2));
        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth,
                "Глубина рекурсии " + metrics.getMaxDepth() + " превысила предел " + maxAllowedDepth);
    }

    @Test
    public void testAllEqualElements() {
        int n = 100000;
        int[] arr = new int[n];
        Arrays.fill(arr, 7);

        Metrics metrics = new Metrics();
        QuickSort.sort(arr, metrics);

        int[] expected = new int[n];
        Arrays.fill(expected, 7);
        assertArrayEquals(expected, arr);

        int maxAllowedDepth = 2 * (int) (Math.log(n) / Math.log(2));
        assertTrue(metrics.getMaxDepth() <= maxAllowedDepth,
                "3-way partition не сработал: глубина " + metrics.getMaxDepth());
    }

    @Test
    public void testEdgeCases() {
        int[] empty = {};
        QuickSort.sort(empty, new Metrics());
        assertArrayEquals(new int[]{}, empty);

        int[] single = {42};
        QuickSort.sort(single, new Metrics());
        assertArrayEquals(new int[]{42}, single);

        int[] reversed = {5, 4, 3, 2, 1};
        QuickSort.sort(reversed, new Metrics());
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, reversed);
    }

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = rand.nextInt();
        return arr;
    }
}
