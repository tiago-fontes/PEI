package com.peiload.ridecare.car.repository;

import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
    List<Car> findAll();
    Optional<Car> findByLicensePlate(String licensePlate);
    List<Car> findAllByUser(User user);
    List<Car> findAllByUserAndStatus(User user, String status);
}
