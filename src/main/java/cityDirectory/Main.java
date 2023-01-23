package cityDirectory;

import cityDirectory.model.City;

import java.io.IOException;
import java.util.List;
import java.util.Map;


public class Main {

    public static void main(String[] args) throws IOException {
        printCountCitiesGroupingByRegion();
    }

    private static void printCountCitiesGroupingByRegion() throws IOException{
        Map<String, List<City>> countCitiesGroupingByRegion = CityUtils.groupingByRegion(CityUtils.parse());
        countCitiesGroupingByRegion.forEach((k, v) -> System.out.println(k + " - " + v.size()));
    }

}
