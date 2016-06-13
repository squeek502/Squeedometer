package squeek.speedometer.gui.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.config.GuiConfig;
import squeek.speedometer.ModConfig;
import squeek.speedometer.ModInfo;

public class GuiSpeedometerConfig extends GuiConfig {
    public GuiSpeedometerConfig(GuiScreen parentScreen) {
        super(parentScreen, new ConfigElement(ModConfig.config.getCategory(ModConfig.CATEGORY_SPEEDOMETER)).getChildElements(), ModInfo.MODID, false, false, GuiConfig.getAbridgedConfigPath(ModConfig.config.toString()));
    }
}