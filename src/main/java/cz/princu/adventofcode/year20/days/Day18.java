package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedList;

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

    @Override
    public int getDayNumber() {
        return 18;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
