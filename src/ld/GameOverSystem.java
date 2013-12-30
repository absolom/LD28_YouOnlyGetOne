package ld;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.annotations.Mapper;
import com.artemis.systems.EntityProcessingSystem;

public class GameOverSystem extends EntityProcessingSystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Ninja> nm;

    MapLocation lordLoc;
    LudumDareGame game;

    @SuppressWarnings("unchecked")
    public GameOverSystem(LudumDareGame game)
    {
        super(Aspect.getAspectForAll(Position.class, Ninja.class));

        this.game = game;
    }

    public void setLordLocation(MapLocation loc)
    {
        lordLoc = loc;
    }

    @Override
    protected void process(Entity e)
    {
        Position p = pm.get(e);

        if(p.xTile == lordLoc.xTile &&
           p.yTile == lordLoc.yTile)
            game.endGame();
    }

}