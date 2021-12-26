package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.common.utils.Coords;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class Day25 extends Day {
    public static void main(String[] args) throws IOException {
        new Day25().printParts();
    }

    @Override
    public Object part1(String data) {

        char[][] field = initField(data);
        logField(field, "init");

        long i = 1;

        while (performOneStep(field)) {
            logField(field, "step " + i);
            i++;
        }

        return i;

    }

    boolean performOneStep(char[][] field) {
        int moves = 0;
        moves += performMoves(field, findWhoCanMove(field, '>'));
        applyBorders(field, '>');
        moves += performMoves(field, findWhoCanMove(field, 'v'));
        applyBorders(field, 'v');
        return moves > 0;

    }

    int performMoves(char[][] field, List<Coords> whoCanMove) {

        for (Coords coords : whoCanMove) {
            if (field[coords.getJ()][coords.getI()] == '>') {
                if (coords.getI() == 0) {
                    field[coords.getJ()][field[coords.getJ()].length - 1] = '.';
                }
                field[coords.getJ()][coords.getI()] = '.';
                field[coords.getJ()][coords.getI() + 1] = '>';
            } else {
                if (coords.getJ() == 0) {
                    field[field.length - 1][coords.getI()] = '.';
                }
                field[coords.getJ()][coords.getI()] = '.';
                field[coords.getJ() + 1][coords.getI()] = 'v';
            }
        }

        return whoCanMove.size();

    }

    List<Coords> findWhoCanMove(char[][] field, char who) {

        List<Coords> result = new ArrayList<>();
        var b = who == '>';
        var yMax = field.length - (b ? 0 : 1);
        var xMax = field[0].length - (b ? 1 : 0);
        for (int y = 0; y < yMax; y++) {
            for (int x = 0; x < xMax; x++) {
                if (field[y][x] == who && canMove(field, x, y))
                    result.add(new Coords(x, y));
            }
        }
        return result;
    }

    private boolean canMove(char[][] field, int x, int y) {

        var c = field[y][x];
        if (c == '>') {
            return field[y][x + 1] == '.';
        }

        if (c == 'v') {
            return field[y + 1][x] == '.';
        }

        return false;

    }


    void logField(char[][] field, String text) {

        if (!log.isDebugEnabled())
            return;

        StringBuilder sb = new StringBuilder("\n");

        for (int i = 0, fieldLength = field.length - 1; i < fieldLength; i++) {
            char[] chars = field[i];
            for (int j = 0; j < chars.length - 1; j++) {
                sb.append(chars[j]);
            }
            sb.append("\n");
        }
        sb.append("\n");
        for (int i = 0, fieldLength = field.length ; i < fieldLength; i++) {
            if (i == fieldLength - 1)
                sb.append("-".repeat(field[0].length + 1)).append("\n");
            char[] chars = field[i];
            for (int j = 0; j < chars.length - 1; j++) {
                sb.append(chars[j]);
            }
            sb.append("|");
            sb.append(chars[chars.length - 1]);
            sb.append("\n");
        }

        log.debug("{}: {}", text, sb);



    }

    char[][] initField(String data) {

        String[] rows = data.split("\n");
        int sizeX = rows[0].length() + 1;
        int sizeY = rows.length + 1;

        char[][] field = new char[sizeY][sizeX];

        for (int i = 0; i < sizeY - 1; i++) {
            for (int j = 0; j < sizeX - 1; j++) {
                field[i][j] = rows[i].charAt(j);
            }
        }
        for (int i = 0; i < sizeY; i++) {
            field[i][sizeX - 1] = field[i][0];
        }
        for (int i = 0; i < sizeX; i++) {
            field[sizeY - 1][i] = field[0][i];
        }
        return field;
    }

    void applyBorders(char[][] field, char c) {
        var sizeY = field.length;
        var sizeX = field[0].length;
        if (c == '>') {
            for (int i = 0; i < sizeY; i++) {
                field[i][0] = field[i][sizeX - 1];
            }
        } else {
            for (int i = 0; i < sizeX; i++) {
                field[0][i] = field[sizeY - 1][i];
            }
        }
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");


        return 0L;
    }


    @Override
    public int getDayNumber() {
        return 25;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
