package ld.Components;

import java.util.List;

import ld.Map.MapLocation;

import com.artemis.Component;

public class Path extends Component
{
    public int indexNextTile;
    public int length;
    public List<MapLocation> tiles;

    public Path(List<MapLocation> path)
    {
        indexNextTile = 0;
        length = path.size();
        tiles = path;
    }
}