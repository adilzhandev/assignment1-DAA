import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class QuickSelectTest {
    private final Random rand = new Random();

    @Test
    public void testQuickSelectCorrectness() {
        for (int i = 0; i < 100; i++) {
            int[] arr = generateRandomArray(1000);
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            int k = rand.nextInt(arr.length);
            int result = QuickSelect.select(arr.clone(), k, new Metrics());
            assertEquals(sorted[k], result);
        }
    }

    @Test
    public void testDuplicateHeavyArrays() {
        for (int i = 0; i < 100; i++) {
            int[] arr = new int[1000];
            for (int j = 0; j < arr.length; j++) arr[j] = rand.nextInt(10);
            int[] sorted = arr.clone();
            Arrays.sort(sorted);

            int k = rand.nextInt(arr.length);
            assertEquals(sorted[k], QuickSelect.select(arr.clone(), k, new Metrics()));
        }
    }

    @Test
    public void testBoundaryK() {
        int[] arr = generateRandomArray(1000);
        int[] sorted = arr.clone();
        Arrays.sort(sorted);

        assertEquals(sorted[0], QuickSelect.select(arr.clone(), 0, new Metrics()),
                "k = 0 должен давать минимум");
        assertEquals(sorted[arr.length - 1], QuickSelect.select(arr.clone(), arr.length - 1, new Metrics()),
                "k = n-1 должен давать максимум");
    }

    @Test
    public void testAllSameValue() {
        int[] arr = new int[1000];
        Arrays.fill(arr, 7);

        assertEquals(7, QuickSelect.select(arr.clone(), 0, new Metrics()));
        assertEquals(7, QuickSelect.select(arr.clone(), 500, new Metrics()));
        assertEquals(7, QuickSelect.select(arr.clone(), 999, new Metrics()));
    }

    @Test
    public void testSingleElement() {
        assertEquals(42, QuickSelect.select(new int[]{42}, 0, new Metrics()));
    }

    @Test
    public void testQuickSelectInvalidInput() {
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{1, 2, 3}, 5, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{1, 2, 3}, -1, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{1, 2, 3}, 3, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(new int[]{}, 0, new Metrics()));
        assertThrows(IllegalArgumentException.class,
                () -> QuickSelect.select(null, 0, new Metrics()));
    }

    private int[] generateRandomArray(int size) {
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) arr[i] = rand.nextInt();
        return arr;
    }
}
