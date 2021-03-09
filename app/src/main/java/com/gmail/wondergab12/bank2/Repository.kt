package com.gmail.wondergab12.bank2

import android.content.Context
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.IllegalStateException

class Repository private constructor(context: Context) {

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://belarusbank.by/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val service: BankRetrofitService = retrofit
        .create(BankRetrofitService::class.java)

    fun listATM(city: String, currency: String): Call<List<Atm>> =
        service.listATM(city, currency)

    companion object {
        private var INSTANCE: Repository? = null

        fun init(context: Context) {
            INSTANCE = INSTANCE ?: Repository(context)
        }

        fun getInstance(): Repository =
            INSTANCE ?: throw IllegalStateException("Repository must be initialized")
    }
}
