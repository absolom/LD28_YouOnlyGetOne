package ld;

import com.artemis.Component;
import com.artemis.Entity;
import java.util.List;
import java.util.ArrayList;

public class Vision extends Component
{
    public MoveDirection lookDir;
    public List<Entity> seenGuards;
    public List<Entity> seenNinjas;

    public Vision()
    {
        lookDir = MoveDirection.NONE;
        seenGuards = new ArrayList<>();
        seenNinjas = new ArrayList<>();
    }
}