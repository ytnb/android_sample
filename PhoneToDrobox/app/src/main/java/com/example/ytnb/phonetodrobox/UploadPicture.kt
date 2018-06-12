package com.example.ytnb.phonetodrobox

import android.os.AsyncTask
import android.util.Log
import com.dropbox.core.DbxException
import com.dropbox.core.v2.DbxClientV2
import com.dropbox.core.v2.files.FileMetadata
import com.dropbox.core.v2.files.WriteMode
import java.io.File

class UploadPicture(
        dbxClientV2: DbxClientV2,
        dropBoxPath: String,
        localFile: File
): AsyncTask<Void, Int, FileMetadata?>() {
    private val mDbxClientV2 = dbxClientV2
    private val mDropBoxPath = dropBoxPath
    private val mLocalFile = localFile
    private lateinit var mListener: UploadPictureListener
    private var mException: Exception? = null

    interface UploadPictureListener {
        fun onPreUploadPicture()
        fun onProgressUploadPicture(progress: Int)
        fun onPostUploadPicture(result: FileMetadata)
        fun onError(e: Exception)
    }

    fun setUploadPictureListener(listener: UploadPictureListener) {
        mListener = listener
    }

    override fun onPreExecute() {
        super.onPreExecute()
        mListener.onPreUploadPicture()
    }

    override fun doInBackground(vararg params: Void?): FileMetadata? {
        try {
            return mDbxClientV2.files().uploadBuilder("$mDropBoxPath${mLocalFile.name}")
                    .withMode(WriteMode.OVERWRITE)
                    .uploadAndFinish(mLocalFile.inputStream())
        } catch (e: DbxException) {
            Log.e("UploadPicture", "Failed to upload file", e)
            mException = e
        }
        return null
    }

    override fun onProgressUpdate(vararg values: Int?) {
        super.onProgressUpdate(*values)
        val progress = values[0] ?: 0
        mListener.onProgressUploadPicture(progress)
    }

    override fun onPostExecute(result: FileMetadata?) {
        super.onPostExecute(result)
        if (mException != null) {
            val e = mException ?: return
            mListener.onError(e)
        }
        val fm = result ?: return
        mListener.onPostUploadPicture(fm)
    }
}