package ru.princesch.testbbchars.view

import android.os.Build
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.squareup.picasso.Picasso
import ru.princesch.testbbchars.databinding.FragmentCharacterBinding
import ru.princesch.testbbchars.model.Character
import ru.princesch.testbbchars.viewmodel.AppState
import ru.princesch.testbbchars.viewmodel.DetailsViewModel

class CharacterFragment : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "character"

        fun newInstance(bundle: Bundle): CharacterFragment {
            val fragment = CharacterFragment()
            fragment.arguments = bundle
            return fragment
        }
    }

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    private lateinit var characterBundle: Character

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Character()
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getDataFromServer(characterBundle.char_id)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.DetailSuccess -> {
                setCharacter(appState.detailData)
            }
            is AppState.Loading -> {
                binding.toolbar.showSnakeBar("Loading")
            }

            else -> {
                binding.toolbar.showSnakeBar("Reload")
                viewModel.getDataFromServer(characterBundle.char_id)
            }
        }
    }

    private fun setCharacter(character: Character) {
        character.apply {
            with(binding) {
                Picasso
                    .get()
                    .load(img)
                    .into(toolbarImage)
                Picasso
                    .get()
                    .load(img)
                    .fit()
                    .centerCrop(Gravity.TOP)
                    .into(actorImage)
                alsoKnownEdit.text = nickname
                if (birthday != "Unknown") {
                    birthDateEdit.text = birthday
                }
                statusEdit.text = character.status
                workEdit.text = convertToString(occupation)
                if (!character.appearance.isNullOrEmpty()) {
                    appearanceEdit.show()
                    seasonsEdit.text = convertToString(character.appearance)
                }
                actorEdit.text = portrayed
            }
        }
    }

    private fun convertToString(list: List<Any>): String {
        var result = ""
        list.forEach {
            result += if (it == list.last()) {
                "$it"
            } else {
                "$it, "
            }
        }
        return result
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
