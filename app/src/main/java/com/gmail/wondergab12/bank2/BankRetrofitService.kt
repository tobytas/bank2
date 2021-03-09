package com.gmail.wondergab12.bank2

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface BankRetrofitService {
    @GET("atm")
    fun listATM(
        @Query("city") city: String,
        @Query("ATM_currency") currency: String
    ): Call<List<Atm>>
}
