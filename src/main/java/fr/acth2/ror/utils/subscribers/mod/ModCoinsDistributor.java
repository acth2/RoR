package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.entities.constructors.ExampleEntity;
import fr.acth2.ror.entities.constructors.clucker.CluckerEntity;
import fr.acth2.ror.entities.constructors.curser.CurserEntity;
import fr.acth2.ror.entities.constructors.hopper.HopperEntity;
import fr.acth2.ror.entities.constructors.lc.LostCaverEntity;
import fr.acth2.ror.entities.constructors.rc.RustedCoreEntity;
import fr.acth2.ror.entities.constructors.wicked.WickedEntity;
import fr.acth2.ror.entities.constructors.woodfall.WoodFallEntity;
import fr.acth2.ror.entities.constructors.ws.WoodSpiritEntity;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.utils.References;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static fr.acth2.ror.utils.subscribers.gen.overworld.NPCSpawnSubscriber.*;

@Mod.EventBusSubscriber(modid = References.MODID)
public class ModCoinsDistributor {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {

        if (CoinsManager.hasLeastCoins(1500)) {
            SPAWN_INTERVAL_TICKS = 1000;
            ATTEMPTS_PER_PLAYER = 3;
            SPAWN_CHANCE = 3.0D;
        }

        if (event.getSource().getEntity() instanceof PlayerEntity) {
            String entityType = getEntityType((LivingEntity) event.getEntity());

            switch (entityType) {
                case "Zombie":
                    CoinsManager.addCoins(10);
                    break;

                case "Cow":
                    CoinsManager.addCoins(5);
                    break;

                case "Sheep":
                    CoinsManager.addCoins(3);
                    break;

                case "Creeper":

                case "Curser":

                case "RustedCore":
                    CoinsManager.addCoins(20);
                    break;

                case "Skeleton":
                    CoinsManager.addCoins(15);
                    break;

                case "Enderman":
                    CoinsManager.addCoins(40);
                    break;

                case "Clucker":

                case "Hopper":

                case "WoodFall":
                    CoinsManager.addCoins(50);
                    break;

                case "LostCaver":

                case "Wicked":

                case "WoodSpirit":
                    CoinsManager.addCoins(70);
                    break;

                default:
                    CoinsManager.addCoins(1);
                    break;
            }
        }
    }

    private static String getEntityType(LivingEntity entity) {
        if (entity instanceof ZombieEntity) {
            return "Zombie";
        } else if (entity instanceof CowEntity) {
            return "Cow";
        } else if (entity instanceof SheepEntity) {
            return "Sheep";
        } else if (entity instanceof CreeperEntity) {
            return "Creeper";
        } else if (entity instanceof SkeletonEntity) {
            return "Skeleton";
        } else if (entity instanceof EndermanEntity) {
            return "Enderman";
        } else if (entity instanceof CurserEntity) {
            return "Curser";
        } else if (entity instanceof CluckerEntity) {
            return "Clucker";
        } else if (entity instanceof HopperEntity) {
            return "Hopper";
        } else if (entity instanceof LostCaverEntity){
            return "LostCaver";
        } else if (entity instanceof RustedCoreEntity) {
            return "RustedCore";
        } else if (entity instanceof WickedEntity) {
            return "Wicked";
        } else if (entity instanceof WoodFallEntity) {
            return "WoodFall";
        } else if (entity instanceof WoodSpiritEntity) {
            return "WoodSpirit";
        } else if (entity instanceof ExampleEntity) {
            return "ExampleEntity";
        }

        return "Unknown";
    }
}
