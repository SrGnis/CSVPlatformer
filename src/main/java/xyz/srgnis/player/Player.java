package xyz.srgnis.player;

import com.jme3.bullet.collision.shapes.SphereCollisionShape;
import com.jme3.bullet.control.CharacterControl;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Sphere;
import xyz.srgnis.Materials;
import xyz.srgnis.config.Config;

public class Player {
    private Geometry playerGeometry;
    private CharacterControl playerControl;

    public Player() {
        Sphere sphere = new Sphere(10, 10, 1);
        playerGeometry = new Geometry("Player", sphere);
        playerGeometry.setMaterial(Materials.RED_MATERIAL);

        // Create the PhysicsControl
        SphereCollisionShape shape = new SphereCollisionShape(1);
        float stepHeight = 0.01f;
        playerControl = new CharacterControl(shape, stepHeight);
        playerControl.setGravity(Config.GRAVITY);
        playerControl.setJumpSpeed(Config.JUMP_SPEED);
        playerGeometry.addControl(playerControl);
    }

    public Geometry getPlayerGeometry() {
        return playerGeometry;
    }

    public CharacterControl getPlayerControl() {
        return playerControl;
    }

    public void moveTo(Vector3f pos) {
        playerControl.setPhysicsLocation(pos);
    }
}
