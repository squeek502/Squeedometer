package squeek.speedometer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import squeek.speedometer.gui.screen.ScreenSpeedometerSettings;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent.KeyInputEvent;

public class SpeedometerKeyHandler
{
	private static final KeyBinding SETTINGS_KEY = new KeyBinding("squeedometer.key.settings", Keyboard.KEY_P, ModInfo.MODID);
	static
	{
		ClientRegistry.registerKeyBinding(SETTINGS_KEY);
	}

	@SubscribeEvent
	public void onKeyEvent(KeyInputEvent event)
	{
		if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == SETTINGS_KEY.getKeyCode())
		{
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.currentScreen != null)
				return;

			mc.displayGuiScreen(new ScreenSpeedometerSettings());
		}
	}
}
