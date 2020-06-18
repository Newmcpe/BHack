package ru.newmcpe.bhop.util

import com.sun.jna.Memory
import org.jire.arrowhead.Module
import org.jire.arrowhead.Source
import org.jire.arrowhead.unsign
import ru.newmcpe.bhop.offsets.ModuleScan
import ru.newmcpe.bhop.offsets.Offset
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.concurrent.ThreadLocalRandom
import kotlin.concurrent.thread

internal operator fun Module.invoke(patternOffset: Long = 0, addressOffset: Long = 0,
                                    read: Boolean = true, subtract: Boolean = true)
        = ModuleScan(this, patternOffset, addressOffset, read, subtract)

internal operator fun Module.invoke(patternOffset: Long = 0, addressOffset: Long = 0,
                                    read: Boolean = true, subtract: Boolean = true, className: String)
        = Offset(this, patternOffset, addressOffset, read, subtract, className.toByteArray(Charsets.UTF_8))

internal operator fun Module.invoke(patternOffset: Long = 0, addressOffset: Long = 0,
                                    read: Boolean = true, subtract: Boolean = true, offset: Long)
        = Offset(this, patternOffset, addressOffset, read, subtract,
    ByteBuffer.allocate(4).order(ByteOrder.LITTLE_ENDIAN).putInt(offset.toInt()).array())

fun Source.uint(address: Long, offset: Long = 0) = int(address, offset).unsign()


data class RepeatedInt(val value: Int, val repeats: Int)

operator fun Int.get(repeats: Int) = RepeatedInt(this, repeats)

inline fun <R> retry(duration: Long = 1000, noinline exceptionHandler: ((Throwable) -> Unit)? = null, body: () -> R) {
    while (!Thread.interrupted()) {
        try {
            body()
            break
        } catch (t: Throwable) {
            exceptionHandler?.invoke(t)
            Thread.sleep(duration)
            t.printStackTrace()
        }
    }
}
fun Memory?.readable() = null != this

internal fun ByteArray.toNetVarString(): String {
    for (i in 0..size - 1) if (0.toByte() == this[i]) this[i] = 32
    return String(this).split(" ")[0].trim()
}

inline fun every(duration: Int, continuous: Boolean = false,
                 crossinline body: () -> Unit) = every(duration, duration, continuous, body)

inline fun every(minDuration: Int, maxDuration: Int,
                 continuous: Boolean = false,
                 crossinline body: () -> Unit) = thread {
    while (!Thread.interrupted()) {
        if (continuous || !(inBackground && notInGame)) body()
        Thread.sleep((if (maxDuration > minDuration)
            ThreadLocalRandom.current().nextInt(maxDuration - minDuration + 1) + minDuration
        else minDuration).toLong())
    }
}
@Volatile
var inBackground = false
@Volatile
var notInGame = false