package com.gmail.wondergab12.bank2

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose

@Entity
data class Atm(
    @PrimaryKey
    @Expose
    val id:                 String = "",
    @Expose
    val area:               String = "",
    @Expose
    val cityType:           String = "",
    @Expose
    val city:               String = "",
    @Expose
    val addressType:        String = "",
    @Expose
    val house:              String = "",
    @Expose
    val installPlace:       String = "",
    @Expose
    val workTime:           String = "",
    @Expose
    val gpsX:               String = "",
    @Expose
    val gpsY:               String = "",
    @Expose
    val installPlaceFull:   String = "",
    @Expose
    val workTimeFull:       String = "",
    @Expose
    val atmType:            String = "",
    @Expose
    val atmError:           String = "",
    @Expose
    val currency:           String = "",
    @Expose
    val atmPrinter:         String = ""
)
