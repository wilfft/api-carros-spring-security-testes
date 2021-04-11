package com.example.lightingmcqueenecommerce;


import com.example.lightingmcqueenecommerce.api.domain.Car;
import com.example.lightingmcqueenecommerce.api.domain.dto.CarDTO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = LightingMcqueenEcommerceApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CarsApiTest {
    @Autowired
    protected TestRestTemplate rest;

    private ResponseEntity<CarDTO> getCar(String url) {
        return rest.getForEntity(url, CarDTO.class);
    }

    private ResponseEntity<List<CarDTO>> getCars(String url) {
        return rest.exchange(
                url,
                HttpMethod.GET,
                null,
                new ParameterizedTypeReference<List<CarDTO>>() {
                });
    }

    @Test
    public void testList() {
        List<CarDTO> cars = getCars("/api/v1/carros").getBody();
        assertNotNull(cars);
        assertEquals(30, cars.size());
    }

    @Test
    public void TestListByType(String url) {
        assertEquals(10, getCars("/api/v1/tipo/classicos").getBody().size());
        assertEquals(10, getCars("/api/v1/tipo/esportivos").getBody().size());
        assertEquals(10, getCars("/api/v1/tipo/luxo").getBody().size());

        assertEquals(HttpStatus.NO_CONTENT, getCars("/api/v1/tipo/xxx").getStatusCode());
    }

    @Test
    public void testGetCar() {
        ResponseEntity<CarDTO> responseEntity = getCar("/api/v1/carro/11");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.OK);

        CarDTO car = responseEntity.getBody();
        assertEquals("Ferrari FF", car.getName());
    }

    @Test
    public void testGetCarNotFound() {
        ResponseEntity responseEntity = getCar("/api/v1/carro/1111");
        assertEquals(responseEntity.getStatusCode(), HttpStatus.NOT_FOUND);
    }

    @Test
    public void testSave() {
        Car car = new Car();
        car.setName("Porshe");
        car.setType("esportivos");

        //insert car
        ResponseEntity response = rest.postForEntity("/api/v1/carros", car, null);
        System.out.println(response);

        //verify if was add
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        //search for object
        String location = response.getHeaders().get("location").get(0);
        CarDTO carDTO = getCar(location).getBody();

        assertNotNull(carDTO);
        assertEquals("Porshe", carDTO.getName());
        assertEquals("esportivos", car.getType());

        //Delete object
        rest.delete(location);

        //Verfivy if was removed
        assertEquals(HttpStatus.NOT_FOUND, getCar(location).getStatusCode());


    }


}
