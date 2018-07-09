package ca.mindmagic.game.map

import ca.mindmagic.game.map.grid.Coordinate
import ca.mindmagic.game.map.tile.Tile
import java.util.function.Supplier

class GenericMap<T : Tile?>(val gridMap: GridMap, type: Supplier<T>) {
    val tiles = ArrayList<T>(gridMap.width * gridMap.height)
    val width = gridMap.width
    val height = gridMap.height

    init {
        for (x in 0 until width * height) tiles.add(type.get())
    }

    fun neighborsOf(tile: T): Set<T> {
        if (!tiles.contains(tile)) return emptySet()
        else {
            return gridMap.neighborsOf(coordinateOf(tile)).map { getTile(it) }.toSet()
        }
    }


    fun rowOf(tile: T) = tiles.indexOf(tile) / width

    fun colOf(tile: T) = tiles.indexOf(tile) % width

    fun coordinateOf(tile: T) = Coordinate(rowOf(tile), colOf(tile))

    fun getTile(location: Coordinate) = tiles[location.row * width + location.col]

    fun range(source: T, target: T) = gridMap.range(coordinateOf(source), coordinateOf(target))

    fun area(center: T, radius: Int): Set<T> {
        return gridMap.getArea(coordinateOf(center), radius).map { getTile(it) }.toSet()
    }
}