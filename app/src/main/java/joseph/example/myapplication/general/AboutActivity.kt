package joseph.example.myapplication.general

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import joseph.example.myapplication.R

class AboutActivity:AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}