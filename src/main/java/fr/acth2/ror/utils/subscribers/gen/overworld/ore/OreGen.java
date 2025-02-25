package fr.acth2.ror.utils.subscribers.gen.overworld.ore;

import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.utils.References;
import net.minecraft.block.Blocks;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraft.world.gen.placement.TopSolidRangeConfig;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class OreGen {

    @SubscribeEvent
    public static void onBiomeLoad(BiomeLoadingEvent event) {
        ConfiguredFeature<?, ?> infraniumOre = Feature.ORE
                .configured(new OreFeatureConfig(
                        OreFeatureConfig.FillerBlockType.NATURAL_STONE,
                        ModBlocks.INFRANIUM_ORE.get().defaultBlockState(),
                        4
                ))
                .decorated(Placement.RANGE.configured(new TopSolidRangeConfig(5, 0, 16)))
                .squared()
                .count(8);

        event.getGeneration().addFeature(GenerationStage.Decoration.UNDERGROUND_ORES, infraniumOre);
    }
}
