package com.example.otpapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContentView(R.layout.activity_main)
        auth = FirebaseAuth.getInstance()
        val phoneEditText = findViewById<EditText>(R.id.phoneEditText)
        val sendOtpBtn = findViewById<Button>(R.id.sendOtpButton)
        sendOtpBtn.setOnClickListener {
            val phone = phoneEditText.text.toString()
            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phone)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                    override fun onVerificationCompleted(credential: com.google.firebase.auth.PhoneAuthCredential) {
                        // OTP تم التحقق تلقائيًا
                    }
                    override fun onVerificationFailed(e: Exception) {
                        // خطأ في التحقق
                    }
                    override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                        Intent(this@MainActivity, VerifyActivity::class.java).also {
                            it.putExtra("verificationId", verificationId)
                            startActivity(it)
                        }
                    }
                }).build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        }
    }
}
