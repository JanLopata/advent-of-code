package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day04 extends Day {
    public static void main(String[] args) throws IOException {
        new Day04().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] inputs = data.split("\n\n");

        List<Integer> drawnNumbers = Arrays.stream(inputs[0].split(",")).map(Integer::valueOf).collect(Collectors.toList());

        List<BingoBoard> bingoBoards = new ArrayList<>();

        for (int i = 1; i < inputs.length; i++) {

            String rawBingoBoard = inputs[i];

            List<Integer> bingoBoardNumbers = Arrays.stream(rawBingoBoard.replace("\n", " ").split("  *"))
                    .filter(StringUtils::isNotBlank)
                    .map(Integer::parseInt).collect(Collectors.toList());

            bingoBoards.add(new BingoBoard(bingoBoardNumbers));

        }

        Pair<BingoBoard, Integer> winnerAndWinningNumber = updateAndFindWinner(drawnNumbers, bingoBoards);
        int unmarkedNumbersSum = winnerAndWinningNumber.getLeft().sumUnmarkedNumbers();

        return (long) unmarkedNumbersSum * winnerAndWinningNumber.getRight();
    }

    private Pair<BingoBoard, Integer> updateAndFindWinner(List<Integer> drawnNumbers, List<BingoBoard> bingoBoards) {
        for (Integer drawnNumber : drawnNumbers) {

            for (BingoBoard bingoBoard : bingoBoards) {
                boolean winner = bingoBoard.addNumber(drawnNumber);
                if (winner)
                    return Pair.of(bingoBoard, drawnNumber);
            }

        }
        return null;
    }

    private Pair<BingoBoard, Integer> updateAndFindLastWinner(List<Integer> drawnNumbers, List<BingoBoard> bingoBoards) {

        int winnersCount = 0;

        for (Integer drawnNumber : drawnNumbers) {

            for (BingoBoard bingoBoard : bingoBoards) {
                if (bingoBoard.alreadyWon)
                    continue;

                boolean winner = bingoBoard.addNumber(drawnNumber);
                if (winner) {
                    winnersCount++;
                    if (winnersCount >= bingoBoards.size()) {
                        return Pair.of(bingoBoard, drawnNumber);
                    }
                }
            }

        }
        return null;

    }

    @Override
    public Object part2(String data) {

        String[] inputs = data.split("\n\n");

        List<Integer> drawnNumbers = Arrays.stream(inputs[0].split(",")).map(Integer::valueOf).collect(Collectors.toList());

        List<BingoBoard> bingoBoards = new ArrayList<>();

        for (int i = 1; i < inputs.length; i++) {

            String rawBingoBoard = inputs[i];

            List<Integer> bingoBoardNumbers = Arrays.stream(rawBingoBoard.replace("\n", " ").split("  *"))
                    .filter(StringUtils::isNotBlank)
                    .map(Integer::parseInt).collect(Collectors.toList());

            bingoBoards.add(new BingoBoard(bingoBoardNumbers));

        }

        Pair<BingoBoard, Integer> winnerAndWinningNumber = updateAndFindLastWinner(drawnNumbers, bingoBoards);
        int unmarkedNumbersSum = winnerAndWinningNumber.getLeft().sumUnmarkedNumbers();

        return (long) unmarkedNumbersSum * winnerAndWinningNumber.getRight();


    }


    private static class BingoBoard {

        private final HashMap<Integer, Pair<Integer, Integer>> numberPositions;
        private final List<Integer> storedNumbers;
        private final HashMap<Integer, List<Boolean>> rowsCompletion;
        private final HashMap<Integer, List<Boolean>> colsCompletion;
        private boolean alreadyWon = false;

        public BingoBoard(List<Integer> numbers) {

            if (numbers.size() != 25) {
                throw new AssertionError("not expected count of bingo board numbers");
            }

            storedNumbers = new ArrayList<>(numbers);
            numberPositions = new HashMap<>(25);

            for (int i = 0; i < 25; i++) {
                Integer number = numbers.get(i);
                numberPositions.put(number, Pair.of(i % 5, i / 5));
            }
            rowsCompletion = new HashMap<>();
            colsCompletion = new HashMap<>();

            for (int i = 0; i < 5; i++) {
                rowsCompletion.put(i, getClearRowOrColumn());
                colsCompletion.put(i, getClearRowOrColumn());
            }

        }

        public boolean addNumber(int number) {

            if (!numberPositions.containsKey(number)) {
                return false;
            }

            Pair<Integer, Integer> coords = numberPositions.get(number);

            boolean isWinner = addToRowAndCheck(coords.getLeft(), coords.getRight())
                    || addToColumnAndCheck(coords.getLeft(), coords.getRight());

            if (isWinner) {
                alreadyWon = true;
            }

            return isWinner;

        }

        public int sumUnmarkedNumbers() {

            int sum = 0;
            for (int i = 0; i < storedNumbers.size(); i++) {

                Integer number = storedNumbers.get(i);
                int x = i % 5;
                int y = i / 5;
                Boolean isMarked = rowsCompletion.get(x).get(y);
                if (!isMarked) {
                    sum += number;
                }

            }

            return sum;
        }

        private boolean addToRowAndCheck(int i, int j) {

            List<Boolean> row = rowsCompletion.get(i);
            row.set(j, true);
            return row.stream().allMatch(it -> it);

        }

        private boolean addToColumnAndCheck(int i, int j) {

            List<Boolean> row = colsCompletion.get(j);
            row.set(i, true);
            return row.stream().allMatch(it -> it);

        }


        private static List<Boolean> getClearRowOrColumn() {
            return Stream.of(false, false, false, false, false).collect(Collectors.toList());
        }


    }

    @Override
    public int getDayNumber() {
        return 4;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
