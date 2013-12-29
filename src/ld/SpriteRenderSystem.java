package ld;

import org.newdawn.slick.Image;

import com.artemis.Aspect;
import com.artemis.ComponentMapper;
import com.artemis.Entity;
import com.artemis.EntitySystem;
import com.artemis.annotations.Mapper;
import com.artemis.utils.ImmutableBag;

public class SpriteRenderSystem extends EntitySystem
{
    @Mapper ComponentMapper<Position> pm;
    @Mapper ComponentMapper<Sprite> sm;

    @SuppressWarnings("unchecked")
    public SpriteRenderSystem()
    {
        super(Aspect.getAspectForAll(Position.class, Sprite.class));
    }

    @Override
    protected void processEntities(ImmutableBag<Entity> entities)
    {
        for(int i = 0; i < entities.size(); i++)
        {
            Entity e = entities.get(i);
            Position p = pm.get(e);
            Sprite s = sm.get(e);

            float d = 4f * LudumDareGame.SCALE;
            float x = p.xTile*d;
            float y = p.yTile*d;

            s.img.draw(x,y,LudumDareGame.SCALE);
        }
    }

    @Override
    protected boolean checkProcessing()
    {
        return true;
    }

}
