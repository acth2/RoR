package fr.acth2.ror.utils;

import net.minecraftforge.common.ForgeConfigSpec;

public class ParticleConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final ForgeConfigSpec.BooleanValue WORLD_PARTICLES_ENABLED;

    static {
        BUILDER.push("World Particles Config");

        WORLD_PARTICLES_ENABLED = BUILDER
                .comment("Whether world particles are enabled")
                .define("worldParticlesEnabled", true);

        BUILDER.pop();
        SPEC = BUILDER.build();
    }
}
