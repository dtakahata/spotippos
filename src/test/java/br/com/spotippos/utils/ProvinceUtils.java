package br.com.spotippos.utils;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.RandomUtils;

import br.com.spotippos.jpa.model.Province;

public class ProvinceUtils {

    public static List<Province> createProvinces(int x) {
        List<Province> provinces = new ArrayList<>();
        for (int i = 0; i < x; i++) {
            provinces.add(createProvince());
        }
        return provinces;
    }

    public static Province createProvince() {
        Province province = new Province()
                .withName("name")
                .withBottomRightX(RandomUtils.nextInt(1400))
                .withBottomRightY(RandomUtils.nextInt(1000))
                .withUpperLeftX(RandomUtils.nextInt(1400))
                .withUpperLeftY(RandomUtils.nextInt(1000));
        return province;
    }

}
