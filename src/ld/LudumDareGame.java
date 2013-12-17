package ld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.BasicGame;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

import com.artemis.Entity;
import com.artemis.World;

public class LudumDareGame extends BasicGame
{
    public static final int WIDTH = 640;
    public static final int HEIGHT = 640;
    public static final int UPDATE_PERIOD = 33;
    public static final float SCALE = 4f;

    static public void main(String[] args) throws SlickException
    {
        try
        {
            BufferedReader in = new BufferedReader(new FileReader("res/map1.csv"));
            String line;
            List<String> lines = new ArrayList<>();
            while((line = in.readLine()) != null)
            {
                lines.add(line);
                System.out.println(line);
            }
            in.close();

            System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");

            TileMap map = new TileMap();
            map.setData(lines);

            System.out.println(map.toString());

            AppGameContainer app = new AppGameContainer(new LudumDareGame(map, new World()));

            app.setDisplayMode(WIDTH, HEIGHT, false);
            app.start();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////

    World world;
    int timeSinceLastUpdate;
    Map<Integer, Image> tileImages;
    TileMap map;
    GuardRenderSystem guardRenderSystem;
    NinjaRenderSystem ninjaRenderSystem;
    LordRenderSystem lordRenderSystem;

    public LudumDareGame(TileMap map, World w)
    {
        super("Ludum Dare 28 - YOGO chance to stop those NINJAS!");
        world = w;

        this.map = map;
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        g.clear();

        int w = map.getWidth();
        int h = map.getHeight();
        float d = 4f * SCALE;

        for(int i = 0; i < w; i++)
        {
            for(int j = 0; j < h; j++)
            {
                int tileId = map.getTileId(i,j);

                if(tileId == 0)
                    continue;

                Image tile = tileImages.get(tileId);

                float x = i*d;
                float y = j*d - (tile.getHeight()-4)*SCALE;

                tile.draw(x,y,SCALE);
            }
        }

        guardRenderSystem.process();
        ninjaRenderSystem.process();
        lordRenderSystem.process();
    }

    @Override
    public void init(GameContainer c) throws SlickException
    {
        timeSinceLastUpdate = 0;

        // Load images
        Image image;

        tileImages = new HashMap<>();
        image = new Image("res/path.png");
        image.setFilter(Image.FILTER_NEAREST);
        tileImages.put(1, image);
        image = new Image("res/test_tile3.png");
        image.setFilter(Image.FILTER_NEAREST);
        tileImages.put(2, image);
        image = new Image("res/forest_tiles_med0.png");
        image.setFilter(Image.FILTER_NEAREST);
        tileImages.put(3, image);
        image = new Image("res/grass.png");
        image.setFilter(Image.FILTER_NEAREST);
        tileImages.put(4, image);

        Image imageGuard = new Image("res/guard.png");
        imageGuard.setFilter(Image.FILTER_NEAREST);
        Image imageNinja = new Image("res/player.png");
        imageNinja.setFilter(Image.FILTER_NEAREST);
        Image imageLord = new Image("res/lord.png");
        imageLord.setFilter(Image.FILTER_NEAREST);

        // Create map navigation logic
        BestFirstSearch bfs = new BestFirstSearch(map);

        // Create systems
        GuardSystem guardSystem = new GuardSystem();
        world.setSystem(guardSystem);
        guardRenderSystem = new GuardRenderSystem(imageGuard);
        world.setSystem(guardRenderSystem, true);

        NinjaSystem ninjaSystem = new NinjaSystem();
        world.setSystem(ninjaSystem);
        ninjaRenderSystem = new NinjaRenderSystem(imageNinja);
        world.setSystem(ninjaRenderSystem, true);

        lordRenderSystem = new LordRenderSystem(imageLord);
        world.setSystem(lordRenderSystem, true);

        // Create some guards
        List<MapLocation> path = new ArrayList<>();
        path.add(new MapLocation(22, 11));
        path.add(new MapLocation(22, 21));
        path.add(new MapLocation(28, 21));
        path.add(new MapLocation(28, 11));
        path.add(new MapLocation(31, 11));
        path.add(new MapLocation(31, 3));
        path.add(new MapLocation(20, 3));
        path.add(new MapLocation(20, 11));

        Entity guard = world.createEntity();
            MapLocation waypoint0 = path.get(0);
            Position p = new Position();
            p.xTile = waypoint0.xTile;
            p.yTile = waypoint0.yTile;
            guard.addComponent(p);
            GuardState gs = new GuardState();
            gs.activity.addFirst(new Patrol(path, bfs));
            gs.waitTimeBeforeMove = 25;
            guard.addComponent(gs);
        guard.addToWorld();

        //

        path = new ArrayList<>();
        path.add(new MapLocation(20, 3));
        path.add(new MapLocation(31, 3));
        path.add(new MapLocation(31, 11));
        path.add(new MapLocation(28, 11));
        path.add(new MapLocation(28, 21));
        path.add(new MapLocation(22, 21));
        path.add(new MapLocation(22, 11));
        path.add(new MapLocation(20, 11));
        path.add(new MapLocation(20, 3));

        guard = world.createEntity();
            waypoint0 = path.get(0);
            p = new Position();
            p.xTile = waypoint0.xTile;
            p.yTile = waypoint0.yTile;
            guard.addComponent(p);
            gs = new GuardState();
            gs.activity.addFirst(new Patrol(path, bfs));
            gs.waitTimeBeforeMove = 35;
            guard.addComponent(gs);
        guard.addToWorld();

        //

        path = new ArrayList<>();
        path.add(new MapLocation(33, 21));
        path.add(new MapLocation(25, 21));
        path.add(new MapLocation(25, 26));
        path.add(new MapLocation(20, 26));
        path.add(new MapLocation(20, 31));
        path.add(new MapLocation(26, 31));
        path.add(new MapLocation(26, 34));
        path.add(new MapLocation(38, 34));
        path.add(new MapLocation(38, 25));
        path.add(new MapLocation(33, 25));

        guard = world.createEntity();
            waypoint0 = path.get(0);
            p = new Position();
            p.xTile = waypoint0.xTile;
            p.yTile = waypoint0.yTile;
            guard.addComponent(p);
            gs = new GuardState();
            gs.activity.addFirst(new Patrol(path, bfs));
            gs.waitTimeBeforeMove = 15;
            guard.addComponent(gs);
        guard.addToWorld();

        //

        path = new ArrayList<>();
        path.add(new MapLocation(26, 34));
        path.add(new MapLocation(38, 34));
        path.add(new MapLocation(38, 16));
        path.add(new MapLocation(28, 16));
        path.add(new MapLocation(28, 21));
        path.add(new MapLocation(25, 21));
        path.add(new MapLocation(25, 26));
        path.add(new MapLocation(20, 26));
        path.add(new MapLocation(20, 31));
        path.add(new MapLocation(26, 31));

        guard = world.createEntity();
            waypoint0 = path.get(0);
            p = new Position();
            p.xTile = waypoint0.xTile;
            p.yTile = waypoint0.yTile;
            guard.addComponent(p);
            gs = new GuardState();
            gs.activity.addFirst(new Patrol(path, bfs));
            gs.waitTimeBeforeMove = 15;
            guard.addComponent(gs);
        guard.addToWorld();

        // Create some ninjas

        path = new ArrayList<>();
        path.add(new MapLocation(0, 34));
        path.add(new MapLocation(15, 34));
        path.add(new MapLocation(15, 5));
        path.add(new MapLocation(20, 5));
        path.add(new MapLocation(20, 3));
        path.add(new MapLocation(31, 3));
        path.add(new MapLocation(31, 6));
        path.add(new MapLocation(38, 6));
        path.add(new MapLocation(38, 7));
        path.add(new MapLocation(39, 7));
        path.add(new MapLocation(39, 11));
        path.add(new MapLocation(37, 11));

        Entity ninja = world.createEntity();
            waypoint0 = path.get(0);
            p = new Position();
            p.xTile = waypoint0.xTile;
            p.yTile = waypoint0.yTile;
            ninja.addComponent(p);
            NinjaState ns = new NinjaState();
            ns.activity.addFirst(new Infiltrate(path));
            ns.waitTimeBeforeMove = 8;
            ninja.addComponent(ns);
        ninja.addToWorld();

        //

        path = new ArrayList<>();
        path.add(new MapLocation(0, 34));
        path.add(new MapLocation(15, 34));
        path.add(new MapLocation(15, 36));
        path.add(new MapLocation(26, 36));
        path.add(new MapLocation(26, 31));
        path.add(new MapLocation(29, 31));
        path.add(new MapLocation(29, 28));
        path.add(new MapLocation(33, 28));
        path.add(new MapLocation(33, 25));
        path.add(new MapLocation(38, 25));
        path.add(new MapLocation(38, 16));
        path.add(new MapLocation(39, 16));
        path.add(new MapLocation(39, 11));
        path.add(new MapLocation(37, 11));

        ninja = world.createEntity();
            waypoint0 = path.get(0);
            p = new Position();
            p.xTile = waypoint0.xTile;
            p.yTile = waypoint0.yTile;
            ninja.addComponent(p);
            ns = new NinjaState();
            ns.activity.addFirst(new Infiltrate(path));
            ns.waitTimeBeforeMove = 8;
            ninja.addComponent(ns);
        ninja.addToWorld();

        //

        path = new ArrayList<>();
        path.add(new MapLocation(0, 34));
        path.add(new MapLocation(15, 34));
        path.add(new MapLocation(15, 11));
        path.add(new MapLocation(22, 11));
        path.add(new MapLocation(22, 21));
        path.add(new MapLocation(25, 21));
        path.add(new MapLocation(25, 26));
        path.add(new MapLocation(29, 26));
        path.add(new MapLocation(29, 31));
        path.add(new MapLocation(26, 31));
        path.add(new MapLocation(26, 34));
        path.add(new MapLocation(38, 34));
        path.add(new MapLocation(38, 16));
        path.add(new MapLocation(39, 16));
        path.add(new MapLocation(39, 11));
        path.add(new MapLocation(37, 11));

        ninja = world.createEntity();
            waypoint0 = path.get(0);
            p = new Position();
            p.xTile = waypoint0.xTile;
            p.yTile = waypoint0.yTile;
            ninja.addComponent(p);
            ns = new NinjaState();
            ns.activity.addFirst(new Infiltrate(path));
            ns.waitTimeBeforeMove = 8;
            ninja.addComponent(ns);
        ninja.addToWorld();

        // Create a lord

        Entity lord = world.createEntity();
            p = new Position();
            p.xTile = 36;
            p.yTile = 11;
            lord.addComponent(p);
            LordState ls = new LordState();
            // ls.activity.addFirst(new Infiltrate(path));
            ls.waitTimeBeforeMove = 66;
            lord.addComponent(ls);
        lord.addToWorld();

        world.initialize();
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        timeSinceLastUpdate += delta;
        while(timeSinceLastUpdate >= UPDATE_PERIOD)
        {
            world.setDelta(UPDATE_PERIOD);
            world.process(false);
            timeSinceLastUpdate -= UPDATE_PERIOD;
        }
    }
}

