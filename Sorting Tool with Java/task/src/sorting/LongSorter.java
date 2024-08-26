package sorting;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class LongSorter extends ObjectSorter {

    public LongSorter(SortingType sortingType) {
        super(sortingType);
    }

    @Override
    public BiConsumer<String, Consumer<String>> getSplitter() {
        return (line, consumer) -> {
            String[] vals = line.split("\\s+");
            for (var val : vals) {
                consumer.accept(val.trim());
            }
        };
    }

    @Override
    protected boolean validValue(String value) {
        try {
            Long.parseLong(value);
            return true;
        } catch (NumberFormatException e) {
            System.out.printf("\"%s\" is not a long. It will be skipped.", value);
            return false;
        }
    }

    @Override
    protected String printSortedDataByCount(int totalCount, List<String> values, Map<String, AtomicInteger> valuesByCount) {
        StringBuilder builder = new StringBuilder();
        builder.append(String.format("Total numbers: %d.%n", totalCount));
        for (var value : values) {
            int count = valuesByCount.get(value).get();
            double percentage = (((double) count * 100) / (double) totalCount) + 0.5;
            builder.append(String.format("%s: %s time(s), %d%%%n", value, count, (long) percentage));
        }
        return builder.toString();
    }

    @Override
    protected String printNaturalSortedData( List<String> values) {
        return String.format("Total numbers: %d.%n", values.size())
            + String.format("Sorted data: %s%n", String.join(" ", values));
    }

    @Override
    public Comparator<String> getComparator() {
        return new LongComparator();
    }
}
