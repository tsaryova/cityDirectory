package cityDirectory;

import cityDirectory.model.City;
import org.apache.commons.lang3.tuple.Pair;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class CityUtils {

    private static final int COUNT_COLUMN = 6;
    private static final String filePath = "src/main/resources/city_ru.csv";

    public static List<City> parse() throws IOException {
        ArrayList<City> cities = new ArrayList<>();

        try (Scanner input = new Scanner(new File(filePath));) {
            input.useDelimiter("\n");

            String name;
            String region;
            String district;
            int population;
            String foundation;

            while (input.hasNext()) {
                String[] dataCity = input.next().split(";");
                if (dataCity.length == COUNT_COLUMN){
                    name = dataCity[1];
                    region = dataCity[2];
                    district = dataCity[3];
                    population =  Integer.parseInt(dataCity[4]);
                    foundation = dataCity[5].replaceAll("(\\r|\\n)", "");
                    cities.add(new City(name, region, district, population, foundation));
                } else {
                    System.out.println("Not all fields are filled for city ID " + dataCity[0]);
                }

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return cities;
    }

    public static void print(List<City> cities) {
        cities.forEach(city -> {
            try {
                System.out.println(new ObjectAnalyzer().toString(city));
            } catch (ReflectiveOperationException e) {
                e.printStackTrace();
            }
        });
    }

    public static List<City> sortedByName(List<City> cities) {
        return cities
                .stream()
                .sorted(Comparator.nullsLast((city1, city2) -> city1.getName().compareToIgnoreCase(city2.getName())))
                .collect(Collectors.toList());
    }

    public static List<City> sortedByNameAndDistrict(List<City> cities) {
        return cities
                .stream()
                .sorted(Comparator.nullsLast((city1, city2) -> city1.getName().compareToIgnoreCase(city2.getName())))
                .sorted(Comparator.nullsLast((city1, city2) -> city1.getDistrict().compareToIgnoreCase(city2.getDistrict())))
                .collect(Collectors.toList());
    }

    public static int findIndexCityWithMaxCountPopulation(City[] cities) {
        int maxPopulation = 0;
        int indexCity = 0;
        for (int i = 0; i < cities.length; i++) {
            int currentPopulation = cities[i].getPopulation();
            if (maxPopulation < currentPopulation) {
                maxPopulation = currentPopulation;
                indexCity = i;
            }
        }

        return indexCity;
    }

    public static void findMaxPopulation(List<City> cities) {
        City city = cities.stream().max(Comparator.comparing(City::getPopulation)).get();
        int index = cities.indexOf(city);
        System.out.println("[" + index + "] = " + city.getPopulation());
    }

    public static Map<String, List<City>> groupingByRegion(List<City> cities) {
        return cities.stream().collect(Collectors.groupingBy(City::getRegion));
    }

}
