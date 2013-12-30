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

    // GuardRenderSystem guardRenderSystem;
    // NinjaRenderSystem ninjaRenderSystem;
    // LordRenderSystem lordRenderSystem;
    SpriteRenderSystem spriteRenderSystem;

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

        // guardRenderSystem.process();
        // ninjaRenderSystem.process();
        // lordRenderSystem.process();
        spriteRenderSystem.process();
    }

    private void createGuard(List<MapLocation> waypoints, int spd, Image img)
    {
        Entity guard = world.createEntity();
            MapLocation waypoint0 = waypoints.get(0);
            Position p = new Position();
            p.xTile = waypoint0.xTile;
            p.yTile = waypoint0.yTile;
            guard.addComponent(p);
            PatrolComponent patrol = new PatrolComponent(waypoints);
            guard.addComponent(patrol);
            Speed speed = new Speed();
            speed.timeToWait = spd;
            guard.addComponent(speed);
            Sprite sprite = new Sprite();
            sprite.img = img;
            guard.addComponent(sprite);
            Vision vision = new Vision();
            guard.addComponent(vision);
            Guard guardC = new Guard();
            guard.addComponent(guardC);
        guard.addToWorld();
    }

    private void createNinja(List<MapLocation> waypoints, int spd, Image img)
    {
        Entity ninja = world.createEntity();
            MapLocation waypoint0 = waypoints.get(0);
            Position p = new Position();
            p.xTile = waypoint0.xTile;
            p.yTile = waypoint0.yTile;
            ninja.addComponent(p);
            PatrolComponent patrol = new PatrolComponent(waypoints);
            ninja.addComponent(patrol);
            Speed speed = new Speed();
            speed.timeToWait = 8;
            ninja.addComponent(speed);
            Sprite sprite = new Sprite();
            sprite.img = img;
            ninja.addComponent(sprite);
        ninja.addToWorld();
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
        PatrolSystem patrolSystem = new PatrolSystem(bfs);
        world.setSystem(patrolSystem);
        MoveToSystem moveToSystem = new MoveToSystem();
        world.setSystem(moveToSystem);
        SpeedSystem speedSystem = new SpeedSystem();
        world.setSystem(speedSystem);
        VisionSystem visionSystem = new VisionSystem(map);
        world.setSystem(visionSystem);
        spriteRenderSystem = new SpriteRenderSystem();
        world.setSystem(spriteRenderSystem, true);

        // NinjaSystem ninjaSystem = new NinjaSystem();
        // world.setSystem(ninjaSystem);

        // Create some guards
        List<MapLocation> waypoints = new ArrayList<>();
        waypoints.add(new MapLocation(22, 11));
        waypoints.add(new MapLocation(22, 21));
        waypoints.add(new MapLocation(28, 21));
        waypoints.add(new MapLocation(28, 11));
        waypoints.add(new MapLocation(31, 11));
        waypoints.add(new MapLocation(31, 3));
        waypoints.add(new MapLocation(20, 3));
        waypoints.add(new MapLocation(20, 11));

        createGuard(waypoints, 25, imageGuard);

        // //

        waypoints = new ArrayList<>();
        waypoints.add(new MapLocation(20, 3));
        waypoints.add(new MapLocation(31, 3));
        waypoints.add(new MapLocation(31, 11));
        waypoints.add(new MapLocation(28, 11));
        waypoints.add(new MapLocation(28, 21));
        waypoints.add(new MapLocation(22, 21));
        waypoints.add(new MapLocation(22, 11));
        waypoints.add(new MapLocation(20, 11));
        waypoints.add(new MapLocation(20, 3));

        createGuard(waypoints, 35, imageGuard);

        // //

        waypoints = new ArrayList<>();
        waypoints.add(new MapLocation(33, 21));
        waypoints.add(new MapLocation(25, 21));
        waypoints.add(new MapLocation(25, 26));
        waypoints.add(new MapLocation(20, 26));
        waypoints.add(new MapLocation(20, 31));
        waypoints.add(new MapLocation(26, 31));
        waypoints.add(new MapLocation(26, 34));
        waypoints.add(new MapLocation(38, 34));
        waypoints.add(new MapLocation(38, 25));
        waypoints.add(new MapLocation(33, 25));

        createGuard(waypoints, 15, imageGuard);

        //

        waypoints = new ArrayList<>();
        waypoints.add(new MapLocation(26, 34));
        waypoints.add(new MapLocation(38, 34));
        waypoints.add(new MapLocation(38, 16));
        waypoints.add(new MapLocation(28, 16));
        waypoints.add(new MapLocation(28, 21));
        waypoints.add(new MapLocation(25, 21));
        waypoints.add(new MapLocation(25, 26));
        waypoints.add(new MapLocation(20, 26));
        waypoints.add(new MapLocation(20, 31));
        waypoints.add(new MapLocation(26, 31));

        createGuard(waypoints, 15, imageGuard);

        // Create some ninjas
        Entity ninja;

        waypoints = new ArrayList<>();
        waypoints.add(new MapLocation(0, 34));
        waypoints.add(new MapLocation(15, 34));
        waypoints.add(new MapLocation(15, 5));
        waypoints.add(new MapLocation(20, 5));
        waypoints.add(new MapLocation(20, 3));
        waypoints.add(new MapLocation(31, 3));
        waypoints.add(new MapLocation(31, 6));
        waypoints.add(new MapLocation(38, 6));
        waypoints.add(new MapLocation(38, 7));
        waypoints.add(new MapLocation(39, 7));
        waypoints.add(new MapLocation(39, 11));
        waypoints.add(new MapLocation(37, 11));

        createNinja(waypoints, 8, imageNinja);

        //

        waypoints = new ArrayList<>();
        waypoints.add(new MapLocation(0, 34));
        waypoints.add(new MapLocation(15, 34));
        waypoints.add(new MapLocation(15, 36));
        waypoints.add(new MapLocation(26, 36));
        waypoints.add(new MapLocation(26, 31));
        waypoints.add(new MapLocation(29, 31));
        waypoints.add(new MapLocation(29, 28));
        waypoints.add(new MapLocation(33, 28));
        waypoints.add(new MapLocation(33, 25));
        waypoints.add(new MapLocation(38, 25));
        waypoints.add(new MapLocation(38, 16));
        waypoints.add(new MapLocation(39, 16));
        waypoints.add(new MapLocation(39, 11));
        waypoints.add(new MapLocation(37, 11));

        createNinja(waypoints, 8, imageNinja);

        //

        waypoints = new ArrayList<>();
        waypoints.add(new MapLocation(0, 34));
        waypoints.add(new MapLocation(15, 34));
        waypoints.add(new MapLocation(15, 11));
        waypoints.add(new MapLocation(22, 11));
        waypoints.add(new MapLocation(22, 21));
        waypoints.add(new MapLocation(25, 21));
        waypoints.add(new MapLocation(25, 26));
        waypoints.add(new MapLocation(29, 26));
        waypoints.add(new MapLocation(29, 31));
        waypoints.add(new MapLocation(26, 31));
        waypoints.add(new MapLocation(26, 34));
        waypoints.add(new MapLocation(38, 34));
        waypoints.add(new MapLocation(38, 16));
        waypoints.add(new MapLocation(39, 16));
        waypoints.add(new MapLocation(39, 11));
        waypoints.add(new MapLocation(37, 11));

        createNinja(waypoints, 8, imageNinja);

        // Create a lord

        Entity lord = world.createEntity();
            Position p = new Position();
            p.xTile = 36;
            p.yTile = 11;
            lord.addComponent(p);
            Sprite sprite = new Sprite();
            sprite.img = imageLord;
            lord.addComponent(sprite);
            // LordState ls = new LordState();
            // // ls.activity.addFirst(new Infiltrate(path));
            // ls.waitTimeBeforeMove = 66;
            // lord.addComponent(ls);
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

