package fr.acth2.ror.utils.subscribers;

import fr.acth2.ror.entities.constructors.ExampleEntity;
import fr.acth2.ror.entities.constructors.hopper.HopperEntity;
import fr.acth2.ror.entities.constructors.lc.LostCaverEntity;
import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.utils.References;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntityAttributes {

    @SubscribeEvent
    public static void onEntityAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(ModEntities.ENTITY_EXAMPLE.get(), ExampleEntity.createAttributes().build());
        event.put(ModEntities.LOST_CAVER.get(), LostCaverEntity.createAttributes().build());
        event.put(ModEntities.HOPPER.get(), HopperEntity.createAttributes().build());
    }
}
