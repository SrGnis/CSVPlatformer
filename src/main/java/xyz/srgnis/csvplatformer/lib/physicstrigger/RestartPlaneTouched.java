package xyz.srgnis.csvplatformer.lib.physicstrigger;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.objects.PhysicsBody;
import com.jme3.bullet.objects.PhysicsCharacter;
import xyz.srgnis.csvplatformer.CSVPlatformer;

public class RestartPlaneTouched extends PhysicsTrigger {
    public RestartPlaneTouched(PhysicsBody triggerBody, PhysicsSpace physicsSpace) {
        super(triggerBody, physicsSpace);
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        PhysicsCollisionObject a = event.getObjectA();
        PhysicsCollisionObject b = event.getObjectB();
        boolean isPlayer = a instanceof PhysicsCharacter;
        boolean isRestartPLane = b.equals(triggerBody);

        if (isRestartPLane && isPlayer)
            CSVPlatformer.INSTANCE.getLevel().movePlayerToStart();
    }
}
