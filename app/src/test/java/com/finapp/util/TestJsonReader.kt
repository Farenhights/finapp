package com.finapp.util

object TestJsonReader {
    fun readJson(fileName: String): String {
        val inputStream = ClassLoader.getSystemResourceAsStream(fileName)
            ?: throw IllegalArgumentException("Arquivo $fileName não encontrado em resources")
        return inputStream.bufferedReader().use { it.readText() }
    }
}