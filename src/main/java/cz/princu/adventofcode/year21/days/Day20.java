package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.common.utils.Coords;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class Day20 extends Day {
    public static void main(String[] args) throws IOException {
        new Day20().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n\n");
        String enhancement = input[0];

        boolean flipin = enhancement.startsWith("#");
        boolean universeLit = false;

        var imageLines = input[1].split("\n");

        Set<Coords> map = new HashSet<>();
        for (int i = 0; i < imageLines.length; i++) {
            var chars = imageLines[i].toCharArray();
            for (int j = 0; j < chars.length; j++) {
                if (chars[j] == '#') {
                    map.add(new Coords(i, j));
                }
            }
        }
        printMap(map);

        for (int i = 0; i < 2; i++) {
            Set<Coords> enhanced = doEnhancement(enhancement, map, universeLit);
            printMap(enhanced);
            universeLit = flipin && !universeLit;
            map = new HashSet<>(enhanced);
        }


        return (long) map.size();
    }

    private void printMap(Set<Coords> map) {

        StringBuilder sb = new StringBuilder();
        var minX = map.stream().mapToInt(Coords::getI).min().orElse(-1);
        var maxX = map.stream().mapToInt(Coords::getI).max().orElse(-1);
        var minY = map.stream().mapToInt(Coords::getJ).min().orElse(-1);
        var maxY = map.stream().mapToInt(Coords::getJ).max().orElse(-1);

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {

                sb.append(map.contains(new Coords(x, y)) ? "#" : '.');

            }
            sb.append("\n");
        }

        log.info("Map:\n{}", sb);
    }

    private Set<Coords> doEnhancement(String enhancement, Set<Coords> map, boolean universeLit) {
        var minX = map.stream().mapToInt(Coords::getI).min().orElse(-1);
        var maxX = map.stream().mapToInt(Coords::getI).max().orElse(-1);
        var minY = map.stream().mapToInt(Coords::getJ).min().orElse(-1);
        var maxY = map.stream().mapToInt(Coords::getJ).max().orElse(-1);

        if (universeLit) {
            for (int i = minX - 1; i <= maxX + 1; i++) {
               map.add(new Coords(i, minY - 1));
               map.add(new Coords(i, maxY + 1));
            }
            for (int i = minY-1; i <= maxY+1; i++) {
                map.add(new Coords(minX-1, i));
                map.add(new Coords(maxX+1, i));
            }
        }

        Set<Coords> result = new HashSet<>();

        for (int x = minX - 1; x <= maxX + 1; x++) {
            for (int y = minY - 1; y <= maxY + 1; y++) {

                int surrounding = surroundingToNumber(map, new Coords(x, y));

                if (enhancement.charAt(surrounding) == '#') {
                    result.add(new Coords(x, y));
                }

            }
        }


        return result;
    }

    private int surroundingToNumber(Set<Coords> map, Coords coords) {

        int result = 0;
        int magnitude = 256;
        for (int i = -1; i < 2; i++) {
            for (int j = -1; j < 2; j++) {
                result += (map.contains(coords.plus(i, j)) ? 1 : 0) * magnitude;
                magnitude /= 2;
            }
        }
        return result;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @Override
    public int getDayNumber() {
        return 20;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
