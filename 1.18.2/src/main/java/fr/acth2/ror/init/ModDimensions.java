package fr.acth2.ror.init;

import com.mojang.serialization.Lifecycle;
import fr.acth2.ror.dimension.abyssaria.AbyssariaDimensionType;
import fr.acth2.ror.dimension.abyssaria.AbyssariaGenerator;
import fr.acth2.ror.dimension.example.ExampleGenerator;
import fr.acth2.ror.dimension.skyria.SkyriaGenerator;
import fr.acth2.ror.utils.References;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.RegistryKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.MutableRegistry;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.DimensionType;
import net.minecraft.world.level.Level;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

import java.util.OptionalLong;

public class ModDimensions {
    public static final RegistryKey<World> SKYRIA = RegistryKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(References.MODID, "skyria"));

    public static final RegistryKey<World> ABYSSARIA = RegistryKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(References.MODID, "abyssaria"));

    public static final DimensionType ABYSSARIA_DIMENSION_TYPE = new AbyssariaDimensionType();


    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.CHUNK_GENERATOR,
                    new ResourceLocation(References.MODID, "example_generator"),
                    ExampleGenerator.CODEC
            );
        });

        event.enqueueWork(() -> {
            Registry.register(Registry.CHUNK_GENERATOR,
                    new ResourceLocation(References.MODID, "skyria_generator"),
                    SkyriaGenerator.CODEC
            );
        });

        event.enqueueWork(() -> {
            Registry.register(Registry.CHUNK_GENERATOR,
                    new ResourceLocation(References.MODID, "abyssaria_generator"),
                    AbyssariaGenerator.CODEC
            );
        });
    }
}
