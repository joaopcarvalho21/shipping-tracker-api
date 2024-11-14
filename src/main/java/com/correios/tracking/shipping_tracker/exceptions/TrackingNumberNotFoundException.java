package com.correios.tracking.shipping_tracker.exceptions;

public class TrackingNumberNotFoundException extends RuntimeException {

    public TrackingNumberNotFoundException(String trackingNumber) {
        super("Tracking number " + trackingNumber + " not found");
    }
}