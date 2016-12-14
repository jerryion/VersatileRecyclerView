package com.crazypumpkin.versatilerecyclerview.citypicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 省
 * Created by CrazyPumPkin on 2016/12/13.
 */

public class Province {

    private String areaName;

    private List<City> cities = new ArrayList<>();

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
