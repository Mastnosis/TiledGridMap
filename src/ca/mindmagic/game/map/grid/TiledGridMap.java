package ca.mindmagic.game.map.grid;

import ca.mindmagic.game.map.grid.pattern.Pattern;
import kotlin.collections.SetsKt;

import java.util.*;
import java.util.function.Supplier;

public class TiledGridMap<T> extends GridMap {

    ArrayList<T> tiles;
    Map<T, Set<T>> neighborMap = new HashMap<>();

    public TiledGridMap(Pattern pattern, int width, int height, boolean wrapHorizontal, boolean wrapVertical, Supplier<T> tileType) {
        super(pattern, height, width, wrapHorizontal, wrapVertical);
        tiles = new ArrayList<>(width * height);
        initializeTiles(tileType);
        mapNeighbors();
    }

    private void initializeTiles(Supplier<T> tileType) {
        for (int i = 0; i < super.getWidth() * super.getHeight(); i++) {
            tiles.add(tileType.get());
        }
    }

    private void mapNeighbors() {
        tiles.forEach(t -> neighborMap.put(t, calculateNeighbors(t)));
    }

    private Set<T> calculateNeighbors(T tile) {
        return SetsKt.emptySet();
    }

    public T getTile(int row, int col) {
        if (row > super.getHeight() || col > super.getWidth()) return null;
        return tiles.get(row * super.getWidth() + col);
    }

    public Set<T> getNeighborsOf(T tile) {
        return neighborMap.get(tile);
    }
}
