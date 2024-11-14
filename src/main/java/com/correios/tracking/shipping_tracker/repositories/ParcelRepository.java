package com.correios.tracking.shipping_tracker.repositories;

import com.correios.tracking.shipping_tracker.entities.Parcel;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ParcelRepository extends JpaRepository<Parcel, Long> {
    Optional<Parcel> findByTrackingNumber(String trackingNumber);
}