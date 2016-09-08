package br.com.spotippos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import br.com.spotippos.jpa.model.Property;

public interface PropertyRepository extends CrudRepository<Property, String> {

    Property findOneById(int id);

    @Query("select prop from Property prop where (prop.latitude between ?1 and ?3) and (prop.longitude between ?4 and ?2)")
    List<Property> findPropertiesByCordinates(int ax, int ay, int bx, int by);

}
