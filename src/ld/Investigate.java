package ld;

import java.util.Collections;
import java.util.List;

public class Investigate implements GuardActivity
{
    List<MapLocation> path;
    int indexCurrent;
    boolean goingTo;

    public Investigate(List<MapLocation> path)
    {
        this.path = path;
        indexCurrent = 0;
        goingTo = true;
        // TODO: Change speed
    }

    @Override
    public MoveDirection getMoveDirection()
    {
        return MoveDirection.getDirectionTo(path.get(indexCurrent), 
                path.get(indexCurrent+1));
    }

    @Override
    public GuardActivity newMapLocation(MapLocation locNew)
    {
        indexCurrent++;
        
        if(locNew.equals(path.get(path.size()-1)))
        {
            if(goingTo)
            {
                // TODO: Set delay for next move
                // TODO: Change speed
                Collections.reverse(path);
                indexCurrent = 0;
                goingTo = false;
                return this;
            }
            else
                return null;
        }
        else
            return this;
    }

    @Override
    public GuardActivity noiseHeard(MapLocation locNoise)
    {
        return this;
    }

}
