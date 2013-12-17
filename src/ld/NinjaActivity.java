package ld;

public interface NinjaActivity
{
    MoveDirection getMoveDirection();

    NinjaActivity newMapLocation(MapLocation locNew);
}
