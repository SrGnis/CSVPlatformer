package xyz.srgnis.csvplatformer.appstate;

import com.jme3.app.Application;
import com.jme3.app.state.BaseAppState;
import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.PhysicsTickListener;
import com.jme3.input.InputManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.math.Quaternion;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.Sounds;
import xyz.srgnis.csvplatformer.config.Config;
import xyz.srgnis.csvplatformer.player.Player;

public class PlayerState extends BaseAppState implements ActionListener, PhysicsTickListener {

    private static volatile boolean jumpRequested;
    private static volatile boolean walkAway;
    private static volatile boolean walkLeft;
    private static volatile boolean walkRight;
    private static volatile boolean walkToward;
    private Player player;
    private boolean lastOnGround;
    private float lastJumpTime = -1;
    private Vector3f lastVelocity;


    @Override
    protected void initialize(Application app) {
        player = new Player();

        CSVPlatformer.INSTANCE.getPhysicsSpace().addTickListener(this);

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

        /*TODO: we should use the walk direction but, we can set it to 0,0,0 and this causes problems with the interpolation
        so as a temporal measure whe use the view direction but we need to use a local variable to store it or something*/
        // When we implement movement by speed this problem will dissapear
        Quaternion currentDir = new Quaternion().lookAt(player.getPlayerControl().getViewDirection(new Vector3f()), Vector3f.UNIT_Y);
        Quaternion desiredDir = new Quaternion().lookAt(direction, Vector3f.UNIT_Y);

        currentDir.nlerp(desiredDir, .15f);

        Vector3f result = currentDir.mult(Vector3f.UNIT_Z);
        //TODO: this should be in Player?

        if (!direction.equals(Vector3f.ZERO)) {
            player.getPlayerControl().setWalkDirection(result.mult(Config.MOV_SPEED));
            player.getPlayerControl().setViewDirection(result);
        } else {
            player.getPlayerControl().setWalkDirection(Vector3f.ZERO);
        }

        if (player.getPlayerControl().isOnGround()) {
            if (direction.equals(Vector3f.ZERO)) {
                player.setAnimation("Idle");
            } else {
                player.setAnimation("Run");
            }
        } else {
            player.setAnimation("Jumping");
        }

        // We need to set a small cool-down for jumps, if not, multiple jumps can be invoked in fractions of second
        // when jumping on ledges
        if (lastJumpTime >= 0) lastJumpTime += tpf;
        if (lastJumpTime >= Config.JUMP_COOLDOWN) lastJumpTime = -1;
        // Decide whether to start jumping.
        if (jumpRequested && player.getPlayerControl().isOnGround() && lastJumpTime < 0) {
            player.getPlayerControl().jump();
            Sounds.rand_monke_jump().play();
            lastJumpTime = 0;
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

    @Override
    public void prePhysicsTick(PhysicsSpace space, float timeStep) {
        lastOnGround = player.getPlayerControl().isOnGround();
        lastVelocity = player.getPlayerControl().getVelocity().clone();
    }

    @Override
    public void physicsTick(PhysicsSpace space, float timeStep) {
        if (player.getPlayerControl().isOnGround() && !lastOnGround && lastVelocity.getY() < -10) {
            //System.out.println(lastVelocity + " " + player.getPlayerControl().getVelocity() );
            Sounds.LAND.play();
            player.setAnimation("JumpEnd");
        }
    }
}
