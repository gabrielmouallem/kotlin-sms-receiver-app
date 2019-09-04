package com.example.kotlin_sms_receiver


import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.telephony.SmsMessage
import android.util.Log
import android.widget.Toast

class MyReceiver : BroadcastReceiver() {

    private val SMS_RECEIVED: String = "android.provider.Telephony.SMS_RECEIVED"
    private val TAG: String = "SmsBroadcastReceiver"
    var msg: String = ""
    var phoneNo: String = ""

    override fun onReceive(context: Context, intent: Intent) {
        //retrieves the general action to be performed and display on log
        Log.i(TAG, "Intent Received: " + intent.action)
        if (intent.action == SMS_RECEIVED){

            //retrieves a map of extended data from the intent
            var dataBundle: Bundle? = intent.extras
            if (dataBundle != null){

                //creating PDU(Protocol Data Unity) object witch is a protocol for transferring message
                var mypdu = dataBundle.get("pdus") as Array<Any>
                //arrayOfNulls???
                val message = arrayOfNulls<SmsMessage>(mypdu.size)

                for (i in 0 until mypdu.size) {

                    //for build versions >= API Level 23
                    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                        var format: String? = dataBundle.getString("format")

                        //From PDU we get all object and SmsMessage Objet using following line of code
                        message[i] = SmsMessage.createFromPdu(mypdu[i] as ByteArray, format)
                    } else {

                        //<API Level 23
                        message[i] = SmsMessage.createFromPdu(mypdu[i] as ByteArray)
                    }
                    msg = message[i]!!.messageBody
                    phoneNo = message[i]!!.originatingAddress!!
                }
                val startIntent = context
                    .packageManager
                    .getLaunchIntentForPackage("br.com.irricontrol")

                        startIntent!!.setFlags(
                            Intent.FLAG_ACTIVITY_REORDER_TO_FRONT or
                            Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED
                    )
                context.startActivity(startIntent)
                Toast.makeText(context, "Message: " + msg + "\nNember: " +phoneNo, Toast.LENGTH_LONG).show()
            }
        }
    }
}
