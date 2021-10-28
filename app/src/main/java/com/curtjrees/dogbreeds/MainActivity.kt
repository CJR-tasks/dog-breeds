package com.curtjrees.dogbreeds

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        switchFragment(DogBreedListFragment(), addToBackStack = false)
    }

    private fun switchFragment(fragment: Fragment, addToBackStack: Boolean) {
        supportFragmentManager.commit {
            if (addToBackStack) addToBackStack(fragment::class.qualifiedName)
            replace(R.id.fragmentContainerView, fragment)
        }
    }
}