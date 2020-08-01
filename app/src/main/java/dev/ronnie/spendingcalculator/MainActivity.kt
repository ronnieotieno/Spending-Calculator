package dev.ronnie.spendingcalculator

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import dev.ronnie.spendingcalculator.utils.Event
import dev.ronnie.spendingcalculator.utils.EventObject
import dev.ronnie.spendingcalculator.utils.SMS_PERMISSION_REQUEST

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            SMS_PERMISSION_REQUEST -> if (grantResults.isNotEmpty() &&
                grantResults[0] == PackageManager.PERMISSION_GRANTED
            ) {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_SMS
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    EventObject.statusMessage.value =
                        Event("Permission Granted")
                }
            } else {
                EventObject.statusMessage.value =
                    Event("Permission Denied")
            }
        }
    }
}