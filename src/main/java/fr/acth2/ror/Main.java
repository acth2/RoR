package fr.acth2.ror;

import fr.acth2.ror.dimension.example.ExampleGenerator;
import fr.acth2.ror.entities.entity.lc.EntityLostCaver;
import fr.acth2.ror.init.ModBlocks;
import fr.acth2.ror.init.ModDimensions;
import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.init.ModItems;
import fr.acth2.ror.proxy.ClientProxy;
import fr.acth2.ror.proxy.CommonProxy;
import fr.acth2.ror.utils.References;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
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
        ModDimensions.register(event);

        proxy.setup();
    }
}
