package com.crazypumpkin.versatilerecyclerview.citypicker;

import java.util.ArrayList;
import java.util.List;

/**
 * 城市
 * Created by CrazyPumPkin on 2016/12/13.
 */

public class City {

    private String areaName;

    private List<County> counties = new ArrayList<>();

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public List<County> getCounties() {
        return counties;
    }

    public void setCounties(List<County> counties) {
        this.counties = counties;
    }
}
