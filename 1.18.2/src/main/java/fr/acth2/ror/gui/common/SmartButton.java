package fr.acth2.ror.gui.common;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.network.chat.TextComponent;

public class SmartButton extends Button {
    public SmartButton(int x, int y, int width, int height, TextComponent text, IPressable onPress) {
        super(x, y, width, height, text, onPress);
    }
}
