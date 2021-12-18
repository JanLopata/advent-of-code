package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

@Slf4j
public class Day18 extends Day {
    public static void main(String[] args) throws IOException {
        new Day18().printParts();
    }

    private static final Pattern NUMBER_PATTERN = Pattern.compile("\\d+");

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        for (String s : input) {
            Term term = new Term();
            parseTerm(s, term, new AtomicInteger(), true);
            log.info("s: {} term: {}", s, term);
        }

        return 0L;
    }

    private void parseTerm(String data, Term term, AtomicInteger globalIndex, boolean left) {

        var s = data.substring(globalIndex.get());
        log.info("parsing: '{}'", s);
        if (s.startsWith("[")) {
            // start term

            {
                var subterm = new Term();
                subterm.parent = term;
                globalIndex.incrementAndGet();
                parseTerm(data, subterm, globalIndex, true);

                term.left = subterm;
            }
            if (data.charAt(globalIndex.get()) != ',') {
                throw new IllegalArgumentException(", not found");
            }

            {
                var subterm = new Term();
                subterm.parent = term;
                globalIndex.incrementAndGet();
                parseTerm(data, subterm, globalIndex, false);

                term.right = subterm;
            }
        }

        s = data.substring(globalIndex.get());

        if (s.startsWith("]")) {
            globalIndex.incrementAndGet();
            return;
        }

        // must be number
        var matcher = NUMBER_PATTERN.matcher(s);
        if (matcher.find()) {
            var group = matcher.group();
            var value = Integer.parseInt(group);
            term.number = value;
            globalIndex.addAndGet(group.length());
        }


    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }

    @Data
    @ToString(exclude = "parent", includeFieldNames = false)
    public static class Term {

        private Integer number;
        private Term left;
        private Term right;

        private Term parent;


        boolean shouldSplitThis() {
            return number > 9;
        }

        Term findOneToExplode(int depth) {
            if (depth >= 5) {
                return this;
            }

            if (left != null) {
                var result = left.findOneToExplode(depth + 1);
                if (result != null) return result;
            }

            if (right != null) {
                return right.findOneToExplode(depth + 1);
            }

            return null;
        }

        Term findOneToSplit() {

            if (shouldSplitThis())
                return this;

            if (left != null) {
                Term result;
                result = left.findOneToSplit();
                if (result != null)
                    return result;
            }
            if (right != null) {
                return right.findOneToSplit();
            }

            return null;

        }

        private int getDepth() {

            return 1 + Math.max(
                    left != null ? left.getDepth() : 0,
                    right != null ? right.getDepth() : 0
            );
        }


    }


    @Override
    public int getDayNumber() {
        return -1;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
