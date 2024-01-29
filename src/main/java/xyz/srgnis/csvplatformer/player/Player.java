package xyz.srgnis.csvplatformer.player;

import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Sphere;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.Materials;
import xyz.srgnis.csvplatformer.config.Config;

public class Player {
    private Geometry playerGeometry;
    private BetterCharacterControl playerControl;
    private Node playerNode = new Node("player node");

    public Player() {
        Sphere sphere = new Sphere(10, 10, 1);
        playerGeometry = new Geometry("Player", sphere);
        playerGeometry.setMaterial(Materials.RED_MATERIAL);

        CSVPlatformer.INSTANCE.getRootNode().attachChild(playerNode);
        playerNode.attachChild(playerGeometry);
        playerGeometry.move(0f, 1f, 0f);


        // Create the PhysicsControl
        playerControl = new BetterCharacterControl(0.75f, 2f + 0.001f, Config.MASS);
        playerControl.setJumpForce(Vector3f.UNIT_Y.mult(Config.JUMP_FORCE));

        playerNode.addControl(playerControl);
    }

    public Geometry getPlayerGeometry() {
        return playerGeometry;
    }

    public BetterCharacterControl getPlayerControl() {
        return playerControl;
    }

    public void moveTo(Vector3f pos) {
        playerControl.warp(pos);
    }
}
