package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class Day14 extends Day {
    public static void main(String[] args) throws IOException {
        new Day14().printParts();
    }

    private static final Pattern MASK_PATTERN = Pattern.compile("mask = [^01X]*([01X]+)$");
    private static final Pattern MEM_OP_PATTERN = Pattern.compile("mem\\[([0-9]+)] = ([0-9]+)");

    @Override
    public Object part1(String data) {

        String[] lines = data.split("\n");


        Mask mask = new Mask("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX");

        Map<Long, Long> memory = new HashMap<>();

        for (String line : lines) {

            final Matcher maskMatcher = MASK_PATTERN.matcher(line);
            if (maskMatcher.matches()) {
                mask = parseMask(maskMatcher);
                continue;
            }

            final Matcher memOpMatcher = MEM_OP_PATTERN.matcher(line);
            if (memOpMatcher.matches()) {

                MemoryOperation memoryOperation = parseMemoryOperation(memOpMatcher);

                performMemoryOperation(memory, mask, memoryOperation);

            }

        }

        return memory.values().stream().mapToLong(it -> it).sum();
    }

    private Long performMemoryOperation(Map<Long, Long> memory, Mask mask, MemoryOperation memoryOperation) {
        return memory.put(memoryOperation.getTarget(), mask.apply(memoryOperation.getValue()));
    }

    @Override
    public Object part2(String data) {

        String[] lines = data.split("\n");

        MemoryMask mask = new MemoryMask("000000000000000000000000000000000000");

        Map<BitSet, Long> memory = new HashMap<>();

        for (String line : lines) {

            final Matcher maskMatcher = MASK_PATTERN.matcher(line);
            if (maskMatcher.matches()) {
                mask = parseMemoryMask(maskMatcher);
                continue;
            }

            final Matcher memOpMatcher = MEM_OP_PATTERN.matcher(line);
            if (memOpMatcher.matches()) {

                MemoryOperation memoryOperation = parseMemoryOperation(memOpMatcher);

                MemoryMask resultMask = mask.apply(memoryOperation.getTarget());

                addValues(memoryOperation.getValue(), memory, resultMask.getMaskString());
            }

        }

        return memory.values().stream().mapToLong(it -> it).sum();

    }

    MemoryOperation parseMemoryOperation(Matcher matcher) {

        if (!matcher.matches())
            return null;

        return new MemoryOperation(Long.parseLong(matcher.group(1)), Long.parseLong(matcher.group(2)));
    }

    Mask parseMask(Matcher matcher) {
        if (!matcher.matches())
            return null;

        return new Mask(matcher.group(1));
    }

    MemoryMask parseMemoryMask(Matcher matcher) {

        return new MemoryMask(matcher.group(1));
    }


    @ToString(of = "maskString")
    private static class MemoryMask {

        @Getter
        private final String maskString;

        private final int[] bits = new int[36];

        public MemoryMask(String maskString) {
            this.maskString = maskString;

            if (maskString.length() != 36)
                throw new IllegalArgumentException("wrong length");

            for (int i = 0; i < maskString.length(); i++) {
                final char c = maskString.charAt(i);

                if (c == '0')
                    bits[i] = 0;
                else if (c == '1')
                    bits[i] = 1;
                else
                    bits[i] = -1;
            }

        }

        private MemoryMask(int[] bitArray) {

            maskString = convertToMaskString(bitArray);
            System.arraycopy(bitArray, 0, bits, 0, bitArray.length);

        }

        private static String convertToMaskString(int[] bitArray) {

            StringBuilder stringBuilder = new StringBuilder();
            for (int i : bitArray) {
                if (i == -1)
                    stringBuilder.append('X');
                else
                    stringBuilder.append(i);
            }

            return stringBuilder.toString();

        }

        public MemoryMask apply(long number) {

            long workNumber = number;

            int[] appliedMaskBits = new int[36];

            for (int i = 0; i < maskString.length(); i++) {
                final long base = 1L << (35 - i);
                long bitValue = workNumber / base;
                workNumber = workNumber % base;

                int appliedValue = -1;
                if (bits[i] == 1)
                    appliedValue = 1;

                if (bits[i] == 0)
                    appliedValue = bitValue > 0 ? 1 : 0;

                appliedMaskBits[i] = appliedValue;
            }

            return new MemoryMask(appliedMaskBits);
        }

    }


    static void addValues(long value, Map<BitSet, Long> memory, String address) {

        final char[] chars = address.toCharArray();
        BitSet bitSet = new BitSet(36);

        List<Integer> flippableIndices = new ArrayList<>();

        for (int i = 0; i < chars.length; i++) {
            if (chars[i] == '1')
                bitSet.set(i);

            if (chars[i] == 'X')
                flippableIndices.add(i);
        }

        addValues(value, memory, bitSet, flippableIndices.stream().mapToInt(it -> it).toArray());
    }

    static void addValues(long value, Map<BitSet, Long> memory, BitSet bitSet, int[] flippableIndices) {

        if (flippableIndices.length == 0) {
            memory.put(bitSet, value);
            return;
        }

        // recursion
        int flipIdx = flippableIndices[flippableIndices.length - 1];
        int[] flippableSub = Arrays.copyOf(flippableIndices, flippableIndices.length - 1);

        BitSet workBitSet = (BitSet) bitSet.clone();
        addValues(value, memory, workBitSet, flippableSub);

        workBitSet = (BitSet) bitSet.clone();
        workBitSet.flip(flipIdx);
        addValues(value, memory, workBitSet, flippableSub);

    }


    @RequiredArgsConstructor
    @ToString
    @Getter
    private static class MemoryOperation {
        private final long target;
        private final long value;
    }

    @ToString(of = "maskString")
    private static class Mask {
        private final String maskString;

        private final int[] bits = new int[36];


        public Mask(String maskString) {
            this.maskString = maskString;

            if (maskString.length() != 36)
                throw new IllegalArgumentException("wrong length");

            for (int i = 0; i < maskString.length(); i++) {
                final char c = maskString.charAt(i);

                if (c == '0')
                    bits[i] = 0;
                else if (c == '1')
                    bits[i] = 1;
                else
                    bits[i] = -1;
            }

        }

        public long apply(long value) {

            long result = 0;
            for (int i = 0; i < 36; i++) {

                if (bits[i] == 0)
                    continue;

                final int bitShift = 35 - i;

                final long bitValue = 1L << bitShift;

                if (bits[i] == 1) {
                    result += bitValue;
                } else {
                    result += bitValue * (value >> bitShift & 1);
                }

            }
            return result;

        }


    }

    @Override
    public int getDayNumber() {
        return 14;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
