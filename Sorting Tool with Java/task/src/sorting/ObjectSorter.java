package sorting;

import java.io.OutputStream;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public abstract class ObjectSorter {

    private List<String> values;
    private SortingType sortingType;
    private Map<String, AtomicInteger> valuesByCount = new HashMap<>();

    protected ObjectSorter(SortingType sortingType) {
        this.sortingType = sortingType;
    }

    protected abstract BiConsumer<String, Consumer<String>> getSplitter();

    protected abstract String printSortedDataByCount(int totalCount, List<String> values, Map<String, AtomicInteger> valuesByCount);

    protected abstract String printNaturalSortedData(List<String> values);

    abstract Comparator<String> getComparator();

    public void setLines(List<String> lines) {
        this.values = new ArrayList<>(lines.stream().mapMulti(getSplitter()).filter(this::validValue).toList());
        for (String value : values) {
            valuesByCount.putIfAbsent(value, new AtomicInteger());
            valuesByCount.get(value).incrementAndGet();
        }
    }

    protected boolean validValue(String value) {
        return true;
    }

    public String sortAndPrintResult() {
        if (sortingType == SortingType.NATURAL) {
            values.sort(getComparator());
            return printNaturalSortedData(values);
        } else {
            var uniqueValues = new ArrayList<>(valuesByCount.keySet());
            uniqueValues.sort((o1, o2) -> {
                var c1 = valuesByCount.get(o1).get();
                var c2 = valuesByCount.get(o2).get();
                var res = Integer.compare(c1, c2);
                if (res == 0) {
                    return getComparator().compare(o1, o2);
                } else {
                    return res;
                }
            });
            return printSortedDataByCount(values.size(), uniqueValues, valuesByCount);
        }
    }

}
