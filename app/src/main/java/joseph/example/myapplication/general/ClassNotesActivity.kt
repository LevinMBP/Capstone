package joseph.example.myapplication.general

import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import joseph.example.myapplication.R
import joseph.example.myapplication.SignInActivity
import joseph.example.myapplication.adapters.ClassNotesAdapter
import joseph.example.myapplication.databinding.ActivityClassNotesBinding
import joseph.example.myapplication.modals.FileUploadData
import joseph.example.myapplication.utils.AppPreferences

class ClassNotesActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager
    private lateinit var classNotesList: ArrayList<FileUploadData>
    private lateinit var classNotesAdapter: ClassNotesAdapter
    private lateinit var binding: ActivityClassNotesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize AppPreferences
        AppPreferences.init(this)

        binding = ActivityClassNotesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.closeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        classNotesList = ArrayList<FileUploadData>()

        // ClassNotesAdapter Layout Manager
        linearLayoutManager = LinearLayoutManager(this)
        linearLayoutManager.orientation = LinearLayoutManager.VERTICAL
        linearLayoutManager.reverseLayout = true

        binding.sortbyDate.setOnClickListener {
            Toast.makeText(this, "Already Sorted by Date!", Toast.LENGTH_LONG).show()
        }

        val intent = intent
        if (intent.getStringExtra("navigatedFrom") != null) {
            binding.sortbyDate.visibility = GONE
            binding.searchFiles.visibility = GONE
            binding.divider.visibility = GONE

            // Showing Search Edit Text
            binding.searchEditText.visibility = VISIBLE
        }

        binding.searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                filter(s.toString())
            }
        })

        binding.animationView.visibility = VISIBLE
        binding.prefsLayout.visibility = GONE
        binding.recyclerLayout.visibility = GONE

        binding.searchFiles.setOnClickListener {
            binding.sortbyDate.visibility = GONE
            binding.searchFiles.visibility = GONE
            binding.divider.visibility = GONE

            // Showing Search Edit Text
            binding.searchEditText.visibility = VISIBLE
        }

        // Retrieving Data from Firebase Realtime Database
        retriveClassNotesData()

        binding.classNotesFAB.setOnClickListener {
            val intent = Intent(this, UploadFilesActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
        }

        // Bottom navigation dialog setup
        setupBottomNavigationDialog()
    }

    private fun setupBottomNavigationDialog() {
        binding.classNotesBottomNav.setOnClickListener {
            val dialog = BottomSheetDialog(this, R.style.BottomSheetDialog)
            val view = layoutInflater.inflate(R.layout.bottom_home, null)

            view.findViewById<TextView>(R.id.homePage).setOnClickListener {
                navigateToActivity(HomeActivity::class.java, view)
            }

            view.findViewById<TextView>(R.id.classNotes).setOnClickListener {
                navigateToActivity(ClassNotesActivity::class.java, view)
            }

            view.findViewById<TextView>(R.id.remainders).setOnClickListener {
                navigateToActivity(ReminderActivity::class.java, view)
            }

            view.findViewById<TextView>(R.id.profile).setOnClickListener {
                navigateToActivity(StudentProfileActivity::class.java, view)
            }

            view.findViewById<TextView>(R.id.rateUs).setOnClickListener {
                navigateToActivity(AboutActivity::class.java, view)
            }

            view.findViewById<TextView>(R.id.logOut).setOnClickListener {
                showLogoutDialog(view)
            }

            view.findViewById<TextView>(R.id.collegeMates).setOnClickListener {
                navigateToActivity(ClassMatesActivity::class.java, view)
            }

            dialog.setContentView(view)
            dialog.show()
        }
    }

    private fun navigateToActivity(activityClass: Class<*>, view: View) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
        clearDialogBackgrounds(view)
    }

    private fun clearDialogBackgrounds(view: View) {
        val backgroundResId = 0 // Reset background
        view.findViewById<TextView>(R.id.homePage).setBackgroundResource(backgroundResId)
        view.findViewById<TextView>(R.id.classNotes).setBackgroundResource(backgroundResId)
        view.findViewById<TextView>(R.id.remainders).setBackgroundResource(backgroundResId)
        view.findViewById<TextView>(R.id.profile).setBackgroundResource(backgroundResId)
        view.findViewById<TextView>(R.id.rateUs).setBackgroundResource(backgroundResId)
        view.findViewById<TextView>(R.id.logOut).setBackgroundResource(backgroundResId)
        view.findViewById<TextView>(R.id.collegeMates).setBackgroundResource(backgroundResId)
    }

    private fun showLogoutDialog(view: View) {
        val logoutDialog = Dialog(this)
        logoutDialog.setContentView(R.layout.logout_dialog)
        logoutDialog.setCancelable(false)
        logoutDialog.setCanceledOnTouchOutside(false)
        logoutDialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

        logoutDialog.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            // Clear preferences on logout
            AppPreferences.isLogin = false
            AppPreferences.studentID = ""
            AppPreferences.studentName = ""
            val intent = Intent(this, SignInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
        }

        logoutDialog.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            logoutDialog.dismiss()
        }

        logoutDialog.show()
        clearDialogBackgrounds(view)
    }

    private fun retriveClassNotesData() {
        val studentId = AppPreferences.studentID // Get student ID from preferences
        val myRef = FirebaseDatabase.getInstance().reference.child(studentId).child("files_data")
        myRef.keepSynced(true)

        val classNotesListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                classNotesList.clear()

                if (dataSnapshot.exists()) {
                    binding.animationView.visibility = GONE
                    binding.prefsLayout.visibility = VISIBLE
                    binding.recyclerLayout.visibility = VISIBLE

                    for (ds in dataSnapshot.children) {
                        val classNotesData = ds.getValue(FileUploadData::class.java)
                        classNotesData?.let { classNotesList.add(it) } // Safely add to list
                    }

                    classNotesAdapter = ClassNotesAdapter(this@ClassNotesActivity, classNotesList, AppPreferences.studentName, AppPreferences.studentID)

                    binding.classNotesRecycler.setHasFixedSize(true)
                    linearLayoutManager.stackFromEnd = true
                    binding.classNotesRecycler.layoutManager = linearLayoutManager
                    binding.classNotesRecycler.adapter = classNotesAdapter
                    classNotesAdapter.notifyDataSetChanged()
                } else {
                    showNoDataFound()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showNoDataFound()
                Toast.makeText(this@ClassNotesActivity, error.message, Toast.LENGTH_LONG).show()
            }
        }
        myRef.addValueEventListener(classNotesListener)
    }

    private fun showNoDataFound() {
        binding.animationView.visibility = GONE
        binding.prefsLayout.visibility = GONE
        binding.recyclerLayout.visibility = GONE
        binding.noDataAnimation.visibility = View.VISIBLE
        Toast.makeText(baseContext, "No Data Found !!!", Toast.LENGTH_LONG).show()
    }

    private fun filter(text: String) {
        val filteredlist: ArrayList<FileUploadData> = ArrayList()
        for (item in classNotesList) {
            if (item.fileName!!.toLowerCase().contains(text.toLowerCase()) ||
                item.subjectName?.toLowerCase()?.contains(text.toLowerCase()) == true ||
                item.unitNumber?.toLowerCase()?.contains(text.toLowerCase()) == true ||
                item.studentName?.toLowerCase()?.contains(text.toLowerCase()) == true ||
                item.fileType?.toLowerCase()?.contains(text.toLowerCase()) == true) {
                filteredlist.add(item)
            }
        }
        classNotesAdapter.filterList(filteredlist)
    }
}
