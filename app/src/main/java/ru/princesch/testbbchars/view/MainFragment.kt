package ru.princesch.testbbchars.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import ru.princesch.testbbchars.R
import ru.princesch.testbbchars.databinding.FragmentMainBinding
import ru.princesch.testbbchars.viewmodel.AppState
import ru.princesch.testbbchars.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this).get(MainViewModel::class.java)
    }
    private val adapter = MainFragmentAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        adapter.setOnItemViewClickListener { character ->
            activity?.supportFragmentManager?.apply {
                this.beginTransaction()
                    .replace(R.id.container, CharacterFragment.newInstance(Bundle().apply {
                        putParcelable(CharacterFragment.BUNDLE_EXTRA, character)
                    }))
                    .addToBackStack("")
                    .commitAllowingStateLoss()
            }
        }
        super.onViewCreated(view, savedInstanceState)

        binding.fragmentMainRecyclerView.adapter = adapter
        val observer = Observer<AppState> { appStateData -> renderData(appStateData) }
        viewModel.getData().observe(viewLifecycleOwner, observer)
        viewModel.getDataFromServer()
    }

    private fun renderData(appStateData: AppState) {
        when (appStateData) {
            is AppState.CharactersSuccess -> {
                val charData = appStateData.charactersData
                adapter.setData(charData)
            }
            is AppState.Loading -> {

            }

            else -> {
                binding.fragmentMain.showSnakeBar("Reload")
                viewModel.getDataFromServer()
            }
        }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}