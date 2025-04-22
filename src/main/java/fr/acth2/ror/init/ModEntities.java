package fr.acth2.ror.init;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.aquamarin.EntityAquamarin;
import fr.acth2.ror.entities.entity.bo.EntityBadOmen;
import fr.acth2.ror.entities.entity.cg.EntityCoinGiver;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.entity.curser.EntityCurser;
import fr.acth2.ror.entities.entity.echo.EntityEcho;
import fr.acth2.ror.entities.entity.fussle.EntityFussle;
import fr.acth2.ror.entities.entity.ghost.EntityGhost;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.lc.EntityLostCaver;
import fr.acth2.ror.entities.entity.mimic.EntityMimic;
import fr.acth2.ror.entities.entity.mw.EntityMajorWicked;
import fr.acth2.ror.entities.entity.ookla.EntityOokla;
import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.entities.entity.seeker.EntitySeeker;
import fr.acth2.ror.entities.entity.silker.EntitySilker;
import fr.acth2.ror.entities.entity.traveler.EntityTraveler;
import fr.acth2.ror.entities.entity.wicked.EntityWicked;
import fr.acth2.ror.entities.entity.woodfall.EntityWoodFall;
import fr.acth2.ror.entities.entity.woodfall.solider.EntityWoodFallSolider;
import fr.acth2.ror.entities.entity.ws.EntityWoodSpirit;
import fr.acth2.ror.init.constructors.throwable.entiity.WickedProjectile;
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

    public static final RegistryObject<EntityType<WickedProjectile>> WICKED_PROJECTILE = ENTITY_TYPES.register("wicked_projectile", () ->
            EntityType.Builder.<WickedProjectile>of(WickedProjectile::new, EntityClassification.MISC)
                    .sized(0.25F, 0.25F)
                    .build("wicked_projectile")
    );

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
                    .sized(0.3F, 1.5F)
                    .build("woodfall")
    );

    public static final RegistryObject<EntityType<EntityWoodFallSolider>> WOODFALL_SOLIDER = ENTITY_TYPES.register("woodfall_solider", () ->
            EntityType.Builder.<EntityWoodFallSolider>of(EntityWoodFallSolider::new, EntityClassification.CREATURE)
                    .sized(0.5F, 1.7F)
                    .build("woodfall_solider")
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
                    .sized(0.55F, 1.00F)
                    .build("coin_giver")
    );

    public static final RegistryObject<EntityType<EntityGhost>> GHOST = ENTITY_TYPES.register("ghost", () ->
            EntityType.Builder.<EntityGhost>of(EntityGhost::new, EntityClassification.CREATURE)
                    .sized(0.8F, 2.0F)
                    .build("ghost")
    );

    public static final RegistryObject<EntityType<EntitySeeker>> SEEKER = ENTITY_TYPES.register("seeker", () ->
            EntityType.Builder.<EntitySeeker>of(EntitySeeker::new, EntityClassification.CREATURE)
                    .sized(0.6F, 1.0F)
                    .build("seeker")
    );

    public static final RegistryObject<EntityType<EntityAquamarin>> AQUAMARIN = ENTITY_TYPES.register("aquamarin", () ->
            EntityType.Builder.<EntityAquamarin>of(EntityAquamarin::new, EntityClassification.CREATURE)
                    .sized(0.9F, 0.8F)
                    .build("aquamarin")
    );

    public static final RegistryObject<EntityType<EntityFussle>> FUSSLE = ENTITY_TYPES.register("fussle", () ->
            EntityType.Builder.<EntityFussle>of(EntityFussle::new, EntityClassification.CREATURE)
                    .sized(0.7F, 1.1F)
                    .build("fussle")
    );

    public static final RegistryObject<EntityType<EntityEcho>> ECHO = ENTITY_TYPES.register("echo", () ->
            EntityType.Builder.<EntityEcho>of(EntityEcho::new, EntityClassification.CREATURE)
                    .sized(0.9F, 1.3F)
                    .build("echo")
    );

    public static final RegistryObject<EntityType<EntitySilker>> SILKER = ENTITY_TYPES.register("silker", () ->
            EntityType.Builder.<EntitySilker>of(EntitySilker::new, EntityClassification.CREATURE)
                    .sized(0.6F, 1.0F)
                    .build("silker")
    );

    public static final RegistryObject<EntityType<EntityMajorWicked>> MAJOR_WICKED = ENTITY_TYPES.register("major_wicked", () ->
            EntityType.Builder.<EntityMajorWicked>of(EntityMajorWicked::new, EntityClassification.CREATURE)
                    .sized(0.6F, 2.8F)
                    .build("major_wicked")
    );

    public static final RegistryObject<EntityType<EntityOokla>> OOKLA = ENTITY_TYPES.register("ookla", () ->
            EntityType.Builder.<EntityOokla>of(EntityOokla::new, EntityClassification.CREATURE)
                    .sized(0.6F, 2.8F)
                    .build("ookla")
    );

    public static final RegistryObject<EntityType<EntityBadOmen>> BAD_OMEN = ENTITY_TYPES.register("bad_omen", () ->
            EntityType.Builder.<EntityBadOmen>of(EntityBadOmen::new, EntityClassification.CREATURE)
                    .sized(2.0F, 2.0F)
                    .build("bad_omen")
    );

    public static final RegistryObject<EntityType<EntityMimic>> MIMIC = ENTITY_TYPES.register("mimic", () ->
            EntityType.Builder.<EntityMimic>of(EntityMimic::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.0F)
                    .build("mimic")
    );

    public static void register(IEventBus modEventBus) {
        ModEntities.ENTITY_TYPES.register(modEventBus);

        // OVERWORLD

        // DAY MONSTER GENERATION
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(HOPPER, 45, null));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(RUSTED_CORE, 25, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOOD_SPIRIT, 1, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CLUCKER, 100, Blocks.SAND));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(AQUAMARIN, 100, Blocks.WATER));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(FUSSLE, 50, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(ECHO, 100, null));

        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(FUSSLE, 75, Blocks.COARSE_DIRT));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(FUSSLE, 75, Blocks.PODZOL));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(RUSTED_CORE, 75, Blocks.COARSE_DIRT));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(RUSTED_CORE, 75, Blocks.PODZOL));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(HOPPER, 75, Blocks.COARSE_DIRT));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(HOPPER, 75, Blocks.PODZOL));

        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 300, Blocks.VINE));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 300, Blocks.LILY_PAD));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 1000, Blocks.LILY_PAD));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 1000, Blocks.VINE));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 5000, Blocks.MOSSY_COBBLESTONE));

        // NIGHT MONSTER GENERATION
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CLUCKER, 100, Blocks.SAND));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CURSER, 1, Blocks.RED_SAND));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CURSER, 1, Blocks.TERRACOTTA));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(GHOST, 100, null));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(AQUAMARIN, 100, Blocks.WATER));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(SILKER, 85, null));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(MAJOR_WICKED, 25, Blocks.STONE));

        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 500, Blocks.VINE));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 500, Blocks.LILY_PAD));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 1000, Blocks.LILY_PAD));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 1000, Blocks.VINE));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 5000, Blocks.MOSSY_COBBLESTONE));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(OOKLA, 25, null));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(BAD_OMEN, 1, Blocks.AIR));

        // CAVE MONSTER GENERATION
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(LOST_CAVER, 25, Blocks.STONE));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WICKED, 100, Blocks.STONE));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(SEEKER, 50, Blocks.STONE));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(MAJOR_WICKED, 25, Blocks.STONE));

        // RARE CAVE MONSTER GENERATION

        RareCaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(MIMIC, 100, null));

        // NPC CREATURE GENERATION
        NPCSpawnSubscriber.mobListLV1.add(new MobSpawnData(TRAVELER, 50, null));

        // BUFFS GENERATION
        BuffsSpawnSubscriber.mobListLV1.add(new MobSpawnData(COIN_GIVER, 50, null));
    }
}
