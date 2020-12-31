package cz.princu.adventofcode.year20.days.day20;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
@Slf4j
public class TileManager {

    private final TileConfiguration[][] field;

    @Getter
    private final Set<Tile> usedTiles = new HashSet<>();

    private final LinkedList<UsedTile> stack = new LinkedList<>();
    @Getter
    private final int tileCount;
    @Getter
    private final int sideSize;

    @Getter
    @Setter
    private int currentTile = 0;

    private boolean frozen = false;

    public static TileManager init(int tileCount) {

        int size = 0;
        while (size * size < tileCount)
            size++;

        TileConfiguration[][] field = new TileConfiguration[size + 2][];

        for (int i = 0; i < field.length; i++) {
            field[i] = new TileConfiguration[size + 2];
        }

        return new TileManager(field, tileCount, size);

    }

    public TileConfiguration getTileOnCoordinates(int i, int j) {

        return field[i + 1][j + 1];
    }

    public List<TileConfiguration> getCurrentNeighbors() {
        return getNeighbors(currentTile / sideSize, currentTile % sideSize);
    }

    private List<TileConfiguration> getNeighbors(int i, int j) {

        List<TileConfiguration> result = new ArrayList<>();

        result.add(field[i][j + 1]);
        result.add(field[i + 1][j + 2]);
        result.add(field[i + 2][j + 1]);
        result.add(field[i + 1][j]);
        return result;

    }


    public boolean addTile(TileConfiguration tileConf) {

        return addTile(tileConf, currentTile / sideSize, currentTile % sideSize);

    }

    private boolean addTile(TileConfiguration tileConf, int i, int j) {

        if (i < 0 || j < 0 || i >= field.length || j >= field.length)
            return false;

        if (usedTiles.contains(tileConf.getTile()))
            return false;

        if (field[i + 1][j + 1] != null)
            throw new IllegalArgumentException("position occupied");

        field[i + 1][j + 1] = tileConf;
        stack.addLast(new UsedTile(tileConf, i, j));
        usedTiles.add(tileConf.getTile());
        currentTile++;
        return true;
    }

    public boolean removeLast() {

        if (frozen)
            return false;

        if (stack.isEmpty())
            return false;

        final UsedTile last = stack.pollLast();
        if (field[last.getI() + 1][last.getJ() + 1] == null)
            throw new IllegalStateException("field is empty");

        field[last.getI() + 1][last.getJ() + 1] = null;
        usedTiles.remove(last.getTileConfiguration().getTile());
        currentTile--;

        return true;
    }


    public String getTextRepresentation() {

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Tile IDs:");

        for (int i = 0; i < sideSize; i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < sideSize; j++) {

                final TileConfiguration t = field[i + 1][j + 1];
                stringBuilder.append(t == null ? "-   " : t.getTile().getId());
                stringBuilder.append("\t");

            }
        }
        stringBuilder.append("\nTile variants:");

        for (int i = 0; i < sideSize; i++) {
            stringBuilder.append("\n");
            for (int j = 0; j < sideSize; j++) {

                final TileConfiguration t = field[i + 1][j + 1];
                stringBuilder.append(t == null ? "-   " : t.getVariant());
                stringBuilder.append("\t");

            }
        }

        return stringBuilder.toString();
    }

    public char[][] assemble() {
        if (!allTilesUsed()) {
            throw new IllegalStateException("cannot assemble");
        }

        final int pieceSize = stack.get(0).getTileConfiguration().getTile().getGrid().length - 2;
        final int wholeSize = sideSize * pieceSize;
        char[][] result = new char[wholeSize][];
        for (int i = 0; i < wholeSize; i++) {
            result[i] = new char[wholeSize];
            for (int j = 0; j < wholeSize; j++) {
                result[i][j] = '.';
            }
        }

        for (UsedTile usedTile : stack) {


            log.info("Tile on ({},{}) has ID {} :\n{}",
                    usedTile.getI(), usedTile.getJ(), usedTile.getTileConfiguration().getTile().getId(),
                    usedTile.getTileConfiguration().gridAsText());

            for (int i = 0; i < pieceSize; i++) {
                for (int j = 0; j < pieceSize; j++) {

                    int pos_i = i + usedTile.getI() * pieceSize;
                    int pos_j = j + usedTile.getJ() * pieceSize;

                    result[pos_i][pos_j] = usedTile.getTileConfiguration().getGrid()[i + 1][j + 1];
                }
            }
        }

        return result;
    }


    public boolean allTilesUsed() {
        return this.usedTiles.size() >= this.tileCount;
    }


    public void freeze() {
        frozen = true;
    }
}
