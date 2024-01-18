package xyz.srgnis.csvplatformer.level.loader;

import org.jetbrains.annotations.Nullable;
import xyz.srgnis.csvplatformer.level.Level;

public interface LevelLoader {
    public Level loadLevel(@Nullable String source);
}
