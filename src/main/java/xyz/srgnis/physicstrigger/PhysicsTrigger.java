package xyz.srgnis.physicstrigger;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionListener;
import com.jme3.bullet.objects.PhysicsBody;

public abstract class PhysicsTrigger implements PhysicsCollisionListener {
    protected PhysicsSpace physicsSpace;
    protected PhysicsBody triggerBody;

    public PhysicsTrigger(PhysicsBody triggerBody, PhysicsSpace physicsSpace) {
        this.triggerBody = triggerBody;
        this.physicsSpace = physicsSpace;
        physicsSpace.addCollisionObject(triggerBody);
        physicsSpace.addCollisionListener(this);
    }

    public void destroy() {
        physicsSpace.remove(triggerBody);
        physicsSpace.removeCollisionListener(this);
    }
}
