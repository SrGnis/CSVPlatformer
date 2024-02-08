package xyz.srgnis.csvplatformer.player;

import com.jme3.anim.AnimComposer;
import com.jme3.anim.util.AnimMigrationUtils;
import com.jme3.bullet.control.BetterCharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.config.Config;

public class Player {
    private Spatial playerSpatial;
    private BetterCharacterControl playerControl;
    private Node playerNode = new Node("player node");
    private AnimComposer composer;

    public Player() {
//        Sphere sphere = new Sphere(10, 10, 1);
//        playerSpatial = new Geometry("Player", sphere);
//        playerSpatial.setMaterial(Materials.RED_MATERIAL);
//
//        CSVPlatformer.INSTANCE.getRootNode().attachChild(playerNode);
//        playerNode.attachChild(playerSpatial);
//        playerSpatial.move(0f, 1f, 0f);

        CSVPlatformer.INSTANCE.getRootNode().attachChild(playerNode);
        playerSpatial = CSVPlatformer.INSTANCE.getAssetManager().loadModel("Models/Jaime/Jaime.j3o");
        playerNode.attachChild(playerSpatial);
        playerSpatial.scale(2);
        //playerSpatial.move(0f,1f,0f);

        AnimMigrationUtils.migrate(playerSpatial);
        composer = playerSpatial.getControl(AnimComposer.class);
        composer.setGlobalSpeed(1f);

        // Create the PhysicsControl
        playerControl = new BetterCharacterControl(0.75f, 2f + 0.001f, Config.MASS);
        playerControl.setJumpForce(Vector3f.UNIT_Y.mult(Config.JUMP_FORCE));

        playerNode.addControl(playerControl);
    }

    public Spatial getPlayerSpatial() {
        return playerSpatial;
    }

    public BetterCharacterControl getPlayerControl() {
        return playerControl;
    }

    public void moveTo(Vector3f pos) {
        playerControl.warp(pos);
    }

    public void setAnimation(String animation) {
        var current = composer.getLayer(composer.DEFAULT_LAYER).getCurrentActionName();
        var target = composer.getAnimClip(animation);

        if (current == null || target != null && !target.getName().equals(current)) {
            composer.removeAction(current);
            composer.setCurrentAction(animation);
        }
    }

}
