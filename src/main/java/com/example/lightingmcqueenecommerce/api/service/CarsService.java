package com.example.lightingmcqueenecommerce.api.service;

import com.example.lightingmcqueenecommerce.api.domain.Car;
import com.example.lightingmcqueenecommerce.api.domain.dto.CarDTO;
import com.example.lightingmcqueenecommerce.api.exception.ObjectNotFoundException;
import com.example.lightingmcqueenecommerce.api.repository.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CarsService {

    @Autowired
    CarRepository repository;


    public List<CarDTO> getAllCars() {
        return repository.findAll().stream().map(CarDTO::create).collect(Collectors.toList());
    }

    public List<CarDTO> getCarByTipo(String type) {
        return repository.findByType(type).stream().map(CarDTO::create).collect(Collectors.toList());
    }

    public CarDTO getCarById(Long id) {
        Optional<Car> optional = repository.findById(id);
        return optional.map(CarDTO::create)
                .orElseThrow(() -> new ObjectNotFoundException("Carro não encontrado"));
    }

    public CarDTO insert(Car car) {
        Assert.isNull(car.getId(), "Não foi possivel inserir o registro");

        return CarDTO.create(repository.save(car));

    }

    public CarDTO update(Car car, Long id) {
        Assert.notNull(id, "informe o id do carro");
        Optional<Car> optionalCar = repository.findById(id);

        if (optionalCar.isPresent()) {
            Car db = optionalCar.get();
            db.setName(car.getName());
            db.setType(car.getType());
            repository.save(db);
            return CarDTO.create(db);
        } else {
            return null;
        }

    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
