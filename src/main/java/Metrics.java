public class Metrics {
    private long comparisons = 0;
    private int maxDepth = 0;

    public void addComparison() {
        comparisons++;
    }

    public void updateDepth(int depth) {
        if (depth > maxDepth) {
            maxDepth = depth;
        }
    }

    public long getComparisons() { return comparisons; }
    public int getMaxDepth() { return maxDepth; }
}