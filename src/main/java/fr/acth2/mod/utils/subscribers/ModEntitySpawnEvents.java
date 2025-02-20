package fr.acth2.mod.utils.subscribers;

import fr.acth2.mod.init.ModEntities;
import fr.acth2.mod.utils.References;
import net.minecraft.entity.EntityClassification;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEntitySpawnEvents {

    @SubscribeEvent
    public static void onBiomeLoading(BiomeLoadingEvent event) {
        event.getSpawns().getSpawner(EntityClassification.CREATURE)
                .add(new MobSpawnInfo.Spawners(ModEntities.ENTITY_EXAMPLE.get(), 100, 1, 3));
    }
}