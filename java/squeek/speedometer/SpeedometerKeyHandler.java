package squeek.mods.speedometer;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;

public class SpeedometerKeyHandler extends KeyHandler {
    private static final KeyBinding SETTINGS_KEY = new KeyBinding("key.speedometer.settings", Keyboard.KEY_P);
    private static final KeyBinding[] KEYS = new KeyBinding[]{SETTINGS_KEY};
    private static final boolean[] REPEAT = new boolean[]{false};
    
    public SpeedometerKeyHandler() {
        super(KEYS, REPEAT);
    }

    @Override
    public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat) {
        if (!tickEnd) {
            return;
        }
        
        if (kb == SETTINGS_KEY) {
            if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && Keyboard.isKeyDown(Keyboard.KEY_LCONTROL)) {
                Minecraft mc = Minecraft.getMinecraft();
                mc.displayGuiScreen(new GuiSpeedometerSettingsScreen());
            }
        }
    }

    @Override
    public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
    }

    @Override
    public EnumSet<TickType> ticks() {
        return EnumSet.of(TickType.CLIENT);
    }

    @Override
    public String getLabel() {
        return getClass().getSimpleName();
    }
}