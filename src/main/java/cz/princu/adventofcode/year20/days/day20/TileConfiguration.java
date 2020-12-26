package cz.princu.adventofcode.year20.days.day20;

import lombok.Getter;
import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
public class TileConfiguration {

    @Getter
    private final Tile tile;
    @Getter
    private final int variant;
    private final String[] cache = new String[4];

    char[][] getGrid() {

        if (variant == 0)
            return tile.getGrid();

        if (variant == 1)
            return tile.getV1();

        if (variant == 2) {
            return tile.getV2();
        }

        if (variant == 3) {
            return tile.getV3();
        }

        throw new IllegalArgumentException("unknown variant");
    }

    /**
     * get side as a String, from left to right, from top to bottom
     *
     * @param side
     * @return
     */
    public String getSide(int side) {

        if (cache[side] == null) {
            final char[][] grid = this.getGrid();
            cache[side] = getSideFromGrid(grid, side);
        }

        return cache[side];
    }

    private static String getSideFromGrid(char[][] grid, int side) {

        StringBuilder sb = new StringBuilder();

        if (side == 0) {
            for (char c : grid[0]) {
                sb.append(c);
            }
            return sb.toString();
        }

        if (side == 2) {
            for (char c : grid[grid.length - 1]) {
                sb.append(c);
            }
            return sb.toString();
        }

        if (side == 1) {
            for (char[] row : grid) {
                sb.append(row[row.length - 1]);
            }
            return sb.toString();
        }

        if (side == 3) {
            for (char[] row : grid) {
                sb.append(row[0]);
            }
            return sb.toString();
        }

        throw new IllegalArgumentException("unknown side");

    }


}
