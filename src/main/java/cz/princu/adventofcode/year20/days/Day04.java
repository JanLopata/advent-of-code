package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day04 extends Day {

    public static void main(String[] args) throws IOException {
        new Day04().printParts();
    }

    private static final List<String> REQUIRED_FIELDS_WITHOUT_PID = Stream.of("byr", "iyr", "eyr", "hgt", "hcl", "ecl").collect(Collectors.toList());

    private static final Set<String> ECL_VALID_VALUES = Arrays.stream("amb blu brn gry grn hzl oth".split(" ")).collect(Collectors.toSet());
    private static final Pattern FOUR_DIGITS_REGEX = Pattern.compile("[0-9]{4}");
    private static final Pattern HCL_PATTERN = Pattern.compile("#[0-9a-f]{6}");
    private static final Pattern PID_PATTERN = Pattern.compile("[0-9]{9}");


    private boolean hasValidFields(Map<String, String> fields) {

        return
                validateByr(fields)
                        && validateIyr(fields)
                        && validateEyr(fields)
                        && validateHgt(fields)
                        && validateHcl(fields)
                        && validateEcl(fields)
                        && validatePid(fields);

    }

    private boolean validateByr(Map<String, String> fields) {
        String value = fields.get("byr");
        if (value == null)
            return false;

        if (!FOUR_DIGITS_REGEX.matcher(value).matches())
            return false;

        return validateToRange(value, 1920, 2002);
    }


    private boolean validateIyr(Map<String, String> fields) {
        String value = fields.get("iyr");
        if (value == null)
            return false;

        if (!FOUR_DIGITS_REGEX.matcher(value).matches())
            return false;

        return validateToRange(value, 2010, 2020);
    }

    private boolean validateEyr(Map<String, String> fields) {
        String value = fields.get("eyr");
        if (value == null)
            return false;

        if (!FOUR_DIGITS_REGEX.matcher(value).matches())
            return false;

        return validateToRange(value, 2020, 2030);
    }

    private boolean validateToRange(String value, int i, int i2) {

        try {
            int valueInt = Integer.parseInt(value);
            return valueInt >= i && valueInt <= i2;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean validateHgt(Map<String, String> fields) {
        String value = fields.get("hgt");
        if (value == null)
            return false;

        if (value.endsWith("in"))
            return validateToRange(value.replace("in", ""), 59, 76);

        if (value.endsWith("cm"))
            return validateToRange(value.replace("cm", ""), 150, 193);

        return false;

    }

    private boolean validateHcl(Map<String, String> fields) {
        String value = fields.get("hcl");
        if (value == null)
            return false;

        return HCL_PATTERN.matcher(value).matches();
    }

    private boolean validateEcl(Map<String, String> fields) {
        String value = fields.get("ecl");
        return ECL_VALID_VALUES.contains(value);
    }


    private boolean validatePid(Map<String, String> fields) {
        String value = fields.get("pid");
        if (value == null)
            return false;

        return PID_PATTERN.matcher(value).matches();
    }


    private boolean isValid(Set<String> fieldsPresent) {

        if (fieldsPresent.size() == 8)
            return true;

        for (String neeededpart : REQUIRED_FIELDS_WITHOUT_PID) {
            if (!fieldsPresent.contains(neeededpart)) {
                return false;
            }
        }

        if (fieldsPresent.size() == 7) {

            if (!fieldsPresent.contains("cid"))
                return true;

        }

        return false;
    }


    @Override
    public Object part1(String data) {

        String[] lines = data.split("\n\n");

        long validCount = 0;
        for (String passLine : lines) {

            String[] passParts = passLine.split("[\n ]");
            Set<String> fieldsPresent = Arrays.stream(passParts).map(s -> s.split(":")[0]).collect(Collectors.toSet());
            if (isValid(fieldsPresent))
                validCount++;

        }

        return validCount;

    }

    @Override
    public Object part2(String data) {
        String[] lines = data.split("\n\n");

        long validCount = 0;
        for (String passLine : lines) {

            String[] passParts = passLine.split("[\n ]");

            Map<String, String> fieldValuesMap = Arrays.stream(passParts).map(s -> s.split(":")).collect(Collectors.toMap(arr -> arr[0], arr -> arr[1]));

            if (hasValidFields(fieldValuesMap))
                validCount++;

        }
        return validCount;

    }

    @Override
    public int getDayNumber() {
        return 4;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
