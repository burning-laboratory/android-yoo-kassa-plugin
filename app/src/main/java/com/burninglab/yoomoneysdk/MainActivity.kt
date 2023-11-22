package com.burninglab.yoomoneysdk

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.burninglab.ymunityagent.YmUnityAgentActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    public fun startPaymentProcess(view: View){
        val intent = Intent(this, YmUnityAgentActivity::class.java)
        startActivity(intent)
    }
}