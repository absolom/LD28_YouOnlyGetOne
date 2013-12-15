package ld;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.EOFException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public class TileMap
{
    int numRows;
    int numColumns;

    List<List<Integer>> tileData;

    public TileMap() throws IOException
    {
        numRows = 0;
        numColumns = 0;
        tileData = new ArrayList<>();
    }

    public void setData(List<String> lines)
    {
        tileData.clear();

        for(int i = 0; i < lines.size(); i++)
        {
            List<Integer> rowData = new ArrayList<>();
            String rowStr = lines.get(i);

            StringTokenizer tokenizer = new StringTokenizer(rowStr, ",", false);
            while(tokenizer.hasMoreTokens())
            {
                int tileId = Integer.parseInt(tokenizer.nextToken());
                rowData.add(tileId);
            }

            tileData.add(rowData);
        }
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();

        Iterator<List<Integer>> it = tileData.iterator();

        while(it.hasNext())
        {
            List<Integer> row = it.next();
            Iterator<Integer> itRow = row.iterator();

            while(itRow.hasNext())
            {
                int tileId = itRow.next();
                sb.append(tileId);
                sb.append(" ");
            }
        }

        return sb.toString();
    }

}
