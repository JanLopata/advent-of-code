package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Slf4j
public class Day24 extends Day {
    public static void main(String[] args) throws IOException {
        new Day24().printParts();
    }

    private static final AtomicLong logCounter = new AtomicLong();

    @Override
    public Object part1(String data) {

        var dataMul = "inp x\n" +
                "mul x -1";

        var dataEql = "inp z\n" +
                "inp x\n" +
                "mul z 3\n" +
                "eql z x";

        var dataBinarizer = "inp w\n" +
                "add z w\n" +
                "mod z 2\n" +
                "div w 2\n" +
                "add y w\n" +
                "mod y 2\n" +
                "div w 2\n" +
                "add x w\n" +
                "mod x 2\n" +
                "div w 2\n" +
                "mod w 2";

        var alu = new ALU(dataMul.split("\n"));
        alu.reset("4");
        alu.run();
        assert alu.vars[1] == -4;

        alu = new ALU(dataEql.split("\n"));
        alu.reset("26");
        alu.run();
        assert alu.vars[3] == 1;

        alu = new ALU(dataBinarizer.split("\n"));
        alu.reset("9");
        alu.run();
        assert alu.vars[3] == 1;
        assert alu.vars[2] == 0;
        assert alu.vars[1] == 0;
        assert alu.vars[0] == 1;

        alu.reset("5");
        alu.run();
        assert alu.vars[3] == 1;
        assert alu.vars[2] == 0;
        assert alu.vars[1] == 1;
        assert alu.vars[0] == 0;

        var instructions = data.split("\n");
        var ranges = findValidRangesForZ(instructions);

        log.info("ranges for z: {}", ranges);
        var start = new ALU(instructions);
        start.init14();

        findValidModelNumber(ranges, start, 0);


//        alu = new ALU(data.split("\n"));
//        alu.init14();
//        while (!alu.run()) {
//            alu.downNext();
//        }

        var collect = Arrays.stream(alu.inputVars).mapToObj(String::valueOf).collect(Collectors.joining());
        return Long.parseLong(collect);
    }

    private void findValidModelNumber(Map<Integer, Pair<Integer, Integer>> ranges, ALU start, int i) {

        if (logCounter.incrementAndGet() > 1_000_000) {
            log.info("input examined to depth {} - {}", i, start.inputVars);
            logCounter.set(0L);
        }

        if (ranges.containsKey(i)) {
            var zetMod26 = start.vars[3] % 26;
            var max = ranges.get(i).getRight();
            var min = ranges.get(i).getLeft();
            if (zetMod26 > max || zetMod26 < min) {
                // dead end
                return;
            }
        }

        if (start.hasNoOtherInstructions()) {
            if (start.vars[3] == 0) {
                log.info("Best solution: {}", start.inputVars);
                throw new RuntimeException("solution found");
            }
            // dead end
            return;
        }

        for (int j = 9; j >= 1; j--) {
            var aluCopy = ALU.copyOf(start);
            aluCopy.inputVars[i] = j;
            aluCopy.run(18);
            findValidModelNumber(ranges, aluCopy, i + 1);
        }

    }

    private Map<Integer, Pair<Integer, Integer>> findValidRangesForZ(String[] instruction) {

        Map<Integer, Pair<Integer, Integer>> result = new HashMap<>(14);
        int i = 0;
        for (String s : instruction) {
            if (s.startsWith("add")) {
                var split = s.split(" ");
                if ("x".equals(split[1]) && !"z".equals(split[2])) {
                    int num = Integer.parseInt(split[2]);
                    if (num <= 9) {
                        result.put(i, Pair.of(1 - num, 9 - num));
                    }
                    i++;
                }
            }
        }
        return result;

    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @RequiredArgsConstructor
    public static class ALU {

        private static final int BITS = 14;
        private static final int MEMORY_SIZE = 4;
        private final int[] vars;
        private final String[] instructions;
        private int[] inputVars = new int[BITS];
        private int currentInstruction = 0;
        private int currentInputPosition;

        public static ALU copyOf(ALU instance) {
            return new ALU(instance.instructions,
                    instance.vars,
                    instance.currentInstruction,
                    instance.currentInputPosition,
                    instance.inputVars);
        }

        public ALU(String[] instructions) {
            this.vars = new int[MEMORY_SIZE];
            this.instructions = instructions;
            this.inputVars = new int[BITS];
        }

        public ALU(String[] instructions,
                   int[] vars,
                   int currentInstruction,
                   int currentInputPosition,
                   int[] inputVars) {
            this.instructions = instructions;
            this.currentInputPosition = currentInputPosition;
            this.currentInstruction = currentInstruction;
            this.vars = Arrays.copyOf(vars, vars.length);
            this.inputVars = inputVars;
        }

        public void reset(String inputString) {
            Arrays.fill(vars, 0);
            currentInputPosition = 0;
            var chars = inputString.toCharArray();
            // TODO: suboptimal
            for (int i = 0; i < chars.length; i++) {
                inputVars[i] = Integer.parseInt(String.valueOf(chars[i]));
            }
        }

        public void init14() {
            Arrays.fill(vars, 0);
            for (int i = 0; i < BITS; i++) {
                inputVars[i] = 9;
            }
        }

        public void downNext() {
            downNext(BITS - 1);
            Arrays.fill(vars, 0);
            currentInputPosition = 0;
        }

        boolean hasNoOtherInstructions() {
            return instructions.length <= currentInstruction;
        }

        public void downNext(int pos) {
            if (pos < 8) {
                log.info("wow - {}", inputVars);
            }

            if (inputVars[pos] > 1) {
                inputVars[pos]--;
            } else {
                inputVars[pos] = 9;
                downNext(pos - 1);
            }

        }

        public boolean run() {

            for (String instruction : instructions) {
                performInstruction(instruction);
            }
            return vars[MEMORY_SIZE - 1] == 0;
        }

        public void run(int instructionsToRun) {

            for (int i = 0; i < instructionsToRun; i++) {
                performInstruction(instructions[currentInstruction++]);
            }

        }

        private void performInstruction(String instruction) {

            int pos = instruction.charAt(4) - 'w';
            if (instruction.startsWith("inp")) {
                vars[pos] = inputVars[currentInputPosition++];
                return;
            }

            var bChar = instruction.charAt(6);
            int b = Character.isLetter(bChar) ?
                    parseLetterValue(bChar) : parseNumber(instruction);

            if (instruction.startsWith("add")) {
                vars[pos] = vars[pos] + b;
                return;
            }

            if (instruction.startsWith("mul")) {
                vars[pos] = vars[pos] * b;
                return;
            }

            if (instruction.startsWith("div")) {
                vars[pos] = vars[pos] / b;
                return;
            }

            if (instruction.startsWith("mod")) {
                vars[pos] = vars[pos] % b;
                return;
            }

            if (instruction.startsWith("eql")) {
                vars[pos] = vars[pos] == b ? 1 : 0;
                return;
            }
            throw new UnsupportedOperationException("unknown instruction " + instruction);

        }

        private int parseNumber(String instruction) {
            return Integer.parseInt(instruction.substring(6));
        }

        private int parseLetterValue(char letter) {
            return vars[letter - 'w'];
        }
    }

    @Override
    public int getDayNumber() {
        return 24;
    }

    @Override
    public int getYear() {
        return 2021;
    }

}