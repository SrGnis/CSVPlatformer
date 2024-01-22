package xyz.srgnis.csvplatformer.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.level.Level;
import xyz.srgnis.csvplatformer.level.loader.CSVLoader;
import xyz.srgnis.csvplatformer.level.loader.LevelLoader;
import xyz.srgnis.csvplatformer.level.loader.RandLoader;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

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
        inputManager.addMapping("load_file", new KeyTrigger(KeyInput.KEY_O));
        inputManager.addListener(this, "random_restart", "load_file");
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
        if (name == "load_file" && isPressed) {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "CSV files", "csv");
            chooser.setFileFilter(filter);
            CSVPlatformer.INSTANCE.showMouse();
            int returnVal = chooser.showOpenDialog(null);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                loadLevel(chooser.getSelectedFile().getAbsolutePath(), new CSVLoader(CSVPlatformer.INSTANCE));
            }
            CSVPlatformer.INSTANCE.hideMouse();
        }
    }

    public Level getLevel() {
        return level;
    }
}
