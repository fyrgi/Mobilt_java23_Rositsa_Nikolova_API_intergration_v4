package se.gritacademy.integration

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject
import com.bumptech.glide.Glide

class LevelPosesActivity : AppCompatActivity() {
    private lateinit var requestQueue: RequestQueue
    private lateinit var mainLayout: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga_level_poses)
        val level = intent.getStringExtra("level")
        val url = "https://yoga-api-nzy4.onrender.com/v1/poses?level=$level"
        Log.d("theUrl", url)
        val pageTitle: TextView = findViewById(R.id.activityTitle)

        if(!level.isNullOrEmpty()){
            pageTitle.text = "$level level poses"
        }
        // reference the container for dynamic content
        mainLayout = findViewById(R.id.mainLayout)

        // Initialize Volley
        requestQueue = Volley.newRequestQueue(this)


        fetchPoses(url)
    }

    private fun fetchPoses(url: String) {
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                val poses = parsePoses(response)
                displayPoses(poses)
            },
            { error ->
                Log.e("API_ERROR", "Failed to fetch poses: ${error.message}")
            }
        )
        requestQueue.add(jsonObjectRequest)
    }

    private fun parsePoses(response: JSONObject): List<YogaPose> {
        val poses = mutableListOf<YogaPose>()
        val jsonArray = response.getJSONArray("poses")
        for (i in 0 until jsonArray.length()) {
            val pose = jsonArray.getJSONObject(i)
            val name = pose.getString("english_name")
            val imageUrl = pose.getString("url_png")
            val url = "https://yoga-api-nzy4.onrender.com/v1/poses?name=$name"
            poses.add(YogaPose(name, imageUrl, url))
        }
        return poses
    }

    private fun displayPoses(poses: List<YogaPose>) {
        for (pose in poses) {
            // Add TextView for the pose name
            val textView = TextView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                textSize = 18f
                text = pose.poseName
                setPadding(0, 16, 0, 8)

                // add the listener for the title
                setOnClickListener {
                    navigateToPoseDetails(pose.poseName, pose.imageUrl, pose.url)
                }
            }
            mainLayout.addView(textView)

            // Add ImageView for the pose image
            val imageView = ImageView(this).apply {
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    400
                )
                scaleType = ImageView.ScaleType.FIT_CENTER
                setOnClickListener {
                    navigateToPoseDetails(pose.poseName, pose.imageUrl, pose.url)
                }
            }
            Glide.with(this).load(pose.imageUrl).into(imageView)
            mainLayout.addView(imageView)
        }
    }

    // data class that stores the displayed data or the link for the api.
    data class YogaPose(
        val poseName: String,
        val imageUrl: String,
        val url: String
    )

    private fun navigateToPoseDetails(name: String, imageUrl: String, url: String) {
        val intent = Intent(this, PoseActivity::class.java)
        intent.putExtra("pose_name", name)
        intent.putExtra("image_url", imageUrl)
        intent.putExtra("url", url)
        startActivity(intent)
    }

}

