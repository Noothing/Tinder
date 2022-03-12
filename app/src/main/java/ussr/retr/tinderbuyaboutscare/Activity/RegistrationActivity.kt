package ussr.retr.tinderbuyaboutscare.Activity

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import ussr.retr.tinderbuyaboutscare.R
import ussr.retr.tinderbuyaboutscare.databinding.ActivityRegistrationBinding
import java.io.IOException
import java.io.OutputStream
import java.util.*

class RegistrationActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private lateinit var binding: ActivityRegistrationBinding
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegistrationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initCamera()
    }

    override fun onResume() {
        super.onResume()

        binding.avatarContainer.setOnClickListener {
            Log.d("tag", "click")
            getPhoto()
        }

        binding.save.setOnClickListener {
            val intent = Intent(this@RegistrationActivity, TinderActivity::class.java)
            startActivity(intent)
        }
    }

    fun initCamera() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                if (result.resultCode == Activity.RESULT_OK) {
                    // Достаем данные из интента
                    val data: Intent? = result.data
                    // Достаем картинку из данных
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    val contentValues = ContentValues()
                    contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, "${UUID.randomUUID()}")
                    contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png")
                    contentValues.put(
                        MediaStore.MediaColumns.RELATIVE_PATH,
                        Environment.DIRECTORY_PICTURES
                    )
                    val imageUri = contentResolver.insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        contentValues
                    )
                    try {
                        val fos: OutputStream = contentResolver.openOutputStream(imageUri!!)!!
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, fos)
                        fos.flush()
                        fos.close()
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }

                    Picasso.with(this).load(imageUri).into(binding.avatarImg)
                }
            }

    }

    fun getPhoto() {
        if (hasCamera()) {
            Log.d("tag", "Has")
            val camera = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            try {
                // Запускаем контракт
                resultLauncher.launch(camera)
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(applicationContext, "Error", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.d("tag", "Has not")
            EasyPermissions.requestPermissions(
                this,
                "I need your camera",
                0,
                android.Manifest.permission.CAMERA
            )
        }
    }

    fun hasCamera(): Boolean {
        return EasyPermissions.hasPermissions(this, android.Manifest.permission.CAMERA)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            0 -> {
                getPhoto()
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            // Создаем андроидовское уведомление ЧТО ЭТО ПРАВДА НАМ НУЖНО и давайка ты сам через настройки сделаешь как надо
            AppSettingsDialog.Builder(this).build().show()
        }
    }
}