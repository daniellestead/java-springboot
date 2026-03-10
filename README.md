# Device API — Spring Boot Demo

A basic REST API built with Spring Boot that tracks configuration states of IoT connected devices. Useful as a reference for Spring Boot project structure, REST controllers, and in-memory data management.

## Features

- `GET /getDevice` — retrieve the current (and optionally previous) configuration state(s) for a device
- `POST /updateDevice` — add a new configuration state for a device
- In-memory storage with three pre-loaded sample devices (IDs: `123`, `456`, `789`)

## Running locally

```bash
./gradlew bootRun
```

The service starts at `http://localhost:8080`.

## Running tests

```bash
./gradlew test
```

## Device JSON format

```json
{
  "id": "123",
  "name": "Lights",
  "status": "off",
  "lastUpdated": "2022-10-25 12:00:00"
}
```

## Endpoints

### GET /getDevice

Returns the most recent configuration state(s) for a given device ID.

| Parameter | Required | Default | Description                                               |
| --------- | -------- | ------- | --------------------------------------------------------- |
| `id`      | Yes      | —       | Device ID                                                 |
| `states`  | No       | `1`     | Number of historical states to return (most recent first) |

**Example:**

```text
GET http://localhost:8080/getDevice?id=123&states=2
```

Returns an array of up to 2 states for device `123`, sorted newest first.

### POST /updateDevice

Adds a new configuration state for a device. The `lastUpdated` field is set automatically by the server.

**Example:**

```http
POST http://localhost:8080/updateDevice
Content-Type: application/json

{
  "id": "123",
  "name": "Lights",
  "status": "on"
}
```

Returns the saved device object with the server-assigned `lastUpdated` timestamp. The `Location` response header points to the GET URL for the device.

## Project structure

```text
src/
└── main/
│   └── java/com/example/app/
│       ├── Application.java   # Spring Boot entry point
│       ├── Device.java            # Data model
│       ├── DeviceData.java        # In-memory data store (singleton)
│       └── DeviceController.java  # REST controller
└── test/
    └── java/com/example/app/
        └── DeviceControllerTest.java
```

## Known limitations

- Storage is in-memory only — data resets on restart.
- Two updates within the same second share a timestamp; only the most recent will be returned by `getDevice`.
