"""Строит три графика из results.csv.

Запуск:  python3 scripts/plot_results.py
Требует: matplotlib
"""
import csv
import math
import os
from collections import defaultdict

import matplotlib
matplotlib.use("Agg")
import matplotlib.pyplot as plt

ROOT = os.path.dirname(os.path.dirname(os.path.abspath(__file__)))
CSV = os.path.join(ROOT, "results.csv")
OUT = os.path.join(ROOT, "plots")

COLORS = {"MergeSort": "#1f77b4", "QuickSort": "#d62728", "QuickSelect": "#2ca02c"}
MARKERS = {"random": "o", "sorted": "s", "duplicates": "^"}
STYLES = {"random": "-", "sorted": "--", "duplicates": ":"}


def load():
    rows = defaultdict(list)
    with open(CSV, newline="") as f:
        for r in csv.DictReader(f):
            rows[(r["algorithm"], r["input"])].append(
                (int(r["n"]), float(r["time_ms"]), int(r["comparisons"]), int(r["max_depth"]))
            )
    for key in rows:
        rows[key].sort()
    return rows


def plot(rows, value_fn, title, ylabel, filename, logy=True):
    fig, ax = plt.subplots(figsize=(9, 5.5))
    for (algo, inp), data in sorted(rows.items()):
        ns = [d[0] for d in data]
        ys = [value_fn(d) for d in data]
        ax.plot(ns, ys,
                color=COLORS[algo], marker=MARKERS[inp], linestyle=STYLES[inp],
                markersize=5, linewidth=1.6, label=f"{algo} / {inp}")

    ax.set_xscale("log")
    if logy:
        ax.set_yscale("log")
    ax.set_xlabel("n (логарифмическая шкала)")
    ax.set_ylabel(ylabel)
    ax.set_title(title)
    ax.grid(True, which="both", alpha=0.3)
    ax.legend(fontsize=8, ncol=3, loc="best")
    fig.tight_layout()
    path = os.path.join(OUT, filename)
    fig.savefig(path, dpi=150)
    plt.close(fig)
    print("сохранён", os.path.relpath(path, ROOT))


def ratio(d, algo):
    n, _, comparisons, _ = d
    if algo == "QuickSelect":
        return comparisons / n
    return comparisons / (n * math.log2(n))


def main():
    os.makedirs(OUT, exist_ok=True)
    rows = load()

    plot(rows, lambda d: d[1],
         "Время выполнения от размера входа", "время, мс (медиана из 5 прогонов)",
         "time_vs_n.png")

    plot(rows, lambda d: d[3],
         "Максимальная глубина рекурсии от размера входа", "max_depth",
         "depth_vs_n.png", logy=False)

    fig, ax = plt.subplots(figsize=(9, 5.5))
    for (algo, inp), data in sorted(rows.items()):
        ns = [d[0] for d in data]
        ys = [ratio(d, algo) for d in data]
        ax.plot(ns, ys, color=COLORS[algo], marker=MARKERS[inp], linestyle=STYLES[inp],
                markersize=5, linewidth=1.6, label=f"{algo} / {inp}")
    ax.set_xscale("log")
    ax.set_xlabel("n (логарифмическая шкала)")
    ax.set_ylabel("comparisons / (n·log₂n)  —  для QuickSelect: comparisons / n")
    ax.set_title("Проверка Θ: отношение измеренной стоимости к ожидаемому росту")
    ax.grid(True, which="both", alpha=0.3)
    ax.legend(fontsize=8, ncol=3, loc="best")
    fig.tight_layout()
    path = os.path.join(OUT, "ratio_vs_n.png")
    fig.savefig(path, dpi=150)
    plt.close(fig)
    print("сохранён", os.path.relpath(path, ROOT))


if __name__ == "__main__":
    main()
