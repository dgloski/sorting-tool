package sorting;


import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(final String[] args) {
        String dataType = "word";
        SortingType sortingType = SortingType.NATURAL;
        String inputFile = null;
        String outputFile = null;
        for (int i = 0; i < args.length; i++) {
            if (args[i].startsWith("-")) {
                if (args[i].equals("-sortingType")) {
                    if (args.length > i + 1) {
                        sortingType = SortingType.valueOf(args[i + 1].toUpperCase().trim());
                    } else {
                        System.out.println("No sorting type defined!");
                    }
                } else if (args[i].equals("-dataType")) {
                    if (args.length > i + 1) {
                        dataType = args[i + 1].trim();
                    } else {
                        System.out.println(" No data type defined!");
                    }
                } else if (args[i].equals("-inputFile")) {
                    if (args.length > i + 1) {
                        inputFile = args[i + 1].trim();
                    } else {
                        System.out.println(" No input file defined!");
                    }
                } else if (args[i].equals("-outputFile")) {
                    if (args.length > i + 1) {
                        outputFile = args[i + 1].trim();
                    } else {
                        System.out.println(" No output file defined!");
                    }
                } else {
                    System.out.printf("\"%s\" is not a valid parameter. It will be skipped.%n", args[i]);
                }
            }
        }
        var objectSorter = getObjectSorter(dataType, sortingType);
        if (inputFile == null) {
            objectSorter.setLines(readLines(System.in));
        } else {
            try(var inputStream = new BufferedInputStream(new FileInputStream(inputFile))) {
                objectSorter.setLines(readLines(inputStream));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (outputFile == null) {
            System.out.print(objectSorter.sortAndPrintResult());
        } else {
            try(var outputStream = new OutputStreamWriter(new BufferedOutputStream(new FileOutputStream(outputFile)))) {
                outputStream.write(objectSorter.sortAndPrintResult());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    private static ObjectSorter getObjectSorter(String dataType, SortingType sortingType) {
        return switch (dataType) {
            case "long" -> new LongSorter(sortingType);
            case "line" -> new LineSorter(sortingType);
            default -> new WordSorter(sortingType);
        };
    }

    private static List<String> readLines(InputStream inputStream) {
        var lines = new ArrayList<String>();
        try (Scanner scanner = new Scanner(inputStream)) {
            while (scanner.hasNextLine()) {
                String next = scanner.nextLine();
                if (next.isEmpty()) {
                    break;
                }
                lines.add(next);
            }
        }
        return lines;
    }
}
