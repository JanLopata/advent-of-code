package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.common.utils.Coords;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
public class Day13 extends Day {
    public static void main(String[] args) throws IOException {
        new Day13().printParts();
    }

    @Override
    public Object part1(String data) {

        Set<Coords> dots = loadAndFold(data, 1);

        return (long) dots.size();
    }

    @Override
    public Object part2(String data) {

        Set<Coords> dots = loadAndFold(data, Integer.MAX_VALUE);

        var max = dots.stream().map(co -> Math.max(co.getI(), co.getJ()))
                .mapToInt(it -> it)
                .max().orElse(1);

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i <= max; i++) {
            for (int j = 0; j <= max; j++) {
                if (dots.contains(new Coords(j, i))) {
                    sb.append("#");
                } else {
                    sb.append(" ");
                }

            }
            sb.append("\n");
        }
        log.info("Day13::part2: \n{}", sb);

        return (long) dots.size();
    }

    private Set<Coords> loadAndFold(String data, int i) {
        String[] input = data.split("\n\n");
        var dotsPart = input[0].split("\n");
        var foldingPart = input[1].split("\n");

        Set<Coords> dots = new HashSet<>();
        for (String s : dotsPart) {
            var split = s.split(",");
            dots.add(new Coords(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
        }

        for (int lineNumber = 0; lineNumber < foldingPart.length; lineNumber++) {
            if (lineNumber >= i)
                break;
            var foldBase = foldingPart[lineNumber].replace("fold along ", "");
            doOneFold(dots, foldBase);
        }
        return dots;
    }

    private void doOneFold(Set<Coords> dots, String foldBase) {
        var foldNumber = Integer.parseInt(foldBase.split("=")[1]);
        if (foldBase.startsWith("x")) {
            var toFold = dots.stream()
                    .filter(it -> it.getI() >= foldNumber)
                    .collect(Collectors.toList());

            for (Coords coords : toFold) {
                dots.remove(coords);
                dots.add(new Coords(2 * foldNumber - coords.getI(), coords.getJ()));
            }
        }

        if (foldBase.startsWith("y")) {
            var toFold = dots.stream()
                    .filter(it -> it.getJ() >= foldNumber)
                    .collect(Collectors.toList());

            for (Coords coords : toFold) {
                dots.remove(coords);
                var newDot = new Coords(coords.getI(), 2 * foldNumber - coords.getJ());
                dots.add(newDot);
            }
        }
    }


    @Override
    public int getDayNumber() {
        return 13;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
