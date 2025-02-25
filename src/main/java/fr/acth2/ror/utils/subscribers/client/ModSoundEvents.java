package fr.acth2.ror.utils.subscribers.client;

import fr.acth2.ror.utils.References;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
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

    public static final RegistryObject<SoundEvent> LOSTCAVER_HIT =
            registerSoundEvent("lc_hit");

    public static final RegistryObject<SoundEvent> LOSTCAVER_AMBIENT =
            registerSoundEvent("lc_ambient");
    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        return SOUND_EVENTS.register(name, () -> new SoundEvent(new ResourceLocation(References.MODID, name)));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
