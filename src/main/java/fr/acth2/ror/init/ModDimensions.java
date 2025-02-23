package fr.acth2.ror.init;

import fr.acth2.ror.dimension.example.ExampleGenerator;
import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;

public class ModDimensions {
    public static void register(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            Registry.register(Registry.CHUNK_GENERATOR,
                    new ResourceLocation(References.MODID, "example_generator"),
                    ExampleGenerator.CODEC
            );
        });
    }
}
