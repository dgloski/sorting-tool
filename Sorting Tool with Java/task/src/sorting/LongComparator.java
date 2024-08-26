package sorting;

import java.util.Comparator;

public class LongComparator implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        return Long.compare(Long.parseLong(o1), Long.parseLong(o2));
    }
}
