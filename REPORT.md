# Report — Assignment 1: Divide and Conquer & Asymptotic Notations

Author: Adilzhan Kuandykov
Repository: https://github.com/adilzhandev/assignment1-DAA

---

## 1. Asymptotic Bounds

| Algorithm | Best | Average | Worst | Reason (which input gives this case) |
|---|---|---|---|---|
| MergeSort | Θ(n log n) | Θ(n log n) | Θ(n log n) | MergeSort always cuts the array exactly in half. The input cannot change the number of levels. Only the merge step reacts to the data. On sorted input the ratio falls to 0.46, but the growth is still the same. |
| QuickSort | Ω(n log n) | Θ(n log n) | O(n²) | Best and average: a random pivot makes two parts of similar size. Worst: every pivot is the smallest or the largest element, so one part is empty. We use a random pivot, so nobody can prepare such an input. This is why we write O and not Θ. |
| QuickSelect | Ω(n) | Θ(n) | O(n²) | Average: every partition removes a large part of the array, so the total work is n + n/2 + n/4 + ... = 2n. Worst: very bad pivots many times in a row. A random pivot makes this very unlikely. |
| Insertion Sort | Θ(n) | Θ(n²) | Θ(n²) | Best: the array is already sorted, so the inner loop stops after one comparison. Worst: the array is in reverse order, so every element moves through the whole prefix. |

---

## 2. Recurrences and Master Theorem

### MergeSort

T(n) = 2·T(n/2) + Θ(n)

MergeSort cuts the array into two halves and merges them in linear time. So a = 2,
b = 2 and f(n) = Θ(n). We compare f(n) with n^(log_b a) = n^(log2 2) = n. Both grow
at the same speed, so this is **case 2** of the Master Theorem. The result is
**Θ(n log n)**.

The measurements show the same thing. The ratio `comparisons / (n·log2 n)` stays
between 0.95 and 1.00 on random input for all four sizes. So the real cost follows
n·log2 n.

### QuickSort (assuming a balanced split)

T(n) = 2·T(n/2) + Θ(n)

If the split is balanced, the recurrence is the same as for MergeSort: a = 2, b = 2
and f(n) = Θ(n), because the partition step reads the whole subarray one time. Again
n^(log2 2) = n is equal to f(n), so this is **case 2** and the result is
**Θ(n log n)**.

A random pivot gives O(n log n) on average. The pivot cuts the array in a random
proportion, and even a bad cut still removes a large part of the elements. For
example, a 1:9 cut gives a depth that is only a constant times bigger than log2 n.
To get the quadratic worst case, bad cuts must happen many times in a row, and this
becomes less and less probable. In theory the average number of comparisons is about
1.39·n·log2 n. Our ratio on random input is 1.70–2.14, a little higher, because the
3-way partition makes up to two comparisons per element instead of one.

### QuickSelect (assuming a balanced split)

T(n) = 1·T(n/2) + Θ(n)

This is a different case of the Master Theorem, because QuickSelect solves only
**one** subproblem and not two. After the partition it goes only into the part that
contains position k, and it throws the other part away. So a = 1, b = 2 and
f(n) = Θ(n). Here n^(log_b a) = n^(log2 1) = n^0 = 1, and f(n) = Θ(n) grows much
faster than 1. This is **case 3**, and the result is **Θ(n)**. The first level does
most of the work.

It is easy to see why: the work is n + n/2 + n/4 + ... = 2n, which is linear. The
measurements agree. The value `comparisons / n` stays between 3.6 and 7.3 and does
not grow, even when n becomes 1000 times bigger.

---

## 3. Measurements

Setup: JDK 17, median of 5 runs for every case.
Full data is in `results.csv`.

### Time, ms

| n | MS random | MS sorted | MS dup | QS random | QS sorted | QS dup | Sel random | Sel sorted | Sel dup |
|---|---|---|---|---|---|---|---|---|---|
| 1 000 | 0.397 | 0.132 | 0.208 | 0.404 | 0.323 | 0.463 | 0.082 | 0.017 | 0.082 |
| 10 000 | 5.072 | 0.320 | 0.666 | 5.409 | 2.439 | 0.200 | 0.762 | 0.103 | 0.156 |
| 100 000 | 11.045 | 3.194 | 6.391 | 12.336 | 8.720 | 1.954 | 1.554 | 0.589 | 1.251 |
| 1 000 000 | 128.661 | 44.354 | 77.186 | 152.365 | 100.945 | 19.657 | 16.478 | 7.385 | 13.333 |

### Maximum recursion depth

| n | MS random | MS sorted | MS dup | QS random | QS sorted | QS dup | Sel random | Sel sorted | Sel dup |
|---|---|---|---|---|---|---|---|---|---|
| 1 000 | 8 | 8 | 8 | 5 | 6 | 2 | 11 | 12 | 4 |
| 10 000 | 11 | 11 | 11 | 8 | 8 | 3 | 19 | 18 | 5 |
| 100 000 | 14 | 14 | 14 | 11 | 11 | 2 | 15 | 25 | 3 |
| 1 000 000 | 18 | 18 | 18 | 13 | 13 | 3 | 24 | 21 | 5 |

The limit from the task, `2·log2(n)`, is 19 / 26 / 33 / 39 for the four sizes.
For QuickSelect this column counts loop iterations, not stack depth: the algorithm
is iterative and always uses a single stack frame.

### Ratio (Θ check)

For the sorts: `comparisons / (n·log2 n)`. For QuickSelect: `comparisons / n`.

| n | MS random | MS sorted | MS dup | QS random | QS sorted | QS dup | Sel random | Sel sorted | Sel dup |
|---|---|---|---|---|---|---|---|---|---|
| 1 000 | 0.95 | 0.43 | 0.91 | 2.14 | 1.57 | 0.51 | 3.92 | 4.07 | 3.61 |
| 10 000 | 0.96 | 0.45 | 0.91 | 1.70 | 1.78 | 0.38 | 6.64 | 7.33 | 4.02 |
| 100 000 | 0.99 | 0.45 | 0.94 | 1.80 | 1.88 | 0.34 | 4.80 | 4.55 | 3.69 |
| 1 000 000 | 1.00 | 0.46 | 0.95 | 1.86 | 1.81 | 0.25 | 4.33 | 5.30 | 4.20 |

---

## 4. Plots

![Time vs n](plots/time_vs_n.png)

![Max recursion depth vs n](plots/depth_vs_n.png)

![Theta check](plots/ratio_vs_n.png)

---

## 5. Θ Check

Bounds of the ratio for every series (over the four sizes):

| series | c1 (min) | c2 (max) |
|---|---|---|
| MergeSort / random | 0.95 | 1.00 |
| MergeSort / sorted | 0.43 | 0.46 |
| MergeSort / duplicates | 0.91 | 0.95 |
| QuickSort / random | 1.70 | 2.14 |
| QuickSort / sorted | 1.57 | 1.88 |
| QuickSort / duplicates | 0.25 | 0.51 |
| QuickSelect / random | 3.92 | 6.64 |
| QuickSelect / sorted | 4.07 | 7.33 |
| QuickSelect / duplicates | 3.61 | 4.20 |

The definition of Θ needs constants c1, c2 and n0, so that
c1·g(n) ≤ f(n) ≤ c2·g(n) for all n ≥ n0. Here f(n) is the number of comparisons we
measured, and g(n) is n·log2 n for the sorts and n for QuickSelect.

For MergeSort the ratio is almost constant from the smallest size. So n0 = 1000,
c1 = 0.95 and c2 = 1.00 on random input. The line is very flat, so the bound is
tight and we can write Θ(n log n) and not only O(n log n). The sorted and the
duplicate series are also flat, but on a different level (about 0.45 and 0.93). This
means that the type of input changes the constant, but not the growth.

For QuickSort the ratio is stable from n = 10 000. So n0 = 10 000, c1 = 1.70 and
c2 = 1.88 on random input. At n = 1000 the value is 2.14, a little outside this
band. This is normal: on a small array one bad pivot changes the average a lot.

For QuickSelect the ratio stays between 3.6 and 7.3 and does not grow, so the linear
bound works with n0 = 1000, c1 = 3.6 and c2 = 7.3. The band is wider than for the
sorts. This is not a mistake in the measurement. QuickSelect makes only about log n
partitions in one run, so one bad pivot changes the result a lot. MergeSort always
does the same work, and QuickSort has many more partitions, so its randomness is
averaged better. More repetitions would make the band narrower. The important thing
is that the band is limited and does not go up.

---

## 6. Discussion (5–10 sentences)

The number of comparisons matches the theory very well. The time does not always
match it, and all the reasons are outside the asymptotic model.

The best example is memory. In an early version of MergeSort we created a new
helper array inside every merge call. At n = 100 000 this version needed 5000.51 ms.
The final version with one buffer needed 12.66 ms. Both versions made exactly the
same 1 536 159 comparisons. The algorithm did not change at all. The difference
comes from memory allocation and the garbage collector, and Θ notation says nothing
about this.

The second reason is JVM warm-up. We ran the same sort ten times. The first run took
46.26 ms, the second 26.05 ms, and from the third run it was about 10.7 ms. The JIT
compiler needs some time to translate the hot methods into native code. The
difference is four times, and this is why the task asks for five runs and the median
instead of one measurement.

The constants also depend on the type of input. MergeSort makes about two times
fewer comparisons on sorted input (ratio 0.46 against 1.00). When every element of
the left half is smaller than every element of the right half, the merge loop takes
all elements from the left half first, and then copies the right half without any
comparison. QuickSort is the fastest of all on duplicates (ratio 0.25 and only
19.66 ms at n = 1 000 000). The 3-way partition puts all elements equal to the pivot
into the middle part, where they are already in their final place, so they never go
into the recursion. On an array of equal values the whole array becomes this middle
part after one pass.

The engineering decisions also work as planned. Recursion into the smaller side
made the depth on sorted input fall from 36 to 10. This is much lower than the
limit 2·log2(n) = 33, and the program works at n = 1 000 000 even with a small stack
of 512 KB. The 3-way partition removed a StackOverflowError that happened at depth
11 590 on an array of equal elements. Now the depth is 1 and the algorithm makes
about 2n comparisons. The insertion sort cutoff made the number of comparisons a
little higher (from 1 536 159 to 1 639 641), but the depth fell from 18 to 14. This
is the expected trade-off: insertion sort is cheaper per element on short subarrays,
but it makes more comparisons than merging.

---

## 7. Conclusion

All three algorithms match the theory on real data. The ratio
`comparisons / (n·log2 n)` is almost constant for MergeSort and QuickSort, and
`comparisons / n` for QuickSelect is limited and does not grow. The safety
requirements are also done: MergeSort allocates memory only one time, QuickSort has
a depth of maximum 13 at n = 1 000 000, and there is no stack overflow on sorted
input or on input with many equal values. The main lesson is simple. Asymptotic
analysis predicts the number of comparisons very well, but it says nothing about the
constants that decide the real time. Memory allocation, the garbage collector and
JIT warm-up changed our measured time up to 400 times, and the number of comparisons
stayed exactly the same.
