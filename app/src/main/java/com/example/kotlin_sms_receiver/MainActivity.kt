package com.example.kotlin_sms_receiver

import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Switch
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    //ask for permission code
    val MY_PERMISSIONS_REQUEST_RECEIVE_SMS: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //check if permission is not granted
        var context: Context
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECEIVE_SMS) != PackageManager.PERMISSION_GRANTED){
            //if the permission is not granted then check if the user has denied the permission
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.RECEIVE_SMS)){

                //do nothing as user has denied

            } else {
                //a pop up will appear asking required permission i.e Allow or Deny
                ActivityCompat.requestPermissions(this, arrayOf<String>(android.Manifest.permission.RECEIVE_SMS), MY_PERMISSIONS_REQUEST_RECEIVE_SMS)
            }
        }
    }//onCreate
    // after getting the result of permission requests the result will be passed throught this method
    override fun onRequestPermissionsResult( requestCode:Int, permissions:Array<String>, grantResults:IntArray){

        when(requestCode){
            MY_PERMISSIONS_REQUEST_RECEIVE_SMS -> {
                //check wheather the length of grantResults if greater than 0 and is equal to PERMISSIONS_GRANTED
                if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //***************** Now broadcastreceiver will work in background
                    Toast.makeText(this, "Obrigado por nos conceder permissão!", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Ação bloqueda, permissão não concedida pelo usuário.", Toast.LENGTH_LONG).show()
                }
            }
        }

    }
}
