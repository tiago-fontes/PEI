package com.peiload.ridecare.anomaly.repository;

import com.peiload.ridecare.anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.anomaly.model.Anomaly;
import com.peiload.ridecare.car.model.Car;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnomalyRepository extends CrudRepository<Anomaly, Integer> {

    List<Anomaly> findAll();
    List<AnomalyShowDto> findAllByViewedAndCarIn(Boolean viewed, List<Car> car);
    List<AnomalyShowDto> findAllByViewedAndCar(Boolean viewed, Car car);

}
