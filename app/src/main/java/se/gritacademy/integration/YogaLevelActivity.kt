package se.gritacademy.integration

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment

class YogaLevelActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga_level)

        val category = intent.getStringExtra("category") ?: "Yoga"
        val beginnerButton: Button = findViewById(R.id.beginnerButton)
        val intermediateButton: Button = findViewById(R.id.intermediateButton)
        val expertButton: Button = findViewById(R.id.expertButton)

        beginnerButton.setOnClickListener {
            navigateToPoseList(category, "Beginner")
        }
        intermediateButton.setOnClickListener {
            navigateToPoseList(category, "Intermediate")
        }
        expertButton.setOnClickListener {
            navigateToPoseList(category, "Expert")
        }
    }

    private fun navigateToPoseList(category: String, level: String) {
        val intent = if (category == "Yoga") {
            Intent(this, LevelPosesActivity::class.java)
        } else {
            Intent(this, PilatesActivity::class.java)
        }
        intent.putExtra("level", level)
        startActivity(intent)
    }

}
