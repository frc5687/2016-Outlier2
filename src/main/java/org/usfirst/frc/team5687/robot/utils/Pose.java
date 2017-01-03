package org.usfirst.frc.team5687.robot.utils;

/**
 * Created by Ben Bernard on 6/18/2016.
 */
public class Pose {
    protected long _millis;

    public Pose() {
        _millis = System.currentTimeMillis();
    }

    public long getMillis() {
        return _millis;
    }
}
