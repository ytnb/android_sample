package com.example.ytnb.phonetodrobox

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.FileProvider
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.dropbox.core.DbxRequestConfig
import com.dropbox.core.android.Auth
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        private const val APP_KEY = "xxxxxxxxxxx"
        private const val NEW_PICTURE = 1
        private const val PHOTO_DIR = "/Photos/"
        private const val MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE = 1
    }
    private var mLoggedIn = false
    private var mDbxClientV2: DbxClientV2? = null
    private lateinit var mAppName: String
    private var mCameraFileName: String = ""
    private lateinit var mImageView: ImageView
    private var mFile: File? = null
    private var mUri: Uri? = null
//    private var mSwingListener: SwingListener? = null
    private lateinit var mProgressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAppName = R.string.app_name.toString()
        mImageView = iv_picture
        mProgressBar = progressBar
        mProgressBar.visibility = View.GONE

        checkWriteExternalPermission()

        btn_login.setOnClickListener {
            if (mLoggedIn) {
                logOut()
                btn_login.setText(R.string.login)
            } else {
                Auth.startOAuth2Authentication(this@MainActivity, APP_KEY)
            }
        }

        btn_photo.setOnClickListener {
            val df = SimpleDateFormat("yyyy-MM-dd-kk-mm-ss", Locale.US)
            val newPicFile = df.format(Date()) + ".jpg"
            val newDir = File(applicationContext.filesDir, "images")
            newDir.mkdirs()

            val newFile = File(newDir, newPicFile)
            mCameraFileName = newFile.toString()
            Log.i(mAppName, "Importion New Picture: $mCameraFileName")

            mUri = FileProvider.getUriForFile(
                    this@MainActivity,
                    applicationContext.packageName + ".fileprovider",
                    newFile
            )
            Log.i(mAppName, "url: $mUri")

            val intent = Intent().also {
                it.action = MediaStore.ACTION_IMAGE_CAPTURE
                it.putExtra(MediaStore.EXTRA_OUTPUT, mUri)
            }
            try {
                startActivityForResult(intent, NEW_PICTURE)
            } catch (e: ActivityNotFoundException) {
                showToast("There doesn't seem to be a camera.")
            }
        }

        btn_upload.isEnabled = false
        btn_upload.setOnClickListener {
            if (mFile == null) {
                showToast("Take a picture!!")
            }
            val localFile = mFile ?: return@setOnClickListener
            if (mDbxClientV2 == null) {
                showToast("ログインして")
            }
            val dbxClient = mDbxClientV2 ?: return@setOnClickListener

            UploadPicture(dbxClient, PHOTO_DIR, localFile).also {
                it.setUploadPictureListener(object : UploadPicture.UploadPictureListener {
                    override fun onPreUploadPicture() {
//                        mProgressBar = ProgressBar(this@MainActivity)
                        mProgressBar.isIndeterminate = true
                        mProgressBar.visibility = View.VISIBLE
                    }

                    override fun onProgressUploadPicture(progress: Int) {
                    }

                    override fun onPostUploadPicture(result: FileMetadata) {
                        mProgressBar.visibility = View.GONE
                    }

                    override fun onError(e: Exception) {
                        mProgressBar.visibility = View.GONE
                        showToast("An error has occurred")
                    }
                })
                it.execute()
            }
        }
//        mSwingListener = SwingListener(this)
//        mSwingListener?.setOnSwingListener(object : SwingListener.OnSwingListener {
//            override fun onSwing() {
//                val sourceFile = mFile ?: return
//                val dbxclient = mDbxClientV2 ?: return
//                UploadPicture(dbxclient, PHOTO_DIR, sourceFile).apply {
//                    setUploadPictureListener(object : UploadPicture.UploadPictureListener {
//                        override fun onPreUploadPicture() {
//                            mProgressBar = ProgressBar(this@MainActivity)
//                            mProgressBar?.isIndeterminate = true
//                        }
//
//                        override fun onProgressUploadPicture(progress: Int) {
//                        }
//
//                        override fun onPostUploadPicture(result: FileMetadata) {
//                            mProgressBar?.visibility = View.GONE
//                        }
//
//                        override fun onError(e: Exception) {
//                            mProgressBar?.visibility = View.GONE
//                            Toast.makeText(
//                                    this@MainActivity,
//                                    "An error has occurred",
//                                    Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    })
//                    execute()
//                }
//            }
//        })
//        mSwingListener?.registSensor()
    }

    override fun onResume() {
        super.onResume()
        val accessToken = Auth.getOAuth2Token()
        if (accessToken != null) {
            if (mDbxClientV2 == null) {
                val versionName = this.packageManager.getPackageInfo(this.packageName, 0).versionName
                val requestConfig = DbxRequestConfig("$mAppName/$versionName")
                mDbxClientV2 = DbxClientV2(requestConfig, accessToken)
                mLoggedIn = true
                btn_login.setText(R.string.logout)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == NEW_PICTURE) {
            if (resultCode == Activity.RESULT_OK) {
                mUri ?: return

                mImageView.setImageBitmap(decodeSampledBitmap(mImageView.width, mImageView.height))
                mFile = File(mCameraFileName)
                btn_upload.isEnabled = true
                if (mUri != null) {
                    showToast("UPLOADで、Dropboxに写真をアップします")
                }
            } else {
                Log.w(mAppName, "Unknown Activity Result from mediaImport: $resultCode")
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("mCameraFileName", mCameraFileName)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        savedInstanceState?.let {
            mCameraFileName = it.getString("mCameraFileName")
        }
    }

    private fun logOut() {
        mLoggedIn = false
        //todo https://stackoverflow.com/questions/41053675/how-to-disconnect-one-dropbox-account-from-the-app
        //todo https://github.com/dropbox/dropbox-sdk-java/issues/92
        //todo https://gist.github.com/androidovshchik/3a5d39430f1f1506abe0257f352e70b7
    }

    private fun showToast(msg: String) {
        val error = Toast.makeText(this, msg, Toast.LENGTH_LONG)
        error.show()
    }

    private fun calculateSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round((height / reqHeight).toFloat())
            val widthRatio = Math.round((width / reqWidth).toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    private fun decodeSampledBitmap(reqWidth: Int, reqHeight: Int): Bitmap {
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        var stream = applicationContext.contentResolver.openInputStream(mUri)
        BitmapFactory.decodeStream(stream, null, options)

        options.inSampleSize = calculateSampleSize(options, reqWidth, reqHeight)

        options.inJustDecodeBounds = false
        stream.close()

        stream = applicationContext.contentResolver.openInputStream(mUri)
        return BitmapFactory.decodeStream(stream, null, options)
    }

    private fun checkWriteExternalPermission() {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                android.app.AlertDialog.Builder(this)
                        .setTitle("許可が必要です")
                        .setMessage("ファイルを保存してアップロードするために、WRITE_EXTERNAL_STOREAGEを許可してください")
                        .setPositiveButton("OK", object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                requestWriteExternalStorage()
                            }
                        })
                        .setNegativeButton("Cancel", object :DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                showToast("外部へのファイルの保存が許可されなかったので、画像を保存できません")
                            }
                        })
                        .show()

            } else {
                requestWriteExternalStorage()
            }
        }
    }

    private fun requestWriteExternalStorage() {
        ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE
        )
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_WRITE_EXTERNAL_STORAGE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showToast("これで画像をアップロードできます")
                }
                else {
                    showToast("外部へのファイルの保存が許可されなかったので、画像を保存できません")
                }
            }
        }
    }
}
