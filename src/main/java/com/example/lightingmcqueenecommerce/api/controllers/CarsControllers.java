package com.example.lightingmcqueenecommerce.api.controllers;


import com.example.lightingmcqueenecommerce.api.domain.Car;
import com.example.lightingmcqueenecommerce.api.domain.dto.CarDTO;
import com.example.lightingmcqueenecommerce.api.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/carros/")
public class CarsControllers {


    @Autowired
    private CarsService carsService;


    @GetMapping()
    public ResponseEntity<List<CarDTO>> getAllCars() {
        return ResponseEntity.ok(carsService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity getCarById(@PathVariable Long id) {
        return ResponseEntity.ok(carsService.getCarById(id));
    }

    @GetMapping("/tipo/{tipo}")
    public ResponseEntity<List<CarDTO>> getCarsByTipo(@PathVariable("tipo") String type) {
        List<CarDTO> cars = carsService.getCarByTipo(type);
        return cars
                .isEmpty()
                ? ResponseEntity.noContent().build()
                : ResponseEntity.ok(cars);
    }

    @PostMapping()
    public ResponseEntity createBy(@RequestBody Car car) {
        CarDTO carDTO = carsService.insert(car);

        URI location = getUri(car.getId());
        return ResponseEntity.created(location).build();

    }

    private URI getUri(Long id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(id).toUri();
    }

    @PutMapping("{id}")
    public ResponseEntity<CarDTO> updateBy(@RequestBody Car car, @PathVariable("id") Long id) {
        car.setId(id);

        CarDTO carDTO = carsService.update(car, id);

        return carDTO != null ? ResponseEntity.ok(carDTO) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteBy(@PathVariable("id") Long id) {
        carsService.delete(id);
        return ResponseEntity.ok().build();

    }
}
