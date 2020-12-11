package com.peiload.ridecare.statusHistory.repository;

import com.peiload.ridecare.statusHistory.model.StatusHistory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatusHistoryRepository extends CrudRepository<StatusHistory, Integer> {
}
