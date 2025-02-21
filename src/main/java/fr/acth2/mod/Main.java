package fr.acth2.mod;

import com.mojang.serialization.Codec;
import fr.acth2.mod.dimension.example.ExampleGenerator;
import fr.acth2.mod.gui.MainMenuGui;
import fr.acth2.mod.init.ModBlocks;
import fr.acth2.mod.init.ModEntities;
import fr.acth2.mod.init.ModItems;
import fr.acth2.mod.proxy.ClientProxy;
import fr.acth2.mod.proxy.CommonProxy;
import fr.acth2.mod.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(References.MODID)
public class Main {

    public static final CommonProxy proxy = DistExecutor.safeRunForDist(
            () -> ClientProxy::new,
            () -> CommonProxy::new
    );


    public Main() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.ENTITY_TYPES.register(modEventBus);
        modEventBus.addListener(this::setup);
    }

    private void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.CHUNK_GENERATOR, new ResourceLocation(References.MODID, "example_generator"), ExampleGenerator.CODEC);
        });
        proxy.setup();
    }

}
