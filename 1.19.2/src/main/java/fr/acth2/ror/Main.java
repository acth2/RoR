package fr.acth2.ror;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Lifecycle;
import fr.acth2.ror.gui.diary.DiaryManager;
import fr.acth2.ror.init.*;
import fr.acth2.ror.proxy.ClientProxy;
import fr.acth2.ror.proxy.CommonProxy;
import fr.acth2.ror.utils.ParticleConfig;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import fr.acth2.ror.utils.subscribers.gen.skyria.SkyriaMonsterSpawnerSubscriber;
import fr.acth2.ror.utils.subscribers.gen.utils.MobSpawnData;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.ServerWorldInfo;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.server.FMLServerStartedEvent;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;
import software.bernie.geckolib3.GeckoLib;

import java.util.UUID;

@Mod(References.MODID)
public class Main implements IMixinConnector {
    public static final CommonProxy proxy = DistExecutor.safeRunForDist(
            () -> ClientProxy::new,
            () -> CommonProxy::new
    );

    public Main() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.ror.json");
        References.HEALTH_MODIFIER_UUID = UUID.randomUUID();
        References.DEXTERITY_MODIFIER_UUID = UUID.randomUUID();
        References.STRENGTH_MODIFIER_UUID = UUID.randomUUID();

        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        GeckoLib.initialize();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSoundEvents.register(modEventBus);
        ModTileEntities.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        DiaryManager.registerAutosave();
        modEventBus.addListener(this::setup);
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, ParticleConfig.SPEC);
        MinecraftForge.EVENT_BUS.register(this);
    }   

    private void setup(FMLCommonSetupEvent event) {
        ModNetworkHandler.registerPackets();
        ModDimensions.register(event);
        proxy.setup();
    }

    @SubscribeEvent
    public static void onServerRegisteringDimensions(FMLServerStartingEvent event) {
        try {
            MutableRegistry<DimensionType> registry = (MutableRegistry<DimensionType>)
                    ((DynamicRegistries.Impl) event.getServer().registryAccess())
                            .registryOrThrow(Registry.DIMENSION_TYPE_REGISTRY);

            registry.register(
                    RegistryKey.create(Registry.DIMENSION_TYPE_REGISTRY,
                            new ResourceLocation(References.MODID, "abyssaria")),
                    ModDimensions.ABYSSARIA_DIMENSION_TYPE,
                    Lifecycle.stable()
            );

            System.out.println("DEBUG - Abyssaria dimension type registered successfully");
        } catch (Exception e) {
            System.out.println("DEBUG - Failed to register dimension type: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

    private void onServerStarting(FMLServerStartingEvent event) {
        DiaryManager.initialize(event.getServer().getServerDirectory());
    }

    @Override
    public void connect() {
        Mixins.addConfiguration("mixins.ror.json");
    }
}
