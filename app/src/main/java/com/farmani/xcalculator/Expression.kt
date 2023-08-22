package com.farmani.xcalculator

import java.util.Stack

class Expression(var infixExpression: MutableList<String>) {
    private var postFix: String = ""

    // It's private to follow encapsulation rule (It's used only in this class)
    private fun infixToPostfix() {
        var result = ""
        val stack = Stack<String>()
        for (element in infixExpression) {
            if (element.all { it.isDigit() } || element.any { it == '.' }) {
                result += "$element "
            } else if (element == "(") {
                stack.push(element)
            } else if (element == ")") {
                while (stack.peek() != "(" && stack.isNotEmpty()) {
                    // pop returns the value then empties the stack
                    result += "${stack.pop()} "
                }

                if (stack.isNotEmpty()) {
                    stack.pop()
                }
            } else {
                while (stack.isNotEmpty() && precedence(stack.peek()) >= precedence(element)) {
                    result += "${stack.pop()} "
                }
                stack.push(element)
            }
        }
        while (stack.isNotEmpty()) {
            // returns the last item which is left inside stack
            result += "${stack.pop()} "
        }
        postFix = result
    }

    private fun precedence(operator: String): Int {
        return when (operator) {
            // Higher number means higher priority
            "×", "÷" -> 2
            "+", "−" -> 1
            else -> 0
        }
    }

    fun evaluateExpression(): Number {
        infixToPostfix()
        val stack = Stack<Double>()
        var i = 0
        // Use while cause we need to skip some steps
        while (i < postFix.length) {
            if (postFix[i] == ' ') {
                i++
                continue
            } else if (Character.isDigit(postFix[i])) {
                var number = ""
                while (Character.isDigit(postFix[i]) || postFix[i] == '.') {
                    number += postFix[i]
                    i++
                }
                stack.push(number.toDouble())
            } else {
                val x = stack.pop() // first top number of stack
                val y = stack.pop() // second top number of stack
                when (postFix[i]) {
                    '×' -> stack.push(y * x)
                    '÷' -> stack.push(y / x)
                    '+' -> stack.push(y + x)
                    '−' -> stack.push(y - x)
                }
            }
            i++
        }
        // to change numbers like 2.0 into 2
        return if (stack.peek() / stack.peek().toInt() == 1.0) stack.peek()
            .toInt() else stack.peek()
    }
}