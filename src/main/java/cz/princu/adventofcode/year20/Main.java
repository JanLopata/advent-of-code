package cz.princu.adventofcode.year20;

import cz.princu.adventofcode.common.Day;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException, ClassNotFoundException, IOException, InvocationTargetException, NoSuchMethodException {
        for (int day = 1; day <= 1; day++) {
            System.out.println("Day " + day + ":");
            Day instance = (Day) Class.forName("cz.princu.adventofcode.year20.days.Day" + day).getDeclaredConstructor().newInstance();
            instance.printParts();
            System.out.println();
        }
    }
}
