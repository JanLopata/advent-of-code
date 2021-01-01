package cz.princu.adventofcode.year20.days;

import cz.princu.adventofcode.common.Day;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
public class Day21 extends Day {
    public static void main(String[] args) throws IOException {
        new Day21().printParts();
    }

    @Override
    public Object part1(String data) {

        String[] input = data.split("\n");

        Map<String, AtomicInteger> ingredientCounter = new HashMap<>();
        Set<String> ingredients = new HashSet<>();
        Map<String, List<Set<String>>> allergenRowsMap = new HashMap<>();

        parseInput(input, ingredientCounter, ingredients, allergenRowsMap);

        Map<String, Set<String>> possibleAllergenIngredients = assessPossibleAllergenIngredients(allergenRowsMap);

        clearifyAllergenIngredients(possibleAllergenIngredients);

        Map<String, String> ingredientsToAllergensMap = transformToInverseMap(possibleAllergenIngredients);

        Set<String> result = new HashSet<>(ingredients);
        result.removeAll(ingredientsToAllergensMap.keySet());

        return (long) result.stream().map(ingredientCounter::get).mapToInt(AtomicInteger::get).sum();

    }

    private Map<String, String> transformToInverseMap(Map<String, Set<String>> possibleAllergenIngredients) {
        Map<String, String> ingredientsToAllergensMap = new HashMap<>();

        for (Map.Entry<String, Set<String>> entry : possibleAllergenIngredients.entrySet()) {

            final String allergen = entry.getKey();
            if (entry.getValue().size() != 1)
                throw new IllegalStateException("do not know");

            String ingredient = entry.getValue().stream().findAny().get();
            ingredientsToAllergensMap.put(ingredient, allergen);
        }
        return ingredientsToAllergensMap;
    }

    private void clearifyAllergenIngredients(Map<String, Set<String>> possibleAllergenIngredients) {
        Set<String> ignored = new HashSet<>();
        Optional<String> target = getTarget(possibleAllergenIngredients, ignored);

        while (target.isPresent()) {

            final String ingredient = possibleAllergenIngredients.get(target.get()).stream().findAny().get();

            for (Map.Entry<String, Set<String>> entry : possibleAllergenIngredients.entrySet()) {

                if (entry.getKey().equals(target.get()))
                    continue;

                entry.getValue().remove(ingredient);
            }
            target = getTarget(possibleAllergenIngredients, ignored);
        }
    }

    private void parseInput(String[] input, Map<String, AtomicInteger> ingredientCounter, Set<String> ingredients, Map<String, List<Set<String>>> allergenRowsMap) {
        for (String row : input) {

            final String[] rowSplit = row.split("\\(contains ");
            Set<String> unknowns = parseUnknowns(rowSplit[0]);
            for (String unknown : unknowns) {
                ingredientCounter.putIfAbsent(unknown, new AtomicInteger(0));
                ingredientCounter.get(unknown).incrementAndGet();
            }

            ingredients.addAll(unknowns);

            Set<String> allergens;
            if (rowSplit.length > 1) {

                allergens = parseAllergens(rowSplit[1]);
            } else {
                throw new UnsupportedOperationException("do not know what to do");
            }

            for (String allergen : allergens) {

                allergenRowsMap.putIfAbsent(allergen, new ArrayList<>());
                allergenRowsMap.get(allergen).add(new HashSet<>(unknowns));

            }
        }
    }

    private Optional<String> getTarget(Map<String, Set<String>> possibleAllergenIngredients, Set<String> ignored) {
        final Optional<String> target = possibleAllergenIngredients.entrySet().stream()
                .filter(e -> !ignored.contains(e.getKey()))
                .filter(e -> e.getValue().size() == 1)
                .map(Map.Entry::getKey)
                .findAny();

        target.ifPresent(ignored::add);

        return target;
    }

    private Map<String, Set<String>> assessPossibleAllergenIngredients(Map<String, List<Set<String>>> allergenRowsMap) {
        Map<String, Set<String>> allergenPossibleIngredientsMap = new HashMap<>();

        for (Map.Entry<String, List<Set<String>>> allergenRows : allergenRowsMap.entrySet()) {

            final String allergen = allergenRows.getKey();

            final List<Set<String>> rows = allergenRows.getValue();
            Set<String> possibilities = new HashSet<>(rows.get(0));

            for (int i = 1; i < rows.size(); i++) {
                possibilities.retainAll(rows.get(i));
            }

            allergenPossibleIngredientsMap.put(allergen, possibilities);
        }
        return allergenPossibleIngredientsMap;
    }


    private Set<String> parseAllergens(String s) {
        return Arrays.stream(s.replace(")", "").split(", ")).collect(Collectors.toSet());
    }

    private Set<String> parseUnknowns(String s) {
        return Arrays.stream(s.split(" ")).collect(Collectors.toSet());
    }

    @Override
    public Object part2(String data) {

        String[] input = data.split("\n");

        Map<String, AtomicInteger> ingredientCounter = new HashMap<>();
        Set<String> ingredients = new HashSet<>();
        Map<String, List<Set<String>>> allergenRowsMap = new HashMap<>();

        parseInput(input, ingredientCounter, ingredients, allergenRowsMap);

        Map<String, Set<String>> possibleAllergenIngredients = assessPossibleAllergenIngredients(allergenRowsMap);

        clearifyAllergenIngredients(possibleAllergenIngredients);

        Map<String, String> ingredientsToAllergensMap = transformToInverseMap(possibleAllergenIngredients);

        return ingredientsToAllergensMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.joining(","));
    }


    @Override
    public int getDayNumber() {
        return 21;
    }

    @Override
    public int getYear() {
        return 2020;
    }
}
