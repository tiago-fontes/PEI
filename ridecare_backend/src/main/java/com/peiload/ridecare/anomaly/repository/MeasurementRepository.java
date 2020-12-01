package com.peiload.ridecare.anomaly.repository;


import com.peiload.ridecare.anomaly.model.Measurement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Integer> {


}
