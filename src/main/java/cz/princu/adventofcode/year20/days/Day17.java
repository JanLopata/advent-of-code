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

        GeneralMemory memory = new GeneralMemory(3);

        for (int i = 0; i < input.length; i++) {

            final String row = input[i];

            for (int j = 0; j < row.length(); j++) {

                if (row.charAt(j) == '#') {
                    List<Integer> idxVars = new ArrayList<>();
                    idxVars.add(i);
                    idxVars.add(j);
                    idxVars.add(0);
                    memory.activatePosition(new GeneralIndex(idxVars));
                }
            }

        }


        GeneralMemory workMemory = GeneralMemory.copyOf(memory);

        for (int i = 0; i < 6; i++) {

            makeStep(memory, workMemory, new ArrayList<>());

            log.info("active {}", workMemory.getActiveCount());
            memory = workMemory;

        }

        return workMemory.getActiveCount();
    }

    private void makeStep(GeneralMemory oldState, GeneralMemory newState, List<Integer> givenIndices) {

        if (givenIndices.size() == oldState.getMemoryIdxDimension()) {
            // final

            final GeneralIndex target = new GeneralIndex(givenIndices);
            int vicinityActive = oldState.getActiveInVicinity(target);

            if (oldState.isActivated(target) && (vicinityActive == 2 || vicinityActive == 3)) {
                newState.deactivatePosition(target);
                return;
            }

            if (!oldState.isActivated(target) && vicinityActive == 3) {
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

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @EqualsAndHashCode
    @Getter
    @RequiredArgsConstructor
    private static class ThreeIndex {
        private final int x;
        private final int y;
        private final int z;
    }

    @EqualsAndHashCode(of = "idxVars")
    @ToString
    @Getter
    public static class GeneralIndex {


        private final int [] vars;

        private final List<Integer> idxVars;

        // TODO: varargs
        public GeneralIndex(List<Integer> idx) {
            idxVars = idx;
            vars = new int[idx.size()];
            for (int i = 0; i < idx.size(); i++) {
                vars[i] = idx.get(i);
            }
        }

        public int getDimensionValue(int dim) {
            return idxVars.get(dim);
        }

        public GeneralIndex plus(GeneralIndex another) {
            if (another.idxVars.size() != this.idxVars.size())
                throw new IllegalArgumentException("incompatibile dimensions");

            List<Integer> newIndices = new ArrayList<>();
            for (int i = 0; i < idxVars.size(); i++) {
                newIndices.add(this.idxVars.get(i) + another.idxVars.get(i));
            }
            return new GeneralIndex(newIndices);
        }

    }

    @RequiredArgsConstructor
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
