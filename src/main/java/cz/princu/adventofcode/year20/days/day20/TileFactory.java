package cz.princu.adventofcode.year20.days.day20;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class TileFactory {

    private final List<SideSearch> sideSearchList;
    private final List<Tile> allTiles;
    @Getter
    private final List<TileConfiguration> tileConfigurationList;

    public static TileFactory init(List<Tile> allAvailableTiles) {

        List<TileConfiguration> tileConfigurationList = new ArrayList<>();

        List<SideSearch> sideSearchList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            sideSearchList.add(new SideSearch());
        }

        List<Boolean> booleans = Stream.of(Boolean.TRUE, Boolean.FALSE).collect(Collectors.toList());

        for (Tile tile : allAvailableTiles) {
            for (boolean flip : booleans) {
                for (int variant = 0; variant < 4; variant++) {

                    final TileConfiguration tileInConf = new TileConfiguration(tile, variant, flip);
                    tileConfigurationList.add(tileInConf);

                    for (int side = 0; side < 4; side++) {
                        final String sideString = tileInConf.getSide(side);

                        sideSearchList.get(side).addConfiguration(sideString, tileInConf);
                    }

                }
            }
        }

        return new TileFactory(sideSearchList,
                new ArrayList<>(allAvailableTiles),
                tileConfigurationList);

    }

    public Set<TileConfiguration> getPossibleTiles(String sideString, int side) {

        if (sideString == null)
            return new HashSet<>(tileConfigurationList);

        return new HashSet<>(sideSearchList.get(side).getConfiguration(sideString));

    }

}
