package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Day17Test {

    Day17 tested = new Day17();

    @Test
    void testPart1_simpleData() {

        String testData = ".#.\n" +
                "..#\n" +
                "###";

        Assertions.assertEquals(112L, tested.part1(testData));

    }

    @Test
    void testPart2_one_simpleData() {

        String testData = "";

        Assertions.assertEquals(0L, tested.part2(testData));

    }

    @Test
    void testSet_generalIndex() {

        List<Integer> idxList = new ArrayList<>();
        idxList.add(1);
        idxList.add(5);
        idxList.add(7);

        List<Integer> idxListSame = new ArrayList<>();
        idxListSame.add(1);
        idxListSame.add(5);
        idxListSame.add(7);

        Day17.GeneralIndex idx1 = new Day17.GeneralIndex(idxList);
        Day17.GeneralIndex idx2 = new Day17.GeneralIndex(idxListSame);

        Set<Day17.GeneralIndex> mySet = new HashSet<>();

        mySet.add(idx1);
        mySet.add(idx2);

        Assertions.assertEquals(1, mySet.size());



    }

}