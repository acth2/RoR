package fr.acth2.ror.utils.subscribers.client;

import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, References.MODID);

    public static final RegistryObject<SoundEvent> CURSER_HIT =
            registerSoundEvent("curser_hit");

    public static final RegistryObject<SoundEvent> CURSER_AMBIENT =
            registerSoundEvent("curser_ambient");

    public static final RegistryObject<SoundEvent> WICKED_HIT =
            registerSoundEvent("wicked_hit");

    public static final RegistryObject<SoundEvent> WICKED_AMBIENT =
            registerSoundEvent("wicked_ambient");
    public static final RegistryObject<SoundEvent> WICKED_DIE =
            registerSoundEvent("wicked_die");

    public static final RegistryObject<SoundEvent> LOSTCAVER_HIT =
            registerSoundEvent("lc_hit");

    public static final RegistryObject<SoundEvent> LOSTCAVER_AMBIENT =
            registerSoundEvent("lc_ambient");

    public static final RegistryObject<SoundEvent> LOSTCAVER_DIE =
            registerSoundEvent("lc_die");

    public static final RegistryObject<SoundEvent> CLUCKER_HIT =
            registerSoundEvent("clucker_hit");

    public static final RegistryObject<SoundEvent> CLUCKER_AMBIENT =
            registerSoundEvent("clucker_ambient");

    public static final RegistryObject<SoundEvent> CLUCKER_DIE =
            registerSoundEvent("clucker_die");

    public static final RegistryObject<SoundEvent> HOPPER_HIT =
            registerSoundEvent("hopper_hit");

    public static final RegistryObject<SoundEvent> HOPPER_AMBIENT =
            registerSoundEvent("hopper_ambient");

    public static final RegistryObject<SoundEvent> HOPPER_DIE =
            registerSoundEvent("hopper_die");

    public static final RegistryObject<SoundEvent> RUSTEDCORE_HIT =
            registerSoundEvent("rc_hit");

    public static final RegistryObject<SoundEvent> RUSTEDCORE_AMBIENT =
            registerSoundEvent("rc_ambient");

    public static final RegistryObject<SoundEvent> RUSTEDCORE_DIE =
            registerSoundEvent("rc_die");

    public static final RegistryObject<SoundEvent> RUSTEDCORE_EXPLODE =
            registerSoundEvent("rc_explode");

    public static final RegistryObject<SoundEvent> WOODSPIRIT_AMBIENT =
            registerSoundEvent("ws_ambient");

    public static final RegistryObject<SoundEvent> WOODSPIRIT_DIE =
            registerSoundEvent("ws_die");

    public static final RegistryObject<SoundEvent> WOODSPIRIT_HIT =
            registerSoundEvent("ws_hit");

    public static final RegistryObject<SoundEvent> WOODFALL_HIT =
            registerSoundEvent("wf_hit");

    public static final RegistryObject<SoundEvent> WOODFALL_AMBIENT =
            registerSoundEvent("wf_ambient");

    public static final RegistryObject<SoundEvent> WOODFALL_DIE =
            registerSoundEvent("wf_die");

    public static final RegistryObject<SoundEvent> WOODFALL_SOLIDER_HIT =
            registerSoundEvent("wfs_hit");

    public static final RegistryObject<SoundEvent> WOODFALL_SOLIDER_AMBIENT =
            registerSoundEvent("wfs_ambient");

    public static final RegistryObject<SoundEvent> WOODFALL_SOLIDER_DIE =
            registerSoundEvent("wfs_die");

    public static final RegistryObject<SoundEvent> COINGIVER_AMBIENT =
            registerSoundEvent("cg_ambient");

    public static final RegistryObject<SoundEvent> COINGIVER_DIE =
            registerSoundEvent("cg_die");

    public static final RegistryObject<SoundEvent> GHOST_HIT =
            registerSoundEvent("ghost_hit");

    public static final RegistryObject<SoundEvent> GHOST_AMBIENT =
            registerSoundEvent("ghost_ambient");

    public static final RegistryObject<SoundEvent> GHOST_DIE =
            registerSoundEvent("ghost_die");

    public static final RegistryObject<SoundEvent> SEEKER_HIT =
            registerSoundEvent("seeker_hit");

    public static final RegistryObject<SoundEvent> SEEKER_AMBIENT =
            registerSoundEvent("seeker_ambient");

    public static final RegistryObject<SoundEvent> SEEKER_DIE =
            registerSoundEvent("seeker_die");

    public static final RegistryObject<SoundEvent> AQUAMARIN_HIT =
            registerSoundEvent("aquamarin_hit");

    public static final RegistryObject<SoundEvent> AQUAMARIN_AMBIENT =
            registerSoundEvent("aquamarin_ambient");

    public static final RegistryObject<SoundEvent> AQUAMARIN_DIE =
            registerSoundEvent("aquamarin_die");

    public static final RegistryObject<SoundEvent> FUSSLE_HIT =
            registerSoundEvent("fussle_hit");

    public static final RegistryObject<SoundEvent> FUSSLE_AMBIENT =
            registerSoundEvent("fussle_ambient");

    public static final RegistryObject<SoundEvent> FUSSLE_DIE =
            registerSoundEvent("fussle_die");

    public static final RegistryObject<SoundEvent> ECHO_HIT =
            registerSoundEvent("echo_hit");

    public static final RegistryObject<SoundEvent> ECHO_AMBIENT =
            registerSoundEvent("echo_ambient");

    public static final RegistryObject<SoundEvent> ECHO_DIE =
            registerSoundEvent("echo_die");

    public static final RegistryObject<SoundEvent> OOKLA_AMBIENT =
            registerSoundEvent("ookla_ambient");

    public static final RegistryObject<SoundEvent> OOKLA_DIE =
            registerSoundEvent("ookla_die");

    public static final RegistryObject<SoundEvent> BROKEN_MOON_AMBIENT =
            registerSoundEvent("bm_ambient");

    public static final RegistryObject<SoundEvent> BADOMEN_AMBIENT =
            registerSoundEvent("bo_ambient");

    public static final RegistryObject<SoundEvent> BADOMEN_DIE =
            registerSoundEvent("bo_die");

    public static final RegistryObject<SoundEvent> BADOMEN_HIT =
            registerSoundEvent("bo_hit");

    public static final RegistryObject<SoundEvent> MIMIC_AMBIENT =
            registerSoundEvent("mimic_ambient");

    public static final RegistryObject<SoundEvent> MIMIC_DIE =
            registerSoundEvent("mimic_die");

    public static final RegistryObject<SoundEvent> MIMIC_HIT =
            registerSoundEvent("mimic_hit");

    public static final RegistryObject<SoundEvent> SKYEJECTOR_AMBIENT =
            registerSoundEvent("se_ambient");

    public static final RegistryObject<SoundEvent> SKYEJECTOR_DIE =
            registerSoundEvent("se_die");

    public static final RegistryObject<SoundEvent> SKYEJECTOR_HIT =
            registerSoundEvent("se_hit");

    public static final RegistryObject<SoundEvent> FLYER_AMBIENT =
            registerSoundEvent("flyer_ambient");

    public static final RegistryObject<SoundEvent> FLYER_DIE =
            registerSoundEvent("flyer_die");

    public static final RegistryObject<SoundEvent> FLYER_HIT =
            registerSoundEvent("flyer_hit");

    public static final RegistryObject<SoundEvent> CAVE_SUCKER_AMBIENT =
            registerSoundEvent("cs_ambient");

    public static final RegistryObject<SoundEvent> CAVE_SUCKER_DIE =
            registerSoundEvent("cs_die");

    public static final RegistryObject<SoundEvent> CAVE_SUCKER_HIT =
            registerSoundEvent("cs_hit");

    public static final RegistryObject<SoundEvent> BLOOD_INFECTIONER_AMBIENT =
            registerSoundEvent("bi_ambient");

    public static final RegistryObject<SoundEvent> BLOOD_INFECTIONER_DIE =
            registerSoundEvent("bi_die");

    public static final RegistryObject<SoundEvent> BLOOD_INFECTIONER_HIT =
            registerSoundEvent("bi_hit");

    public static final RegistryObject<SoundEvent> DESPITER_AMBIENT =
            registerSoundEvent("despiter_ambient");

    public static final RegistryObject<SoundEvent> DESPITER_DIE =
            registerSoundEvent("despiter_die");

    public static final RegistryObject<SoundEvent> DESPITER_HIT =
            registerSoundEvent("despiter_hit");

    public static final RegistryObject<SoundEvent> BROKEN_INSURRECTIONIST_AMBIENT =
            registerSoundEvent("bri_ambient");

    public static final RegistryObject<SoundEvent> BROKEN_INSURRECTIONIST_DIE =
            registerSoundEvent("bri_die");

    public static final RegistryObject<SoundEvent> BROKEN_INSURRECTIONIST_HIT =
            registerSoundEvent("bri_hit");

    public static final RegistryObject<SoundEvent> GRASSER_AMBIENT =
            registerSoundEvent("grasser_ambient");

    public static final RegistryObject<SoundEvent> GRASSER_DIE =
            registerSoundEvent("grasser_die");

    public static final RegistryObject<SoundEvent> GRASSER_HIT =
            registerSoundEvent("grasser_hit");

    public static final RegistryObject<SoundEvent> HOWLER_AMBIENT =
            registerSoundEvent("howler_ambient");

    public static final RegistryObject<SoundEvent> HOWLER_DIE =
            registerSoundEvent("howler_die");

    public static final RegistryObject<SoundEvent> HOWLER_HIT =
            registerSoundEvent("howler_hit");

    public static final RegistryObject<SoundEvent> AXIS_AMBIENT =
            registerSoundEvent("axis_ambient");

    public static final RegistryObject<SoundEvent> AXIS_DIE =
            registerSoundEvent("axis_die");

    public static final RegistryObject<SoundEvent> AXIS_HIT =
            registerSoundEvent("axis_hit");

    public static final RegistryObject<SoundEvent> LAVA_BEING_AMBIENT =
            registerSoundEvent("lb_ambient");

    public static final RegistryObject<SoundEvent> LAVA_BEING_DIE =
            registerSoundEvent("lb_die");

    public static final RegistryObject<SoundEvent> LAVA_BEING_HIT =
            registerSoundEvent("lb_hit");

    public static final RegistryObject<SoundEvent> WHISP_AMBIENT =
            registerSoundEvent("whisp_ambient");


    public static final RegistryObject<SoundEvent> CORRUPTED_DIE =
            registerSoundEvent("corrupted_die");

    public static final RegistryObject<SoundEvent> CORRUPTED_HIT =
            registerSoundEvent("corrupted_hit");

    public static final RegistryObject<SoundEvent> CORRUPTED_AMBIENT =
            registerSoundEvent("corrupted_ambient");

    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(References.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
