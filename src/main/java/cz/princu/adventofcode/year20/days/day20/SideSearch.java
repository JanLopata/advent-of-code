package cz.princu.adventofcode.year20.days.day20;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SideSearch {

    private final Map<String, List<TileConfiguration>> configurationMap = new HashMap<>();

    public void addConfiguration(String key, TileConfiguration tileConfiguration) {

        configurationMap.putIfAbsent(key, new ArrayList<>());
        configurationMap.get(key).add(tileConfiguration);

    }

    public List<TileConfiguration> getConfiguration(String key) {

        return configurationMap.getOrDefault(key, new ArrayList<>());
    }

}
