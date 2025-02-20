package fr.acth2.mod.utils.subscribers;

import fr.acth2.mod.entities.entity.EntityExample;
import fr.acth2.mod.init.ModEntities;
import fr.acth2.mod.utils.References;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityAttributes {

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ENTITY_EXAMPLE.get(), EntityExample.createAttributes().build());
    }
}
