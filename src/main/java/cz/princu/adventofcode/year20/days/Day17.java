package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day17 extends Day {
    public static void main(String[] args) throws IOException {
        new Day17().printParts();
    }


    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        return countActiveNodesAfterGivenSteps(input, 3, 6);
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return countActiveNodesAfterGivenSteps(input, 4, 6);
    }

    private long countActiveNodesAfterGivenSteps(String[] input, int problemDimension, int steps) {
        GeneralMemory memory = new GeneralMemory(problemDimension);

        for (int i = 0; i < input.length; i++) {

            final String row = input[i];

            for (int j = 0; j < row.length(); j++) {

                if (row.charAt(j) == '#') {
                    List<Integer> idxVars = new ArrayList<>();
                    for (int k = 0; k < problemDimension - 2; k++) {
                        idxVars.add(0);
                    }
                    idxVars.add(i);
                    idxVars.add(j);
                    memory.activatePosition(new GeneralIndex(idxVars));
                }
            }
        }

        GeneralMemory workMemory = new GeneralMemory(problemDimension);

        for (int i = 0; i < steps; i++) {
            log.info("Step {}\n{}", i, memory.getPrintableString());

            makeStep(memory, workMemory, new ArrayList<>());

            log.info("active {}", workMemory.getActiveCount());
            memory = workMemory;
            workMemory = new GeneralMemory(problemDimension);

        }
        log.info("Final step\n{}", workMemory.getPrintableString());

        return memory.getActiveCount();
    }

    private void makeStep(GeneralMemory oldState, GeneralMemory newState, List<Integer> givenIndices) {

        if (givenIndices.size() == oldState.getMemoryIdxDimension()) {
            // final

            final GeneralIndex target = new GeneralIndex(givenIndices);
            final int vicinityActive = oldState.getActiveInVicinity(target);
            final boolean activated = oldState.isActivated(target);

            if (activated && (vicinityActive == 2 || vicinityActive == 3)) {
                newState.activatePosition(target);
            }

            if (!activated && vicinityActive == 3) {
                newState.activatePosition(target);
            }

            return;
        }

        int workDimIndex = givenIndices.size();

        int min = oldState.getMinAtDim(workDimIndex);
        int max = oldState.getMaxAtDim(workDimIndex);

        List<Integer> workIndices = new ArrayList<>(givenIndices);
        workIndices.add(0);

        for (int idx = min - 1; idx <= max + 1; idx++) {
            workIndices.set(workDimIndex, idx);
            makeStep(oldState, newState, workIndices);
        }

    }


    @EqualsAndHashCode
    @ToString
    @Getter
    public static class GeneralIndex {

        private final int[] vars;

        // TODO: varargs
        public GeneralIndex(List<Integer> idx) {
            vars = new int[idx.size()];
            for (int i = 0; i < idx.size(); i++) {
                vars[i] = idx.get(i);
            }
        }

        public int getDimensionValue(int dim) {
            return vars[dim];
        }

        public GeneralIndex plus(GeneralIndex another) {
            if (another.getVars().length != this.vars.length)
                throw new IllegalArgumentException("incompatible dimensions");

            List<Integer> newIndices = new ArrayList<>();
            for (int i = 0; i < vars.length; i++) {
                newIndices.add(this.vars[i] + another.getVars()[i]);
            }
            return new GeneralIndex(newIndices);
        }

    }

    @RequiredArgsConstructor
    @ToString
    private static class GeneralMemory {
        private final Set<GeneralIndex> memory = new HashSet<>();
        @Getter
        private final int memoryIdxDimension;

        public void activatePosition(GeneralIndex ti) {
            memory.add(ti);
        }

        public void deactivatePosition(GeneralIndex ti) {
            memory.remove(ti);
        }

        public boolean isActivated(GeneralIndex ti) {
            return memory.contains(ti);
        }

        public long getActiveCount() {
            return memory.size();
        }

        public static GeneralMemory copyOf(GeneralMemory tm) {
            GeneralMemory result = new GeneralMemory(tm.getMemoryIdxDimension());

            result.memory.addAll(tm.memory);
            return result;
        }

        public GeneralIndex getMin() {

            List<Integer> resultIdx = new ArrayList<>();
            for (int dim = 0; dim < memoryIdxDimension; dim++) {
                int min = Integer.MAX_VALUE;
                for (GeneralIndex generalIndex : memory) {
                    final int valueInDimension = generalIndex.getDimensionValue(dim);
                    if (valueInDimension < min)
                        min = valueInDimension;
                }
                resultIdx.add(min);
            }


            return new GeneralIndex(resultIdx);

        }

        public int getMinAtDim(int dim) {
            int min = Integer.MAX_VALUE;

            for (GeneralIndex generalIndex : memory) {
                final int valueInDimension = generalIndex.getDimensionValue(dim);
                if (valueInDimension < min)
                    min = valueInDimension;
            }

            return min;
        }

        public int getMaxAtDim(int dim) {
            int max = Integer.MIN_VALUE;

            for (GeneralIndex generalIndex : memory) {
                final int valueInDimension = generalIndex.getDimensionValue(dim);
                if (valueInDimension > max)
                    max = valueInDimension;
            }

            return max;
        }

        public GeneralIndex getMax() {

            List<Integer> resultIdx = new ArrayList<>();
            for (int dim = 0; dim < memoryIdxDimension; dim++) {
                int max = Integer.MIN_VALUE;
                for (GeneralIndex generalIndex : memory) {
                    final int valueInDimension = generalIndex.getDimensionValue(dim);
                    if (valueInDimension > max)
                        max = valueInDimension;
                }
                resultIdx.add(max);
            }

            return new GeneralIndex(resultIdx);

        }

        public int getActiveInVicinity(GeneralIndex generalIndex) {

            int dimFold = 1;
            for (int i = 0; i < memoryIdxDimension; i++) {
                dimFold *= 3;
            }

            int result = 0;
            for (int foldIndex = 0; foldIndex < dimFold; foldIndex++) {

                int workIdx = foldIndex;
                List<Integer> idxVars = new ArrayList<>();
                for (int i = 0; i < memoryIdxDimension; i++) {
                    idxVars.add(workIdx % 3 - 1);
                    workIdx = workIdx / 3;
                }

                if (idxVars.stream().allMatch(it -> it.equals(0)))
                    continue;

                GeneralIndex target = generalIndex.plus(new GeneralIndex(idxVars));
                if (memory.contains(target))
                    result++;

            }

            return result;
        }

        private void fillStringBuilder(StringBuilder sb, List<Integer> givenIndices) {

            if (givenIndices.size() == memoryIdxDimension) {

                final GeneralIndex target = new GeneralIndex(givenIndices);
                if (isActivated(target))
                    sb.append("#");
                else
                    sb.append(".");

                return;
            }

            final ArrayList<Integer> workIndices = new ArrayList<>(givenIndices);
            workIndices.add(0);

            for (int i = getMinAtDim(givenIndices.size()); i <= getMaxAtDim(givenIndices.size()); i++) {
                workIndices.set(givenIndices.size(), i);
                fillStringBuilder(sb, workIndices);


            }
            if (givenIndices.size() == getMemoryIdxDimension() - 1) {
                sb.append("\n");
            }

            if (!givenIndices.isEmpty() && givenIndices.size() < getMemoryIdxDimension() - 1) {
                sb.append("---");
                sb.append(givenIndices.size());
                sb.append("---\n");
            }

        }

        public String getPrintableString() {
            StringBuilder sb = new StringBuilder();
            fillStringBuilder(sb, new ArrayList<>());
            return sb.toString();
        }

    }

    @Override
    public int getDayNumber() {
        return 17;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
