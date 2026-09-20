# Assignment 1 — Divide and Conquer & Asymptotic Notations

This project has three algorithms: MergeSort, QuickSort and QuickSelect.
For every run it measures the time, the number of comparisons and the maximum
recursion depth.

## What you need

- JDK 17 or newer
- Maven 3.6 or newer

## Build and run the tests

```bash
mvn test
```

This runs all 14 JUnit 5 tests. The tests compare the results with `Arrays.sort`
on more than 100 random arrays, check edge cases, and check that QuickSort keeps
`maxDepth <= 2*log2(n)`.

To build without running the tests:

```bash
mvn -q package
```

## Run the benchmark

```bash
mvn -q compile exec:java -Dexec.mainClass=Benchmark
```

If the project is already built, you can also run it directly:

```bash
java -Xmx3g -cp target/classes Benchmark
```

The benchmark runs all three algorithms on four sizes (1 000, 10 000, 100 000 and
1 000 000) and on three kinds of input (`random`, `sorted` and `duplicates`).
Each case runs 5 times, and the median time goes into `results.csv`.
This takes a few minutes.

## Make the plots

```bash
python3 scripts/plot_results.py
```

You need `matplotlib` for this. The script reads `results.csv` and writes three
PNG files into `plots/`:

| file | what it shows |
|---|---|
| `plots/time_vs_n.png` | time against the size of the input |
| `plots/depth_vs_n.png` | maximum recursion depth against the size of the input |
| `plots/ratio_vs_n.png` | `comparisons / (n·log2 n)` for the sorts, and `comparisons / n` for QuickSelect |

If `matplotlib` is not installed and your system does not allow `pip install`
(this happens on newer Linux systems, see PEP 668), use a virtual environment:

```bash
python3 -m venv .venv && .venv/bin/pip install matplotlib
.venv/bin/python scripts/plot_results.py
```

## Project structure

```
src/main/java/
  Metrics.java       counters for comparisons and depth, timer on System.nanoTime()
  MergeSort.java     one reusable buffer, cutoff 15 to Insertion Sort
  QuickSort.java     random pivot, 3-way partition, recursion into the smaller side
  QuickSelect.java   iterative, reuses QuickSort.partition
  Benchmark.java     writes the results to results.csv
src/test/java/       JUnit 5 tests
scripts/             script that builds the plots
plots/               the PNG plots
results.csv          benchmark results
REPORT.md            asymptotic analysis, recurrences and discussion
```

## How the requirements are covered

**MergeSort.** The helper array is created only once, in the public method, and
then passed down through the recursion. Subarrays of 15 elements or fewer are
sorted with Insertion Sort. The merge step is linear.

**QuickSort.** The pivot is chosen at random, so sorted input does not become the
worst case. The partition is 3-way (`< pivot`, `== pivot`, `> pivot`), so arrays
with many equal values stay fast. The algorithm calls itself only on the smaller
part and handles the larger part in a `while` loop, so the stack depth stays
close to log n and the program never crashes with a `StackOverflowError`.

**QuickSelect.** It returns the k-th smallest element, where k starts from 0.
It uses the same partition method as QuickSort. After each partition it continues
only in the part that contains position k, so it does not sort the whole array.
It is iterative, so it uses O(1) extra memory. If the array is empty or k is out
of range, it throws an `IllegalArgumentException` with a clear message.
