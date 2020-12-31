package cz.princu.adventofcode.year20.days.day20;

import org.apache.commons.lang3.tuple.Pair;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

public class SeaMonster {


    private final Set<Pair<Integer, Integer>> monsterCoords;
    private final int minI;
    private final int maxI;
    private final int minJ;
    private final int maxJ;

    public SeaMonster(String monsterText) {

        super();
        Set<Pair<Integer, Integer>> monsterCoordsPrep = new HashSet<>();

        final String[] split = monsterText.split("\n");
        for (int i = 0; i < split.length; i++) {

            for (int j = 0; j < split[i].length(); j++) {
                if (split[i].charAt(j) == '#')
                    monsterCoordsPrep.add(Pair.of(i, j));
            }
        }

        this.monsterCoords = Collections.unmodifiableSet(monsterCoordsPrep);

        this.minI = monsterCoords.stream().map(Pair::getLeft).min(Comparator.naturalOrder()).orElse(Integer.MAX_VALUE);
        this.maxI = monsterCoords.stream().map(Pair::getLeft).max(Comparator.naturalOrder()).orElse(Integer.MIN_VALUE);
        this.minJ = monsterCoords.stream().map(Pair::getRight).min(Comparator.naturalOrder()).orElse(Integer.MAX_VALUE);
        this.maxJ = monsterCoords.stream().map(Pair::getRight).max(Comparator.naturalOrder()).orElse(Integer.MIN_VALUE);

    }

    public boolean findAndReplaceMonster(char[][] sea) {

        for (int startI = minI; startI < sea.length - maxI; startI++) {

            for (int startJ = minJ; startJ < sea[0].length - maxJ; startJ++) {

                boolean monsterFound = checkMonsterOnStartingCoords(sea, startI, startJ);
                if (monsterFound) {
                    replaceMonsterOnStartingCoords(sea, startI, startJ);
                    return true;
                }


            }
        }

        return false;
    }

    private void replaceMonsterOnStartingCoords(char[][] sea, int startI, int startJ) {
        for (Pair<Integer, Integer> monsterCoord : monsterCoords) {

            int i = startI + monsterCoord.getLeft();
            int j = startJ + monsterCoord.getRight();

            sea[i][j] = 'O';
        }
    }

    private boolean checkMonsterOnStartingCoords(char[][] sea, int startI, int startJ) {
        boolean monsterFound = true;
        for (Pair<Integer, Integer> monsterCoord : monsterCoords) {

            int i = startI + monsterCoord.getLeft();
            int j = startJ + monsterCoord.getRight();

            if (sea[i][j] != '#') {
                monsterFound = false;
                break;
            }

        }
        return monsterFound;
    }


}
