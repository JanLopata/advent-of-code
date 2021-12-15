package cz.princu.adventofcode.year21.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.common.utils.ArrayIndexChecker;
import cz.princu.adventofcode.common.utils.Coords;
import cz.princu.adventofcode.common.utils.DataArrayUtils;
import lombok.extern.slf4j.Slf4j;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
public class Day15 extends Day {
    public static void main(String[] args) throws IOException {
        new Day15().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");
        var enterRisk = DataArrayUtils.parseDataArray(input);

        var start = new Coords(0, 0);
        var end = new Coords(enterRisk.length - 1, enterRisk[0].length - 1);

        ArrayIndexChecker checker = new ArrayIndexChecker(enterRisk.length, enterRisk[0].length);


        var graph = buildGraph(enterRisk, checker);

        var dijkstraShortestPath = new DijkstraShortestPath<Coords, DefaultEdge>(graph);
        var path = dijkstraShortestPath.getPath(start, end);

        return (long) path.getWeight();
    }


    private Graph<Coords, DefaultEdge> buildGraph(int[][] enterRisk, ArrayIndexChecker checker) {

        Graph<Coords, DefaultEdge> graph = new DirectedWeightedMultigraph<>(DefaultEdge.class);
        for (int i = 0; i < enterRisk.length; i++) {
            for (int j = 0; j < enterRisk[i].length; j++) {
                var current = new Coords(i, j);
                graph.addVertex(current);
                for (Coords coords : navigation) {

                    var next = coords.plus(current);
                    if (checker.isOutOfBounds(next))
                        continue;

                    graph.addVertex(next);
                    var edge = graph.addEdge(current, next);
                    if (edge != null) {
                        graph.setEdgeWeight(edge, enterRisk[next.getI()][next.getJ()]);
                    }
                }

            }
        }

        return graph;
    }


    private static final List<Coords> navigation = Stream.of(new Coords(1, 0), new Coords(0, 1),
            new Coords(-1, 0), new Coords(0, -1)).collect(Collectors.toUnmodifiableList());

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        return 0L;
    }


    @Override
    public int getDayNumber() {
        return 15;
    }

    @Override
    public int getYear() {
        return 2021;
    }
}
