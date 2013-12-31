package ld.Input;

import org.newdawn.slick.*;
import com.artemis.*;
import ld.Map.*;
import ld.Systems.*;

public class Mapper implements KeyListener, MouseListener
{
    Input input;
    GhostSystem sys;

    public Mapper()
    {
    }

    public void setGhostSystem(GhostSystem ghostSystem)
    {
        sys = ghostSystem;
    }

    @Override
    public void inputEnded()
    {
    }

    @Override
    public void inputStarted()
    {
    }

    @Override
    public boolean isAcceptingInput()
    {
        return true;
    }

    @Override
    public void setInput(Input input)
    {
        this.input = input;
    }

    @Override
    public void mouseClicked(int button, int x, int y, int count)
    {
    }

    @Override
    public void mouseDragged(int oldx, int oldy, int x, int y)
    {
    }

    @Override
    public void mouseMoved(int oldx, int oldy, int x, int y)
    {
    }

    @Override
    public void mousePressed(int button, int x, int y)
    {
    }

    @Override
    public void mouseReleased(int button, int x, int y)
    {
    }

    @Override
    public void mouseWheelMoved(int delta)
    {
    }

    @Override
    public void keyPressed(int key, char c)
    {
        if(key == Input.KEY_UP)
            sys.movingTowards(MoveDirection.NORTH);

        if(key == Input.KEY_RIGHT)
            sys.movingTowards(MoveDirection.EAST);

        if(key == Input.KEY_DOWN)
            sys.movingTowards(MoveDirection.SOUTH);

        if(key == Input.KEY_LEFT)
            sys.movingTowards(MoveDirection.WEST);
    }

    @Override
    public void keyReleased(int key, char c)
    {
        if(key == Input.KEY_UP ||
           key == Input.KEY_RIGHT ||
           key == Input.KEY_DOWN ||
           key == Input.KEY_LEFT)
            sys.stopMoving();
    }
}