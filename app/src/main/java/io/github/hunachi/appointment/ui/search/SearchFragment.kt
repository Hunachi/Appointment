package io.github.hunachi.appointment.ui.search

import android.content.Context
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.*
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import io.github.hunachi.appointment.data.SearchKey
import io.github.hunachi.appointment.nonNullObserver
import io.github.hunachi.appointment.ui.createApo.CreateApoFragment
import kotlinx.android.synthetic.main.search_fragment.*
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator as SlideInLeftAnimator1
import androidx.appcompat.widget.SearchView
import io.github.hunachi.appointment.R


class SearchFragment : Fragment() {

    companion object {
        fun newInstance() = SearchFragment()
    }

    private lateinit var viewModel: SearchViewModel

    private val professorAdapter = ProfessorAdapter()
    private val searchKeysAdapter = SearchKeysAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.search_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SearchViewModel::class.java)

        viewModel.professors.nonNullObserver(this) {
            professorAdapter.submitList(it)
        }

        viewModel.keys.nonNullObserver(this) {
            searchKeysAdapter.submitList(it)
        }
        val list = listOf(
            SearchKey("name", false),
            SearchKey("lecture", false),
            SearchKey("nickname", false)
        )
        professorAdapter.clickedItem.nonNullObserver(this) {
            val currentActivity = activity ?: return@nonNullObserver
            currentActivity.supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, CreateApoFragment.newInstance(it))
                .commit()
        }
        searchKeysAdapter.clickedItem.nonNullObserver(this) {
            viewModel.refreshKeys(it)
        }
        viewModel.setUpKeys(list)
        viewModel.refreshProfessors()

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        professors.apply {
            adapter = professorAdapter
            layoutManager = GridLayoutManager(context, 3)
            itemAnimator = SlideInLeftAnimator1()
        }

        val currentFocusView = activity?.currentFocus
        if (currentFocusView != null) {
            val manager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            manager.hideSoftInputFromWindow(currentFocusView.windowToken, 0)
        }

        search_list.apply {
            adapter = searchKeysAdapter
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.search, menu)
        (menu.findItem(R.id.search_menu_search_view).actionView as SearchView).apply {
            setIconifiedByDefault(true)
            isSubmitButtonEnabled = true
            setOnQueryTextListener(onQueryTextListener)
        }
    }

    private val onQueryTextListener = object : SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(searchWord: String): Boolean {
            viewModel.refreshProfessors(searchWord)
            val view = activity?.currentFocus
            if (view != null) {
                val manager =
                    activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                manager.hideSoftInputFromWindow(view.windowToken, 0)
            }
            return true
        }

        override fun onQueryTextChange(newText: String): Boolean {
            viewModel.refreshProfessors(newText)
            return false
        }
    }
}
