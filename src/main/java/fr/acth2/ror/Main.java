package fr.acth2.ror;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import fr.acth2.ror.gui.diary.DiaryManager;
import fr.acth2.ror.init.*;
import fr.acth2.ror.proxy.ClientProxy;
import fr.acth2.ror.proxy.CommonProxy;
import fr.acth2.ror.structures.RealmRemnant;
import fr.acth2.ror.structures.pieces.ExamplePiece;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.DimensionSettings;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.StructureFeature;
import net.minecraft.world.gen.feature.structure.IStructurePieceType;
import net.minecraft.world.gen.feature.structure.Structure;
import net.minecraft.world.gen.settings.StructureSeparationSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.world.BiomeLoadingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.server.FMLServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.Mixins;
import org.spongepowered.asm.mixin.connect.IMixinConnector;
import software.bernie.geckolib3.GeckoLib;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

@Mod(References.MODID)
public class Main implements IMixinConnector {
    public static final CommonProxy proxy = DistExecutor.safeRunForDist(
            () -> ClientProxy::new,
            () -> CommonProxy::new
    );

    public static final IStructurePieceType EXAMPLE_PILLAR = Registry.register(
            Registry.STRUCTURE_PIECE,
            new ResourceLocation(References.MODID, "example_pillar"),
            (structurePieceTypeIn, compoundNBT) -> new ExamplePiece(null, compoundNBT)
    );

    public static void setupMapSpacingAndLand(Structure<?> structure,
                                              StructureSeparationSettings separation,
                                              boolean surfaceTransform) {
        if (surfaceTransform) {
            Structure.NOISE_AFFECTING_FEATURES.add(structure);
        }

        for (DimensionSettings settings : WorldGenRegistries.NOISE_GENERATOR_SETTINGS) {
            try {
                Field field = DimensionSettings.class.getDeclaredField("structureConfig");
                field.setAccessible(true);

                ImmutableMap<Structure<?>, StructureSeparationSettings> currentMap =
                        (ImmutableMap<Structure<?>, StructureSeparationSettings>) field.get(settings);

                Map<Structure<?>, StructureSeparationSettings> newMap = new HashMap<>(currentMap);
                newMap.put(structure, separation);
                field.set(settings, ImmutableMap.copyOf(newMap));
            } catch (Exception e) {
                System.err.println("Failed to modify structure separation settings:");
                e.printStackTrace();
            }
        }
    }



    public static final DeferredRegister<Structure<?>> STRUCTURES =
            DeferredRegister.create(ForgeRegistries.STRUCTURE_FEATURES, References.MODID);

    public static final RegistryObject<Structure<NoFeatureConfig>> REALM_REMNANT = STRUCTURES.register(
            "realm_remnant",
            () -> new RealmRemnant(NoFeatureConfig.CODEC)
    );
    public static StructureFeature<NoFeatureConfig, ? extends Structure<NoFeatureConfig>> CONFIGURED_REALM_REMNANT;

    public static void registerConfiguredStructures() {
        CONFIGURED_REALM_REMNANT = REALM_REMNANT.get()
                .configured(NoFeatureConfig.INSTANCE);

        Registry.register(
                WorldGenRegistries.CONFIGURED_STRUCTURE_FEATURE,
                new ResourceLocation(References.MODID, "configured_realm_remnant"),
                CONFIGURED_REALM_REMNANT
        );
    }

    public Main() {
        MixinBootstrap.init();
        Mixins.addConfiguration("mixins.ror.json");

        References.HEALTH_MODIFIER_UUID = UUID.randomUUID();
        References.DEXTERITY_MODIFIER_UUID = UUID.randomUUID();
        STRUCTURES.register(FMLJavaModLoadingContext.get().getModEventBus());
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);

        GeckoLib.initialize();
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);
        ModEntities.register(modEventBus);
        ModSoundEvents.register(modEventBus);

        MinecraftForge.EVENT_BUS.addListener(this::onServerStarting);
        MinecraftForge.EVENT_BUS.addListener(this::onBiomeLoading);
        DiaryManager.registerAutosave();
        modEventBus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void setup(FMLCommonSetupEvent event) {
        ModNetworkHandler.registerPackets();
        ModDimensions.register(event);
        event.enqueueWork(() -> {
            Main.registerConfiguredStructures();
            setupMapSpacingAndLand(Main.REALM_REMNANT.get(), new StructureSeparationSettings(32, 8, 123456789), true);
        });
        proxy.setup();
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        ModCommands.register(event.getDispatcher());
    }

    private void onServerStarting(FMLServerStartingEvent event) {
        DiaryManager.initialize(event.getServer().getServerDirectory());
    }

    private void onBiomeLoading(BiomeLoadingEvent event) {
        event.getGeneration().getStructures().add(() -> Main.CONFIGURED_REALM_REMNANT);
    }


    @Override
    public void connect() {
        Mixins.addConfiguration("mixins.ror.json");

        Mixins.addConfiguration("fr.acth2.ror.mixins.events.bm.MixinWorldRenderer");
        Mixins.addConfiguration("fr.acth2.ror.mixins.events.bm.MixinFogRenderer");
    }
}
