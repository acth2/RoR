package fr.acth2.ror.utils.subscribers.mod;

import fr.acth2.ror.entities.constructors.ExampleEntity;
import fr.acth2.ror.entities.constructors.cg.CoinGiverEntity;
import fr.acth2.ror.entities.constructors.clucker.CluckerEntity;
import fr.acth2.ror.entities.constructors.curser.CurserEntity;
import fr.acth2.ror.entities.constructors.hopper.HopperEntity;
import fr.acth2.ror.entities.constructors.lc.LostCaverEntity;
import fr.acth2.ror.entities.constructors.rc.RustedCoreEntity;
import fr.acth2.ror.entities.constructors.wicked.WickedEntity;
import fr.acth2.ror.entities.constructors.woodfall.WoodFallEntity;
import fr.acth2.ror.entities.constructors.ws.WoodSpiritEntity;
import fr.acth2.ror.entities.entity.ghost.EntityGhost;
import fr.acth2.ror.entities.entity.woodfall.solider.EntityWoodFallSolider;
import fr.acth2.ror.gui.coins.CoinsManager;
import fr.acth2.ror.utils.References;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.CreeperEntity;
import net.minecraft.entity.monster.EndermanEntity;
import net.minecraft.entity.monster.SkeletonEntity;
import net.minecraft.entity.monster.ZombieEntity;
import net.minecraft.entity.passive.CowEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.entity.passive.SheepEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID)
public class ModCoinsDistributor {
    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        if (event.getSource().getEntity() instanceof ServerPlayerEntity) {
            ServerPlayerEntity player = (ServerPlayerEntity) event.getSource().getEntity();
            LivingEntity target = event.getEntityLiving();

            if (player == null || target == null) return;

            String entityType = getEntityType(target);
            int coinsToAdd = getCoinsForEntity(entityType);

            CoinsManager.addCoins(player, coinsToAdd);
            CoinsManager.syncCoins(player);

            if (entityType.equals("CoinGiver")) {
                player.sendMessage(ITextComponent.nullToEmpty("+1000 COINS"), player.getUUID());
            }
        }
    }

    private static int getCoinsForEntity(String entityType) {
        switch (entityType) {
            case "Zombie": return 10;
            case "Sheep": return 3;
            case "Creeper":
            case "Curser":
            case "RustedCore": return 20;
            case "Skeleton": return 15;
            case "Enderman": return 40;
            case "Clucker":
            case "Hopper":
            case "WoodFall": return 50;
            case "LostCaver":
            case "Wicked":
            case "WoodSpirit": return 70;
            case "CoinGiver": return 1000;
            case "WoodFallSolider":
            case "Ghost":
            case "IronGolem":
                return 250;
            default: return 5;
        }
    }

    private static String getEntityType(LivingEntity entity) {
        if (entity instanceof ZombieEntity) return "Zombie";
        if (entity instanceof CowEntity) return "Cow";
        if (entity instanceof SheepEntity) return "Sheep";
        if (entity instanceof CreeperEntity) return "Creeper";
        if (entity instanceof SkeletonEntity) return "Skeleton";
        if (entity instanceof EndermanEntity) return "Enderman";
        if (entity instanceof CurserEntity) return "Curser";
        if (entity instanceof CluckerEntity) return "Clucker";
        if (entity instanceof HopperEntity) return "Hopper";
        if (entity instanceof LostCaverEntity) return "LostCaver";
        if (entity instanceof RustedCoreEntity) return "RustedCore";
        if (entity instanceof WickedEntity) return "Wicked";
        if (entity instanceof WoodFallEntity) return "WoodFall";
        if (entity instanceof WoodSpiritEntity) return "WoodSpirit";
        if (entity instanceof ExampleEntity) return "ExampleEntity";
        if (entity instanceof CoinGiverEntity) return "CoinGiver";
        if (entity instanceof EntityWoodFallSolider) return "WoodFallSolider";
        if (entity instanceof EntityGhost) return "Ghost";
        if (entity instanceof IronGolemEntity) return "IronGolem";

        return "Unknown";
    }
}