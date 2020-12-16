package com.peiload.ridecare.car.repository;

import com.peiload.ridecare.car.model.StatusHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusHistoryRepository extends CrudRepository<StatusHistory, Integer> {



}
