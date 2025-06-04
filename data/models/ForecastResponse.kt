package data.models

import com.google.gson.annotations.SerializedName

data class ForecastResponse(
    @SerializedName("list") val forecastList: List<ForecastItem>
) {
    data class ForecastItem(
        @SerializedName("dt") val dateTime: Long,
        @SerializedName("main") val main: Main,
        @SerializedName("weather") val weather: List<Weather>
    ) {
        data class Main(
            @SerializedName("temp") val temp: Double,
            @SerializedName("temp_min") val tempMin: Double,
            @SerializedName("temp_max") val tempMax: Double
        )
        data class Weather(
            @SerializedName("id") val id: Int,
            @SerializedName("main") val main: String,
            @SerializedName("description") val description: String,
            @SerializedName("icon") val icon: String
        )
    }
}
