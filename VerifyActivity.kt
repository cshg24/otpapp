package com.example.otpapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider

class VerifyActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify)
        auth = FirebaseAuth.getInstance()
        val otpEditText = findViewById<EditText>(R.id.otpEditText)
        val verifyBtn = findViewById<Button>(R.id.verifyOtpButton)
        val verificationId = intent.getStringExtra("verificationId")
        verifyBtn.setOnClickListener {
            val otp = otpEditText.text.toString()
            val credential = PhoneAuthProvider.getCredential(verificationId!!, otp)
            auth.signInWithCredential(credential).addOnCompleteListener { task ->
                if (task.isSuccessful) Toast.makeText(this, "تم التحقق بنجاح", Toast.LENGTH_SHORT).show()
                else Toast.makeText(this, "فشل التحقق", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
