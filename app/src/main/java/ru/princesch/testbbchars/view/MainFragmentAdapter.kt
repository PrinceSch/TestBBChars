package ru.princesch.testbbchars.view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.princesch.testbbchars.databinding.ItemRecyclerCharacterBinding
import ru.princesch.testbbchars.model.Character
import ru.princesch.testbbchars.model.CharacterDTO

class MainFragmentAdapter : RecyclerView.Adapter<MainFragmentAdapter.MainViewHolder>() {

    private var onItemViewClickListener: (Character) -> Unit = {}
    private var charsData: List<Character> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val binding = ItemRecyclerCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MainViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.bind(charsData[position])
    }

    override fun getItemCount(): Int {
        return charsData.size
    }

    fun setOnItemViewClickListener(onItemViewClickListener: (Character) -> Unit) {
        this.onItemViewClickListener = onItemViewClickListener
    }

    fun setData(data: List<Character>){
        charsData = data
        notifyDataSetChanged()
    }

    inner class MainViewHolder(private val binding: ItemRecyclerCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(character: Character) {
            with(binding) {
                Picasso.get()
                    .load(character.img)
                    .fit()
                    .centerCrop(Gravity.TOP)
                    .into(itemImage)
                itemName.text = character.name

                root.setOnClickListener { onItemViewClickListener(character) }
            }
        }
    }

}