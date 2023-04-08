package com.example.retrofitmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import com.example.retrofitmanager.retrofit.MoneyApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private var progress = true
    private val coinMassive: ArrayList<Double> = ArrayList()
    private var num = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView0: TextView = findViewById(R.id.textView)
        val textView1: TextView = findViewById(R.id.textView2)
        val textView2: TextView = findViewById(R.id.textView3)
        val textView3: TextView = findViewById(R.id.textView4)

        val textMoney: TextView = findViewById(R.id.textMoney.toInt())
        val textResult: TextView = findViewById(R.id.textResult)
        val editText: EditText = findViewById(R.id.editText)

        val buttonUSD: Button = findViewById(R.id.buttonUSD)
        val buttonEUR: Button = findViewById(R.id.buttonEUR)
        val imageButton: ImageView = findViewById(R.id.imageView)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://www.cbr-xml-daily.ru/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val moneyApi = retrofit.create(MoneyApi::class.java)
            CoroutineScope(Dispatchers.IO).launch {
                val money = moneyApi.getMonyById()
                runOnUiThread {
                    coinMassive.add(money.Valute.USD.Value.toString().toDouble())
                    coinMassive.add(money.Valute.EUR.Value.toString().toDouble())
                    textView0.text = "Доллар: " + money.Valute.USD.Value
                    textView1.text = "Евро: " + money.Valute.EUR.Value
                    textView2.text = "Юань: " + money.Valute.CNY.Value
                    textView3.text = "Иена: " + money.Valute.JPY.Value
                }
            progress = false
        }
        buttonUSD.setOnClickListener { num = 0; textMoney.text = "Доллар" }
        buttonEUR.setOnClickListener { num = 1; textMoney.text = "Евро" }

        imageButton.setOnClickListener {
            progress = true
            textResult.text = "Result: ${editText.text.toString().toDouble() * coinMassive.get(num)}"
        }
    }
}