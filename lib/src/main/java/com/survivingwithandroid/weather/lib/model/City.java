/**
 * This is a tutorial source code 
 * provided "as is" and without warranties.
 *
 * For any question please visit the web site
 * http://www.survivingwithandroid.com
 *
 * or write an email to
 * survivingwithandroid@gmail.com
 *
 */
package com.survivingwithandroid.weather.lib.model;

/**
 * This class represents the a City as returned during the search process. The search can be done using name pattern
 * or using geographic location. The weather provider used will create the instance of city
 */
public class City {

    /*
    * Unique city identfier
    * */
	private String id;

    /**
     * City name
     */
    private String name;

    /*
    * Country name
    * */
    private String country;

    /*
    * region
    * */
    private String region;

    public City() {}

	public City(String id, String name, String region, String country) {
		super();
		this.id = id;
		this.name = name;
        this.region = region;
		this.country = country;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }
}
