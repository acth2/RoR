package fr.acth2.ror;

import fr.acth2.ror.dimension.example.ExampleGenerator;
import fr.acth2.ror.entities.constructors.hopper.HopperEntity;
import fr.acth2.ror.entities.constructors.lc.LostCaverEntity;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.lc.EntityLostCaver;
import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.proxy.ClientProxy;
import fr.acth2.ror.proxy.CommonProxy;
import fr.acth2.ror.utils.References;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import software.bernie.geckolib3.GeckoLib;

@Mod(References.MODID)
public class Main {

    public static final CommonProxy proxy = DistExecutor.safeRunForDist(
            () -> ClientProxy::new,
            () -> CommonProxy::new
    );


    public Main() {
        GeckoLib.initialize();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        modEventBus.addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.CHUNK_GENERATOR,
                    new ResourceLocation(References.MODID, "example_generator"),
                    ExampleGenerator.CODEC
            );
        });

        event.enqueueWork(() -> {
            EntitySpawnPlacementRegistry.register(
                    ModEntities.LOST_CAVER.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    EntityLostCaver::canSpawnInCave
            );
        });

        event.enqueueWork(() -> {
            EntitySpawnPlacementRegistry.register(
                    ModEntities.HOPPER.get(),
                    EntitySpawnPlacementRegistry.PlacementType.ON_GROUND,
                    Heightmap.Type.MOTION_BLOCKING_NO_LEAVES,
                    EntityHopper::canSpawnInDaySurface
            );
        });
        proxy.setup();
    }


}
