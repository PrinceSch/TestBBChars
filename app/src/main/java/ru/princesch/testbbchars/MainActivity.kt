package ru.princesch.testbbchars

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.princesch.testbbchars.databinding.ActivityMainBinding
import ru.princesch.testbbchars.view.MainFragment

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.container.id, MainFragment.newInstance())
                .commitNow()
        }
    }
}