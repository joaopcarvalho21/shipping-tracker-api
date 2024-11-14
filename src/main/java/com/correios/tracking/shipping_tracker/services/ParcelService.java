package com.correios.tracking.shipping_tracker.services;

import com.correios.tracking.shipping_tracker.entities.Parcel;
import com.correios.tracking.shipping_tracker.exceptions.TrackingNumberNotFoundException;
import com.correios.tracking.shipping_tracker.exceptions.ParcelValidationException;
import com.correios.tracking.shipping_tracker.repositories.ParcelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ParcelService {

    @Autowired
    private ParcelRepository repository;

    public Parcel getParcelByTrackingNumber(String trackingNumber) {
        return repository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new TrackingNumberNotFoundException(trackingNumber));
    }

    public Parcel saveParcel(Parcel parcel) {
        if (parcel.getTrackingNumber() == null || parcel.getTrackingNumber().isEmpty()) {
            throw new ParcelValidationException("Tracking number cannot be null or empty");
        }
        return repository.save(parcel);
    }

    public Parcel updateParcelStatus(String trackingNumber, String status) {
        Parcel parcel = repository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new TrackingNumberNotFoundException(trackingNumber));

        if (status == null || status.isEmpty()) {
            throw new ParcelValidationException("Status cannot be null or empty");
        }

        parcel.setStatus(status);
        return repository.save(parcel);
    }

    public void deleteParcel(String trackingNumber) {
        Parcel parcel = repository.findByTrackingNumber(trackingNumber)
                .orElseThrow(() -> new TrackingNumberNotFoundException(trackingNumber));
        repository.delete(parcel);
    }

    public List<Parcel> getAllParcels() {
        return repository.findAll();
    }
}