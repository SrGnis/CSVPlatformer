package xyz.srgnis.level.loader;

import org.jetbrains.annotations.Nullable;
import xyz.srgnis.level.Level;

public interface LevelLoader {
    public Level loadLevel(@Nullable String source);
}
