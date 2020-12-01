package cz.princu.adventofcode.common;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import static org.apache.commons.io.FileUtils.readFileToString;

public interface DoesFileOperations {

    default String getResourceAsString(String resource) throws IOException {
        return readFileToString(new File(DoesFileOperations.class.getClassLoader().getResource(resource).getFile()));
    }

    default String getDayData(int day, int year) throws IOException {
        return getResourceAsString(year + "/day" + day + ".txt");
    }

    default String[] dayStrings(int day) throws IOException {
        return Arrays.stream(getDayData(day, 2020).split(System.lineSeparator())).toArray(String[]::new);
    }

    default long[] dayNumbers(int day, String split) throws IOException {
        return Arrays.stream(getDayData(day, 2020).split(split)).mapToLong(Long::parseLong).toArray();
    }

}