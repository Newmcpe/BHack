package ru.newmcpe.bhop.offsets

import ru.newmcpe.bhop.Bhop
import ru.newmcpe.bhop.Bhop.clientDLL
import ru.newmcpe.bhop.Bhop.engineDLL
import ru.newmcpe.bhop.offsets.netvars.findFirstClass
import ru.newmcpe.bhop.util.get
import ru.newmcpe.bhop.util.invoke

object ClientOffsets {
    var dwLocalPlayer by clientDLL(3, 4)(
        0x8D, 0x34, 0x85, 0[4], 0x89, 0x15, 0[4], 0x8B, 0x41, 0x08, 0x8B, 0x48, 0x04, 0x83, 0xF9, 0xFF
    )
    val dwFirstClass by lazy(LazyThreadSafetyMode.NONE) { findFirstClass() }

    val decalname by clientDLL(read = false, subtract = false)(
        0x64, 0x65, 0x63, 0x61, 0x6C, 0x6E, 0x61, 0x6D, 0x65, 0x00
    )

    val dwForceJump by clientDLL(2)(
        0x8B, 0x0D, 0[4], 0x8B, 0xD6, 0x8B, 0xC1, 0x83, 0xCA, 0x02
    )

    val dwGlowObject  by clientDLL(1, 4)(0xA1, 0[4], 0xA8, 0x01, 0x75, 0x4B)

    val dwEntityList by clientDLL(1)(0xBB, 0[4], 0x83, 0xFF, 0x01, 0x0F, 0x8C, 0[4], 0x3B, 0xF8)

}