package xyz.srgnis.csvplatformer;

import com.jme3.app.ChaseCameraAppState;
import com.jme3.app.SimpleApplication;
import com.jme3.app.StatsAppState;
import com.jme3.app.state.ConstantVerifierState;
import com.jme3.audio.AudioListenerState;
import com.jme3.audio.AudioNode;
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.font.BitmapFont;
import com.jme3.math.Vector3f;
import com.jme3.system.AppSettings;
import xyz.srgnis.csvplatformer.appstate.LevelState;
import xyz.srgnis.csvplatformer.appstate.LightingState;
import xyz.srgnis.csvplatformer.appstate.PlayerState;
import xyz.srgnis.csvplatformer.config.Config;
import xyz.srgnis.csvplatformer.level.Level;
import xyz.srgnis.csvplatformer.player.Player;

/**
 * This is the Main Class of your Game. It should boot up your game and do initial initialisation
 * Move your Logic into AppStates or Controls or other java classes
 */
public class CSVPlatformer extends SimpleApplication {
    public static final CSVPlatformer INSTANCE = new CSVPlatformer();

    private PhysicsSpace physicsSpace;

    private CSVPlatformer() {
        super(
                new StatsAppState(),
                new AudioListenerState(),
                new ConstantVerifierState(),
                new ChaseCameraAppState(), // TODO: extend/implement our own
                new LightingState()
        );
    }

    public static void main(String[] args) {
        AppSettings settings = new AppSettings(true);
        settings.setFrequency(120);
        settings.setVSync(true);
        settings.setWidth(1024);
        settings.setHeight(768);
        settings.setGammaCorrection(true);

        INSTANCE.setSettings(settings);
        INSTANCE.setShowSettings(true);
        INSTANCE.start();
    }

    @Override
    public void simpleInitApp() {

        setDisplayFps(false);
        setDisplayStatView(false);

        //Load default Materials TODO: do this in the Materials Class
        Materials.RED_MATERIAL = Utils.createLitMaterial(assetManager, 0.5f, 0, 0);
        Materials.GREEN_MATERIAL = Utils.createLitMaterial(assetManager, 0, 0.5f, 0);

        //Load default Sounds TODO: do this in the Sounds class and do it with config files
        Sounds.WIND = new AudioNode(assetManager, "Sounds/Ambient/background_wind.ogg");
        Sounds.WIND.setPositional(false);
        Sounds.WIND.setDirectional(false);
        Sounds.WIND.setLooping(true);

        Sounds.JUMP = new AudioNode(assetManager, "Sounds/Effects/jump.ogg");
        Sounds.JUMP.setPositional(false);
        Sounds.JUMP.setDirectional(false);

        Sounds.LAND = new AudioNode(assetManager, "Sounds/Effects/land.ogg");
        Sounds.LAND.setPositional(false);
        Sounds.LAND.setDirectional(false);

        Sounds.MONKE_JUMP_1 = new AudioNode(assetManager, "Sounds/Effects/monke_jump_1.ogg");
        Sounds.MONKE_JUMP_1.setPositional(false);
        Sounds.MONKE_JUMP_1.setDirectional(false);
        Sounds.MONKE_JUMP_1.setVolume(0.5f);
        Sounds.MONKE_JUMP_2 = new AudioNode(assetManager, "Sounds/Effects/monke_jump_2.ogg");
        Sounds.MONKE_JUMP_2.setPositional(false);
        Sounds.MONKE_JUMP_2.setDirectional(false);
        Sounds.MONKE_JUMP_2.setVolume(0.5f);
        Sounds.MONKE_JUMP_3 = new AudioNode(assetManager, "Sounds/Effects/monke_jump_3.ogg");
        Sounds.MONKE_JUMP_3.setPositional(false);
        Sounds.MONKE_JUMP_3.setDirectional(false);
        Sounds.MONKE_JUMP_3.setVolume(0.5f);
        Sounds.updateMonkeJumps();


        //Initial Camera setup TODO: this should be done in the appState
        ChaseCameraAppState chaseCameraAppState = stateManager.getState(ChaseCameraAppState.class);
        chaseCameraAppState.setDragToRotate(false);
        chaseCameraAppState.setTarget(rootNode);

        BulletAppState bulletAppState = new BulletAppState();
        getStateManager().attach(bulletAppState);
        //bulletAppState.setDebugEnabled(true); // for debug visualization
        physicsSpace = bulletAppState.getPhysicsSpace();
        physicsSpace.setGravity(Vector3f.UNIT_Y.mult(-1 * Config.GRAVITY_FORCE));

        stateManager.attach(new PlayerState());
        stateManager.attach(new LevelState());

        Sounds.WIND.play();
    }

    public Player getPlayer() {
        return this.stateManager.getState(PlayerState.class).getPlayer();
    }

    public void setPlayer(Player player) {
        physicsSpace.add(player.getPlayerControl());

        ChaseCameraAppState chaseCameraAppState = stateManager.getState(ChaseCameraAppState.class);
        chaseCameraAppState.setTarget(player.getPlayerSpatial());
    }

    public Level getLevel() {
        return this.stateManager.getState(LevelState.class).getLevel();
    }

    public PhysicsSpace getPhysicsSpace() {
        return physicsSpace;
    }

    public BitmapFont getGuiFont() {
        return guiFont;
    }

    ;

    public void showMouse() {
        ChaseCameraAppState chaseCameraAppState = stateManager.getState(ChaseCameraAppState.class);
        chaseCameraAppState.setDragToRotate(true);
        chaseCameraAppState.setEnabled(false);
    }

    public void hideMouse() {
        ChaseCameraAppState chaseCameraAppState = stateManager.getState(ChaseCameraAppState.class);
        chaseCameraAppState.setDragToRotate(false);
        chaseCameraAppState.setEnabled(true);
    }
}
