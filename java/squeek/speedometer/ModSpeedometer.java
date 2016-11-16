package squeek.speedometer;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = ModInfo.MODID, version = ModInfo.VERSION, clientSideOnly = true, acceptedMinecraftVersions="[1.11,1.12)", guiFactory = ModInfo.GUI_FACTORY_CLASS)
public class ModSpeedometer
{
	@Instance(value = ModInfo.MODID)
	public static ModSpeedometer instance;

	@EventHandler
	@SideOnly(Side.CLIENT)
	public void preInit(FMLPreInitializationEvent event)
	{
		ModConfig.init(event.getSuggestedConfigurationFile());
		MinecraftForge.EVENT_BUS.register(new ModConfig());
	}

	@EventHandler
	@SideOnly(Side.CLIENT)
	public void load(FMLInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new SpeedometerKeyHandler());
	}

	@EventHandler
	@SideOnly(Side.CLIENT)
	public void postInit(FMLPostInitializationEvent event)
	{
		MinecraftForge.EVENT_BUS.register(new HudSpeedometer());
		FMLInterModComms.sendRuntimeMessage(ModInfo.MODID, "VersionChecker", "addVersionCheck", "http://www.ryanliptak.com/minecraft/versionchecker/squeek502/Squeedometer");
	}
}
