package ca.mindmagic.game.map.grid

data class Coordinate(val row: Int, val col: Int) {
    override fun toString(): String {
        return "($row/$col)"
    }
}
