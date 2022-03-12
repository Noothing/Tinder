package ussr.retr.tinderbuyaboutscare.Objects

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ussr.retr.tinderbuyaboutscare.R

class SwiperAdapter(private val list: MutableList<TinderCard>) : RecyclerView.Adapter<SwiperAdapter.CustomPageViewHolder>() {

    // Создаем кастомномные блоки, которые будут в
    class CustomPageViewHolder(view: View): RecyclerView.ViewHolder(view){
        val name: TextView = view.findViewById(R.id.username)
        val matches: TextView = view.findViewById(R.id.matchesCount)
        val image: ImageView = view.findViewById(R.id.userImage)
    }

    // Узнаем размер нашего recyclerview по списку, который прилетел в конструкторе
    override fun getItemCount(): Int {
        return list.size
    }

    // Подтягиваем лэйаут к кастомному блоку
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomPageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_blank, parent, false)
        return CustomPageViewHolder(view)
    }

// Закидываем информацию в кастомные блоки, согласно тому, как мы объявили в кастомном вью холдере
    override fun onBindViewHolder(holder: CustomPageViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.matches.text = list[position].matches
        holder.image.setImageBitmap(list[position].image)
    }
}
