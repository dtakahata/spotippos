package br.com.spotippos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.spotippos.jpa.model.Province;

public interface ProvinceRepository extends CrudRepository<Province, String> {

    @Query("select province.name from Province province where province.upperLeftX <= ?1 and province.upperLeftY >= ?2 and province.bottomRightX >= ?1 and province.bottomRightY <= ?2")
    List<String> findProvincesByXY(int x, int y);

}
