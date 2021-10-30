package ru.princesch.testbbchars.view

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.squareup.picasso.Picasso
import ru.princesch.testbbchars.R
import ru.princesch.testbbchars.databinding.FragmentCharacterBinding
import ru.princesch.testbbchars.model.Character
import ru.princesch.testbbchars.viewmodel.AppState
import ru.princesch.testbbchars.viewmodel.DetailsViewModel

class CharacterFragment : Fragment() {

    companion object {
        const val BUNDLE_EXTRA = "character"
    }

    private var _binding: FragmentCharacterBinding? = null
    private val binding get() = _binding!!

    private lateinit var characterBundle: Character

    private val viewModel: DetailsViewModel by lazy {
        ViewModelProvider(this).get(DetailsViewModel::class.java)
    }
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCharacterBinding.inflate(inflater, container, false)
        navController = NavHostFragment.findNavController(this)
        setHasOptionsMenu(true)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterBundle = arguments?.getParcelable(BUNDLE_EXTRA) ?: Character()
        binding.toolbar.title = characterBundle.name
        //TODO не изменяется цвет заголовка тулбара
        viewModel.getData().observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getDataFromServer(characterBundle.char_id)

        val activity: AppCompatActivity = getActivity() as AppCompatActivity
        activity.setSupportActionBar(binding.toolbar)
        NavigationUI.setupActionBarWithNavController(activity, navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            navController.popBackStack()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.DetailSuccess -> {
                setCharacter(appState.detailData)

            }
            is AppState.Loading -> {

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
                    .fit()
                    .centerCrop(Gravity.TOP)
                    .into(toolbarImage)
                Picasso
                    .get()
                    .load(img)
                    .fit()
                    .centerCrop(Gravity.TOP)
                    .into(actorImage)
                alsoKnownEdit.text = nickname
                if (birthday != "Unknown") {
//                    birthDateEdit.text = birthday
                    birthDateEdit.text = convertDate(birthday)
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

    private fun convertDate(date: String): String {
        var stringMonth = ""
        val monthList = resources.getStringArray(R.array.month)
        stringMonth = if (date[3].toString() == "1") {
            val month = (date[3].toString() + date[4].toString()).toInt()
            monthList[month - 1]
        } else {
            val month = date[4].toString()
            monthList[month.toInt() - 1]
        }
        return "${date[0]}${date[1]} $stringMonth ${date[6]}${date[7]}${date[8]}${date[9]}"
    }


    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
