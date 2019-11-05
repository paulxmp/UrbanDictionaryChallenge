package com.nomadconsultants.urbandictionarychallenge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomadconsultants.urbandictionarychallenge.R
import com.nomadconsultants.urbandictionarychallenge.model.Definition
import kotlinx.android.synthetic.main.definitions_cardview_item.view.*

class DefinitionsAdapter() :  RecyclerView.Adapter<DefinitionsAdapter.DefinitionsViewHolder>() {
    var items: MutableList<Definition> = mutableListOf()
    val itemsHashMap = mutableMapOf<Int, Definition>()

    class DefinitionsViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    override fun onBindViewHolder(holder: DefinitionsViewHolder, position: Int) {
        val definition = items.get(position)
        holder.card.cardviewDefinitionTextView.text = definition.definition
        holder.card.thumbsUpValueTextView.text = definition.thumbsUp.toString()
        holder.card.thumbsDownValueTextView.text = definition.thumbsDown.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefinitionsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.definitions_cardview_item, parent, false)
        return DefinitionsViewHolder(view)
    }

    override fun getItemCount() = items.size

    fun addData(definitionList: List<Definition>) {
        for (definition in definitionList) {
            items.add(definition)
            itemsHashMap.put(itemsHashMap.size + 1, definition)
        }
        notifyDataSetChanged()
    }

    fun clearData() {
        items.clear()
        itemsHashMap.clear()
    }

    fun sortData(byVoteType: String) {
        var sortedItems: List<Definition> = listOf()
        sortedItems =  if (byVoteType.equals("thumbsUp")) {
            items.sortedBy { value -> value.thumbsUp }.reversed()
        } else {
            items.sortedBy { value -> value.thumbsDown }.reversed()
        }

        items.clear()
        items.addAll(sortedItems)
        notifyDataSetChanged()
    }
}