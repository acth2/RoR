package fr.acth2.ror.init;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.cg.EntityCoinGiver;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.entity.curser.EntityCurser;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.lc.EntityLostCaver;
import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.entities.entity.traveler.EntityTraveler;
import fr.acth2.ror.entities.entity.wicked.EntityWicked;
import fr.acth2.ror.entities.entity.woodfall.EntityWoodFall;
import fr.acth2.ror.entities.entity.ws.EntityWoodSpirit;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.overworld.*;
import fr.acth2.ror.utils.subscribers.gen.utils.MobSpawnData;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
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
                    .sized(1.2F, 3.4F)
                    .build("lost_caver")
    );

    public static final RegistryObject<EntityType<EntityHopper>> HOPPER = ENTITY_TYPES.register("hopper", () ->
            EntityType.Builder.<EntityHopper>of(EntityHopper::new, EntityClassification.CREATURE)
                    .sized(0.9F, 1.3F)
                    .build("hopper")
    );

    public static final RegistryObject<EntityType<EntityRustedCore>> RUSTED_CORE = ENTITY_TYPES.register("rusted_core", () ->
            EntityType.Builder.<EntityRustedCore>of(EntityRustedCore::new, EntityClassification.CREATURE)
                    .sized(1.0F, 4F)
                    .build("rusted_core")
    );

    public static final RegistryObject<EntityType<EntityWicked>> WICKED = ENTITY_TYPES.register("wicked", () ->
            EntityType.Builder.<EntityWicked>of(EntityWicked::new, EntityClassification.CREATURE)
                    .sized(0.6F, 2.8F)
                    .build("wicked")
    );

    public static final RegistryObject<EntityType<EntityClucker>> CLUCKER = ENTITY_TYPES.register("clucker", () ->
            EntityType.Builder.<EntityClucker>of(EntityClucker::new, EntityClassification.CREATURE)
                    .sized(0.8F, 1.8F)
                    .build("clucker")
    );

    public static final RegistryObject<EntityType<EntityCurser>> CURSER = ENTITY_TYPES.register("curser", () ->
            EntityType.Builder.<EntityCurser>of(EntityCurser::new, EntityClassification.CREATURE)
                    .sized(0.6F, 3.1F)
                    .build("curser")
    );

    public static final RegistryObject<EntityType<EntityWoodFall>> WOODFALL = ENTITY_TYPES.register("woodfall", () ->
            EntityType.Builder.<EntityWoodFall>of(EntityWoodFall::new, EntityClassification.CREATURE)
                    .sized(0.3F, 1.3F)
                    .build("woodfall")
    );

    public static final RegistryObject<EntityType<EntityWoodSpirit>> WOOD_SPIRIT = ENTITY_TYPES.register("wood_spirit", () ->
            EntityType.Builder.<EntityWoodSpirit>of(EntityWoodSpirit::new, EntityClassification.CREATURE)
                    .sized(1.0F, 4F)
                    .build("wood_spirit")
    );

    public static final RegistryObject<EntityType<EntityTraveler>> TRAVELER = ENTITY_TYPES.register("traveler", () ->
            EntityType.Builder.<EntityTraveler>of(EntityTraveler::new, EntityClassification.CREATURE)
                    .sized(2.0F, 2F)
                    .build("traveler")
    );

    public static final RegistryObject<EntityType<EntityCoinGiver>> COIN_GIVER = ENTITY_TYPES.register("coin_giver", () ->
            EntityType.Builder.<EntityCoinGiver>of(EntityCoinGiver::new, EntityClassification.CREATURE)
                    .sized(0.55F, 0.75F)
                    .build("coin_giver")
    );

    public static void register(IEventBus modEventBus) {
        ModEntities.ENTITY_TYPES.register(modEventBus);

        // OVERWORLD

        // DAY MONSTER GENERATION
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(HOPPER, 45, null));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(RUSTED_CORE, 25, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOOD_SPIRIT, 1, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CLUCKER, 100, Blocks.SAND));

        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 300, Blocks.VINE));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 300, Blocks.LILY_PAD));

        // NIGHT MONSTER GENERATION
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CLUCKER, 100, Blocks.SAND));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CURSER, 1, Blocks.RED_SAND));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CURSER, 1, Blocks.TERRACOTTA));

        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 500, Blocks.VINE));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 500, Blocks.LILY_PAD));

        // CAVE MONSTER GENERATION
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(LOST_CAVER, 25, Blocks.STONE));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WICKED, 100, Blocks.STONE));

        // NPC CREATURE GENERATION
        NPCSpawnSubscriber.mobListLV1.add(new MobSpawnData(TRAVELER, 50, null));

        // BUFFS GENERATION
        BuffsSpawnSubscriber.mobListLV1.add(new MobSpawnData(COIN_GIVER, 50, null));
    }
}
