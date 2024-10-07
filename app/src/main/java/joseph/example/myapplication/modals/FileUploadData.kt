package joseph.example.myapplication.modals

import android.os.Parcelable
import com.google.firebase.database.IgnoreExtraProperties
import com.google.firebase.database.PropertyName
import kotlinx.parcelize.Parcelize

@IgnoreExtraProperties
@Parcelize
data class FileUploadData (
    @PropertyName("fileName")
    var fileName: String? = "",
    @PropertyName("subjectName")
    var subjectName: String? = "",
    @PropertyName("unitNumber")
    var unitNumber: String? = "",
    @PropertyName("fileType")
    var fileType: String? = "",
    @PropertyName("fileURL")
    var fileURL: String? = "",
    @PropertyName("studentName")
    var studentName: String? = "",
    @PropertyName("dateOfPublishing")
    var dateOfPublishing: String? = ""
):Parcelable