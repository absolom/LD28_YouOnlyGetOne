package ld;

import com.artemis.Component;
import java.util.List;

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