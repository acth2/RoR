package fr.acth2.ror.init.constructors.items;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.ITeleporter;
import fr.acth2.ror.utils.References;

import javax.annotation.Nullable;
import java.util.List;
import java.util.function.Function;

public class ItemExample extends Item {
    public ItemExample(Properties properties) {
        super(properties);
    }

    @Override
    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        if (!world.isClientSide()) {
            ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;
            ResourceLocation dimensionRL = new ResourceLocation(References.MODID, "ed");
            ServerWorld targetWorld = serverPlayer.getServer().getLevel(RegistryKey.create(Registry.DIMENSION_REGISTRY, dimensionRL));
            if (targetWorld != null) {
                serverPlayer.changeDimension(targetWorld, new ITeleporter() {
                    @Override
                    public Entity placeEntity(Entity entity, ServerWorld currentWorld, ServerWorld destWorld, float yaw, Function<Boolean, Entity> repositionEntity) {
                        return repositionEntity.apply(false);
                    }
                });
            } else {
                serverPlayer.sendMessage(new StringTextComponent("a mistake has been made in the code, send log to discord."), serverPlayer.getUUID());
            }
        }
        return ActionResult.success(player.getItemInHand(hand));
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable World world, List<ITextComponent> tooltip, ITooltipFlag flag) {
        tooltip.add(new StringTextComponent(TextFormatting.GRAY + "If you really want to use this item"));
        tooltip.add(new StringTextComponent(TextFormatting.RED + "Then remove your equipment"));
        super.appendHoverText(stack, world, tooltip, flag);
    }
}
