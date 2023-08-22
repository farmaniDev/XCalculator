package com.farmani.xcalculator

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton

class MainActivity : AppCompatActivity() {
    private val input = mutableListOf<String>()
    private var result: TextView? = null
    private var infixExpression: Expression? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        result = findViewById(R.id.resultTv)
    }

    // Must be view otherwise onClick won't be accessed
    fun onClick(button: View) {
        // casting to receive texts
        val buttonText = (button as AppCompatButton).text.toString()
        if (Character.isDigit(buttonText[0]) || buttonText[0] == '.') {
            // check if the last index is digit
            if (input.isNotEmpty() && Character.isDigit(input.last()[0])) {
                input[input.lastIndex] = input.last() + buttonText
                result?.text = "${result?.text}${button.text}"
            } else {
                input.add(buttonText)
                result?.text = "${result?.text} ${button.text}"
            }
        } else {
            input.add(buttonText)
            result?.text = "${result?.text} ${button.text}"
        }
    }

    fun equation(button: View) {
        infixExpression = Expression(input)
        result?.text = infixExpression!!.evaluateExpression().toString()
        // Continue app with result
        input.clear()
        input.add(result?.text.toString())
    }

    fun clear(button: View) {
        input.clear()
        result?.text = ""
    }

    fun baskSpace(button: View) {
        if (input.isNotEmpty()) {
            result?.text = "${result?.text}".dropLast(1)
            if (result?.text?.last() == ' ')
                result?.text = "${result?.text}".dropLast(1)

            // One digit
            if (input.last().length == 1)
                input.removeAt(input.lastIndex)
            else
                input[input.lastIndex] = input.last().dropLast(1)
        }
    }
}