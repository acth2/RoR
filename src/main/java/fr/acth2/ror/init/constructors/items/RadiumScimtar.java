package fr.acth2.ror.init.constructors.items;

import fr.acth2.ror.init.ModNetworkHandler;
import fr.acth2.ror.init.constructors.tools.Radium1ItemTier;
import fr.acth2.ror.init.constructors.tools.RadiumItemTier;
import fr.acth2.ror.network.skills.RequestSyncPlayerStatsPacket;
import fr.acth2.ror.utils.subscribers.mod.skills.PlayerStats;
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class RadiumScimtar extends SwordItem {
    public RadiumScimtar(Item.Properties builder) {
        super(Radium1ItemTier.RADIUM1, 3, -0.8F, builder);
    }

    public void appendHoverText(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        PlayerEntity player = Minecraft.getInstance().player;
        if (player != null) {
            PlayerStats stats = PlayerStats.get(player);
            float damage = (stats.getDexterity() / 5.0f);
            tooltip.add(new StringTextComponent("Your damage scale with your dexterity!").withStyle(TextFormatting.LIGHT_PURPLE));
            tooltip.add(new StringTextComponent("Bonus damage is: " + String.format("%.2f", damage)).withStyle(TextFormatting.BOLD));
        }
    }


}
