package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
public class Day18 extends Day {
    public static void main(String[] args) throws IOException {
        new Day18().printParts();
    }


    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        long result = 0;

        for (String expression : input) {

            result += evaluateExpression(expression);

        }

        return result;
    }

    private long evaluateExpression(String expression) {


        ParsingAutomaton parser = new ParsingAutomaton();

        for (char c : expression.toCharArray()) {
            parser.parseNextChar(c);
        }

        return parser.evaluate();

    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


//    private static class FiniteAutoma

    private static class ParsingAutomaton {

        private String state = "init";
        private int currentDepth = 0;
        private Term currentTerm = new UnaryTerm(null);

        private long currentParsedValue = 0;

        public void parseNextChar(char c) {

            if ("init".equals(state)) {

                if (c == '(') {
                    currentDepth += 1;
                    currentTerm = new UnaryTerm(currentTerm);
                }

                if (Character.isDigit(c)) {
                    currentParsedValue = currentParsedValue * 10 + (c - '0');
                    state = "number";
                }

                return;
            }

            if ("number".equals(state)) {

                if (c == '(')
                    throw new IllegalArgumentException("cannot parse ( when parsing number");

                if (Character.isDigit(c)) {
                    currentParsedValue = currentParsedValue * 10 + (c - '0');
                }

                if (c == '+' || c == '*') {

                    currentTerm = BinaryTerm.extendFromTerm(currentTerm);
                    currentTerm = new UnaryTerm()

                }

            }


        }

        public long evaluate() {
            currentTerm.evaluate();
            return currentTerm.getValue();
        }
    }

    @Getter
    @Setter
    @EqualsAndHashCode(of = {"left", "right", "operator"}, callSuper = false)
    private static class BinaryTerm extends Term {
        private Term left;
        private Term right;
        private char operator;

        @Override
        public void evaluate() {

            if (this.computed)
                return;

            if (!left.computed) {
                left.evaluate();
            }

            if (!right.computed) {
                right.evaluate();
            }

            if (operator == '+')
                this.value = left.value + right.value;

            if (operator == '*')
                this.value = left.value * right.value;

            computed = true;
        }

        public static BinaryTerm extendFromTerm(Term term) {

            final BinaryTerm result = new BinaryTerm();
            result.computed = false;
            result.left = term;

            return result;
        }

    }


    @Override
    public int getDayNumber() {
        return 18;
    }

    @Override
    public int getYear() {
        return 2020;
    }

    @Getter
    private static class SimpleTerm extends Term {

        public SimpleTerm(long value, Term parent) {
            this.value = value;
            this.computed = true;
            this.parent = parent;
        }

        public void evaluate() {
            // nothing
        }

    }


    @Setter
    @Getter
    @EqualsAndHashCode(of = {"term"}, callSuper = false)
    private static class UnaryTerm extends Term {

        public UnaryTerm(Term parentTerm) {
            this.parent = parentTerm;
        }

        private Term term;

        @Override
        public void evaluate() {

            if (computed)
                return;

            if (!term.isComputed()) {
                term.evaluate();
            }

            this.value = term.value;
            this.computed = true;
        }
    }

    @Getter
    private abstract static class Term {

        protected Long value;
        protected boolean computed = false;
        protected Term parent;

        public abstract void evaluate();

    }
}
