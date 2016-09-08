package br.com.spotippos.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.spotippos.jpa.model.Province;
import br.com.spotippos.repository.ProvinceRepository;

@Service
public class ProvinceServiceImpl implements ProvinceService {

    @Autowired
    private ProvinceRepository provinceRepository;

    @Override
    public Province save(Province property) {
        return provinceRepository.save(property);
    }

    @Override
    public List<Province> findAll() {
        return (List<Province>) provinceRepository.findAll();
    }

    @Override
    public List<String> findProvincesByXY(int x, int y) {
        return provinceRepository.findProvincesByXY(x, y);
    }

    @Override
    public void deleteAll() {
        provinceRepository.deleteAll();
    }

}
