package com.finapp.data.remote

import android.content.Context
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody

class MockInterceptor(private val context: Context) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val uri = chain.request().url.toString()

        val responseString = when {
            uri.endsWith("balance") -> loadJSONFromAsset("balance.json")
            uri.endsWith("transactions") -> loadJSONFromAsset("transactions.json")
            uri.endsWith("transfer") -> ""
            else -> "{}"
        }

        return Response.Builder()
            .code(200)
            .message(responseString)
            .request(chain.request())
            .protocol(Protocol.HTTP_1_1)
            .body(
                ResponseBody.create(
                    "application/json".toMediaTypeOrNull(),
                    responseString
                )
            )
            .addHeader("content-type", "application/json")
            .build()
    }

    private fun loadJSONFromAsset(fileName: String): String {
        return context.assets.open(fileName).bufferedReader().use { it.readText() }
    }
}
