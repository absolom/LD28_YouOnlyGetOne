package ld;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

import java.util.*;

public class VisionSystem extends EntitySystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Vision> vm;
    @Mapper ComponentMapper<Guard> gm;
    @Mapper ComponentMapper<Ninja> nm;

    List<Entity> guards;
    List<Entity> ninjas;
    TileMap map;

    @SuppressWarnings("unchecked")
    public VisionSystem(TileMap map)
    {
        super(Aspect.getAspectForAll(Position.class, Vision.class).one(Guard.class, Ninja.class));

        this.map = map;

        guards = new ArrayList<>();
        ninjas = new ArrayList<>();
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities)
    {
        List<Entity> possibles = new ArrayList<>();

        // for all guards, see what they can see
        for(int i = 0; i < guards.size(); i++)
        {
            Entity guard = guards.get(i);

            Position p = pm.get(guard);
            Vision v = vm.get(guard);

            v.lookDir = MoveDirection.getDirectionTo(new MapLocation(p.xTileLast, p.yTileLast),
                                                     new MapLocation(p.xTile, p.yTile));

            v.seenGuards.clear();
            v.seenNinjas.clear();
            possibles.clear();

            for(int j = 0; j < ninjas.size(); j++)
            {
                Entity ninja = ninjas.get(j);
                Position pNinja = pm.get(ninja);

                if(p.xTile == pNinja.xTile ||
                   p.yTile == pNinja.yTile)
                    possibles.add(ninja);
            }

            Iterator<Entity> it = possibles.iterator();

            while(it.hasNext())
            {
                Entity e = it.next();
                Position pTarg = pm.get(e);

                switch(v.lookDir)
                {
                    case NORTH:
                    {
                        if(p.xTile != pTarg.xTile)
                            continue;

                        if(p.yTile < pTarg.yTile)
                            continue;

                        boolean losBlocked = false;
                        for(int k = pTarg.yTile; k < p.yTile; k++)
                        {
                            if(!map.isPassable(new MapLocation(p.xTile, k)))
                            {
                                losBlocked = true;
                                break;
                            }
                        }

                        if(!losBlocked)
                            v.seenNinjas.add(e);
                        break;
                    }
                    case EAST:
                    {
                        if(p.yTile != pTarg.yTile)
                            continue;

                        if(p.xTile > pTarg.xTile)
                            continue;

                        boolean losBlocked = false;
                        for(int k = p.xTile; k < pTarg.xTile; k++)
                        {
                            if(!map.isPassable(new MapLocation(k, p.yTile)))
                            {
                                losBlocked = true;
                                break;
                            }
                        }

                        if(!losBlocked)
                            v.seenNinjas.add(e);
                        break;
                    }
                    case WEST:
                    {
                        if(p.yTile != pTarg.yTile)
                            continue;

                        if(p.xTile < pTarg.xTile)
                            continue;

                        boolean losBlocked = false;
                        for(int k = pTarg.xTile; k < p.xTile; k++)
                        {
                            if(!map.isPassable(new MapLocation(k, p.yTile)))
                            {
                                losBlocked = true;
                                break;
                            }
                        }

                        if(!losBlocked)
                            v.seenNinjas.add(e);
                        break;
                    }
                    case SOUTH:
                    {
                        if(p.xTile != pTarg.xTile)
                            continue;

                        if(p.yTile > pTarg.yTile)
                            continue;

                        boolean losBlocked = false;
                        for(int k = p.yTile; k < pTarg.yTile; k++)
                        {
                            if(!map.isPassable(new MapLocation(p.xTile, k)))
                            {
                                losBlocked = true;
                                break;
                            }
                        }

                        if(!losBlocked)
                            v.seenNinjas.add(e);
                        break;
                    }
                    case NONE:
                    {
                        v.seenNinjas.add(e);
                        break;
                    }
                }
            }
        }
    }

    @Override
    protected void inserted(Entity e)
    {
        Guard g = gm.getSafe(e);

        if(g == null)
        {
            ninjas.add(e);
        }
        else
        {
            guards.add(e);
        }
    }

    @Override
    protected void removed(Entity e)
    {
        ninjas.remove(e);
        guards.remove(e);
    }

    @Override
    protected boolean checkProcessing()
    {
        return true;
    }
}