package br.com.spotippos.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.spotippos.jpa.model.Province;
import br.com.spotippos.utils.ProvinceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional(propagation = Propagation.NOT_SUPPORTED)
public class ProvinceRepositoryTest {

    @Before
    public void before() {
        after();
    }

    @After
    public void after() {
        provinceRepository.deleteAll();
    }

    @Autowired
    ProvinceRepository provinceRepository;

    @Test
    public void findsAll() {
        List<Province> provincesInsert = (List<Province>) this.provinceRepository
                .save(ProvinceUtils.createProvinces(6));
        List<Province> provinces = (List<Province>) this.provinceRepository.findAll();
        validateProvinces(6, provinces);
        assertThat(provinces).containsAll(provincesInsert);
    }

    @Test
    public void findsByXY() {
        Province province = this.provinceRepository.save(ProvinceUtils.createProvince()
                .withBottomRightX(600)
                .withBottomRightY(500)
                .withUpperLeftX(0)
                .withUpperLeftY(1000));
        validateProvincesByXY(province.getBottomRightX(), province.getBottomRightY(), 1);
    }

    @Test
    public void findsByXYWithTwoProvinces() {
        this.provinceRepository.save(ProvinceUtils.createProvince()
                .withBottomRightX(600)
                .withBottomRightY(500)
                .withUpperLeftX(0)
                .withUpperLeftY(1000));
        this.provinceRepository.save(ProvinceUtils.createProvince()
                .withBottomRightX(1100)
                .withBottomRightY(500)
                .withUpperLeftX(400)
                .withUpperLeftY(1000));
        validateProvincesByXY(500, 550, 2);
    }

    private void validateProvincesByXY(int x, int y, int quantities) {
        List<String> provinces = this.provinceRepository.findProvincesByXY(x, y);
        validateProvinces(quantities, provinces);
    }

    private void validateProvinces(int quantities, List<?> provinces) {
        assertThat(provinces).hasSize(quantities);
        assertThat(provinces).doesNotContainNull();
    }
}
