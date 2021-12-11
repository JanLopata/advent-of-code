package cz.princu.adventofcode.year20.days;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class Day19Test {

    Day19 tested = new Day19();

    final String simpleData = "0: 4 1 5\n" +
            "1: 2 3 | 3 2\n" +
            "2: 4 4 | 5 5\n" +
            "3: 4 5 | 5 4\n" +
            "4: \"a\"\n" +
            "5: \"b\"\n" +
            "\n" +
            "ababbb\n" +
            "bababa\n" +
            "abbbab\n" +
            "aaabbb\n" +
            "aaaabbb";

    final String advancedData = "42: 9 14 | 10 1\n" +
            "9: 14 27 | 1 26\n" +
            "10: 23 14 | 28 1\n" +
            "1: \"a\"\n" +
            "11: 42 31\n" +
            "5: 1 14 | 15 1\n" +
            "19: 14 1 | 14 14\n" +
            "12: 24 14 | 19 1\n" +
            "16: 15 1 | 14 14\n" +
            "31: 14 17 | 1 13\n" +
            "6: 14 14 | 1 14\n" +
            "2: 1 24 | 14 4\n" +
            "0: 8 11\n" +
            "13: 14 3 | 1 12\n" +
            "15: 1 | 14\n" +
            "17: 14 2 | 1 7\n" +
            "23: 25 1 | 22 14\n" +
            "28: 16 1\n" +
            "4: 1 1\n" +
            "20: 14 14 | 1 15\n" +
            "3: 5 14 | 16 1\n" +
            "27: 1 6 | 14 18\n" +
            "14: \"b\"\n" +
            "21: 14 1 | 1 14\n" +
            "25: 1 1 | 1 14\n" +
            "22: 14 14\n" +
            "8: 42\n" +
            "26: 14 22 | 1 20\n" +
            "18: 15 15\n" +
            "7: 14 5 | 1 21\n" +
            "24: 14 1\n" +
            "\n" +
            "abbbbbabbbaaaababbaabbbbabababbbabbbbbbabaaaa\n" +
            "bbabbbbaabaabba\n" +
            "babbbbaabbbbbabbbbbbaabaaabaaa\n" +
            "aaabbbbbbaaaabaababaabababbabaaabbababababaaa\n" +
            "bbbbbbbaaaabbbbaaabbabaaa\n" +
            "bbbababbbbaaaaaaaabbababaaababaabab\n" +
            "ababaaaaaabaaab\n" +
            "ababaaaaabbbaba\n" +
            "baabbaaaabbaaaababbaababb\n" +
            "abbbbabbbbaaaababbbbbbaaaababb\n" +
            "aaaaabbaabaaaaababaa\n" +
            "aaaabbaaaabbaaa\n" +
            "aaaabbaabbaaaaaaabbbabbbaaabbaabaaa\n" +
            "babaaabbbaaabaababbaabababaaab\n" +
            "aabbbbbaabbbaaaaaabbbbbababaaaaabbaaabba";

    @Test
    void testPart1_simpleData() {

        Assertions.assertEquals(2L, tested.part1(simpleData));

    }

    @Test
    void testPart1_advancedData() {

        Assertions.assertEquals(3L, tested.part1(advancedData));

    }

    @Test
    void testPart2_advancedData() {

        Assertions.assertEquals(12L, tested.part2(advancedData));

    }

}