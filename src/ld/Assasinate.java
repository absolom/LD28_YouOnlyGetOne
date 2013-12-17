package ld;

import java.util.List;

public class Assasinate implements NinjaActivity
{

    public Assasinate()
    {
    }

    @Override
    public MoveDirection getMoveDirection()
    {
        return MoveDirection.NONE;
    }

    @Override
    public NinjaActivity newMapLocation(MapLocation l)
    {
        return this;
    }

}
