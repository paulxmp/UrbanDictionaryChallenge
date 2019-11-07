package com.nomadconsultants.urbandictionarychallenge.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.fragment_display_definitions, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

        mainDefinitionsRecyclerView.adapter = definitionsAdapter
        mainDefinitionsRecyclerView.layoutManager = LinearLayoutManager(context)

        definitionsViewModel.definitionsList.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it != null) {
                definitionsAdapter.clearData()
                definitionsAdapter.addData(it.definitions)
                definitionsAdapter.sortData("thumbsUp")
                sortByButton.text = getString(R.string.sort_by_down)
            }
            searchProgressBar.visibility = View.GONE
        } )
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