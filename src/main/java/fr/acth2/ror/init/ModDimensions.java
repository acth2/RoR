package fr.acth2.ror.init;

import fr.acth2.ror.dimension.example.ExampleGenerator;
import fr.acth2.ror.dimension.skyria.SkyriaGenerator;
import fr.acth2.ror.utils.References;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModDimensions {
    public static final RegistryKey<World> SKYRIA = RegistryKey.create(Registry.DIMENSION_REGISTRY,
            new ResourceLocation(References.MODID, "skyria"));

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
    }
}
