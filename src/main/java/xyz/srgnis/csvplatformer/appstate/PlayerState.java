package xyz.srgnis.csvplatformer.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.config.Config;
import xyz.srgnis.csvplatformer.player.Player;

public class PlayerState extends BaseAppState implements ActionListener {

    private static volatile boolean jumpRequested;
    private static volatile boolean walkAway;
    private static volatile boolean walkLeft;
    private static volatile boolean walkRight;
    private static volatile boolean walkToward;
    private Player player;
    private float jumpTime;


    @Override
    protected void initialize(Application app) {
        player = new Player();

        configureInput();
    }

    @Override
    protected void cleanup(Application app) {

    }

    @Override
    protected void onEnable() {
        CSVPlatformer app = (CSVPlatformer) getApplication();
        app.setPlayer(player);
    }

    @Override
    protected void onDisable() {

    }

    @Override
    public void update(float tpf) {

        Camera cam = getApplication().getCamera();

        Vector3f away = cam.getDirection();
        away.y = 0;
        away.normalizeLocal();

        Vector3f left = cam.getLeft();
        left.y = 0;
        left.normalizeLocal();

        // Determine the walk velocity from keyboard inputs.
        Vector3f direction = new Vector3f();
        if (walkAway) {
            direction.addLocal(away);
        }
        if (walkLeft) {
            direction.addLocal(left);
        }
        if (walkRight) {
            direction.subtractLocal(left);
        }
        if (walkToward) {
            direction.subtractLocal(away);
        }
        direction.normalizeLocal();

        Vector3f walkOffset = direction.mult(Config.MOV_SPEED);


        //TODO: this should be in Player?
        player.getPlayerControl().setWalkDirection(walkOffset);

        // Decide whether to start jumping.
        if (jumpRequested && player.getPlayerControl().onGround() && jumpTime == 0) {
            jumpTime = tpf;
        }

        // FIXME: in low fps the jump is shorter?
        if (jumpTime > 0) {
            player.getPlayerControl().jump();
            jumpTime += tpf;
            jumpTime = jumpTime > Config.JUMP_TIME ? 0 : jumpTime;
        }
    }

    /**
     * Configure keyboard input.
     */
    private void configureInput() {
        InputManager inputManager = this.getApplication().getInputManager();
        inputManager.addMapping("jump", new KeyTrigger(KeyInput.KEY_SPACE));
        inputManager.addMapping("walk away", new KeyTrigger(KeyInput.KEY_W));
        inputManager.addMapping("walk left", new KeyTrigger(KeyInput.KEY_A));
        inputManager.addMapping("walk right", new KeyTrigger(KeyInput.KEY_D));
        inputManager.addMapping("walk toward", new KeyTrigger(KeyInput.KEY_S));
        inputManager.addListener(this,
                "jump", "walk away", "walk left", "walk right", "walk toward");
    }

    /**
     * Callback to handle keyboard input events.
     *
     * @param action    the name of the input event
     * @param isPressed true &rarr; pressed, false &rarr; released
     * @param tpf       the time per frame (in seconds, &ge;0)
     */
    @Override
    public void onAction(String action, boolean isPressed, float tpf) {
        switch (action) {
            case "jump":
                jumpRequested = isPressed;
                return;
            case "walk away":
                walkAway = isPressed;
                return;
            case "walk left":
                walkLeft = isPressed;
                return;
            case "walk right":
                walkRight = isPressed;
                return;
            case "walk toward":
                walkToward = isPressed;
                return;
            default:
        }
    }

    public Player getPlayer() {
        return player;
    }
}
