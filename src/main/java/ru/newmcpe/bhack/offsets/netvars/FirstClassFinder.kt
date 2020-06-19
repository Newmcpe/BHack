package ru.newmcpe.bhack.offsets.netvars

import ru.newmcpe.bhack.BHack
import ru.newmcpe.bhack.BHack.clientDLL
import ru.newmcpe.bhack.offsets.ClientOffsets.decalname
import ru.newmcpe.bhack.offsets.Offset
import ru.newmcpe.bhack.offsets.mask
import ru.newmcpe.bhack.util.uint

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

fun findFirstClass() = BHack.csgo.uint(findDecal() + 0x3B)