package se.gritacademy.integration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val yogaButton: Button = findViewById(R.id.yogaButton)
        val pilatesButton: Button = findViewById(R.id.pilatesButton)

        yogaButton.setOnClickListener {
            val intent = Intent(this, YogaLevelActivity::class.java)
            intent.putExtra("category", "Yoga")
            startActivity(intent)
            finish()
        }

        pilatesButton.setOnClickListener {
            val intent = Intent(this, YogaLevelActivity::class.java)
            intent.putExtra("category", "Pilates")
            startActivity(intent)
            finish()
        }
    }

}