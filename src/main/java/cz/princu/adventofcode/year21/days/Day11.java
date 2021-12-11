package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.common.utils.ArrayIndexChecker;
import cz.princu.adventofcode.common.utils.Coords;
import cz.princu.adventofcode.common.utils.DataArrayUtils;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.LinkedList;

@Slf4j
public class Day11 extends Day {
    public static void main(String[] args) throws IOException {
        new Day11().printParts();
    }


    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        int[][] dataArray = DataArrayUtils.parseDataArray(input);

        var checker = new ArrayIndexChecker(dataArray.length, dataArray[0].length);

        long flashes = 0L;
        for (int i = 0; i < 100; i++) {
            flashes += doStep(dataArray, checker);
        }

        return flashes;

    }

    private long doStep(int[][] dataArray, ArrayIndexChecker checker) {

        LinkedList<Coords> notDistributedFlashes = new LinkedList<>();

        flashByEnergyIncrease(dataArray, notDistributedFlashes);
        while (!notDistributedFlashes.isEmpty()) {
            var currentFlashCoords = notDistributedFlashes.pollFirst();
            flashAdjacent(dataArray, checker, notDistributedFlashes, currentFlashCoords);
        }

        return dropEnergyOfFlashed(dataArray);
    }

    private long dropEnergyOfFlashed(int[][] dataArray) {
        long result = 0L;
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[i].length; j++) {
                if (dataArray[i][j] > 9) {
                    dataArray[i][j] = 0;
                    result += 1;
                }
            }
        }
        return result;
    }

    private void flashByEnergyIncrease(int[][] dataArray, LinkedList<Coords> notDistributedFlashes) {
        for (int i = 0; i < dataArray.length; i++) {
            for (int j = 0; j < dataArray[i].length; j++) {
                dataArray[i][j] += 1;

                if (dataArray[i][j] > 9) {
                    notDistributedFlashes.add(new Coords(i, j));
                }
            }
        }
    }

    private void flashAdjacent(int[][] dataArray,
                               ArrayIndexChecker checker,
                               LinkedList<Coords> flashList,
                               Coords flashOrigin) {

        for (int i = -1; i < 2; i++) {

            for (int j = -1; j < 2; j++) {

                if (i == 0 && j == 0)
                    continue;

                int x = flashOrigin.getI() + i;
                int y = flashOrigin.getJ() + j;
                if (checker.isOutOfBounds(x, y)) {
                    continue;
                }

                dataArray[x][y] += 1;
                if (dataArray[x][y] == 10) {
                    flashList.add(new Coords(x, y));
                }
            }
        }
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        int[][] dataArray = DataArrayUtils.parseDataArray(input);

        var checker = new ArrayIndexChecker(dataArray.length, dataArray[0].length);
        var size = (long) dataArray.length * dataArray[0].length;

        for (long i = 0; i < 10_000_000; i++) {
            var flashesCount = doStep(dataArray, checker);
            if (flashesCount >= size) {
                return i + 1;
            }
        }

        return -1;
    }


    @Override
    public int getDayNumber() {
        return 11;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
