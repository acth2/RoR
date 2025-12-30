package fr.acth2.ror.init;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.entities.entity.EntityExampleInvader;
import fr.acth2.ror.entities.entity.aquamarin.EntityAquamarin;
import fr.acth2.ror.entities.entity.axis.EntityAxis;
import fr.acth2.ror.entities.entity.bi.EntityBloodInfectioner;
import fr.acth2.ror.entities.entity.bo.EntityBadOmen;
import fr.acth2.ror.entities.entity.bri.EntityBrokenInsurrectionist;
import fr.acth2.ror.entities.entity.catapleer.EntityCatapleer;
import fr.acth2.ror.entities.entity.cavesucker.EntityCaveSucker;
import fr.acth2.ror.entities.entity.cg.EntityCoinGiver;
import fr.acth2.ror.entities.entity.clucker.EntityClucker;
import fr.acth2.ror.entities.entity.copier.EntityCopier;
import fr.acth2.ror.entities.entity.corrupted.EntityCorrupted;
import fr.acth2.ror.entities.entity.curser.EntityCurser;
import fr.acth2.ror.entities.entity.despiter.EntityDespiter;
import fr.acth2.ror.entities.entity.echo.EntityEcho;
import fr.acth2.ror.entities.entity.flyer.EntityFlyer;
import fr.acth2.ror.entities.entity.fussle.EntityFussle;
import fr.acth2.ror.entities.entity.ghost.EntityGhost;
import fr.acth2.ror.entities.entity.grasser.EntityGrasser;
import fr.acth2.ror.entities.entity.hopper.EntityHopper;
import fr.acth2.ror.entities.entity.howler.EntityHowler;
import fr.acth2.ror.entities.entity.lb.EntityLavaBeing;
import fr.acth2.ror.entities.entity.lc.EntityLostCaver;
import fr.acth2.ror.entities.entity.EntityExampleParticle;
import fr.acth2.ror.entities.entity.lp.EntityLivingParticle;
import fr.acth2.ror.entities.entity.mimic.EntityMimic;
import fr.acth2.ror.entities.entity.mw.EntityMajorWicked;
import fr.acth2.ror.entities.entity.ookla.EntityOokla;
import fr.acth2.ror.entities.entity.rc.EntityRustedCore;
import fr.acth2.ror.entities.entity.rift.EntityRiftV2;
import fr.acth2.ror.entities.entity.se.EntitySkyEjector;
import fr.acth2.ror.entities.entity.seeker.EntitySeeker;
import fr.acth2.ror.entities.entity.silker.EntitySilker;
import fr.acth2.ror.entities.entity.skyder.EntitySkyder;
import fr.acth2.ror.entities.entity.spying.EntitySpying;
import fr.acth2.ror.entities.entity.traders.EntitySkyriaTrader;
import fr.acth2.ror.entities.entity.traveler.EntityTraveler;
import fr.acth2.ror.entities.entity.rift.EntityRift;
import fr.acth2.ror.entities.entity.wicked.EntityWicked;
import fr.acth2.ror.entities.entity.woodfall.EntityWoodFall;
import fr.acth2.ror.entities.entity.woodfall.solider.EntityWoodFallSolider;
import fr.acth2.ror.entities.entity.ws.EntityWoodSpirit;
import fr.acth2.ror.init.constructors.throwable.entiity.WickedProjectile;
import fr.acth2.ror.utils.References;
import fr.acth2.ror.utils.subscribers.gen.overworld.*;
import fr.acth2.ror.utils.subscribers.gen.skyria.SkyriaMonsterSpawnerSubscriber;
import fr.acth2.ror.utils.subscribers.gen.utils.MobSpawnData;
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

    public static final RegistryObject<EntityType<EntityExampleParticle>> EXAMPLE_PARTICLE = ENTITY_TYPES.register("example_particle", () ->
            EntityType.Builder.<EntityExampleParticle>of(EntityExampleParticle::new, EntityClassification.CREATURE)
                    .sized(0.1F, 0.1F)
                    .build("example_particle")
    );

    public static final RegistryObject<EntityType<EntityExampleInvader>> EXAMPLE_INVADER = ENTITY_TYPES.register("example_invader", () ->
            EntityType.Builder.<EntityExampleInvader>of(EntityExampleInvader::new, EntityClassification.CREATURE)
                    .sized(1.0F, 2.0F)
                    .build("example_invader")
    );

    public static final RegistryObject<EntityType<EntityLostCaver>> LOST_CAVER = ENTITY_TYPES.register("lost_caver", () ->
            EntityType.Builder.<EntityLostCaver>of(EntityLostCaver::new, EntityClassification.MONSTER)
                    .sized(1.2F, 3.4F)
                    .build("lost_caver")
    );


    public static final RegistryObject<EntityType<WickedProjectile>> WICKED_PROJECTILE = ENTITY_TYPES.register("wicked_projectile", () ->
            EntityType.Builder.<WickedProjectile>of(WickedProjectile::new, EntityClassification.MISC)
                    .sized(0.25F, 0.25F)
                    .build("wicked_projectile")
    );

    public static final RegistryObject<EntityType<EntityHopper>> HOPPER = ENTITY_TYPES.register("hopper", () ->
            EntityType.Builder.<EntityHopper>of(EntityHopper::new, EntityClassification.CREATURE)
                    .sized(1.5F, 1.3F)
                    .build("hopper")
    );

    public static final RegistryObject<EntityType<EntityRustedCore>> RUSTED_CORE = ENTITY_TYPES.register("rusted_core", () ->
            EntityType.Builder.<EntityRustedCore>of(EntityRustedCore::new, EntityClassification.CREATURE)
                    .sized(1.0F, 4F)
                    .build("rusted_core")
    );

    public static final RegistryObject<EntityType<EntityWicked>> WICKED = ENTITY_TYPES.register("wicked", () ->
            EntityType.Builder.<EntityWicked>of(EntityWicked::new, EntityClassification.CREATURE)
                    .sized(0.6F, 1.6F)
                    .build("wicked")
    );

    public static final RegistryObject<EntityType<EntityClucker>> CLUCKER = ENTITY_TYPES.register("clucker", () ->
            EntityType.Builder.<EntityClucker>of(EntityClucker::new, EntityClassification.CREATURE)
                    .sized(0.8F, 1.8F)
                    .build("clucker")
    );

    public static final RegistryObject<EntityType<EntityCurser>> CURSER = ENTITY_TYPES.register("curser", () ->
            EntityType.Builder.<EntityCurser>of(EntityCurser::new, EntityClassification.CREATURE)
                    .sized(1.0F, 3.8F)
                    .build("curser")
    );

    public static final RegistryObject<EntityType<EntityWoodFall>> WOODFALL = ENTITY_TYPES.register("woodfall", () ->
            EntityType.Builder.<EntityWoodFall>of(EntityWoodFall::new, EntityClassification.CREATURE)
                    .sized(0.3F, 1.5F)
                    .build("woodfall")
    );

    public static final RegistryObject<EntityType<EntityWoodFallSolider>> WOODFALL_SOLIDER = ENTITY_TYPES.register("woodfall_solider", () ->
            EntityType.Builder.<EntityWoodFallSolider>of(EntityWoodFallSolider::new, EntityClassification.CREATURE)
                    .sized(0.5F, 2.6F)
                    .build("woodfall_solider")
    );

    public static final RegistryObject<EntityType<EntityWoodSpirit>> WOOD_SPIRIT = ENTITY_TYPES.register("wood_spirit", () ->
            EntityType.Builder.<EntityWoodSpirit>of(EntityWoodSpirit::new, EntityClassification.CREATURE)
                    .sized(2.0F, 3.0F)
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
                    .sized(1.0F, 1.5F)
                    .build("seeker")
    );

    public static final RegistryObject<EntityType<EntityAquamarin>> AQUAMARIN = ENTITY_TYPES.register("aquamarin", () ->
            EntityType.Builder.<EntityAquamarin>of(EntityAquamarin::new, EntityClassification.CREATURE)
                    .sized(1.3F, 0.8F)
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
                    .sized(0.6F, 1.6F)
                    .build("silker")
    );

    public static final RegistryObject<EntityType<EntityMajorWicked>> MAJOR_WICKED = ENTITY_TYPES.register("major_wicked", () ->
            EntityType.Builder.<EntityMajorWicked>of(EntityMajorWicked::new, EntityClassification.CREATURE)
                    .sized(1.0F, 4.0F)
                    .build("major_wicked")
    );

    public static final RegistryObject<EntityType<EntityOokla>> OOKLA = ENTITY_TYPES.register("ookla", () ->
            EntityType.Builder.<EntityOokla>of(EntityOokla::new, EntityClassification.CREATURE)
                    .sized(0.6F, 2.8F)
                    .build("ookla")
    );

    public static final RegistryObject<EntityType<EntityBadOmen>> BAD_OMEN = ENTITY_TYPES.register("bad_omen", () ->
            EntityType.Builder.<EntityBadOmen>of(EntityBadOmen::new, EntityClassification.CREATURE)
                    .sized(5.0F, 5.0F)
                    .build("bad_omen")
    );

    public static final RegistryObject<EntityType<EntityMimic>> MIMIC = ENTITY_TYPES.register("mimic", () ->
            EntityType.Builder.<EntityMimic>of(EntityMimic::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.5F)
                    .build("mimic")
    );

    public static final RegistryObject<EntityType<EntitySkyEjector>> SKY_EJECTOR = ENTITY_TYPES.register("sky_ejector", () ->
            EntityType.Builder.<EntitySkyEjector>of(EntitySkyEjector::new, EntityClassification.CREATURE)
                    .sized(1.0F, 2.8F)
                    .build("sky_ejector")
    );

    public static final RegistryObject<EntityType<EntityLavaBeing>> LAVA_BEING = ENTITY_TYPES.register("lava_being", () ->
            EntityType.Builder.<EntityLavaBeing>of(EntityLavaBeing::new, EntityClassification.CREATURE)
                    .sized(1.0F, 3.4F)
                    .build("lava_being")
    );

    public static final RegistryObject<EntityType<EntityGrasser>> GRASSER = ENTITY_TYPES.register("grasser", () ->
            EntityType.Builder.<EntityGrasser>of(EntityGrasser::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.4F)
                    .build("grasser")
    );

    public static final RegistryObject<EntityType<EntityFlyer>> FLYER = ENTITY_TYPES.register("flyer", () ->
            EntityType.Builder.<EntityFlyer>of(EntityFlyer::new, EntityClassification.CREATURE)
                    .sized(1.4F, 1.0F)
                    .build("flyer")
    );

    public static final RegistryObject<EntityType<EntityBloodInfectioner>> BLOOD_INFECTIONER = ENTITY_TYPES.register("blood_infectioner", () ->
            EntityType.Builder.<EntityBloodInfectioner>of(EntityBloodInfectioner::new, EntityClassification.CREATURE)
                    .sized(1.2F, 0.7F)
                    .build("blood_infectioner")
    );

    public static final RegistryObject<EntityType<EntityBrokenInsurrectionist>> BROKEN_INSURRECTIONIST = ENTITY_TYPES.register("broken_insurrectionist", () ->
            EntityType.Builder.<EntityBrokenInsurrectionist>of(EntityBrokenInsurrectionist::new, EntityClassification.CREATURE)
                    .sized(1F, 1.4F)
                    .build("broken_insurrectionist")
    );

    public static final RegistryObject<EntityType<EntityCaveSucker>> CAVE_SUCKER = ENTITY_TYPES.register("cave_sucker", () ->
            EntityType.Builder.<EntityCaveSucker>of(EntityCaveSucker::new, EntityClassification.CREATURE)
                    .sized(1F, 1.4F)
                    .build("cave_sucker")
    );

    public static final RegistryObject<EntityType<EntityAxis>> AXIS = ENTITY_TYPES.register("axis", () ->
            EntityType.Builder.<EntityAxis>of(EntityAxis::new, EntityClassification.CREATURE)
                    .sized(1F, 2.0F)
                    .build("axis")
    );

    public static final RegistryObject<EntityType<EntityHowler>> HOWLER = ENTITY_TYPES.register("howler", () ->
            EntityType.Builder.<EntityHowler>of(EntityHowler::new, EntityClassification.CREATURE)
                    .sized(1F, 2.0F)
                    .build("howler")
    );

    public static final RegistryObject<EntityType<EntityDespiter>> DESPITER = ENTITY_TYPES.register("despiter", () ->
            EntityType.Builder.<EntityDespiter>of(EntityDespiter::new, EntityClassification.CREATURE)
                    .sized(1F, 1.0F)
                    .build("despiter")
    );

    public static final RegistryObject<EntityType<EntityRift>> RIFT = ENTITY_TYPES.register("rift", () ->
            EntityType.Builder.<EntityRift>of(EntityRift::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.0F)
                    .build("rift")
    );

    public static final RegistryObject<EntityType<EntityRiftV2>> RIFT_V2 = ENTITY_TYPES.register("rift_variant2", () ->
            EntityType.Builder.<EntityRiftV2>of(EntityRiftV2::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.0F)
                    .build("rift_variant2")
    );

    public static final RegistryObject<EntityType<EntityCorrupted>> CORRUPTED = ENTITY_TYPES.register("corrupted", () ->
            EntityType.Builder.<EntityCorrupted>of(EntityCorrupted::new, EntityClassification.CREATURE)
                    .sized(1.0F, 2.0F)
                    .build("corrupted")
    );

    public static final RegistryObject<EntityType<EntityLivingParticle>> LIVING_PARTICLE = ENTITY_TYPES.register("living_particle", () ->
            EntityType.Builder.<EntityLivingParticle>of(EntityLivingParticle::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.0F)
                    .build("living_particle")
    );

    public static final RegistryObject<EntityType<EntitySkyriaTrader>> SKYRIA_TRADER = ENTITY_TYPES.register("skyria_trader", () ->
            EntityType.Builder.<EntitySkyriaTrader>of(EntitySkyriaTrader::new, EntityClassification.CREATURE)
                    .sized(1.0F, 2.0F)
                    .build("skyria_trader")
    );

    public static final RegistryObject<EntityType<EntitySkyder>> SKYDER = ENTITY_TYPES.register("skyder", () ->
            EntityType.Builder.<EntitySkyder>of(EntitySkyder::new, EntityClassification.CREATURE)
                    .sized(1.0F, 3.0F)
                    .build("skyder")
    );

    public static final RegistryObject<EntityType<EntityCopier>> COPIER = ENTITY_TYPES.register("copier", () ->
            EntityType.Builder.<EntityCopier>of(EntityCopier::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.8F)
                    .build("copier")
    );

    public static final RegistryObject<EntityType<EntitySpying>> SPYING = ENTITY_TYPES.register("spying", () ->
            EntityType.Builder.<EntitySpying>of(EntitySpying::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.8F)
                    .build("spying")
    );

    public static final RegistryObject<EntityType<EntityCatapleer>> CATAPLEER = ENTITY_TYPES.register("catapleer", () ->
            EntityType.Builder.<EntityCatapleer>of(EntityCatapleer::new, EntityClassification.CREATURE)
                    .sized(1.0F, 1.2F)
                    .build("spying")
    );

    public static void register(IEventBus modEventBus) {
        ModEntities.ENTITY_TYPES.register(modEventBus);

        // OVERWORLD

        // DAY MONSTER GENERATION
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(HOPPER, 45, null));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(GRASSER, 100, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(RUSTED_CORE, 25, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOOD_SPIRIT, 1, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CLUCKER, 100, Blocks.SAND));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(AQUAMARIN, 100, Blocks.WATER));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(FUSSLE, 50, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(ECHO, 100, null));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(HOWLER, 100, Blocks.GRASS_BLOCK));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(DESPITER, 100, null));

        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(FUSSLE, 75, Blocks.COARSE_DIRT));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(FUSSLE, 75, Blocks.PODZOL));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(RUSTED_CORE, 75, Blocks.COARSE_DIRT));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(RUSTED_CORE, 75, Blocks.PODZOL));

        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 300, Blocks.VINE));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 300, Blocks.LILY_PAD));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 1000, Blocks.LILY_PAD));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 1000, Blocks.VINE));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 5000, Blocks.MOSSY_COBBLESTONE));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(RIFT_V2, 100, null));
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(LIVING_PARTICLE, 100, null));

        // NIGHT MONSTER GENERATION
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CLUCKER, 100, Blocks.SAND));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CURSER, 1, Blocks.RED_SAND));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CURSER, 1, Blocks.TERRACOTTA));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(GHOST, 100, null));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(AQUAMARIN, 100, Blocks.WATER));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(SILKER, 85, null));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(MAJOR_WICKED, 25, Blocks.STONE));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(RIFT, 100, null));

        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 500, Blocks.VINE));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL, 500, Blocks.LILY_PAD));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 1000, Blocks.LILY_PAD));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 1000, Blocks.VINE));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WOODFALL_SOLIDER, 5000, Blocks.MOSSY_COBBLESTONE));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(OOKLA, 25, null));
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(BAD_OMEN, 1, Blocks.AIR));

        // CAVE MONSTER GENERATION
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(LOST_CAVER, 25, null));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(WICKED, 100, null));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(SEEKER, 50, null));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(MAJOR_WICKED, 25, Blocks.STONE));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(LAVA_BEING, 100, Blocks.LAVA));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CAVE_SUCKER, 100, null));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CORRUPTED, 100, null));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(COPIER, 100, null));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(SPYING, 100, null));
        CaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(CATAPLEER, 100, null));

        // RARE CAVE MONSTER GENERATION

        RareCaveMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(MIMIC, 100, null));

        // NPC CREATURE GENERATION
        NPCSpawnSubscriber.mobListLV1.add(new MobSpawnData(TRAVELER, 50, null));

        // BUFFS GENERATION
        BuffsSpawnSubscriber.mobListLV1.add(new MobSpawnData(COIN_GIVER, 50, null));

        // SKYRIA
        SkyriaMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(SKY_EJECTOR, 100, null));
        SkyriaMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(FLYER, 25, null));
        SkyriaMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(AXIS, 15, null));
        SkyriaMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(SKYDER, 100, null));

        // BLOOD SUN
        DaylightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(BLOOD_INFECTIONER, 100, null, 1));

        // BROKEN MOON
        NightMonsterSpawnerSubscriber.mobListLV1.add(new MobSpawnData(BROKEN_INSURRECTIONIST, 100, null, 0));
    }
}

// 231483547259349260

// /execute in ror:skyria run teleport Dev 602 104 -118
// -443095963033985164