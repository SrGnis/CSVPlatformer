package xyz.srgnis.csvplatformer.control;


import com.jme3.font.BitmapText;
import com.jme3.math.Vector3f;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.scene.control.AbstractControl;
import xyz.srgnis.csvplatformer.CSVPlatformer;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StopWatchControl extends AbstractControl {

    private long time = 0;
    private boolean run = false;
    private float startX;
    private float endX = Float.POSITIVE_INFINITY;

    static public String stopWatchString(long time) {
        return (new SimpleDateFormat("mm:ss:SSS")).format(new Date(time));
    }

    @Override
    protected void controlUpdate(float tpf) {
        Vector3f location = CSVPlatformer.INSTANCE.getPlayer().getPlayerControl().getPhysicsLocation();
        if (!run && location.getX() >= startX) {
            start();
        }
        if (run && location.getX() < startX) {
            stop();
            restart();
        }
        if (run && location.getX() >= endX) {
            stop();
        }
        if (run) {
            time += tpf * 1000;
        }
    }

    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {
        ((BitmapText) spatial).setText(stopWatchString(time));
    }

    public void start() {
        run = true;
    }

    public void stop() {
        run = false;
    }

    public void restart() {
        time = 0;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }
}
