package com.peiload.ridecare.Anomaly.repository;

import com.peiload.ridecare.Anomaly.dto.AnomalyShowDto;
import com.peiload.ridecare.Anomaly.model.Anomaly;
import com.peiload.ridecare.car.model.Car;
import com.peiload.ridecare.user.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnomalyRepository extends CrudRepository<Anomaly, Integer> {

    List<AnomalyShowDto> findAllByViewedAndCarIn(Boolean viewed, List<Car> car);
    List<AnomalyShowDto> findAllByViewedAndCar(Boolean viewed, Car car);

}
