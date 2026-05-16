package fr.acth2.ror.dimension.abyssaria;

import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.dimension.DimensionType;

import java.util.OptionalLong;

public class AbyssariaDimensionType extends DimensionType {

    public AbyssariaDimensionType() {
        super(
                OptionalLong.empty(),                        // fixed time
                true,                                        // has skylight
                false,                                       // has ceiling
                false,                                       // ultrawarm
                true,                                        // natural
                1.0D,                                        // coordinate scale
                false,                                       // piglin safe
                false,                                       // bed works
                true,                                        // respawn anchor works
                false,                                       // has raids
                256,                                         // logical height
                BlockTags.INFINIBURN_OVERWORLD.getName(),    // infiniburn
                DimensionType.OVERWORLD_EFFECTS,             // effects
                0.0F                                         // ambient light
        );
    }
}