package xyz.srgnis.csvplatformer.level.platform;

import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Mesh;
import com.jme3.scene.shape.Box;
import xyz.srgnis.csvplatformer.Materials;
import xyz.srgnis.csvplatformer.config.Config;

public class SimplePlatform extends Platform {
    private static Mesh defaultMesh = new Box(Config.CELL_DIM_X / 2, 0.5f, Config.CELL_DIM_Z / 2);

    public SimplePlatform(int col, int row, float height) {
        this(new Vector3f(
                Config.INIT_PLATFORM_DEEP + (row * Config.CELL_DIM_X) + Config.CELL_DIM_X / 2,
                height,
                (col * Config.CELL_DIM_Z) + Config.CELL_DIM_Z / 2
        ));
    }

    public SimplePlatform(Vector3f pos) {
        this(defaultMesh, Materials.GREEN_MATERIAL, pos);
    }


    public SimplePlatform(Mesh mesh, Material material, Vector3f pos) {
        super(mesh, material, pos);
    }
}
