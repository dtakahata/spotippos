package br.com.spotippos.service;

import java.util.List;

import br.com.spotippos.jpa.model.Province;

public interface ProvinceService {

    Province save(Province property);

    List<Province> findAll();
    
    void deleteAll();

    List<String> findProvincesByXY(int x, int y);
}
