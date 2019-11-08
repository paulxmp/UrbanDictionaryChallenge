package com.nomadconsultants.urbandictionarychallenge.ui

import android.os.Bundle
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.nomadconsultants.urbandictionarychallenge.R
import com.nomadconsultants.urbandictionarychallenge.adapters.UrbanDictionaryDefinitionsAdapter
import com.nomadconsultants.urbandictionarychallenge.model.UrbanDictionaryDefinitionsViewModel
import kotlinx.android.synthetic.main.fragment_display_definitions.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import org.koin.android.viewmodel.ext.android.viewModel

class DisplayDefinitionsFragment : Fragment() {

    val definitionsViewModel: UrbanDictionaryDefinitionsViewModel by viewModel()
    val ioScope = CoroutineScope(Dispatchers.IO)
    val definitionsAdapter = UrbanDictionaryDefinitionsAdapter()
    lateinit var termsAdapter: ArrayAdapter<String>
    lateinit var menu: Menu

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_display_definitions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        searchSearchView.setIconifiedByDefault(false)
        searchSearchView.queryHint = getString(R.string.search_text)

        searchSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    searchSearchView.clearFocus()
                    searchProgressBar.visibility = View.VISIBLE
                    ioScope.launch {
                        definitionsViewModel.getDefinitions(query)
                    }
                }
                return true
            }
        })

        sortByButton.setOnClickListener {
            if (sortByButton.text.contains("Up")) {
                definitionsAdapter.sortData("thumbsUp")
                sortByButton.text = getString(R.string.sort_by_down)
            } else {
                definitionsAdapter.sortData("thumbsDown")
                sortByButton.text = getString(R.string.sort_by_up)
            }
            mainDefinitionsRecyclerView.scrollToPosition(0)
        }

        val keyList = definitionsViewModel.getPreviousTerms().toList()
        termsAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_spinner_dropdown_item, keyList)
        previousTermsListView.adapter = termsAdapter
        previousTermsListView.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position,id->
            searchProgressBar.visibility = View.VISIBLE
            previousTermsListView.visibility = View.GONE
            menu.getItem(0).title = getString(R.string.show_previous_terms)
            val query = parent?.getItemAtPosition(position).toString()
            ioScope.launch {
                definitionsViewModel.getDefinitions(query)
            }
        }

        mainDefinitionsRecyclerView.adapter = definitionsAdapter
        mainDefinitionsRecyclerView.layoutManager = LinearLayoutManager(context)

        definitionsViewModel.definitionsList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                definitionsAdapter.clearData()
                definitionsAdapter.addData(it.definitions)
                definitionsAdapter.sortData("thumbsUp")
                sortByButton.text = getString(R.string.sort_by_down)

                // put term in listview
                val keyList = definitionsViewModel.getPreviousTerms().toList()
                termsAdapter = ArrayAdapter<String>(context!!, android.R.layout.simple_expandable_list_item_1, keyList)
                previousTermsListView.adapter = termsAdapter
            }
            searchProgressBar.visibility = View.GONE
        } )
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_display_definitions_fragment, menu)
        super.onCreateOptionsMenu(menu, inflater)
        this.menu = menu
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_previous_items -> {
                if (item.title.contains("Show")) {
                    previousTermsListView.visibility = View.VISIBLE
                    item.title = getString(R.string.hide_previous_terms)
                } else {
                    previousTermsListView.visibility = View.GONE
                    item.title = getString(R.string.show_previous_terms)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }
    override fun onResume() {
        super.onResume()
        mainDefinitionsRecyclerView.scrollToPosition(0)
    }

    override fun onDestroy() {
        super.onDestroy()
        ioScope.coroutineContext.cancel()
    }
}