package com.example.lightingmcqueenecommerce;

import com.example.lightingmcqueenecommerce.api.domain.Car;
import com.example.lightingmcqueenecommerce.api.domain.dto.CarDTO;
import com.example.lightingmcqueenecommerce.api.exception.ObjectNotFoundException;
import com.example.lightingmcqueenecommerce.api.service.CarsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.Assert.*;


//@RunWith(SpringRunner.class)
@SpringBootTest
public class LightingMcqueenEcommerceApplicationTests {


    @Autowired
    private CarsService service;


    @Test
    public void testInsert() {
        Car car = new Car();
        car.setName("Ferrari");
        car.setType("Esportivo");

        CarDTO carDTO = service.insert(car);
        assertNotNull(carDTO);

        Long id = carDTO.getId();
        assertNotNull(id);

        //Buscar o objeto
        carDTO = service.getCarById(id);
        assertNotNull(carDTO);

        assertEquals("Ferrari", carDTO.getName());

        assertEquals("Esportivo", carDTO.getType());

        //Deletar o objeto
        service.delete(id);

        //Verficar se Deletou
        try {
            assertNull(service.getCarById(id));

            fail("O carro não foi excluido");
        } catch (ObjectNotFoundException e) {
            //OK
        }


    }

    @Test
    public void testList() {
        List<CarDTO> cars = service.getAllCars();
        assertTrue(cars.size() == 30);
    }

    @Test
    public void testGet() {
        CarDTO carDTO = service.getCarById(11L);
        assertNotNull(carDTO);
        assertEquals("Ferrari FF", carDTO.getName());
    }

    @Test
    public void testListType() {
        assertEquals(10, service.getCarByTipo("classicos").size());
        assertEquals(10, service.getCarByTipo("esportivos").size());
        assertEquals(10, service.getCarByTipo("luxo").size());
        assertEquals(0, service.getCarByTipo("x").size());

    }

}
