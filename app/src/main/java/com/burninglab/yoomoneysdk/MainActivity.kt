package com.burninglab.yoomoneysdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.burninglab.yookassaunityplugin.YooKassaUnityPluginActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun startPaymentProcess(view: View){
        val intent = Intent(this, YooKassaUnityPluginActivity::class.java)
        startActivity(intent)
    }
}