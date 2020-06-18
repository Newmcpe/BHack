package ru.newmcpe.bhop.api.features

import ru.newmcpe.bhop.Bhop

abstract class Feature (
    val name: String
){
    protected val clientDLL = Bhop.clientDLL
    protected val csgoProcess = Bhop.csgo

    open fun update(){}
}