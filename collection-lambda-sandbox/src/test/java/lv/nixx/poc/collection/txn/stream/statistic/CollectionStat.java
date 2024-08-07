package lv.nixx.poc.collection.txn.stream.statistic;

import java.util.Comparator;
import java.util.stream.Collector;

public class CollectionStat<T> {
    int count;

    final Comparator<? super T> comparator;
    T min;
    T max;

    public CollectionStat(Comparator<? super T> comparator) {
        this.comparator = comparator;
    }

    public int count() {
        return count;
    }

    public T min() {
        return min;
    }

    public T max() {
        return max;
    }

    public void accept(T val) {
        if (count == 0)
            min = max = val;
        else if (comparator.compare(val, min) < 0)
            min = val;
        else if (comparator.compare(val, max) > 0)
            max = val;

        count++;
    }

    public CollectionStat<T> combine(CollectionStat<T> that) {
        if (this.count == 0) return that;
        if (that.count == 0) return this;

        this.count += that.count;
        if (comparator.compare(that.min, this.min) < 0)
            this.min = that.min;
        if (comparator.compare(that.max, this.max) > 0)
            this.max = that.max;

        return this;
    }

    public static <T> Collector<T, CollectionStat<T>, CollectionStat<T>> collector(Comparator<? super T> comparator) {
        return Collector.of(
                () -> new CollectionStat<>(comparator),
                CollectionStat::accept,
                CollectionStat::combine,
                Collector.Characteristics.UNORDERED, Collector.Characteristics.IDENTITY_FINISH
        );
    }

    public static <T extends Comparable<? super T>> Collector<T, CollectionStat<T>, CollectionStat<T>> collector() {
        return collector(Comparator.naturalOrder());
    }

    @Override
    public String toString() {
        return "count:" + count + " min:" + min + " max:" + max;
    }

}