package com.peiload.ridecare.Anomaly.repository;

import com.peiload.ridecare.Anomaly.model.Anomaly;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnomalyRepository extends CrudRepository<Anomaly, Integer> {
}
