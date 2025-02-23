package fr.acth2.ror.utils.subscribers;

import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.utils.References;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEntitySpawnEvents {

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND) {
            event.getSpawns().getSpawner(EntityClassification.MONSTER)
                    .add(new MobSpawnInfo.Spawners(ModEntities.LOST_CAVER.get(), 25, 0, 1));
        }

        if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND) {
            event.getSpawns().getSpawner(EntityClassification.MONSTER)
                    .add(new MobSpawnInfo.Spawners(ModEntities.RUSTED_CORE.get(), 25, 0, 1));
        }

        if (event.getCategory() != Biome.Category.NETHER && event.getCategory() != Biome.Category.THEEND) {
            event.getSpawns().getSpawner(EntityClassification.CREATURE)
                    .add(new MobSpawnInfo.Spawners(ModEntities.HOPPER.get(), 1000, 1, 1));
        }
    }
}