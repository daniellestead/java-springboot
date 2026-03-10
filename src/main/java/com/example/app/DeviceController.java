package com.example.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class DeviceController {

    @Autowired
    private DeviceData deviceData;

    @GetMapping(value = "/getDevice")
    public ResponseEntity<List<Device>> getDevice(
            @RequestParam("id") String id,
            @RequestParam(value = "states", defaultValue = "1") int states) {
        List<Device> devices = deviceData.getDevice(id, states);
        if (devices.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(devices);
    }

    @PostMapping(value = "/updateDevice", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Device> updateDevice(@RequestBody Device device) {
        Device saved = deviceData.putDevice(device);
        URI location = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/getDevice").queryParam("id", saved.getId()).build().toUri();
        return ResponseEntity.created(location).body(saved);
    }
}
