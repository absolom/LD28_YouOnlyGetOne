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

import com.artemis.World;

public class LudumDareGame extends BasicGame
{
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 768;
    public static final int UPDATE_PERIOD = 33;
    public static final float SCALE = 3f;

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

            AppGameContainer app = new AppGameContainer(new LudumDareGame(new World()));

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

    public LudumDareGame(World w)
    {
        super("Ludum Dare 28 - You only get one AIRSTRIKE!");
        world = w;
    }

    @Override
    public void render(GameContainer container, Graphics g) throws SlickException
    {
        g.clear();

        // TODO: Render the map here
        Image tmp = tileImages.get(1);
        tmp.draw(300, 300, SCALE);

        tmp = tileImages.get(2);
        tmp.draw(400, 300, SCALE);

        tmp = tileImages.get(3);
        tmp.draw(300, 400, SCALE);

        tmp = tileImages.get(4);
        tmp.draw(400, 400, SCALE);
    }

    @Override
    public void init(GameContainer c) throws SlickException
    {
        timeSinceLastUpdate = 0;
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

