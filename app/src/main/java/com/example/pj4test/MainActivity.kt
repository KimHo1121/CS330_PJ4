package com.example.pj4test

import android.Manifest.permission.CAMERA
import android.Manifest.permission.RECORD_AUDIO
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.pj4test.fragment.CameraFragment
import com.example.pj4test.fragment.AudioFragment
import java.util.*

interface CallBack {
    fun callBackExample(c: Int)
}

class MainActivity : AppCompatActivity(), CallBack {
    private val TAG = "MainActivity"

    override fun callBackExample(c: Int) {
        if (c == 1) {
            val fm = supportFragmentManager
            val tr = fm.beginTransaction()
            val cameraF = CameraFragment()
            tr.replace(R.id.cameraFragmentContainerView, cameraF, "com.example.pj4test.fragment.CameraFragment").commit()
        }
    }

    // permissions
    private val permissions = arrayOf(RECORD_AUDIO, CAMERA)
    private val PERMISSIONS_REQUEST = 0x0000001;

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        checkPermissions() // check permissions
    }

    private fun checkPermissions() {
        if (permissions.all{ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_GRANTED}){
            Log.d(TAG, "All Permission Granted")
        }
        else{
            requestPermissions(permissions, PERMISSIONS_REQUEST)
        }
    }
}