package cz.princu.adventofcode.common.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class DataArrayUtils {

    public static int[][] parseDataArray(String[] input) {
        int[][] dataArray = new int[input.length][input[0].length()];
        for (int i = 0; i < input.length; i++) {
            var split = input[i].split("");
            for (int j = 0; j < split.length; j++) {
                dataArray[i][j] = Integer.parseInt(split[j]);
            }
        }
        return dataArray;
    }

}
