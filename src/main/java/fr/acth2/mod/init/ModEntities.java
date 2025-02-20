package fr.acth2.mod.init;

import fr.acth2.mod.entities.entity.EntityExample;
import fr.acth2.mod.utils.References;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.MODID);

    public static final RegistryObject<EntityType<EntityExample>> ENTITY_EXAMPLE = ENTITY_TYPES.register("entity_example", () ->
            EntityType.Builder.<EntityExample>of(EntityExample::new, EntityClassification.CREATURE)
                    .sized(0.6F, 1.95F)
                    .build("entity_example")
    );
}
