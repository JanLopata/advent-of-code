package cz.princu.adventofcode.year21;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

@Slf4j
public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, InvocationTargetException, NoSuchMethodException {
        for (int day = 1; day <= 5; day++) {
            String dayString = String.format("%02d", day);
            log.info("Day {}:", dayString);
            Day instance = (Day) Class.forName("cz.princu.adventofcode.year21.days.Day" + dayString).getDeclaredConstructor().newInstance();
            instance.printParts();
            log.info("Day {} --- END\n", dayString);
        }
    }
}
