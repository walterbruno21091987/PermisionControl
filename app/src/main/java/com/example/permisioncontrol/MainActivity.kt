package com.example.permisioncontrol

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.permisioncontrol.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

binding.btnPhoto.setOnClickListener { checkPermissions()
}

    }

    private fun checkPermissions() {
       if(ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED){
           //permiso no aceptado por el momento PRIMERA VEZ
           requesCameraPermission()
       }else{
           //abrir camara
           openCamera()
       }

    }

    private fun openCamera() {
        Toast.makeText(this,"Abriendo camara",Toast.LENGTH_LONG).show()
    }

    private fun requesCameraPermission() {
        if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.CAMERA)){
            //el usuario ya rechazo el permiso
            Toast.makeText(this,"Permisos rechazados",Toast.LENGTH_LONG).show()
        }else{
            ActivityCompat.requestPermissions(this,arrayOf(Manifest.permission.CAMERA),777)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==777){
            //nuestros permisos
            if(grantResults.isNotEmpty()&&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openCamera()
            }else{
                //el permiso no ha sido aceptado
            Toast.makeText(this,"Permisos rechazados por primera vez",Toast.LENGTH_LONG).show()
            }
        }
    }
}