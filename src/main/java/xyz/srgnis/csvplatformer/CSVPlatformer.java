package xyz.srgnis.csvplatformer;

import com.jme3.app.ChaseCameraAppState;
import com.jme3.app.DebugKeysAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.system.AppSettings;
import xyz.srgnis.csvplatformer.appstate.LevelState;
import xyz.srgnis.csvplatformer.appstate.LightingState;
import xyz.srgnis.csvplatformer.appstate.PlayerState;
import xyz.srgnis.csvplatformer.level.Level;
import xyz.srgnis.csvplatformer.player.Player;

/**
 * This is the Main Class of your Game. It should boot up your game and do initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */
public class CSVPlatformer extends SimpleApplication {
    public static final CSVPlatformer INSTANCE = new CSVPlatformer();

    private PhysicsSpace physicsSpace;

    public CSVPlatformer() {
        super(
                new StatsAppState(),
                new AudioListenerState(),
                new DebugKeysAppState(),
                new ConstantVerifierState(),
                new ChaseCameraAppState(), // TODO: extend/implement our own
                new LightingState()
        );
    }

    public static void main(String[] args) {
        INSTANCE.setShowSettings(true); //Settings dialog not supported on mac

        AppSettings settings = new AppSettings(true);
        settings.setGammaCorrection(true);
        INSTANCE.setSettings(settings);

        INSTANCE.start();
    }

    @Override
    public void simpleInitApp() {
        //Load default Materials
        Materials.RED_MATERIAL = Utils.createLitMaterial(assetManager, 0.5f, 0, 0);
        Materials.GREEN_MATERIAL = Utils.createLitMaterial(assetManager, 0, 0.5f, 0);

        //Initial Camera setup TODO: this should be done in the appState
        ChaseCameraAppState chaseCameraAppState = stateManager.getState(ChaseCameraAppState.class);
        chaseCameraAppState.setDragToRotate(false);
        chaseCameraAppState.setTarget(rootNode);

        BulletAppState bulletAppState = new BulletAppState();
        getStateManager().attach(bulletAppState);
        bulletAppState.setDebugEnabled(true); // for debug visualization
        physicsSpace = bulletAppState.getPhysicsSpace();
        stateManager.attach(new PlayerState());
        stateManager.attach(new LevelState());

    }

    public Player getPlayer() {
        return this.stateManager.getState(PlayerState.class).getPlayer();
    }

    public void setPlayer(Player player) {
        rootNode.attachChild(player.getPlayerGeometry());
        physicsSpace.add(player.getPlayerControl());

        ChaseCameraAppState chaseCameraAppState = stateManager.getState(ChaseCameraAppState.class);
        chaseCameraAppState.setTarget(player.getPlayerGeometry());
    }

    public Level getLevel() {
        return this.stateManager.getState(LevelState.class).getLevel();
    }

    public PhysicsSpace getPhysicsSpace() {
        return physicsSpace;
    }
}