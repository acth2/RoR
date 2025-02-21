package fr.acth2.mod.gui.common;

import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.StringTextComponent;

public class SmartButton extends Button {
    public SmartButton(int x, int y, int width, int height, StringTextComponent text, IPressable onPress) {
        super(x, y, width, height, text, onPress);
    }
}
