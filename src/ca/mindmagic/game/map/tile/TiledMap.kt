package ca.mindmagic.game.map.tile

import ca.mindmagic.game.map.grid.Grid
import ca.mindmagic.game.map.grid.Coordinate
import java.util.ArrayList
import java.util.HashSet

/**
 * Creates a map board that holds references to all the tiles.
 */

class TiledMap @JvmOverloads constructor(protected var grid: Grid, protected var width: Int, protected var height: Int, protected var wrapsX: Boolean = false, protected var wrapsY: Boolean = false) {

    protected var tiles: ArrayList<Tile>

    init {

        tiles = ArrayList(height * width)
        initTiles()
    }

    fun getAllTilesInRange(center: Coordinate, range: Int): Set<Tile> {
        val inRange = HashSet<Tile>()
        for (c in grid.area(center, range)) {
            val tile = getTile(c) ?: continue
            inRange.add(tile)
        }
        return inRange
    }

    private fun initTiles() {
        for (index in tiles.indices) {
//            tiles.add(Tile())
        }
        for (tile in tiles) {
            addAllNeighbors(tile)
        }
    }

    private fun addAllNeighbors(tile: Tile) {
        val index = tiles.indexOf(tile)
    }

    fun getTile(c: Coordinate): Tile? {    // TODO add out of bounds checking
        val index = c.row * width + c.col
        return tiles[index]
    }

    fun getCoordinate(tile: Tile): Coordinate? {
        return null
    }

    fun range(t1: Tile, t2: Tile): Int {
        return grid.range(getCoordinate(t1)!!, getCoordinate(t2)!!)
    }


    //	public List<Tile> calculateShortestPath(Moveable unit, Koordinate start, Koordinate destination){
    //	Set<Koordinate> path = new LinkedHashSet<Koordinate>();
    //	Map<Koordinate, Integer> values = new HashMap<Koordinate, Integer>();
    //	values.put(start, 0);
    //	return new LinkedList<Tile>(path);
    //}

}//public TiledMap(){
//	this(new SquareGrid(),10, 10);
//}
