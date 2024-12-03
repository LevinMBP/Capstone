package joseph.example.myapplication.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import joseph.example.myapplication.databinding.ItemClassmateBinding
import joseph.example.myapplication.general.ProfileDetailsActivity
import joseph.example.myapplication.modals.Classmate

class ClassmatesAdapter(
    private val context: Context,
    private var classmates: List<Classmate>
) : RecyclerView.Adapter<ClassmatesAdapter.ClassmateViewHolder>() {

    inner class ClassmateViewHolder(val binding: ItemClassmateBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ClassmateViewHolder {
        val binding = ItemClassmateBinding.inflate(LayoutInflater.from(context), parent, false)
        return ClassmateViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ClassmateViewHolder, position: Int) {
        val classmate = classmates[position]
        holder.binding.classmateName.text = classmate.name
        holder.binding.classmateRole.text = classmate.role
        holder.binding.profilePicture.setImageResource(classmate.profilePicture)

        holder.itemView.setOnClickListener {
            // Intent to navigate to ProfileDetailsActivity
            val intent = Intent(holder.itemView.context, ProfileDetailsActivity::class.java)

            // Pass the classmate details as extras
            intent.putExtra("name", classmate.name)
            intent.putExtra("role",classmate.role)

            // Start the activity
            holder.itemView.context.startActivity(intent)
        }
    }


    override fun getItemCount(): Int = classmates.size

    fun updateList(newList: List<Classmate>) {
        classmates = newList
        notifyDataSetChanged()
    }
}