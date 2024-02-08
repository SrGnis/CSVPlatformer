package xyz.srgnis.csvplatformer;

import com.jme3.audio.AudioNode;
import com.jme3.math.FastMath;

import java.util.List;

public class Sounds {
    public static AudioNode WIND;
    public static AudioNode JUMP;
    public static AudioNode LAND;

    public static AudioNode MONKE_JUMP_1;
    public static AudioNode MONKE_JUMP_2;
    public static AudioNode MONKE_JUMP_3;

    public static List<AudioNode> MONKE_JUMPS;

    public static void updateMonkeJumps() {
        MONKE_JUMPS = List.of(
                MONKE_JUMP_1,
                MONKE_JUMP_2,
                MONKE_JUMP_3
        );
    }

    public static AudioNode rand_monke_jump() {
        return MONKE_JUMPS.get(FastMath.nextRandomInt(0, MONKE_JUMPS.size() - 1));
    }
}
