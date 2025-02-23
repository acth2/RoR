package fr.acth2.ror.utils.subscribers;

import fr.acth2.ror.entities.entity.EntityExample;
import fr.acth2.ror.gui.diary.DiaryEntry;
import fr.acth2.ror.gui.diary.DiaryManager;
import fr.acth2.ror.utils.References;
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
                entity.getType().getRegistryName().getNamespace().equals("ror")) {
            if (entity instanceof EntityExample) {
                DiaryEntry entry = new DiaryEntry(
                        entity.getName().getString(),
                        entity.getType(),
                        "wait what?"
                );
                DiaryManager.addEntry(entry);
            } else {
                DiaryEntry entry = new DiaryEntry(
                        entity.getName().getString(),
                        entity.getType(),
                        DiaryManager.pickDescription(entity.getName().getString())
                );
                DiaryManager.addEntry(entry);
            }
        }
    }
}
