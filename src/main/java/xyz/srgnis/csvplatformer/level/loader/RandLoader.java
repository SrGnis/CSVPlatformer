package xyz.srgnis.csvplatformer.level.loader;

import com.jme3.math.FastMath;
import org.jetbrains.annotations.Nullable;
import xyz.srgnis.csvplatformer.CSVPlatformer;
import xyz.srgnis.csvplatformer.config.Config;
import xyz.srgnis.csvplatformer.level.Level;
import xyz.srgnis.csvplatformer.level.platform.SimplePlatform;

public class RandLoader implements LevelLoader {

    private final CSVPlatformer app;

    public RandLoader(CSVPlatformer app) {
        this.app = app;
    }

    @Override
    public Level loadLevel(@Nullable String seed) {
        int max = (int) (Config.INIT_PLATFORM_Y + Config.INIT_PLATFORM_Y / 3);
        int min = (int) (Config.INIT_PLATFORM_Y - Config.INIT_PLATFORM_Y / 3);

        Level level = new Level(Config.RAND_LEVEL_WIDTH, app);

        for (int col = 0; col < Config.RAND_LEVEL_WIDTH; col++) {
            for (int row = 0; row < Config.RAND_LEVEL_LENGTH; row++) {
                int height = FastMath.nextRandomInt(min, max);
                level.addPlatform(new SimplePlatform(col, row, height));
            }
        }
        level.createEndPlatform(Config.RAND_LEVEL_WIDTH, Config.RAND_LEVEL_LENGTH);
        return level;
    }
}
