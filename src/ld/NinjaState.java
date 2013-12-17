package ld;

import java.util.ArrayDeque;
import java.util.Deque;

import com.artemis.Component;

public class NinjaState extends Component
{
    public int timeSpentWaiting;
    public int waitTimeBeforeMove;
    public Deque<NinjaActivity> activity = new ArrayDeque<NinjaActivity>();
}
