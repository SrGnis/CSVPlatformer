package xyz.srgnis.csvplatformer.lib.physicstrigger;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.PhysicsCollisionEvent;
import com.jme3.bullet.collision.PhysicsCollisionObject;
import com.jme3.bullet.objects.PhysicsBody;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.appstate.StopWatch;

public class StartStopWatch extends PhysicsTrigger {
    private StopWatch stopWatch;

    public StartStopWatch(StopWatch stopWatch, PhysicsBody triggerBody, PhysicsSpace physicsSpace) {
        super(triggerBody, physicsSpace);
        this.stopWatch = stopWatch;
    }

    @Override
    public void collision(PhysicsCollisionEvent event) {
        PhysicsCollisionObject a = event.getObjectA();
        PhysicsCollisionObject b = event.getObjectB();
        boolean isPlayer = a.equals(CSVPlatformer.INSTANCE.getPlayer().getPlayerControl().getCharacter());
        boolean isTriggerBody = b.equals(triggerBody);

        if (isTriggerBody && isPlayer)
            stopWatch.start();
    }
}
