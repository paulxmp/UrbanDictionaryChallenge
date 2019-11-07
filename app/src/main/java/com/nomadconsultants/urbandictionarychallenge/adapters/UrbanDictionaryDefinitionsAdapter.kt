package com.nomadconsultants.urbandictionarychallenge.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.nomadconsultants.urbandictionarychallenge.R
import com.nomadconsultants.urbandictionarychallenge.model.UrbanDictionaryDefinition
import kotlinx.android.synthetic.main.definitions_cardview_item.view.*

class UrbanDictionaryDefinitionsAdapter() :  RecyclerView.Adapter<UrbanDictionaryDefinitionsAdapter.DefinitionsViewHolder>() {
    var items: MutableList<UrbanDictionaryDefinition> = mutableListOf()

    class DefinitionsViewHolder(val card: View) : RecyclerView.ViewHolder(card)

    override fun onBindViewHolder(holder: DefinitionsViewHolder, position: Int) {
        val definition = items.get(position)
        holder.card.cardviewDefinitionTextView.text = definition.definition
        holder.card.thumbsUpValueTextView.text = definition.thumbsUp.toString()
        holder.card.thumbsDownValueTextView.text = definition.thumbsDown.toString()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DefinitionsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.definitions_cardview_item, parent, false))


    override fun getItemCount() = items.size

    fun addData(definitionList: List<UrbanDictionaryDefinition>) {
        for (definition in definitionList) {
            items.add(definition)
        }
        notifyDataSetChanged()
    }

    fun clearData() = items.clear()

    fun sortData(byVoteType: String) {
        var sortedItems: List<UrbanDictionaryDefinition> = listOf()
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