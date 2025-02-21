package fr.acth2.mod.utils.subscribers;

import fr.acth2.mod.gui.diary.DiaryEntry;
import fr.acth2.mod.gui.diary.DiaryManager;
import fr.acth2.mod.utils.References;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID)
public class ModEntityDeathHandler {

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        LivingEntity entity = event.getEntityLiving();
        if (entity.getType().getRegistryName() != null &&
                entity.getType().getRegistryName().getNamespace().equals("minecraft")) {
            DiaryEntry entry = new DiaryEntry(
                    entity.getName().getString(),
                    entity.getType(),
                    entity.getName().getString() == "Entity Example" ? "wait what?" : DiaryManager.pickDescription(entity.getName().getString())
            );
            DiaryManager.addEntry(entry);
        }
    }
}
