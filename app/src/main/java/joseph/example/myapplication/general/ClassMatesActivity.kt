package joseph.example.myapplication.general

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import joseph.example.myapplication.R
import joseph.example.myapplication.adapters.ClassmatesAdapter
import joseph.example.myapplication.databinding.ActivityClassMatesBinding
import joseph.example.myapplication.modals.Classmate

class ClassMatesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClassMatesBinding
    private lateinit var classmatesList: ArrayList<Classmate>
    private lateinit var classmatesAdapter: ClassmatesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClassMatesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize list
        classmatesList = ArrayList()
        populateClassmatesList()

        // Setup RecyclerView
        classmatesAdapter = ClassmatesAdapter(this, classmatesList)
        binding.classmatesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.classmatesRecyclerView.adapter = classmatesAdapter

        // Search functionality
        binding.searchBar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                filter(s.toString())
            }
        })
    }

    private fun populateClassmatesList() {
        classmatesList.add(Classmate("Joseph", "Team Member", R.drawable.ic_profile_picture_placeholder))
        classmatesList.add(Classmate("Vraj Patel", "Team Member", R.drawable.ic_profile_picture_placeholder))
        classmatesList.add(Classmate("Priyank Vora", "Team Member", R.drawable.ic_profile_picture_placeholder))
        // Add more classmates
    }

    private fun filter(query: String) {
        val filteredList = classmatesList.filter {
            it.name.contains(query, ignoreCase = true) || it.role.contains(query, ignoreCase = true)
        }
        classmatesAdapter.updateList(filteredList)
    }
}