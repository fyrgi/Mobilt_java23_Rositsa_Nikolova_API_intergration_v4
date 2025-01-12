package se.gritacademy.integration

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide

class PoseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pose)

        // pick up the data
        val poseName = intent.getStringExtra("pose_name")
        val imageUrl = intent.getStringExtra("image_url")
        val url = intent.getStringExtra("url")

        val poseImageView: ImageView = findViewById(R.id.poseImage)
        val poseEnNameTextView: TextView = findViewById(R.id.poseEnName)
        val sanskritNameTextView: TextView = findViewById(R.id.sanskritName)
        val descriptionTextView: TextView = findViewById(R.id.poseDescription)
        val benefitsTextView: TextView = findViewById(R.id.poseBenefits)

        // Get the url sent from the intent that launched this activity
        if (url != null) {
            Log.d("check", "I have url "+url)
            fetchPoseData(url, poseImageView, poseEnNameTextView, sanskritNameTextView, descriptionTextView, benefitsTextView)
        }
    }

    private fun fetchPoseData(url: String, poseImageView: ImageView, poseNameTextView: TextView, sanskritNameTextView: TextView, descriptionTextView: TextView, benefitsTextView: TextView) {
        val requestQueue = Volley.newRequestQueue(this)

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                try {
                    // Get data from API response
                    val urlPng = response.getString("url_png")
                    val englishName = response.getString("english_name")
                    val sanskritName = response.getString("sanskrit_name")
                    val poseDescription = response.getString("pose_description")
                    val poseBenefits = response.getString("pose_benefits")
                    Log.d("posedata",sanskritName+poseBenefits)
                    // Update UI elements on the main thread
                    poseNameTextView.text = englishName
                    sanskritNameTextView.text = sanskritName
                    descriptionTextView.text = "Description: " + poseDescription
                    benefitsTextView.text = "Benefits: " + poseBenefits

                    // Use Picasso to load image
                    Glide.with(this).load(urlPng).into(poseImageView)

                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            { error ->
                error.printStackTrace()
            })

        requestQueue.add(jsonObjectRequest)
    }

}