package com.example

import java.util.concurrent.LinkedBlockingDeque

object ChatHistory {
    private const val MAX_HISTORY = 50
    private val history = LinkedBlockingDeque<String>(MAX_HISTORY)

    fun add(message: String) {
        if (history.size == MAX_HISTORY) history.pollFirst()
        history.offerLast(message)
    }

    fun getRecent(): List<String> = history.toList()
}