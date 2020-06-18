package ru.newmcpe.bhop.offsets.netvars

import ru.newmcpe.bhop.Bhop
import ru.newmcpe.bhop.Bhop.clientDLL
import ru.newmcpe.bhop.offsets.ClientOffsets.decalname
import ru.newmcpe.bhop.offsets.Offset
import ru.newmcpe.bhop.offsets.mask
import ru.newmcpe.bhop.util.uint

fun findDecal(): Long {
    val mask = ByteArray(4)
    for (i in 0..3) mask[i] = (((decalname shr 8 * i)) and 0xFF).toByte()

    val memory = Offset.memoryByModule[clientDLL]!!

    var skipped = 0
    var currentAddress = 0L
    while (currentAddress < clientDLL.size - mask.size) {
        if (memory.mask(currentAddress, mask, false)) {
            if (skipped < 5) { // skips
                currentAddress += 0xA // skipSize
                skipped++
                continue
            }
            return currentAddress + clientDLL.address
        }
        currentAddress++
    }

    return -1L
}

fun findFirstClass() = Bhop.csgo.uint(findDecal() + 0x3B)