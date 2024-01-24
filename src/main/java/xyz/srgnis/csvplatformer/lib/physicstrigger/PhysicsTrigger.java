package xyz.srgnis.csvplatformer.lib.physicstrigger;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.collision.PhysicsCollisionObject;

public abstract class PhysicsTrigger implements PhysicsCollisionListener {
    protected PhysicsSpace physicsSpace;
    protected PhysicsCollisionObject triggerBody;

    public PhysicsTrigger(PhysicsCollisionObject triggerBody, PhysicsSpace physicsSpace) {
        this.triggerBody = triggerBody;
        this.physicsSpace = physicsSpace;
        physicsSpace.addCollisionObject(triggerBody);
        physicsSpace.addCollisionListener(this);
    }

    public void destroy() {
        physicsSpace.remove(triggerBody);
        physicsSpace.removeCollisionListener(this);
    }

    //TODO: write collison
}
