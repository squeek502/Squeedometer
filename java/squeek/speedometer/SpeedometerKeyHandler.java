package squeek.speedometer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.input.Keyboard;
import squeek.speedometer.gui.screen.ScreenSpeedometerSettings;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

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
		if (SETTINGS_KEY.isPressed())
		{
			Minecraft mc = Minecraft.getMinecraft();

			if (mc.currentScreen != null)
				return;

			mc.displayGuiScreen(new ScreenSpeedometerSettings());
		}
	}
}
