package cz.princu.adventofcode.year20.days.day20;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;


@RequiredArgsConstructor
@ToString
public class TileConfiguration {

    @Getter
    private final Tile tile;
    @Getter
    private final int variant;
    @Getter
    private final boolean flip;
    private final String[] cache = new String[4];

    public char[][] getGrid() {

        if (!flip) {
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
        } else {

            if (variant == 0)
                return tile.getGridF();

            if (variant == 1)
                return tile.getV1F();

            if (variant == 2) {
                return tile.getV2F();
            }

            if (variant == 3) {
                return tile.getV3F();
            }
        }

        throw new IllegalArgumentException("unknown variant");
    }

    public String gridAsText() {

        StringBuilder stringBuilder = new StringBuilder();

        final char[][] grid = getGrid();
        for (char[] chars : grid) {
            stringBuilder.append("\n");
            for (char aChar : chars) {
                stringBuilder.append(aChar);
            }
        }

        stringBuilder.replace(0, 1, "");
        return stringBuilder.toString();
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
