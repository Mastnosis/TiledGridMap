package ca.mindmagic.game.map.grid

import ca.mindmagic.game.map.grid.pattern.Pattern

import java.util.*
import java.util.function.Supplier

class TiledGridMap<T>(pattern: Pattern, width: Int, height: Int, wrapHorizontal: Boolean, wrapVertical: Boolean, tileType: Supplier<T>) : GridMap(pattern, height, width, wrapHorizontal, wrapVertical) {

    private val tiles = ArrayList<T>(width * height)
    private val neighborMap: MutableMap<T, Set<T>> = HashMap()

    init {
        initializeTiles(tileType)
        mapNeighbors()
    }

    private fun initializeTiles(tileType: Supplier<T>) {
        for (i in 0 until super.width * super.height) {
            tiles.add(tileType.get())
        }
    }

    private fun mapNeighbors() {
        tiles.forEach { t -> neighborMap[t] = calculateNeighbors(t) }
    }

    private fun calculateNeighbors(tile: T): Set<T> {
        TODO("return mapped set of neighbors")
        return emptySet()
    }

    fun getTile(row: Int, col: Int): T? {
        return if (row > super.height || col > super.width) null else tiles[row * super.width + col]
    }

    fun getTileCoordinate(tile: T): Coordinate {
        val index = tiles.indexOf(tile);
        return Coordinate(row(index), col(index))
    }

    fun getTile(location: Coordinate) = getTile(location.row, location.col)

    fun getNeighborsOf(tile: T): Set<T> {
        return neighborMap[tile] ?: emptySet()
    }

    private fun row(index: Int) = index / width

    private fun col(index: Int) = index % width

}
