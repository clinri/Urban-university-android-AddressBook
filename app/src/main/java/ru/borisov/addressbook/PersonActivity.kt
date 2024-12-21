package ru.borisov.addressbook

import android.content.Intent
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class PersonActivity : AppCompatActivity() {
    lateinit var nameTV: TextView
    lateinit var surnameTV: TextView
    lateinit var addressTV: TextView
    lateinit var phoneTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_person)
        val person = intent.parcelable<Person>(Person::class.java.simpleName)
        nameTV = findViewById(R.id.nameTV)
        surnameTV = findViewById(R.id.surnameTV)
        addressTV = findViewById(R.id.addressTV)
        phoneTV = findViewById(R.id.phoneTV)
        person?.apply {
            nameTV.text = name
            surnameTV.text = surname
            addressTV.text = address
            phoneTV.text = phone
        }
    }

    inline fun <reified T : Parcelable> Intent.parcelable(key: String): T? = when {
        SDK_INT >= 33 -> getParcelableExtra(key, T::class.java)
        else -> @Suppress("DEPRECATION") getParcelableExtra(key) as? T
    }
}