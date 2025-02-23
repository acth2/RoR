package fr.acth2.ror.init;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.lc.EntityLostCaver;
import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.CaveMonsterSpawnerSubscriber;
import fr.acth2.ror.utils.subscribers.gen.DaylightMonsterSpawnerSubscriber;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITIES, References.MODID);

    public static final RegistryObject<EntityType<EntityExample>> ENTITY_EXAMPLE = ENTITY_TYPES.register("entity_example", () ->
            EntityType.Builder.<EntityExample>of(EntityExample::new, EntityClassification.CREATURE)
                    .sized(0.6F, 3F)
                    .build("entity_example")
    );

    public static final RegistryObject<EntityType<EntityLostCaver>> LOST_CAVER = ENTITY_TYPES.register("lost_caver", () ->
            EntityType.Builder.<EntityLostCaver>of(EntityLostCaver::new, EntityClassification.MONSTER)
                    .sized(0.6F, 2.6F)
                    .build("lost_caver")
    );

    public static final RegistryObject<EntityType<EntityHopper>> HOPPER = ENTITY_TYPES.register("hopper", () ->
            EntityType.Builder.<EntityHopper>of(EntityHopper::new, EntityClassification.CREATURE)
                    .sized(0.9F, 0.75F)
                    .build("hopper")
    );

    public static final RegistryObject<EntityType<EntityRustedCore>> RUSTED_CORE = ENTITY_TYPES.register("rusted_core", () ->
            EntityType.Builder.<EntityRustedCore>of(EntityRustedCore::new, EntityClassification.CREATURE)
                    .sized(0.9F, 0.75F)
                    .build("rusted_core")
    );

    public static void register(IEventBus modEventBus) {
        ModEntities.ENTITY_TYPES.register(modEventBus);

        // DAY MONSTER GENERATION
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(HOPPER);
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(RUSTED_CORE);

        // NIGHT MONSTER GENERATION
        //

        // CAVE MONSTER GENERATION
        CaveMonsterSpawnerSubscriber.mobListLV1.add(LOST_CAVER);
    }
}
