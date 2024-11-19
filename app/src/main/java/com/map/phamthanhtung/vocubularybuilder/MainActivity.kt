package com.map.phamthanhtung.vocubularybuilder

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.map.phamthanhtung.vocubularybuilder.ui.theme.calendar.CalendarFragment
import com.map.phamthanhtung.vocubularybuilder.ui.theme.quiz.QuizFragment
import com.map.phamthanhtung.vocubularybuilder.ui.theme.vocabulary.VocabularyFragment
import com.map.phamthanhtung.vocubularybuilder.ui.theme.vocabulary.AddWordFragment // Import AddWordFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Set your layout

        // Load VocabularyFragment initially
        if (savedInstanceState == null) {
            loadFragment(VocabularyFragment()) // Load VocabularyFragment initially
        }

        // BottomNavigationView setup
        val bottomNavigation: BottomNavigationView = findViewById(R.id.bottom_navigation)
        bottomNavigation.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_vocabulary -> {
                    // Load AddWordFragment when "Vocabulary" is selected
                    loadFragment(AddWordFragment()) // Load AddWordFragment
                    true
                }
                R.id.nav_quiz -> {
//                    loadFragment(QuizFragment())
                    true
                }
                R.id.nav_statistics -> {
                    // Handle StatisticsFragment when selected
                    true
                }
                R.id.nav_calendar ->{
                    loadFragment(CalendarFragment())
                    true
                }
                else -> false
            }
        }
    }

    // Function to load fragments
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.nav_host_fragment, fragment) // Replace the container with the selected fragment
            .commit()
    }
}
