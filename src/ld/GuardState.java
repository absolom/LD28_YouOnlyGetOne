package ld;

import java.util.ArrayDeque;
import java.util.Deque;

import com.artemis.Component;

public class GuardState extends Component
{
    public int timeSpentWaiting;
    public int waitTimeBeforeMove;
    public Deque<GuardActivity> activity = new ArrayDeque<GuardActivity>();
}
