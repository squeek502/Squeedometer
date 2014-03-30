package squeek.mods.speedometer;

import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@Mod(modid="squeek_Speedometer", name="Speedometer", version="0.1.0")
public class ModSpeedometer {

        // The instance of your mod that Forge uses.
        @Instance(value = "squeek_Speedometer")
        public static ModSpeedometer instance;
       
        @EventHandler
        @SideOnly( Side.CLIENT )
        public void preInit(FMLPreInitializationEvent event) {
        	ModConfig.init( event.getSuggestedConfigurationFile() );
        }
       
        @EventHandler
        @SideOnly( Side.CLIENT )
        public void load(FMLInitializationEvent event) {
            KeyBindingRegistry.registerKeyBinding(new SpeedometerKeyHandler());
        }
       
        @EventHandler
        @SideOnly( Side.CLIENT )
        public void postInit(FMLPostInitializationEvent event) {
        	MinecraftForge.EVENT_BUS.register(new GuiSpeedometer());
        }
}
