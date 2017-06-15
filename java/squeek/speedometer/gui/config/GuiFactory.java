package squeek.speedometer.gui.config;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import squeek.speedometer.ModConfig;
import squeek.speedometer.ModInfo;

public class GuiFactory extends DefaultGuiFactory
{
    public GuiFactory() {
        super(ModInfo.MODID, GuiConfig.getAbridgedConfigPath(ModConfig.config.toString()));
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen parentScreen)
    {
        return new GuiConfig(parentScreen, new ConfigElement(ModConfig.config.getCategory(ModConfig.CATEGORY_SPEEDOMETER)).getChildElements(), modid, false, false, title);
    }
}