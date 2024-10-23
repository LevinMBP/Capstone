package joseph.example.myapplication.general

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import joseph.example.myapplication.R
import joseph.example.myapplication.databinding.ActivityUploadFilesBinding
import joseph.example.myapplication.modals.FileUploadData
import joseph.example.myapplication.utils.AppPreferences
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date


class UploadFilesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUploadFilesBinding

    private lateinit var closeButton: ImageView

    val REQUEST_CODE_DOC : Int = 0
    private var fileDownloadUrl: String? = ""
    private var fileSelected: String? = "0"

    private  var unitNumber: String? = ""
    private  var fileType: String? = ""

    private val TAG = "TOKENS_DATA"
    private val FCM_API = "https://fcm.googleapis.com/fcm/send"
    private val serverKey = "key=" + AppPreferences.AUTH_KEY_FCM
    private val contentType = "application/json"
    var TOPIC: String? = null


    lateinit var uri : Uri
    lateinit var mStorage : StorageReference

    private lateinit var database: DatabaseReference

    @SuppressLint("ShowToast", "ResourceAsColor")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadFilesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        AppPreferences.init(this)
        //setUpSubjectList()

        database = FirebaseDatabase.getInstance().reference

        mStorage = FirebaseStorage.getInstance().getReference("123456")

        binding.uploadFileButton.setOnClickListener {

            val mimeTypes = arrayOf(
                "application/msword",
                "application/vnd.openxmlformats-officedocument.wordprocessingml.document",  // .doc & .docx
                "application/vnd.ms-powerpoint",
                "application/vnd.openxmlformats-officedocument.presentationml.presentation",  // .ppt & .pptx
                "text/plain",
                "application/pdf", // PDF
                "application/zip",
                "image/*"// Image
            )

            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE)

            intent.type = if (mimeTypes.size == 1) mimeTypes[0] else "*/*"
            if (mimeTypes.size > 0) {
                intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            }
            startActivityForResult(Intent.createChooser(intent, "ChooseFile"), REQUEST_CODE_DOC)

        }

        binding.oneUnit.setOnClickListener {

            binding.oneUnit.setBackgroundResource(R.drawable.blue_rounded_button)
            binding.oneUnit.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "1"

            binding.secondUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.thirdUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.fourthUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.other.setBackgroundResource(R.drawable.rounded_button)

            binding.secondUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.thirdUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.fourthUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.other.setTextColor(resources.getColor(R.color.colorPrimary))

        }
        binding.secondUnit.setOnClickListener {
            binding.secondUnit.setBackgroundResource(R.drawable.blue_rounded_button)
            binding.secondUnit.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "2"

            binding.oneUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.thirdUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.fourthUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.other.setBackgroundResource(R.drawable.rounded_button)

            binding.oneUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.thirdUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.fourthUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.other.setTextColor(resources.getColor(R.color.colorPrimary))

        }

        binding.thirdUnit.setOnClickListener {
            binding.thirdUnit.setBackgroundResource(R.drawable.blue_rounded_button)
            binding.thirdUnit.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "3"

            binding.oneUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.secondUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.fourthUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.other.setBackgroundResource(R.drawable.rounded_button)

            binding.oneUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.secondUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.fourthUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.other.setTextColor(resources.getColor(R.color.colorPrimary))

        }

        binding.fourthUnit.setOnClickListener {
            binding.fourthUnit.setBackgroundResource(R.drawable.blue_rounded_button)
            binding.fourthUnit.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "4"

            binding.oneUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.thirdUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.secondUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.other.setBackgroundResource(R.drawable.rounded_button)

            binding.oneUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.thirdUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.secondUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.other.setTextColor(resources.getColor(R.color.colorPrimary))

        }

        binding.other.setOnClickListener {
            binding.other.setBackgroundResource(R.drawable.blue_rounded_button)
            binding.other.setTextColor(resources.getColor(R.color.colorWhite))

            unitNumber = "binding.other Files"

            binding.oneUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.thirdUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.fourthUnit.setBackgroundResource(R.drawable.rounded_button)
            binding.secondUnit.setBackgroundResource(R.drawable.rounded_button)

            binding.oneUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.thirdUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.fourthUnit.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.secondUnit.setTextColor(resources.getColor(R.color.colorPrimary))

        }

        binding.imageFormat.setOnClickListener {
            binding.imageFormat.setBackgroundResource(R.drawable.blue_rounded_button)
            binding.imageFormat.setTextColor(resources.getColor(R.color.colorWhite))

            fileType = "Image"

            binding.pdfFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.pdfFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.pptFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.pptFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.docxFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.docxFormat.setTextColor(resources.getColor(R.color.colorPrimary))


        }


        binding.pdfFormat.setOnClickListener {
            binding.pdfFormat.setBackgroundResource(R.drawable.blue_rounded_button)
            binding.pdfFormat.setTextColor(resources.getColor(R.color.colorWhite))

            fileType = "PDF"

            binding.pptFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.pptFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.docxFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.docxFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.imageFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.imageFormat.setTextColor(resources.getColor(R.color.colorPrimary))



        }

        binding.pptFormat.setOnClickListener {
            binding.pptFormat.setBackgroundResource(R.drawable.blue_rounded_button)
            binding.pptFormat.setTextColor(resources.getColor(R.color.colorWhite))

            fileType = "PPT"

            binding.docxFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.docxFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.pdfFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.pdfFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.imageFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.imageFormat.setTextColor(resources.getColor(R.color.colorPrimary))


        }

        binding.docxFormat.setOnClickListener {
            binding.docxFormat.setBackgroundResource(R.drawable.blue_rounded_button)
            binding.docxFormat.setTextColor(resources.getColor(R.color.colorWhite))

            fileType = "DOC"

            binding.pptFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.pptFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.pdfFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.pdfFormat.setTextColor(resources.getColor(R.color.colorPrimary))
            binding.imageFormat.setBackgroundResource(R.drawable.rounded_button)
            binding.imageFormat.setTextColor(resources.getColor(R.color.colorPrimary))


        }


        binding.publishFile.setOnClickListener{

            val fileName: String = binding.editFileTitle.text.toString()
            val subjectName: String = binding.subjectNameSpinner.text.toString()

            if(fileSelected.equals("0")) {
                Toast.makeText(this, "Please select File", Toast.LENGTH_LONG).show()
            } else if(isNullOrEmpty(fileName)) {
                binding.edtFileTitle.error = "Please enter Title for File"
            } else if(isNullOrEmpty(subjectName)) {
                binding.edtFileTitle.error = null
                binding.edtSubjectName.error = "Please select related Subject"
            } else if(unitNumber?.let { it1 -> isNullOrEmpty(it1) }!!) {
                binding.edtSubjectName.error = null
                binding.edtFileTitle.error = null

                Toast.makeText(this, "Please choose Unit Number", Toast.LENGTH_LONG).show()
            } else if(fileType?.let { it1 -> isNullOrEmpty(it1) }!!){
                binding.edtSubjectName.error = null
                binding.edtFileTitle.error = null
                Toast.makeText(this, "Please choose File Type", Toast.LENGTH_LONG).show()
            } else {
                binding.edtSubjectName.error = null
                binding.edtFileTitle.error = null

                binding.edtFileTitle.isEnabled = false
                binding.editFileTitle.isEnabled = false
                binding.edtSubjectName.isEnabled = false
                binding.deleteIcon.isEnabled = false

                binding.oneUnit.isEnabled = false
                binding.secondUnit.isEnabled = false
                binding.thirdUnit.isEnabled = false
                binding.fourthUnit.isEnabled = false
                binding.other.isEnabled = false

                binding.pptFormat.isEnabled = false
                binding.pdfFormat.isEnabled = false

                upload(fileName, subjectName, unitNumber, fileType)

            }

        }

        
        binding.closeButton.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            startActivity(intent)
            finish()
            
        }

        binding.deleteIcon.setOnClickListener {

            fileSelected = "0"

            binding.uploadFileButton.text = "Upload File"
            binding.statusIcon.visibility = View.VISIBLE
            binding.deleteIcon.visibility = View.GONE
            binding.uploadFileButton.isEnabled = true

            binding.greenStatusIcon.visibility = View.GONE
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            uri = data!!.data!!

            fileSelected = "1"
            binding.statusIcon.visibility = View.GONE
            binding.greenStatusIcon.visibility = View.VISIBLE
            binding.uploadFileButton.text = "File Selected !"
            binding.uploadFileButton.isEnabled = false

            binding.deleteIcon.visibility = View.VISIBLE

            //upload()
        }
    }

    

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    private fun upload(
        fileName: String?,
        subjectName: String?,
        unitNumber: String?,
        fileType: String?
    ) {

        var mReference = mStorage.child(fileName.toString())

        Toast.makeText(
            this,
            "Upload in Progress, Please don't close this Window",
            Toast.LENGTH_SHORT
        ).show()

        mReference.putFile(uri).addOnProgressListener { taskSnapshot ->

            val bytesTransferred = taskSnapshot.bytesTransferred
            val totalByteCount = taskSnapshot.totalByteCount

            val progress = (100.0 * bytesTransferred) / totalByteCount

            binding.publishFile.visibility = View.INVISIBLE

            binding.customProgressBar.visibility = View.VISIBLE
            binding.txtProgress.visibility = View.VISIBLE

            binding.customProgressBar.progress = progress.toInt()
            binding.txtProgress.text = "${progress.toInt()}%"
        }
            .continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }

            mReference.downloadUrl

        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {

               var downloadUri = task.result.toString()

                binding.customProgressBar.visibility = View.GONE
                binding.txtProgress.visibility = View.GONE

                binding.publishFile.visibility = View.VISIBLE
                binding.publishFile.text = "Successfully Uploaded !"
                binding.publishFile.setBackgroundResource(R.drawable.green_rounded_button)

                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss")
                val currentDate = sdf.format(Date())


                val fileData = FileUploadData(
                    fileName,
                    subjectName,
                    unitNumber,
                    fileType,
                    downloadUri.toString(),
                    AppPreferences.studentName,
                    currentDate.toString()
                )
                database.child(AppPreferences.studentID).child("files_data").push().setValue(
                    fileData
                )



                try {
                    val queue = com.android.volley.toolbox.Volley.newRequestQueue(this)
                    val url = "https://fcm.googleapis.com/fcm/send"


                    TOPIC = "/topics/"+ AppPreferences.studentID

                    val data = JSONObject()
                    data.put("title",  AppPreferences.studentName + " Uploaded a File!" )
                    data.put("message", fileName + " of " + subjectName + " Uploaded into Stumate" + "\nPlease tap here to open the Class Notes Page")

                    Log.e(TAG, "" + data)

                    val notification_data = JSONObject()
                    notification_data.put("data", data)
                    notification_data.put("to", TOPIC)

                    Log.e(TAG, "" + notification_data)
                    val request: com.android.volley.toolbox.JsonObjectRequest = object :
                        com.android.volley.toolbox.JsonObjectRequest(url, notification_data, object :
                            com.android.volley.Response.Listener<JSONObject?> {
                            override fun onResponse(response: JSONObject?) {

                                var dialog = Dialog(this@UploadFilesActivity)
                                dialog.setContentView(R.layout.upload_success_layout)
                                dialog.setCanceledOnTouchOutside(false)
                                dialog.setCancelable(false)
                                dialog.findViewById<MaterialButton>(R.id.back_to_home).setOnClickListener {
                                    dialog.dismiss()
                                    val intent = Intent(
                                        this@UploadFilesActivity,
                                        HomeActivity::class.java
                                    )
                                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                                    startActivity(intent)
                                    finish()

                                }
                                dialog.window!!.setBackgroundDrawableResource(android.R.color.transparent)

                                dialog.show()


                                // Toast.makeText(baseContext, "Notification sent Successfully to all the Class Mates!", Toast.LENGTH_LONG).show()
                            }
                        }, object : com.android.volley.Response.ErrorListener {
                            override fun onErrorResponse(error: com.android.volley.VolleyError?) {
                                Toast.makeText(
                                    baseContext,
                                    "Error:   " + error.toString(),
                                    Toast.LENGTH_LONG
                                ).show()

                            }
                        }) {
                        override fun getHeaders(): Map<String, String> {
                            val params: MutableMap<String, String> = HashMap()
                            params["Authorization"] = serverKey
                            params["Content-Type"] = contentType
                            return params
                        }
                    }
                    queue.add(request)
                } catch (e: Exception) {
                    e.printStackTrace()
                }



            } else {
                // Handle failures
                // ...
                Toast.makeText(this, "Something Error Occurred !", Toast.LENGTH_LONG).show()

                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                startActivity(intent)
                finish()

            }
        }

    }


/*
    private fun setUpSubjectList() {
        val subjectNames = listOf("Probability and Statistics", "Web Technologies", "Computer Networks", "Operating Systems","Software Engineering","Computer Organization")
        val adapter = ArrayAdapter(this,
            R.layout.list_item, subjectNames)
        (binding.subjectNameSpinner as? AutoCompleteTextView)?.setAdapter(adapter)
    }
*/

    private fun isNullOrEmpty(str: String): Boolean {
        if (str != null && !str.trim().isEmpty())
            return false
        return true
    }



}