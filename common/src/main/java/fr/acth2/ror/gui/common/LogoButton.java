package fr.acth2.ror.gui.common;

import com.mojang.blaze3d.matrix.MatrixStack;
import fr.acth2.ror.utils.subscribers.client.ModSoundEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fml.loading.FMLEnvironment;

import java.util.Random;

public class LogoButton extends Button {
    private static final ResourceLocation LOGO_ICON = new ResourceLocation("ror", "textures/gui/logo.png");
    private static final Random RANDOM = new Random();
    private static int clickCount = 0;

    public LogoButton(int x, int y, int width, int height) {
        super(x, y, width, height, new StringTextComponent(""), button -> {
            clickCount++;

            switch (clickCount) {
                case 1:
                    playSound(ModSoundEvents.LOGO_CLICK_1.get());
                    break;
                case 2:
                    playSound(ModSoundEvents.LOGO_CLICK_2.get());
                    break;
                case 3:
                    playSound(ModSoundEvents.LOGO_CLICK_3.get());
                    break;
                default:
                    playRandomMonsterNoise();
            }
        });
    }

    private static void playSound(SoundEvent sound) {
        if (FMLEnvironment.dist.isClient()) {
            Minecraft.getInstance().getSoundManager().play(
                    SimpleSound.forUI(
                            sound,
                            1.0f,
                            1.0f
                    )
            );
        }
    }

    private static void playRandomMonsterNoise() {
        SoundEvent[] allMonsterSounds = {
                ModSoundEvents.CURSER_AMBIENT.get(),
                ModSoundEvents.CURSER_HIT.get(),
                ModSoundEvents.WICKED_AMBIENT.get(),
                ModSoundEvents.WICKED_HIT.get(),
                ModSoundEvents.WICKED_DIE.get(),
                ModSoundEvents.LOSTCAVER_AMBIENT.get(),
                ModSoundEvents.LOSTCAVER_HIT.get(),
                ModSoundEvents.LOSTCAVER_DIE.get(),
                ModSoundEvents.CLUCKER_AMBIENT.get(),
                ModSoundEvents.CLUCKER_HIT.get(),
                ModSoundEvents.CLUCKER_DIE.get(),
                ModSoundEvents.HOPPER_AMBIENT.get(),
                ModSoundEvents.HOPPER_HIT.get(),
                ModSoundEvents.HOPPER_DIE.get(),
                ModSoundEvents.RUSTEDCORE_AMBIENT.get(),
                ModSoundEvents.RUSTEDCORE_HIT.get(),
                ModSoundEvents.RUSTEDCORE_DIE.get(),
                ModSoundEvents.RUSTEDCORE_EXPLODE.get(),
                ModSoundEvents.WOODSPIRIT_AMBIENT.get(),
                ModSoundEvents.WOODSPIRIT_HIT.get(),
                ModSoundEvents.WOODSPIRIT_DIE.get(),
                ModSoundEvents.WOODFALL_AMBIENT.get(),
                ModSoundEvents.WOODFALL_HIT.get(),
                ModSoundEvents.WOODFALL_DIE.get(),
                ModSoundEvents.WOODFALL_SOLIDER_AMBIENT.get(),
                ModSoundEvents.WOODFALL_SOLIDER_HIT.get(),
                ModSoundEvents.WOODFALL_SOLIDER_DIE.get(),
                ModSoundEvents.COINGIVER_AMBIENT.get(),
                ModSoundEvents.COINGIVER_DIE.get(),
                ModSoundEvents.GHOST_AMBIENT.get(),
                ModSoundEvents.GHOST_HIT.get(),
                ModSoundEvents.GHOST_DIE.get(),
                ModSoundEvents.SEEKER_AMBIENT.get(),
                ModSoundEvents.SEEKER_HIT.get(),
                ModSoundEvents.SEEKER_DIE.get(),
                ModSoundEvents.AQUAMARIN_AMBIENT.get(),
                ModSoundEvents.AQUAMARIN_HIT.get(),
                ModSoundEvents.AQUAMARIN_DIE.get(),
                ModSoundEvents.FUSSLE_AMBIENT.get(),
                ModSoundEvents.FUSSLE_HIT.get(),
                ModSoundEvents.FUSSLE_DIE.get(),
                ModSoundEvents.ECHO_AMBIENT.get(),
                ModSoundEvents.ECHO_HIT.get(),
                ModSoundEvents.ECHO_DIE.get(),
                ModSoundEvents.OOKLA_AMBIENT.get(),
                ModSoundEvents.OOKLA_DIE.get(),
                ModSoundEvents.BROKEN_MOON_AMBIENT.get(),
                ModSoundEvents.BADOMEN_AMBIENT.get(),
                ModSoundEvents.BADOMEN_HIT.get(),
                ModSoundEvents.BADOMEN_DIE.get(),
                ModSoundEvents.MIMIC_AMBIENT.get(),
                ModSoundEvents.MIMIC_HIT.get(),
                ModSoundEvents.MIMIC_DIE.get(),
                ModSoundEvents.SKYEJECTOR_AMBIENT.get(),
                ModSoundEvents.SKYEJECTOR_HIT.get(),
                ModSoundEvents.SKYEJECTOR_DIE.get(),
                ModSoundEvents.FLYER_AMBIENT.get(),
                ModSoundEvents.FLYER_HIT.get(),
                ModSoundEvents.FLYER_DIE.get(),
                ModSoundEvents.CAVE_SUCKER_AMBIENT.get(),
                ModSoundEvents.CAVE_SUCKER_HIT.get(),
                ModSoundEvents.CAVE_SUCKER_DIE.get(),
                ModSoundEvents.BLOOD_INFECTIONER_AMBIENT.get(),
                ModSoundEvents.BLOOD_INFECTIONER_HIT.get(),
                ModSoundEvents.BLOOD_INFECTIONER_DIE.get(),
                ModSoundEvents.DESPITER_AMBIENT.get(),
                ModSoundEvents.DESPITER_HIT.get(),
                ModSoundEvents.DESPITER_DIE.get(),
                ModSoundEvents.BROKEN_INSURRECTIONIST_AMBIENT.get(),
                ModSoundEvents.BROKEN_INSURRECTIONIST_HIT.get(),
                ModSoundEvents.BROKEN_INSURRECTIONIST_DIE.get(),
                ModSoundEvents.GRASSER_AMBIENT.get(),
                ModSoundEvents.GRASSER_HIT.get(),
                ModSoundEvents.GRASSER_DIE.get(),
                ModSoundEvents.HOWLER_AMBIENT.get(),
                ModSoundEvents.HOWLER_HIT.get(),
                ModSoundEvents.HOWLER_DIE.get(),
                ModSoundEvents.AXIS_AMBIENT.get(),
                ModSoundEvents.AXIS_HIT.get(),
                ModSoundEvents.AXIS_DIE.get(),
                ModSoundEvents.LAVA_BEING_AMBIENT.get(),
                ModSoundEvents.LAVA_BEING_HIT.get(),
                ModSoundEvents.LAVA_BEING_DIE.get(),
                ModSoundEvents.WHISP_AMBIENT.get(),
                ModSoundEvents.CORRUPTED_AMBIENT.get(),
                ModSoundEvents.CORRUPTED_HIT.get(),
                ModSoundEvents.CORRUPTED_DIE.get(),
                ModSoundEvents.LIVING_PARTICLE_AMBIENT.get(),
                ModSoundEvents.LIVING_PARTICLE_DIE.get(),
                ModSoundEvents.SILKER_AMBIENT.get(),
                ModSoundEvents.SILKER_HIT.get(),
                ModSoundEvents.SILKER_DIE.get()
        };

        SoundEvent randomSound = allMonsterSounds[RANDOM.nextInt(allMonsterSounds.length)];
        playSound(randomSound);
    }

    public static void resetClickCount() {
        clickCount = 0;
    }


    @Override
    public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Minecraft.getInstance().getTextureManager().bind(LOGO_ICON);
        blit(matrixStack, this.x, this.y, 0, 0, this.width, this.height, this.width, this.height);
    }
}