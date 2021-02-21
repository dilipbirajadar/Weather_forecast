import com.google.gson.annotations.SerializedName


data class Lista (
	@SerializedName("dt") val dt : Int,
	@SerializedName("main") val main : MainForeCast,
	@SerializedName("weather") val weather : List<WeatherFore>,
	@SerializedName("clouds") val clouds : Clouds,
	@SerializedName("wind") val wind : Wind,
	@SerializedName("visibility") val visibility : Int,
	@SerializedName("pop") val pop : Double,
	@SerializedName("sys") val sys : SysFore,
	@SerializedName("rain") val rain : Rain,
	@SerializedName("dt_txt") val dt_txt : String
)