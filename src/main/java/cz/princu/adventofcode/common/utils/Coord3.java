package cz.princu.adventofcode.common.utils;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@RequiredArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class Coord3 {

    private final int x;
    private final int y;
    private final int z;

    public Coord3 plus(Coord3 another) {
        return new Coord3(x + another.x, y + another.y, z + another.z);
    }

    public Coord3 minus(Coord3 another) {
        return new Coord3(x - another.x, y - another.y, z - another.z);
    }

    public static Coord3 parse(String string) {

        var split = string.split(",");
        return new Coord3(
                Integer.parseInt(split[0]),
                Integer.parseInt(split[1]),
                Integer.parseInt(split[2])
        );

    }

    public static Coord3 of(int x, int y, int z) {
        return new Coord3(x, y, z);
    }

    public long magnitude() {

        return (long) x * x + (long) y * y + (long) z * z;

    }

}
