package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.entities.constructors.ExampleEntity;
import fr.acth2.ror.entities.constructors.clucker.CluckerEntity;
import fr.acth2.ror.entities.constructors.curser.CurserEntity;
import fr.acth2.ror.entities.constructors.hopper.HopperEntity;
import fr.acth2.ror.entities.constructors.lc.LostCaverEntity;
import fr.acth2.ror.entities.constructors.rc.RustedCoreEntity;
import fr.acth2.ror.entities.constructors.traveler.TravelerEntity;
import fr.acth2.ror.entities.constructors.wicked.WickedEntity;
import fr.acth2.ror.entities.constructors.woodfall.WoodFallEntity;
import fr.acth2.ror.entities.constructors.ws.WoodSpiritEntity;
import fr.acth2.ror.init.ModEntities;
import fr.acth2.ror.utils.References;
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
        event.put(ModEntities.RUSTED_CORE.get(), RustedCoreEntity.createAttributes().build());
        event.put(ModEntities.WICKED.get(), WickedEntity.createAttributes().build());
        event.put(ModEntities.CLUCKER.get(), CluckerEntity.createAttributes().build());
        event.put(ModEntities.CURSER.get(), CurserEntity.createAttributes().build());
        event.put(ModEntities.WOODFALL.get(), WoodFallEntity.createAttributes().build());
        event.put(ModEntities.WOOD_SPIRIT.get(), WoodSpiritEntity.createAttributes().build());
        event.put(ModEntities.TRAVELER.get(), TravelerEntity.createAttributes().build());
    }
}
