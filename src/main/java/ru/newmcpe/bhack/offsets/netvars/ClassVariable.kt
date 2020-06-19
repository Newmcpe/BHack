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

package ru.newmcpe.bhack.offsets.netvars

import org.jire.arrowhead.Addressed
import ru.newmcpe.bhack.BHack
import ru.newmcpe.bhack.util.toNetVarString
import ru.newmcpe.bhack.util.uint
import kotlin.LazyThreadSafetyMode.NONE

internal class ClassVariable(override val address: Long, val addressOffset: Long) : Addressed {
	
	val resolvedAddress by lazy(NONE) { BHack.csgo.uint(address) }
	
	val name by lazy(NONE) {
		val bytes = ByteArray(32)
		
		val memory = BHack.csgo.read(resolvedAddress, bytes.size)!!
		memory.read(0, bytes, 0, bytes.size)
		
		bytes.toNetVarString()
	}
	
	val table by lazy(NONE) { BHack.csgo.uint(address + 0x28) }
	
	val offset by lazy(NONE) { addressOffset + BHack.csgo.uint(address + 0x2C) }
	
	val type by lazy(NONE) { BHack.csgo.uint(address + 0x4) }
	
	val elements by lazy(NONE) { BHack.csgo.uint(address + 0x34) }
	
	val stringBufferCount by lazy(NONE) { BHack.csgo.uint(address + 0xC) }
	
}