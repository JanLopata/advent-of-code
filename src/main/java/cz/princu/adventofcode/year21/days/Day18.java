package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.UUID;
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

        Term term = new Term();
        parseTerm(input[0], term, new AtomicInteger());

        for (int i = 1; i < input.length; i++) {

            Term nextTerm = new Term();
            parseTerm(input[i], nextTerm, new AtomicInteger());

            term = term.add(nextTerm);
        }

        return term.magnitude();
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        long maxMag = -1;

        for (int i = 0; i < input.length; i++) {

            for (int j = 0; j < input.length; j++) {

                if (i == j)
                    continue;

                Term term = new Term();
                parseTerm(input[i], term, new AtomicInteger());

                Term nextTerm = new Term();
                parseTerm(input[j], nextTerm, new AtomicInteger());

                term = term.add(nextTerm);
                long mag = term.magnitude();

                if (mag > maxMag)
                    maxMag = mag;

            }

        }


        return maxMag;
    }

    public void parseTerm(String data, Term term, AtomicInteger globalIndex) {

        var s = data.substring(globalIndex.get());
        log.debug("parsing: '{}'", s);
        if (s.startsWith("[")) {
            // start term

            {
                var subterm = new Term();
                subterm.parent = term;
                globalIndex.incrementAndGet();
                parseTerm(data, subterm, globalIndex);

                term.left = subterm;
            }
            if (data.charAt(globalIndex.get()) != ',') {
                throw new IllegalArgumentException(", not found");
            }

            {
                var subterm = new Term();
                subterm.parent = term;
                globalIndex.incrementAndGet();
                parseTerm(data, subterm, globalIndex);

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
            term.number = Integer.parseInt(group);
            globalIndex.addAndGet(group.length());
        }
    }

    public static String representTerm(Term term) {

        StringBuilder sb = new StringBuilder();
        representTerm(term, sb);
        log.info("{}", sb);
        return sb.toString();

    }

    private static void representTerm(Term term, StringBuilder sb) {

        if (term == null) {
            sb.append("null");
            return;
        }

        if (term.number != null) {
            sb.append(term.number);
            return;
        }

        sb.append("[");
        representTerm(term.left, sb);
        sb.append(",");
        representTerm(term.right, sb);
        sb.append("]");
    }

    @ToString(exclude = {"parent", "uuid"}, includeFieldNames = false)
    @EqualsAndHashCode(of = "uuid")
    @Getter
    @Setter
    public static class Term {

        String uuid = UUID.randomUUID().toString();

        private Integer number;
        private Term left;
        private Term right;

        private Term parent;


        boolean shouldSplitThis() {
            return number != null && number > 9;
        }

        long magnitude() {

            if (left == null)
                return number;

            return 3 * left.magnitude() + 2 * right.magnitude();


        }

        Term findOneToExplode(int depth) {
            if (depth >= 5) {
                return this;
            }

            if (left != null) {
                var result = left.findOneToExplode(depth + 1);
                if (result != null) {
                    if (result.number != null) {
                        return result.parent;
                    } else {
                        return result;
                    }

                }
            }

            if (right != null) {
                var result = right.findOneToExplode(depth + 1);
                if (result != null) {
                    if (result.number != null) {
                        return result.parent;
                    } else {
                        return result;
                    }
                }
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

        public void explodeHere() {

            var regularLeft = findRegularNumberLeft();
            if (regularLeft != null) {
                regularLeft.number += this.left.number;
            }

            var regularRight = findRegularNumberRight();
            if (regularRight != null) {
                regularRight.number += this.right.number;
            }

            left = null;
            right = null;
            number = 0;

        }

        public Term findRegularNumberLeft() {

            if (this.parent == null)
                return null;

            if (this.parent.left.equals(this)) {
                return this.parent.findRegularNumberLeft();
            }

            if (this.parent.right.equals(this)) {
                return this.parent.left.findRegularNumberLeftDown();
            }

            return null;
        }

        public Term findRegularNumberRight() {

            if (this.parent == null)
                return null;

            if (this.parent.right.equals(this)) {
                return this.parent.findRegularNumberRight();
            }

            if (this.parent.left.equals(this)) {
                return this.parent.right.findRegularNumberRightDown();
            }

            return null;
        }

        private Term findRegularNumberRightDown() {

            if (left != null) {
                return left.findRegularNumberRightDown();
            }

            return this;
        }

        private Term findRegularNumberLeftDown() {

            if (right != null) {
                return right.findRegularNumberLeftDown();
            }

            return this;
        }

        public void split() {

            int x = number / 2, y = (number + 1) / 2;

            number = null;
            left = new Term();
            left.parent = this;
            left.number = x;
            right = new Term();
            right.parent = this;
            right.number = y;

        }

        public void reduce() {

            if (parent != null)
                throw new IllegalStateException("must reduce a root only");

            boolean reductionHappen = true;
            while (reductionHappen) {

                representTerm(this);

                var toExplode = findOneToExplode(0);
                if (toExplode != null) {
                    toExplode.explodeHere();
                    continue;
                }

                var toSplit = findOneToSplit();
                if (toSplit != null) {
                    toSplit.split();
                    continue;
                }

                reductionHappen = false;
            }

        }

        Term add(Term another) {

            if (parent != null) {
                throw new IllegalStateException("must sum a root only");
            }

            Term result = new Term();
            result.left = this;
            result.right = another;
            this.parent = result;
            another.parent = result;

            result.reduce();

            return result;
        }


    }


    @Override
    public int getDayNumber() {
        return 18;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
