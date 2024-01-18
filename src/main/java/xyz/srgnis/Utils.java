package xyz.srgnis;

import com.jme3.asset.AssetManager;
import com.jme3.material.Material;
import com.jme3.material.Materials;
import com.jme3.math.ColorRGBA;

public class Utils {

    /**
     * Create a single-sided lit material with the specified reflectivities.
     *
     * @param red   the desired reflectivity for red light (&ge;0, &le;1)
     * @param green the desired reflectivity for green light (&ge;0, &le;1)
     * @param blue  the desired reflectivity for blue light (&ge;0, &le;1)
     * @return a new instance (not null)
     */
    static public Material createLitMaterial(AssetManager assetManager, float red, float green, float blue) {
        Material result = new Material(assetManager, Materials.LIGHTING);
        result.setBoolean("UseMaterialColors", true);

        float opacity = 1f;
        result.setColor("Ambient", new ColorRGBA(red, green, blue, opacity));
        result.setColor("Diffuse", new ColorRGBA(red, green, blue, opacity));

        return result;
    }
}
