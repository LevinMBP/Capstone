package joseph.example.myapplication.general

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.appcheck.FirebaseAppCheck
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory
import com.google.firebase.auth.FirebaseAuth
import joseph.example.myapplication.R
import joseph.example.myapplication.SignInActivity
import joseph.example.myapplication.adapters.DashboardIconsAdapter
import joseph.example.myapplication.adapters.NewsAdapter
import joseph.example.myapplication.modals.DashboardIconData
import joseph.example.myapplication.modals.NewsData
import joseph.example.myapplication.utils.AppPreferences

class HomeActivity: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var newsLayoutManager: LinearLayoutManager
    lateinit var bottomnav: BottomAppBar

    //All the commented block of code will be covered in upcoming Sprints

    //lateinit var mGoogleSignInClient: GoogleSignInClient


    @SuppressLint("SetTextI18n", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        auth = FirebaseAuth.getInstance()
        setContentView(R.layout.activity_home)
        val studentName = findViewById<TextView>(R.id.studentName)
        val uploadFilesButton = findViewById<FloatingActionButton>(R.id.uploadFilesButton)
        val notificationLayout = findViewById<FrameLayout>(R.id.notificationLayout)
        val dashboardRecycler = findViewById<RecyclerView>(R.id.dashboardRecycler)
        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        val newsRecycler = findViewById<RecyclerView>(R.id.newsRecycler)
        bottomnav=findViewById(R.id.bottomnav)
        AppPreferences.init(this)
        val currentUser = auth.currentUser
        if (currentUser != null) {
            studentName.text = "Hi " + currentUser.email // Display the logged-in user's email
        } else {
            studentName.text = "Hi Guest" // Default message for guests
        }

        // Initialize AppCheck
        val firebaseAppCheck = FirebaseAppCheck.getInstance()
        firebaseAppCheck.installAppCheckProviderFactory(DebugAppCheckProviderFactory.getInstance())

        // Google AdMob
        //We'll do it later
//        MobileAds.initialize(this) {}
//        val adRequest = AdRequest.Builder().build()
//        adView.loadAd(adRequest)



        if(AppPreferences.isLogin) {
            studentName.text = "Hi " + AppPreferences.studentName
        }


//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
//
//        // Build a GoogleSignInClient with the options specified by gso.
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)

        uploadFilesButton.setOnClickListener {
//            ProcessBuilder.Redirect to UploadFilesActivity
            val intent = Intent(this, UploadFilesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        notificationLayout.setOnClickListener {
            //Redirect to ViewNotificationsActivity
//            val intent = Intent(this, ViewNotificationsActivity::class.java)
//            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//            startActivity(intent)
        }

        // DashboardIconsAdapter
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        linearLayoutManager.reverseLayout = false

        val dashboardIconsList = ArrayList<DashboardIconData>()
        dashboardIconsList.add(
            DashboardIconData(
                "Class Notes",
                R.drawable.ic_baseline_menu_book_24
            )
        )
        dashboardIconsList.add(
            DashboardIconData(
                "Class Mates",
                R.drawable.ic_baseline_supervisor_account_24
            )
        )
        dashboardIconsList.add(
            DashboardIconData(
                "Reminders",
                R.drawable.ic_baseline_notifications_active_24
            )
        )
        dashboardIconsList.add(DashboardIconData("Profile", R.drawable.ic_baseline_person_24))

        val dashboardIconAdapter = DashboardIconsAdapter(dashboardIconsList)
        dashboardRecycler.layoutManager = linearLayoutManager
        dashboardRecycler.adapter = dashboardIconAdapter

        searchEditText.setOnClickListener {
            val intent = Intent(this, ClassNotesActivity::class.java)
            intent.putExtra("navigatedFrom", "HomeActivity")
            startActivity(intent)
        }

        // News Adapter
        newsLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val newsList = ArrayList<NewsData>()
        newsList.add(
            NewsData(
                R.drawable.student,
                "New Version is Live!",
                "New UI and added facility to send Reminders for Assignments,Exams,etc."
            )
        )
        newsList.add(
            NewsData(
                R.drawable.app_logo,
                "Welcome to NoteMaster",
                "Hope you are enjoying the App and All the Best for your Exams"
            )
        )

        val newsAdapter = NewsAdapter(newsList)
        newsRecycler.layoutManager = newsLayoutManager
        newsRecycler.adapter = newsAdapter

        bottomnav.setOnClickListener {
            Log.e("BottomSheet","BottomSheet Opened")
            val dialog= BottomSheetDialog(this, R.style.BottomSheetDialog)
            val view=layoutInflater.inflate(R.layout.bottom_items, null)


            view.findViewById<TextView>(R.id.classNotes).setOnClickListener {
                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.classNotes).setTextColor(resources.getColor(R.color.colorPrimary))


                val intent = Intent(this, ClassNotesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)

                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //dialog.dismiss()

            }
            view.findViewById<TextView>(R.id.remainders).setOnClickListener {
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.remainders).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, ReminderActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)


                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                // dialog.dismiss()
            }
            view.findViewById<TextView>(R.id.profile).setOnClickListener {

                view.findViewById<TextView>(R.id.profile).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.profile).setTextColor(resources.getColor(R.color.colorPrimary))
                val intent = Intent(this, StudentProfileActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

            }
            view.findViewById<TextView>(R.id.rateUs).setOnClickListener {
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.rateUs).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, AboutActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()
            }




            view.findViewById<TextView>(R.id.collegeMates).setOnClickListener {
                view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.collegeMates).setTextColor(resources.getColor(R.color.colorPrimary))

                val intent = Intent(this, ClassMatesActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()

            }
//Note: user can logout from the app and able to relogin ,basically redirected to login screen.
            view.findViewById<TextView>(R.id.logOut).setOnClickListener {

                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(R.drawable.bottom_sheet_dialog_button)
                view.findViewById<TextView>(R.id.logOut).setTextColor(resources.getColor(R.color.colorPrimary))

                val logoutDialog = Dialog(this)
                logoutDialog.setContentView(R.layout.logout_dialog)
                logoutDialog.setCancelable(false)
                logoutDialog.setCanceledOnTouchOutside(false)
                logoutDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
                logoutDialog.findViewById<Button>(R.id.btnLogout).setOnClickListener {
                    auth.signOut()
                    AppPreferences.isLogin = false
                    AppPreferences.studentID = ""
                    AppPreferences.studentName = ""
                    val intent = Intent(this, SignInActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                }

                logoutDialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                    logoutDialog.dismiss()
                }

                logoutDialog.show()

                view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.profile).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.remainders).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(0)
                view.findViewById<TextView>(R.id.logOut).setBackgroundResource(0)

                //  dialog.dismiss()
            }


            dialog.setContentView(view)
            //dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)
            dialog.show()

        }

    }
}