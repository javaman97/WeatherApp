package com.aman.weatherapp.model

data class Weather(
    val base: String = "",
    val clouds: Clouds = Clouds(),
    val cod: Int = 0,
    val coord: Coord = Coord(),
    val dt: Int = 0,
    val id: Int = 0,
    val main: Main = Main(),
    val name: String = "",
    val rain: Rain = Rain(),
    val sys: Sys = Sys(),
    val timezone: Int = 0,
    val visibility: Int = 0,
    val weather: List<WeatherX> = listOf(),
    val wind: Wind = Wind()
)