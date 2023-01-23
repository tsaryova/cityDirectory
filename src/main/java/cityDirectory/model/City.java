package cityDirectory.model;

import lombok.Getter;
import lombok.Setter;

public class City {

    @Getter
    @Setter
    private String name;

    @Getter
    @Setter
    private String region;

    @Getter
    @Setter
    private String district;

    @Getter
    @Setter
    private int population;

    @Getter
    @Setter
    private String foundation;

    public City(String name, String region, String district, int population, String foundation) {
        this.name = name;
        this.region = region;
        this.district = district;
        this.population = population;
        this.foundation = foundation;
    }

}
