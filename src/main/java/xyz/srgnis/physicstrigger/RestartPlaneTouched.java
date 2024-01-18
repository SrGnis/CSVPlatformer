package xyz.srgnis.physicstrigger;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.objects.PhysicsBody;
import xyz.srgnis.CSVPlatformer;

public class RestartPlaneTouched extends PhysicsTrigger {
    public RestartPlaneTouched(PhysicsBody triggerBody, PhysicsSpace physicsSpace) {
        super(triggerBody, physicsSpace);
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        PhysicsCollisionObject a = event.getObjectA();
        PhysicsCollisionObject b = event.getObjectB();
        boolean isPlayer = a.equals(CSVPlatformer.INSTANCE.getPlayer().getPlayerControl().getCharacter());
        boolean isRestartPLane = b.equals(triggerBody);

        if (isRestartPLane && isPlayer)
            CSVPlatformer.INSTANCE.getLevel().movePlayerToStart();
    }
}
