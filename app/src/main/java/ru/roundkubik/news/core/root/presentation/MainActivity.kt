package ru.roundkubik.news.core.root.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.roundkubik.news.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}