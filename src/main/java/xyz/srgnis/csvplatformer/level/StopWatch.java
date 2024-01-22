package xyz.srgnis.csvplatformer.level;

import com.jme3.app.Application;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapText;
import com.jme3.math.ColorRGBA;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.control.StopWatchControl;

public class StopWatch {

    private BitmapText stopWatchText;
    private StopWatchControl stopWatchControl;
    private float startX;
    private float endX;

    public StopWatch(Application app) {
        BitmapFont font = ((CSVPlatformer) app).getGuiFont();

        stopWatchText = new BitmapText(font, false);
        stopWatchText.setSize(font.getCharSet().getRenderedSize() * 2);
        stopWatchText.setColor(ColorRGBA.White);
        stopWatchText.setLocalTranslation(0, app.getContext().getFramebufferHeight(), 0);

        ((CSVPlatformer) app).getGuiNode().attachChild(stopWatchText);

        stopWatchControl = new StopWatchControl();
        stopWatchText.addControl(stopWatchControl);
    }

    public void start() {
        stopWatchControl.start();
    }

    public void stop() {
        stopWatchControl.stop();
    }

    public void restart() {
        stopWatchControl.restart();
    }

    public void setStartX(float startX) {
        this.stopWatchControl.setStartX(startX);
    }

    public void setEndX(float endX) {
        this.stopWatchControl.setEndX(endX);
    }

    public void destroy() {
        stopWatchText.removeFromParent();
    }
}
