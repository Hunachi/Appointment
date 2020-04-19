package io.github.hunachi.appointment.ui.profile

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.hunachi.appointment.R

class ProfileActivity : AppCompatActivity() {

    companion object{
        fun createIntent(context: Context) = Intent(context, ProfileActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
    }
}
