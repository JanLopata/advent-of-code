package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

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

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        long result = 0;

        for (String expression : input) {

            String treatedExpression = addPlusParenthesis(expression.replace(" ", ""));

            result += evaluateExpression(treatedExpression);

        }

        return result;
    }

    private long evaluateExpression(String expression) {

        LinkedList<Long> valuesStack = new LinkedList<>();
        LinkedList<Character> operatorsStack = new LinkedList<>();

        int i = 0;

        valuesStack.add(null);
        operatorsStack.add(null);

        while (i < expression.length()) {

            final char c = expression.charAt(i);
            if (Character.isDigit(c)) {
                String numberString = parseNumberAsString(expression, i);
                final long parsedNumber = Long.parseLong(numberString);

                if (valuesStack.getLast() == null) {
                    valuesStack.removeLast();
                    valuesStack.addLast(parsedNumber);
                } else {
                    // perform computation

                    performOperation(valuesStack, operatorsStack, parsedNumber);
                }

                i += numberString.length();
                continue;
            }

            if (c == '*' || c == '+') {

                registerOperator(operatorsStack, c);

            }

            if (c == '(') {

                valuesStack.add(null);
                operatorsStack.add(null);

            }

            if (c == ')') {

                operatorsStack.removeLast();
                long currentNumber = valuesStack.pollLast();

                performOperation(valuesStack, operatorsStack, currentNumber);
            }

            i++;
        }

        return valuesStack.isEmpty() ? 0L : valuesStack.getLast();
    }

    private void registerOperator(LinkedList<Character> operatorsStack, char c) {
        if (operatorsStack.getLast() != null)
            throw new IllegalStateException("already defined operator");

        operatorsStack.removeLast();
        operatorsStack.add(c);
    }

    private void performOperation(LinkedList<Long> valuesStack, LinkedList<Character> operatorsStack, long currentNumber) {
        Character operator = operatorsStack.pollLast();
        operatorsStack.add(null);

        if (operator == null) {
            valuesStack.removeLast();
            valuesStack.add(currentNumber);
            return;
        }

        if (operator == '+') {
            long opResult = valuesStack.pollLast() + currentNumber;
            valuesStack.add(opResult);
        }

        if (operator == '*') {
            long opResult = valuesStack.pollLast() * currentNumber;
            valuesStack.add(opResult);
        }
    }

    private String parseNumberAsString(String expression, int fromPositiong) {

        StringBuilder sb = new StringBuilder();
        for (int i = fromPositiong; i < expression.length(); i++) {
            final char c = expression.charAt(i);
            if (Character.isDigit(c))
                sb.append(c);
            else
                break;
        }

        return sb.toString();
    }

    private int getMatchingParenthesisIndex(String expr, String specialReversed, int startIdx, boolean reversed) {

        String expression;
        if (reversed) {
            expression = specialReversed;
        } else {
            expression = expr;
        }

        int startIndex;
        if (reversed) {
            startIndex = expression.length() - startIdx - 1;
        } else {
            startIndex = startIdx;
        }

        int parenthesisStack = 0;
        for (int i = startIndex; i < expression.length(); i++) {

            final char c = expression.charAt(i);
            if (c == '(') {
                parenthesisStack++;
            }

            if (c == ')')
                parenthesisStack--;

            if (parenthesisStack <= 0)

                if (reversed) {
                    return expression.length() - i;
                } else {
                    return i;
                }
        }

        if (reversed) {
            return 0;
        } else {
            return expression.length() - 1;
        }

    }

    private int getFirstNonDigit(String expr, String specialReversed, int startIdx, boolean reversed) {

        String expression;
        if (reversed) {
            expression = specialReversed;
        } else {
            expression = expr;
        }

        int startIndex;
        if (reversed) {
            startIndex = expression.length() - startIdx;
        } else {
            startIndex = startIdx;
        }

        for (int i = startIndex; i < expression.length(); i++) {

            if (!Character.isDigit(expression.charAt(i))) {

                if (reversed)
                    return expression.length() - i;
                else
                    return i;
            }
        }

        if (reversed) {
            return 0;
        } else {
            return expression.length();
        }

    }


    @Override
    public int getDayNumber() {
        return 18;
    }

    private String addPlusParenthesis(String expression) {

        String result = expression;

        int priorityOperatorCount = countPriorityOperators(expression);

        for (int i = 0; i < priorityOperatorCount; i++) {
            result = addParenthesis(result, i);
        }

        return result;

    }

    private String addParenthesis(String expression, int skip) {
        String specialReversedExp = new StringBuilder(expression).reverse().toString()
                .replace(")", "$").replace("(", ")").replace("$", "(");

        List<CharWithIndex> parenthesisToAdd = new ArrayList<>();

        int currentSkip = 0;

        for (int i = 0; i < expression.length(); i++) {

            final char c = expression.charAt(i);
            if (c == '+') {

                if (currentSkip < skip) {
                    currentSkip++;
                    continue;
                }

                // left
                addLeftParenthesisCandidate(expression, specialReversedExp, parenthesisToAdd, i);

                // right
                addRightParenthesisCandidate(expression, specialReversedExp, parenthesisToAdd, i);

                break;
            }

        }

        parenthesisToAdd.sort(Comparator.comparing(CharWithIndex::getIndex));

        StringBuilder sb = new StringBuilder(expression);

        for (int i = 0; i < parenthesisToAdd.size(); i++) {
            final CharWithIndex charWithIndex = parenthesisToAdd.get(i);
            sb.insert(i + charWithIndex.getIndex(), charWithIndex.getCharacter());
        }


        return sb.toString();
    }

    private int countPriorityOperators(String expression) {
        int priorityOperatorCount = 0;
        for (char c : expression.toCharArray()) {
            if (c == '+') {
                priorityOperatorCount++;
            }
        }
        return priorityOperatorCount;
    }

    private void addRightParenthesisCandidate(String expression, String specialReversedExp, List<CharWithIndex> parenthesisToAdd, int operatorPosition) {
        int rightIndex;
        final char rightChar = expression.charAt(operatorPosition + 1);
        if (rightChar == '(') {
            rightIndex = getMatchingParenthesisIndex(expression, specialReversedExp, operatorPosition + 1, false);
        } else {
            rightIndex = getFirstNonDigit(expression, specialReversedExp, operatorPosition + 1, false);
        }
        parenthesisToAdd.add(new CharWithIndex(rightIndex, ')'));
    }

    private void addLeftParenthesisCandidate(String expression, String specialReversedExp, List<CharWithIndex> parenthesisToAdd, int operatorPosition) {
        int leftIndex;
        final char leftChar = expression.charAt(operatorPosition - 1);
        if (leftChar == ')') {
            leftIndex = getMatchingParenthesisIndex(expression, specialReversedExp, operatorPosition - 1, true);
        } else {
            leftIndex = getFirstNonDigit(expression, specialReversedExp, operatorPosition - 1, true);
        }
        parenthesisToAdd.add(new CharWithIndex(leftIndex, '('));
    }

    @AllArgsConstructor
    @Getter
    @ToString
    private static class CharWithIndex {
        private final int index;
        private final char character;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
