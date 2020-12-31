package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import cz.princu.adventofcode.year20.days.day20.SeaMonster;
import cz.princu.adventofcode.year20.days.day20.Tile;
import cz.princu.adventofcode.year20.days.day20.TileConfiguration;
import cz.princu.adventofcode.year20.days.day20.TileFactory;
import cz.princu.adventofcode.year20.days.day20.TileManager;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class Day20 extends Day {
    public static void main(String[] args) throws IOException {
        new Day20().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n\n");

        List<Tile> tiles = new ArrayList<>();
        for (String s : input) {
            final Tile tile = Tile.initTile(s);
            tiles.add(tile);
        }
        TileFactory tileFactory = TileFactory.init(tiles);
        TileManager tileManager = TileManager.init(tiles.size());

        Set<Long> result = new HashSet<>();
        solve(tileFactory, tileManager, result);

        return result.stream().reduce((a, b) -> a * b).orElse(null);
    }

    private void solve(TileFactory tileFactory, TileManager tileManager, Set<Long> result) {

        if (!result.isEmpty())
            return;

        log.info("{}", tileManager.getTextRepresentation());

        if (tileManager.allTilesUsed()) {
            log.info("solved but what now?");

            List<TileConfiguration> corners = new ArrayList<>();
            corners.add(tileManager.getTileOnCoordinates(0, 0));
            corners.add(tileManager.getTileOnCoordinates(0, tileManager.getSideSize() - 1));
            corners.add(tileManager.getTileOnCoordinates(tileManager.getSideSize() - 1, 0));
            corners.add(tileManager.getTileOnCoordinates(tileManager.getSideSize() - 1, tileManager.getSideSize() - 1));

            corners.stream().map(it -> it.getTile()).map(it -> it.getId()).forEach(result::add);

            tileManager.freeze();
            return;
        }

        final Set<TileConfiguration> candidates = getCandidates(tileFactory, tileManager);

        for (TileConfiguration candidate : candidates) {


            final boolean success = tileManager.addTile(candidate);

            if (success) {
                solve(tileFactory, tileManager, result);
                tileManager.removeLast();

            }

        }

    }

    private Set<TileConfiguration> getCandidates(TileFactory tileFactory, TileManager tileManager) {
        final List<TileConfiguration> currentNeighbors = tileManager.getCurrentNeighbors();

        Set<TileConfiguration> candidates = null;
        for (int neighbourDirection = 0; neighbourDirection < currentNeighbors.size(); neighbourDirection++) {

            final TileConfiguration tileConfiguration = currentNeighbors.get(neighbourDirection);
            if (tileConfiguration == null)
                continue;

            final int oppositeSide = (neighbourDirection + 2) % 4;
            final String sideString = tileConfiguration.getSide(oppositeSide);

            final Set<TileConfiguration> candidateSet = tileFactory.getPossibleTiles(sideString, neighbourDirection);

            if (candidates == null) {
                candidates = candidateSet;
            } else {
                candidates.retainAll(candidateSet);
            }
        }

        if (candidates == null)
            candidates = tileFactory.getPossibleTiles(null, 0);

        return candidates;
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n\n");

        List<Tile> tiles = new ArrayList<>();
        for (String s : input) {
            final Tile tile = Tile.initTile(s);
            tiles.add(tile);
        }
        TileFactory tileFactory = TileFactory.init(tiles);
        TileManager tileManager = TileManager.init(tiles.size());

        solve(tileFactory, tileManager, new HashSet<>());
        char[][] assembled = tileManager.assemble();

        StringBuilder assembleString = new StringBuilder();
        assembleString.append("Tile: 0");
        for (char[] chars : assembled) {
            assembleString.append("\n");
            for (char chachar : chars) {
                assembleString.append(chachar);
            }
        }
        log.info("Assembled: {}", assembleString.toString());
        final Tile monsterTile = Tile.initTile(assembleString.toString());

        TileFactory monsterTileFactory = TileFactory.init(Collections.singletonList(monsterTile));

        String monsterText = "" +
                "                  # \n" +
                "#    ##    ##    ###\n" +
                " #  #  #  #  #  #   ";

        SeaMonster seaMonster = new SeaMonster(monsterText);

        for (TileConfiguration monsterTileConfig : monsterTileFactory.getTileConfigurationList()) {

            // destructive - breaks encapsulation - beware!
            final char[][] grid = monsterTileConfig.getGrid();

            int monsterCount = 0;
            while (seaMonster.findAndReplaceMonster(grid)) {
                monsterCount++;
            }


            if (monsterCount > 0) {

                log.info("Found {} see monster(s):\n{}", monsterCount, monsterTileConfig.gridAsText());

                long hashTiles = 0;
                for (char[] chars : grid) {
                    for (char aChar : chars) {
                        if (aChar == '#')
                            hashTiles++;
                    }
                }
                return hashTiles;

            }

        }


        return 0L;
    }


    @Override
    public int getDayNumber() {
        return 20;
    }


    @Override
    public int getYear() {
        return 2020;
    }
}
