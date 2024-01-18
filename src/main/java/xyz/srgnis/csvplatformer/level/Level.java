package xyz.srgnis.csvplatformer.level;

import com.jme3.bullet.PhysicsSpace;
import com.jme3.bullet.collision.shapes.PlaneCollisionShape;
import com.jme3.bullet.objects.PhysicsBody;
import com.jme3.bullet.objects.PhysicsRigidBody;
import com.jme3.math.Plane;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.shape.Box;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.Materials;
import xyz.srgnis.csvplatformer.config.Config;
import xyz.srgnis.csvplatformer.level.platform.Platform;
import xyz.srgnis.csvplatformer.level.platform.SimplePlatform;
import xyz.srgnis.csvplatformer.lib.physicstrigger.PhysicsTrigger;
import xyz.srgnis.csvplatformer.lib.physicstrigger.RestartPlaneTouched;

import java.util.HashSet;

public class Level {
    private PhysicsSpace physicsSpace;
    private Node rootNode;
    private int columnNumber;
    private Platform initialPlatform;
    private HashSet<Platform> platforms = new HashSet<>();
    private PhysicsTrigger restartPlane;


    public Level(int columnNumber, CSVPlatformer app) {
        this.columnNumber = columnNumber;
        this.rootNode = app.getRootNode();
        this.physicsSpace = app.getPhysicsSpace();

        createRestartPlane();
        createInitialPlatform(this.columnNumber);
    }

    private void createRestartPlane() {
        Plane plane = new Plane(Vector3f.UNIT_Y, Config.RESTART_PLANE_HEIGHT);
        PlaneCollisionShape shape = new PlaneCollisionShape(plane);

        PhysicsRigidBody restartPlane = new PhysicsRigidBody(shape, PhysicsBody.massForStatic);

        this.restartPlane = new RestartPlaneTouched(restartPlane, physicsSpace);
    }

    private void createInitialPlatform(int colNum) {
        float width = colNum * Config.CELL_DIM_Z;

        Platform platform = new SimplePlatform(
                new Box(Config.INIT_PLATFORM_DEEP / 2, 0.5f, width / 2),
                Materials.GREEN_MATERIAL,
                new Vector3f(
                        Config.INIT_PLATFORM_DEEP / 2,
                        Config.INIT_PLATFORM_Y,
                        width / 2
                )
        );

        setInitialPlatform(platform);
    }

    public void addPlatform(Platform platform) {
        addPlatform(platform, true);
    }

    public void addPlatform(Platform platform, boolean addToSet) {
        rootNode.attachChild(platform.getGeometry());
        physicsSpace.add(platform.getRigidBodyControl());
        if (addToSet)
            platforms.add(platform);
    }

    public void removePlatform(Platform platform) {
        platform.getGeometry().removeFromParent();
        physicsSpace.remove(platform.getRigidBodyControl());
        if (platforms.contains(platform))
            platforms.remove(platform);
    }

    public Platform getInitialPlatform() {
        return initialPlatform;
    }

    public void setInitialPlatform(Platform platform) {
        if (this.initialPlatform != null) {
            removePlatform(this.initialPlatform);
        }
        addPlatform(platform, false);
        this.initialPlatform = platform;
    }

    public void movePlayerToStart() {
        if (initialPlatform != null) {
            Vector3f startPos = initialPlatform.getGeometry().getLocalTranslation();
            startPos = startPos.add(0, 2, 0);
            CSVPlatformer.INSTANCE.getPlayer().moveTo(startPos);
        }
    }

    public void destroy() {
        removePlatform(initialPlatform);
        initialPlatform.destroy();
        initialPlatform = null;

        restartPlane.destroy();
        restartPlane = null;

        Platform[] platforms = new Platform[this.platforms.size()];
        platforms = this.platforms.toArray(platforms);
        for (Platform platform : platforms) {
            removePlatform(platform);
            platform.destroy();
        }
    }
}
