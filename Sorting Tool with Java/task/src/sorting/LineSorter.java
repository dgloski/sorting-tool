package sorting;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LineSorter extends ObjectSorter {

    public LineSorter(SortingType sortingType) {
        super(sortingType);
    }

    @Override
    public BiConsumer<String, Consumer<String>> getSplitter() {
        return (line, consumer) -> consumer.accept(line);
    }

    @Override
    protected String  printSortedDataByCount(int totalCount, List<String> values, Map<String, AtomicInteger> valuesByCount) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Total numbers: %d.%n", totalCount));
        for (var value : values) {
            int count = valuesByCount.get(value).get();
            double percentage = (((double) count * 100) / (double)totalCount) + 0.5;
            builder.append(String.format("%s: %s time(s), %d%%%n", value, count, (long)percentage));
        }
        return builder.toString();
    }

    @Override
    protected String printNaturalSortedData( List<String> values) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Total lines: %d.%n", values.size()));
        builder.append(String.format("Sorted data:%n"));
        for (var value : values) {
            builder.append(String.format("%s%n", value));
        }
        return builder.toString();
    }

    @Override
    public Comparator<String> getComparator() {
        return new NaturalComparator();
    }

}
