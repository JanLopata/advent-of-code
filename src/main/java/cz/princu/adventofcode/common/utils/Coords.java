package cz.princu.adventofcode.common.utils;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@AllArgsConstructor
@Getter
@ToString
@EqualsAndHashCode
public class Coords {

    final int i;
    final int j;

    public Coords plus(int i, int j) {
        return new Coords(this.i + i, this.j + j);
    }

    public Coords plus(Coords another) {
        return new Coords(this.i + another.i, this.j + another.j);
    }

}
