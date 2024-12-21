# WeatherApp

WeatherApp is an Android application that provides real-time weather updates for any city using the OpenWeatherMap API. The app includes features like temperature, humidity, wind speed, sunrise/sunset times, and current weather conditions.

## Features
- Search for weather by city name.
- Display temperature, humidity, wind speed, and sea level pressure.
- Show sunrise and sunset times.
- Change background and animations based on weather conditions.

## Prerequisites
- Android Studio Bumblebee or later.
- Minimum SDK version: 21.

## Dependencies
Add the following dependencies in your `build.gradle` file:

```gradle
implementation("com.airbnb.android:lottie:6.1.0")
implementation("com.squareup.retrofit2:converter-gson:2.9.0")
implementation("com.squareup.retrofit2:retrofit:2.9.0")
```

## Setup Instructions
1. Clone this repository:
   ```bash
   git clone https://github.com/your-username/weather-app.git
   ```
2. Open the project in Android Studio.
3. Add your OpenWeatherMap API key in the code:
   ```kotlin
   val response = retrofit.getWeather(cityName, "YOUR_API_KEY", "metric")
   ```
4. Sync the project to download all dependencies.
5. Run the app on an emulator or physical device.

## API Reference
The app uses the OpenWeatherMap API:

- Base URL: `https://api.openweathermap.org/data/2.5/`
- Endpoint: `/weather`
- Query Parameters:
  - `q`: City name (e.g., `jaipur`)
  - `appid`: Your API key
  - `units`: `metric` for Celsius or `imperial` for Fahrenheit

Example API request:
```
https://api.openweathermap.org/data/2.5/weather?q=jaipur&appid=YOUR_API_KEY&units=metric
```

## Screenshots
Add your UI screenshots here:

![Screenshot 1](https://ibb.co/2FPRvp8)
![Screenshot 2](https://ibb.co/GC02ttM)

## License
This project is licensed under the MIT License. See the LICENSE file for details.
