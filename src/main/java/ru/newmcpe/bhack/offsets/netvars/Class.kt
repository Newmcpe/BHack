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
import ru.newmcpe.bhack.util.readable
import ru.newmcpe.bhack.util.uint
import kotlin.LazyThreadSafetyMode.NONE

internal class Class(override val address: Long) : Addressed {
	
	val id by lazy(NONE) { BHack.csgo.uint(address + 20) }
	
	val next by lazy(NONE) { BHack.csgo.uint(address + 16) }
	
	val table by lazy(NONE) { BHack.csgo.uint(address + 12) }
	
	fun readable() = BHack.csgo.read(address, 40).readable()
	
}