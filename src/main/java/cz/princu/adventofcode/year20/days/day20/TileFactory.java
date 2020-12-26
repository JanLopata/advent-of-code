package cz.princu.adventofcode.year20.days.day20;

import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TileFactory {

    private final List<SideSearch> sideSearchList;
    private final List<Tile> allTiles;
    private final List<TileConfiguration> tileConfigurationList;

    public static TileFactory init(List<Tile> allAvailableTiles) {

        List<TileConfiguration> tileConfigurationList = new ArrayList<>();

        List<SideSearch> sideSearchList = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            sideSearchList.add(new SideSearch());
        }

        for (Tile tile : allAvailableTiles) {

            for (int variant = 0; variant < 4; variant++) {

                final TileConfiguration tileInConf = new TileConfiguration(tile, variant);
                tileConfigurationList.add(tileInConf);

                for (int side = 0; side < 4; side++) {
                    final String sideString = tileInConf.getSide(side);

                    sideSearchList.get(side).addConfiguration(sideString, tileInConf);
                }

            }
        }

        return new TileFactory(sideSearchList, allAvailableTiles, tileConfigurationList);

    }

    public List<TileConfiguration> getPossibleTiles(String sideString, int side) {

        return sideSearchList.get(side).getConfiguration(sideString);

    }

}
