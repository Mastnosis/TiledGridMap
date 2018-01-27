package ca.mindmagic.game.map;

import ca.mindmagic.game.map.grid.Grid;
import ca.mindmagic.game.map.grid.Coordinate;
import ca.mindmagic.game.map.tile.Tile;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Creates a map board that holds references to all the tiles.
 */

public class TiledMap {
	
	protected ArrayList<Tile> tiles;
	
	protected Grid grid;
	
	protected int width, height;
	protected boolean wrapsX, wrapsY;
	
	//public TiledMap(){
	//	this(new SquareGrid(),10, 10);
	//}
	
	public TiledMap(Grid grid, int sizeX, int sizeY){
		this(grid, sizeX, sizeY, false, false);
	}
	
	public TiledMap(Grid grid, int sizeX, int sizeY, boolean wrapsX, boolean wrapsY){
		this.grid = grid;
		width = sizeX;
		height = sizeY;
		this.wrapsX = wrapsX;
		this.wrapsY = wrapsY;
		
		tiles = new ArrayList<>(height*width);
		initTiles();
	}

	public Set<Tile> getAllTilesInRange(Coordinate center, int range){
		Set<Tile> inRange = new HashSet<>();
		for (Coordinate c: grid.neighborsRadius(center, range)
		) {
			if(getTile(c) == null) continue;
			inRange.add(getTile(c));
		}
		return inRange;
	}
	
	private void initTiles() {
		for(int index = 0; index < tiles.size(); index++){
		  tiles.add(new Tile());
    }
    for (Tile tile: tiles
    ) {
      addAllNeighbors(tile);
    }
  }

  private void addAllNeighbors(Tile tile) {
    int index = tiles.indexOf(tile);
  }

  public Tile getTile(Coordinate c){	// TODO add out of bounds checking
		int index = c.getRow()*width + c.getCol();
		return tiles.get(index);
	}

	public Coordinate getCoordinate(Tile tile){
		return null;
	}
	
	public int range(Tile t1, Tile t2){
		return grid.range(getCoordinate(t1), getCoordinate(t2));
	}
	
	
	
	
	

	
//	public List<Tile> calculateShortestPath(Moveable unit, Koordinate start, Koordinate destination){
//	Set<Koordinate> path = new LinkedHashSet<Koordinate>();
//	Map<Koordinate, Integer> values = new HashMap<Koordinate, Integer>();
//	values.put(start, 0);
//	return new LinkedList<Tile>(path);
//}

}
