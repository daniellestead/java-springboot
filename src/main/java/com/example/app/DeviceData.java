package com.example.app;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DeviceData {
    private final Map<UUID, Device> deviceMap = new HashMap<>();
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public DeviceData() {
        deviceMap.put(UUID.randomUUID(), new Device("123", "Lights", "off", "2022-10-25 12:00:00"));
        deviceMap.put(UUID.randomUUID(), new Device("456", "Television", "on", "2022-10-25 12:00:00"));
        deviceMap.put(UUID.randomUUID(), new Device("789", "Toaster", "off", "2022-10-25 12:00:00"));
    }

    public List<Device> getDevice(String id, int states) {
        return deviceMap.values().stream()
                .filter(d -> d.getId().equals(id))
                .sorted(Comparator.comparing(Device::getLastUpdated).reversed())
                .limit(states)
                .collect(Collectors.toList());
    }

    public Device putDevice(Device device) {
        device.setLastUpdated(FORMATTER.format(LocalDateTime.now()));
        deviceMap.put(UUID.randomUUID(), device);
        return device;
    }
}
