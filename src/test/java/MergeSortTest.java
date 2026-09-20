import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MergeSortTest {
    private final Random rand = new Random();

    @Test
    public void testMergeSortCorrectness() {
        for (int i = 0; i < 100; i++) {
            int[] arr = generateRandomArray(1000);
            int[] expected = arr.clone();
            Arrays.sort(expected);

            MergeSort.sort(arr, new Metrics());
            assertArrayEquals(expected, arr);
        }
    }

    @Test
    public void testDuplicateHeavyArrays() {
        for (int i = 0; i < 100; i++) {
            int[] arr = new int[1000];
            for (int j = 0; j < arr.length; j++) arr[j] = rand.nextInt(10);
            int[] expected = arr.clone();
            Arrays.sort(expected);

            MergeSort.sort(arr, new Metrics());
            assertArrayEquals(expected, arr);
        }
    }

    @Test
    public void testEdgeCases() {
        int[] empty = {};
        MergeSort.sort(empty, new Metrics());
        assertArrayEquals(new int[]{}, empty);

        int[] single = {42};
        MergeSort.sort(single, new Metrics());
        assertArrayEquals(new int[]{42}, single);

        int[] allEqual = {7, 7, 7, 7, 7};
        MergeSort.sort(allEqual, new Metrics());
        assertArrayEquals(new int[]{7, 7, 7, 7, 7}, allEqual);

        int[] alreadySorted = {1, 2, 3, 4, 5, 6, 7, 8};
        MergeSort.sort(alreadySorted, new Metrics());
        assertArrayEquals(new int[]{1, 2, 3, 4, 5, 6, 7, 8}, alreadySorted);

        int[] reversed = {5, 4, 3, 2, 1};
        MergeSort.sort(reversed, new Metrics());
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, reversed);
    }

    @Test
    public void testArrayLargerThanCutoff() {
        int[] arr = generateRandomArray(1000);
        int[] expected = arr.clone();
        Arrays.sort(expected);

        Metrics metrics = new Metrics();
        MergeSort.sort(arr, metrics);

        assertArrayEquals(expected, arr);
        assertTrue(metrics.getMaxDepth() > 1, "массив в 1000 элементов должен пройти через рекурсию, а не только через insertion sort");
        assertTrue(metrics.getComparisons() > 0, "счётчик сравнений должен работать");
    }

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = rand.nextInt();
        return arr;
    }
}