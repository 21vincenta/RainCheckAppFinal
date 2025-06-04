package ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import data.models.ForecastResponse
import com.example.raincheck.R

class ForecastAdapter : RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>() {
    private var items: List<ForecastResponse.ForecastItem> = emptyList()

    fun submitList(list: List<ForecastResponse.ForecastItem>) {
        items = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_forecast, parent, false)
        return ForecastViewHolder(view)
    }

    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class ForecastViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val icon: ImageView = itemView.findViewById(R.id.imageViewWeatherIcon)
        private val date: TextView = itemView.findViewById(R.id.textViewDate)
        private val condition: TextView = itemView.findViewById(R.id.textViewCondition)
        private val temp: TextView = itemView.findViewById(R.id.textViewTemp)
        fun bind(item: ForecastResponse.ForecastItem) {
            // Set icon (placeholder)
            icon.setImageResource(R.drawable.ic_weather_placeholder)
            // Format date
            date.text = java.text.SimpleDateFormat("EEE", java.util.Locale.getDefault()).format(java.util.Date(item.dateTime * 1000))
            condition.text = item.weather.firstOrNull()?.description ?: "-"
            temp.text = "${item.main.temp.toInt()}°C"
        }
    }
}
