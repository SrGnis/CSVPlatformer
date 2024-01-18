package xyz.srgnis.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import xyz.srgnis.CSVPlatformer;
import xyz.srgnis.level.Level;
import xyz.srgnis.level.loader.LevelLoader;
import xyz.srgnis.level.loader.RandLoader;

public class LevelState extends BaseAppState implements ActionListener {

    private Level level;

    @Override
    protected void initialize(Application app) {
        configureInput();
        //TODO: load random and csv file
        loadLevel(null, new RandLoader(CSVPlatformer.INSTANCE));

    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {

    }

    @Override
    protected void onDisable() {

    }

    /**
     * Configure keyboard input.
     */
    private void configureInput() {
        InputManager inputManager = this.getApplication().getInputManager();
        inputManager.addMapping("random_restart", new KeyTrigger(KeyInput.KEY_R));
        inputManager.addListener(this, "random_restart");
    }

    private void loadLevel(String source, LevelLoader levelLoader) {
        if (this.level != null)
            this.level.destroy();

        this.level = levelLoader.loadLevel(source);
        level.movePlayerToStart();
    }

    @Override
    public void onAction(String name, boolean isPressed, float tpf) {
        if (name == "random_restart" && isPressed) {
            loadLevel(null, new RandLoader(CSVPlatformer.INSTANCE));
        }
    }

    public Level getLevel() {
        return level;
    }
}
