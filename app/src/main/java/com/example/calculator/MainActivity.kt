package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {
    private var result: TextView? = null
    private var numeric: Boolean = false
    private var lastDot: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result = findViewById(R.id.result)
    }

    fun onDigit(view: View) {
        result?.append((view as Button).text)
        numeric = true
        lastDot = false
    }

    fun onClear(view: View) {
        result?.text = ""
    }

    fun onDot(view: View) {
        if (numeric && !lastDot) {
            result?.append(".")
            numeric = false
            lastDot = true
        }
    }

    fun onOperator(view: View) {
        result?.text?.let {
            if (it.isEmpty() && (view as Button).text == "-" || numeric && !operator(it.toString())) {
                result?.append((view as Button).text)
                numeric = false
                lastDot = false
            }
        }
    }

    @SuppressLint("SetTextI18n")
    fun onEqual(view: View) {
        if (numeric) {
            var tvValue = result?.text.toString()
            var prefix = ""
            try {
                if (tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")) {
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    result?.text = (one.toDouble() - two.toDouble()).toString()
                } else if (tvValue.contains("+")) {
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    result?.text = (one.toDouble() + two.toDouble()).toString()
                } else if (tvValue.contains("*")) {
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    result?.text = (one.toDouble() * two.toDouble()).toString()
                } else if (tvValue.contains("/")) {
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    val two = splitValue[1]
                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }
                    result?.text = (one.toDouble() / two.toDouble()).toString()
                }

            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun operator(value: String): Boolean {
        return if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("-")
                    || value.contains("+")
        }
    }
}