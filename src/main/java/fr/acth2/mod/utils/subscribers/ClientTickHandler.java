package fr.acth2.mod.utils.subscribers;

import fr.acth2.mod.gui.MainMenuGui;
import fr.acth2.mod.init.ModKeyBindings;
import fr.acth2.mod.utils.References;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = References.MODID, value = Dist.CLIENT)
public class ClientTickHandler {
    @SubscribeEvent
    public static void onClientTick(TickEvent.ClientTickEvent event) {
        if (Minecraft.getInstance().screen == null && ModKeyBindings.MM.consumeClick()) {
            Minecraft.getInstance().setScreen(new MainMenuGui(Minecraft.getInstance().player));
        }
    }
}