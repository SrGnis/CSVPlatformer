package xyz.srgnis.level.loader;

import com.jme3.math.FastMath;
import org.jetbrains.annotations.Nullable;
import xyz.srgnis.CSVPlatformer;
import xyz.srgnis.config.Config;
import xyz.srgnis.level.Level;
import xyz.srgnis.level.platform.SimplePlatform;

public class RandLoader implements LevelLoader {

    private final CSVPlatformer app;

    public RandLoader(CSVPlatformer app) {
        this.app = app;
    }

    @Override
    public Level loadLevel(@Nullable String seed) {
        int max = (int) (Config.INIT_PLATFORM_Y + Config.INIT_PLATFORM_Y / 2);
        int min = (int) (Config.INIT_PLATFORM_Y - Config.INIT_PLATFORM_Y / 2);

        Level level = new Level(Config.RAND_LEVEL_WIDTH, app);

        for (int w = 0; w < Config.RAND_LEVEL_WIDTH; w++) {
            for (int l = 0; l < Config.RAND_LEVEL_LENGTH; l++) {
                int height = FastMath.nextRandomInt(min, max);
                level.addPlatform(new SimplePlatform(w, l, height));
            }
        }
        return level;
    }
}
