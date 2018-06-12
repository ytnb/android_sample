package com.example.ytnb.fingerprintauth

import android.content.Context
import android.content.pm.PackageManager
import android.hardware.fingerprint.FingerprintManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fingerAuth()
    }

    private fun fingerAuth() {
        val fingerprintManager = getSystemService(Context.FINGERPRINT_SERVICE) as FingerprintManager
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {
            return
        }
        if (fingerprintManager.isHardwareDetected || fingerprintManager.hasEnrolledFingerprints()) {
            fingerprintManager.authenticate(
                    null,
                    null,
                    0,
                    object: FingerprintManager.AuthenticationCallback() {
                        override fun onAuthenticationError(errorCode: Int, errString: CharSequence?) {
                            //super.onAuthenticationError(errorCode, errString)
                            tv_fingerprint_status.text = "${errString}(error code:${errorCode})"
                        }

                        override fun onAuthenticationFailed() {
                            //super.onAuthenticationFailed()
                            tv_fingerprint_status.text = resources.getString(R.string.fingerprint_not_recognized)
                        }

                        override fun onAuthenticationHelp(helpCode: Int, helpString: CharSequence?) {
                            //super.onAuthenticationHelp(helpCode, helpString)
                            tv_fingerprint_status.text = "$helpString (help code: $helpCode)"
                        }

                        override fun onAuthenticationSucceeded(result: FingerprintManager.AuthenticationResult?) {
                            //super.onAuthenticationSucceeded(result)
                            tv_fingerprint_status.text = resources.getString(R.string.fingerprint_success)
                        }
                    },
                    Handler())
        }
    }
}
