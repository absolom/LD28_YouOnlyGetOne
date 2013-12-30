package ld.Components;

import java.util.ArrayList;
import java.util.List;

import ld.Map.MoveDirection;

import com.artemis.Component;
import com.artemis.Entity;

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