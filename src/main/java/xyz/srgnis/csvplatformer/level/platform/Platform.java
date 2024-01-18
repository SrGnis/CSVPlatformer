package xyz.srgnis.csvplatformer.level.platform;

import com.jme3.bullet.collision.shapes.CollisionShape;
import com.jme3.bullet.collision.shapes.MeshCollisionShape;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.bullet.objects.PhysicsBody;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Mesh;

public abstract class Platform {
    protected Geometry geometry;
    protected RigidBodyControl rigidBodyControl;

    public Platform(Mesh mesh, Material material, Vector3f pos) {
        geometry = new Geometry("platform", mesh);
        geometry.setLocalTranslation(pos);

        geometry.setMaterial(material);

        CollisionShape shape = new MeshCollisionShape(mesh);
        rigidBodyControl = new RigidBodyControl(shape, PhysicsBody.massForStatic);
        geometry.addControl(rigidBodyControl);
    }

    public Geometry getGeometry() {
        return geometry;
    }

    public RigidBodyControl getRigidBodyControl() {
        return rigidBodyControl;
    }

    public void destroy() {
        //TODO: We need to do someting more to cleant this?
    }
}
