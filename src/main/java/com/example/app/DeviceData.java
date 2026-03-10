package com.example.app;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class DeviceData {
    private final Map<UUID, Device> deviceMap = new HashMap<>();
    private static final DeviceData instance = new DeviceData();

    public static DeviceData getInstance() {
        return instance;
    }

    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // Private constructor so people know to use the getInstance() function instead
    private DeviceData() {
        deviceMap.put(UUID.randomUUID(), new Device("123", "Lights", "off", "2022-10-25 12:00:00"));
        deviceMap.put(UUID.randomUUID(), new Device("456", "Television", "on", "2022-10-25 12:00:00"));
        deviceMap.put(UUID.randomUUID(), new Device("789", "Toaster", "off", "2022-10-25 12:00:00"));
    }

    public ArrayList<Device> getDevice(String id, Integer states) throws ParseException {
        // Find all entries in map with the id and put them into a tree map to sort by
        // date.
        SortedMap<Date, Device> sorted = new TreeMap<>(Collections.reverseOrder());
        for (Device device : deviceMap.values()) {
            if (device.getId().equals(id)) {
                Date date = sdf.parse(device.getLastUpdated());
                sorted.put(date, device);
            }
        }
        // First n entries in sorted set.
        SortedMap<Date, Device> sortedLimit = sorted.entrySet()
                .stream()
                .limit(states)
                .collect(TreeMap::new, (m, e) -> m.put(e.getKey(), e.getValue()), Map::putAll);
        return new ArrayList<>(sortedLimit.values());
    }

    public Device putDevice(Device device) {
        // The UUID will be distinct for each update to the database, this means we can
        // dedupe
        // on device ID and last updated timestamp.
        UUID uuid = UUID.randomUUID();
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String now = sdf.format(timestamp);
        device.setLastUpdated(now);
        deviceMap.put(uuid, device);
        return device;
    }

}
