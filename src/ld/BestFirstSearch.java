package ld;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Set;

public class BestFirstSearch
{
    TileMap map;

    public BestFirstSearch(TileMap map)
    {
        this.map = map;
    }

    public List<MapLocation> getPath(final MapLocation startLoc, final MapLocation destLoc)
    {
        Comparator<MapLocation> distComp = new Comparator<MapLocation>()
        {
            @Override
            public int compare(MapLocation l0, MapLocation l1)
            {                
                double d0 = Math.sqrt(Math.pow(l0.xTile - destLoc.xTile,2) +
                                      Math.pow(l0.yTile - destLoc.yTile,2));
                double d1 = Math.sqrt(Math.pow(l1.xTile - destLoc.xTile,2) +
                                      Math.pow(l1.yTile - destLoc.yTile,2));

                if(d0 < d1)
                    return -1;

                if(d0 == d1)
                    return 0;

                return 1;
            }
        };

        Set<MapLocation> closed = new HashSet<>();
        PriorityQueue<MapLocation> open = new PriorityQueue<>(10, distComp);
        Map<MapLocation, MapLocation> parents = new HashMap<>();
        open.add(startLoc);

        while(!open.isEmpty())
        {
            MapLocation n = open.poll();
            closed.add(n);

            // Are we at the destination?
            if(n.equals(destLoc))
                return buildPath(parents, n);

            // check where we can go next from our current location
            MoveDirection[] directions = {MoveDirection.NORTH, MoveDirection.EAST, 
                    MoveDirection.SOUTH, MoveDirection.WEST};
            for(int i = 0; i < directions.length; i++)
            {
                MapLocation parent = parents.get(n);

                if(map.isPassable(n.move(directions[i])))
                {
                    MapLocation newLoc = n.move(directions[i]);

                    if(newLoc.equals(parent))
                        continue;

                    if(closed.contains(newLoc))
                    {
                        int pathLenCur = buildPath(parents, n).size()+1;
                        int pathLenExisting = buildPath(parents, newLoc).size();

                        // if our current path is shorter, change this path to point at us
                        if(pathLenCur < pathLenExisting)
                            parents.put(newLoc, n);
                    }
                    else
                    {
                        open.add(newLoc);
                        parents.put(newLoc, n);
                    }
                }
            }
        }

        return null;
    }

    private List<MapLocation> buildPath(Map<MapLocation, MapLocation> parents, MapLocation start)
    {
        List<MapLocation> ret = new ArrayList<>();

        MapLocation n = start;
        while(true)
        {
            ret.add(n);
            n = parents.get(n);
            if(n == null)
            {
                Collections.reverse(ret);
                return ret;
            }
        }
    }
}