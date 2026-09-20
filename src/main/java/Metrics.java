public class Metrics {
    private long comparisons = 0;
    private int maxDepth = 0;
    private long startTime;
    private long endTime;

    public void startTimer() { startTime = System.nanoTime(); }
    public void stopTimer() { endTime = System.nanoTime(); }
    public void addComparison() { comparisons++; }
    public void updateDepth(int depth) {
        if (depth > maxDepth) maxDepth = depth;
    }

    public long getComparisons() { return comparisons; }
    public int getMaxDepth() { return maxDepth; }
    public double getTimeMs() { return (endTime - startTime) / 1_000_000.0; }
}