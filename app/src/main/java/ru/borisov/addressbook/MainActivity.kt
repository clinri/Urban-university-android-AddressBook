package ru.borisov.addressbook

import android.content.Intent
import android.graphics.Typeface
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    val personList: MutableList<Person> = mutableListOf(
        Person("Иван", "Иванов", "г. Москва", "8-800-000-00-00"),
        Person("Роман", "Сидоров", "г. Воронеж", "8-860-001-00-00"),
    )
    lateinit var nameET: EditText
    lateinit var surnameET: EditText
    lateinit var addressET: EditText
    lateinit var phoneET: EditText
    lateinit var saveBTN: Button
    lateinit var nameSurnameListLV: ListView
    lateinit var adapter: ArrayAdapter<Person>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        nameET = findViewById(R.id.nameET)
        surnameET = findViewById(R.id.surnameET)
        addressET = findViewById(R.id.addressET)
        phoneET = findViewById(R.id.phoneET)
        saveBTN = findViewById(R.id.saveBTN)
        nameSurnameListLV = findViewById(R.id.nameSurnameListLV)
        adapter = object : ArrayAdapter<Person>(
            /* context = */ this,
            /* resource = */ android.R.layout.simple_list_item_1,
            /* objects = */ personList
        ) {
            override fun getView(
                position: Int,
                convertView: android.view.View?,
                parent: android.view.ViewGroup,
            ): android.view.View {
                return super.getView(position, convertView, parent).apply {
                    val person = getItem(position)
                    val name = person?.name ?: ""
                    val surname = person?.surname ?: ""
                    val text = "$name $surname"
                    val textView = findViewById<TextView>(android.R.id.text1)
                    textView.text = text
                    textView.typeface = Typeface.DEFAULT
                }
            }
        }
        nameSurnameListLV.adapter = adapter
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        saveBTN.setOnClickListener {
            val name = nameET.text.toString()
            val surname = surnameET.text.toString()
            val address = addressET.text.toString()
            val phone = phoneET.text.toString()
            val person = Person(name, surname, address, phone)
            personList.add(person)
            adapter.notifyDataSetChanged()
            nameET.text.clear()
            surnameET.text.clear()
            addressET.text.clear()
            phoneET.text.clear()
        }
        nameSurnameListLV.setOnItemClickListener { _, _, position, _ ->
            val person = adapter.getItem(position)
            startActivity(Intent(this, PersonActivity::class.java).apply {
                putExtra(Person::class.java.simpleName, person)
            })
        }
    }
}