package com.example.app;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.ArrayList;

@RestController
public class DeviceController {
    // GET endpoint that returns device data.
    @GetMapping(value = "/getDevice")
    public ArrayList<Device> getDevice(
            @RequestParam(value = "id", defaultValue = "0") String id,
            @RequestParam(value = "states", defaultValue = "1") Integer states) throws ParseException {
        return DeviceData.getInstance().getDevice(id, states);
    }

    // POST endpoint that upserts device data.
    @PostMapping(value = "/updateDevice", consumes = "application/json", produces = "application/json")
    public Device updateDevice(@RequestBody Device device, HttpServletResponse response) {
        response.setHeader("Location", ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/getDevice/" + device.getId()).toUriString());
        return DeviceData.getInstance().putDevice(device);
    }
}
