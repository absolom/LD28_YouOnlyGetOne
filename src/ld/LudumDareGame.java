package ld;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LudumDareGame
{
    static public void main(String[] args)
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
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
