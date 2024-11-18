package joseph.example.myapplication.onboarding

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import joseph.example.myapplication.R
import joseph.example.myapplication.general.HomeActivity
import joseph.example.myapplication.utils.AppPreferences


class SplashScreenActivity : AppCompatActivity() {

//    private val SPLASH_DELAY: Long = 3000 //3 seconds
//    private var mDelayHandler: Handler? = null
//    private var progressBarStatus = 0
//    var dummy:Int = 0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash_screen)
//
//        AppPreferences.init(this)
//
//        /* progressBar: Prog = findViewById(R.id.progressBar)
//         val logoImage: ImageView = findViewById(R.id.logo)
// */
//        mDelayHandler = Handler()
//        //Navigate with delay
//        mDelayHandler!!.postDelayed(mRunnable, SPLASH_DELAY)
//
//    }
//
//    private fun launchMainActivity() {
//        val intent = Intent(this, GettingStartedActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        this.finish()
//        mDelayHandler!!.removeCallbacks(mRunnable)
//
//    }
//
//    private val mRunnable: Runnable = Runnable {
//
//        Thread(Runnable {
//            while (progressBarStatus < 100) {
//                // performing some dummy operation
//                try {
//                    dummy = dummy+25
//                    Thread.sleep(100)
//                } catch (e: InterruptedException) {
//                    e.printStackTrace()
//                }
//                // tracking progress
//                progressBarStatus = dummy
//
//                // Updating the progress bar
//                //splash_screen_progress_bar.progress = progressBarStatus
//            }
//
//            //splash_screen_progress_bar.setProgress(10)
//
//
//            if(AppPreferences.isLogin) {
//                // user is logged in
//                val intent = Intent(this, HomeActivity::class.java)
//                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//                startActivity(intent)
//                finish()
//            } else {
//                launchMainActivity()
//
//            }
//
//
//        }).start()
//    }
//
//    override fun onDestroy() {
//
//        if (mDelayHandler != null) {
//            mDelayHandler!!.removeCallbacks(mRunnable)
//        }
//
//        super.onDestroy()
//    }

    private val SPLASH_DELAY: Long = 3000 // 3 seconds

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        AppPreferences.init(this)

        val logoImageView: ImageView = findViewById(R.id.logo)

        // Animate the logo
        ObjectAnimator.ofFloat(logoImageView, "translationY", -200f, 0f).apply {
            duration = 2000
            interpolator = AccelerateDecelerateInterpolator()
            start()
        }

        // Navigate after the animation
        Handler().postDelayed({
            if (AppPreferences.isLogin) {
                // User is logged in
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
            } else {
                val intent = Intent(this, GettingStartedActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
            finish()
        }, SPLASH_DELAY)
    }

}