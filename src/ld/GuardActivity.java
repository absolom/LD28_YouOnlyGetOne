package ld;

public interface GuardActivity
{

    MoveDirection getMoveDirection();

    GuardActivity newMapLocation(MapLocation locNew);

    GuardActivity noiseHeard(MapLocation locNoise);

}
