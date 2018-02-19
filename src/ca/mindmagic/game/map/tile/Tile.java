package ca.mindmagic.game.map.tile;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Deprecated
public class Tile {
	
	Set<Tile> neighbors;

	public Tile(){
		neighbors = new HashSet<>();
	}
	
	public Set<Tile> neighbors(){  // no add and remove required because returning set which can then be modified.
		return neighbors;
	}

}
