package com.correios.tracking.shipping_tracker.controllers;

import com.correios.tracking.shipping_tracker.entities.Parcel;
import com.correios.tracking.shipping_tracker.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/parcels")
public class ParcelController {

    @Autowired
    private ParcelService service;

    @GetMapping("/{trackingNumber}")
    public Parcel getParcelByTrackingNumber(@PathVariable String trackingNumber) {
        return service.getParcelByTrackingNumber(trackingNumber);
    }

    @PostMapping
    public Parcel createParcel(@RequestBody Parcel parcel) {
        return service.saveParcel(parcel);
    }

    // Endpoint para atualizar o status
    @PutMapping("/{trackingNumber}/status")
    public Parcel updateParcelStatus(@PathVariable String trackingNumber, @RequestBody String status) {
        return service.updateParcelStatus(trackingNumber, status);
    }

    // Endpoint para deletar um pacote
    @DeleteMapping("/{trackingNumber}")
    public void deleteParcel(@PathVariable String trackingNumber) {
        service.deleteParcel(trackingNumber);
    }

    // Endpoint para listar todos os pacotes
    @GetMapping
    public List<Parcel> getAllParcels() {
        return service.getAllParcels();
    }
}