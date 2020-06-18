/*
 * Charlatano: Free and open-source (FOSS) cheat for CS:GO/CS:CO
 * Copyright (C) 2017 - Thomas G. P. Nappo, Jonathan Beaudoin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package ru.newmcpe.bhop.offsets.netvars

import org.jire.arrowhead.Addressed
import ru.newmcpe.bhop.Bhop
import ru.newmcpe.bhop.util.readable
import ru.newmcpe.bhop.util.toNetVarString
import ru.newmcpe.bhop.util.uint
import kotlin.LazyThreadSafetyMode.NONE

internal class ClassTable(override val address: Long, val offset: Long = 16) : Addressed {
	
	val name by lazy(NONE) {
		val bytes = ByteArray(64)
		
		val memoryAddress = Bhop.csgo.uint(address + 12)
		val memory = Bhop.csgo.read(memoryAddress, bytes.size)!!
		memory.read(0, bytes, 0, bytes.size)
		
		bytes.toNetVarString()
	}
	
	val propCount by lazy(NONE) { Bhop.csgo.int(address + 4) }
	
	fun propForID(id: Int) = Bhop.csgo.uint(address) + id * 60
	
	fun readable() = Bhop.csgo.read(address, offset.toInt()).readable()
	
}