package dam.pmdm.spyrothedragon.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import dam.pmdm.spyrothedragon.R
import dam.pmdm.spyrothedragon.models.Character

class CharactersAdapter(
    private val list: List<Character>,
    private val onRiptoLongClick: () -> Unit
) : RecyclerView.Adapter<CharactersAdapter.CharactersViewHolder>() {

    private val characterImages = mapOf(
        "spyro" to R.drawable.spyro,
        "hunter" to R.drawable.hunter,
        "elora" to R.drawable.elora,
        "ripto" to R.drawable.ripto
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cardview, parent, false)
        return CharactersViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val character = list[position]
        holder.nameTextView.text = character.name

        val drawableRes = characterImages[character.image] ?: R.drawable.placeholder
        holder.imageImageView.setImageResource(drawableRes)

        holder.itemView.setOnLongClickListener {
            val esRipto =
                character.name.equals("Ripto", ignoreCase = true) ||
                        character.image.equals("ripto", ignoreCase = true)

            if (esRipto) {
                onRiptoLongClick()
                true
            } else {
                false
            }
        }
    }

    override fun getItemCount(): Int = list.size

    class CharactersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView: TextView = itemView.findViewById(R.id.name)
        val imageImageView: ImageView = itemView.findViewById(R.id.image)
    }
}