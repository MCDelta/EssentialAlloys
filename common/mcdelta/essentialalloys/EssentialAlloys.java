package mcdelta.essentialalloys;

import mcdelta.core.IContent;
import mcdelta.core.ModDelta;
import mcdelta.essentialalloys.config.EAConfig;
import mcdelta.essentialalloys.event.EventBlockBreakEA;
import mcdelta.essentialalloys.gui.GuiHandler;
import mcdelta.essentialalloys.proxy.EACommonProxy;
import mcdelta.essentialalloys.world.GeneratorOres;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod (modid = EssentialAlloys.MOD_ID, useMetadata = true)
@NetworkMod (clientSideRequired = true, serverSideRequired = false)
public class EssentialAlloys extends ModDelta
{    
     public static final String          MOD_ID           = "essentialalloys";
     
     @Instance (MOD_ID)
     public static EssentialAlloys       instance;
     
     @SidedProxy (clientSide = "mcdelta.essentialalloys.proxy.EAClientProxy", serverSide = "mcdelta.essentialalloys.proxy.EACommonProxy")
     public static EACommonProxy         proxy;
     
     
     
     
     @Override
     public void deltaInit (final FMLPreInitializationEvent event)
     {
          this.init(event, new EAConfig());
     }
     
     
     
     
     @EventHandler
     public void preInit (final FMLPreInitializationEvent event)
     {
     }
     
     
     
     
     @EventHandler
     public void load (final FMLInitializationEvent event)
     {
          GameRegistry.registerWorldGenerator(new GeneratorOres());
          
          MinecraftForge.EVENT_BUS.register(new EventBlockBreakEA());
          
          proxy.registerRenderers();
          
          NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
     }
     
     
     
     
     @EventHandler
     public void postInit (final FMLPostInitializationEvent event)
     {
     }
     
     private final IContent content = new EAContent();
     
     
     
     
     @Override
     public IContent content ()
     {
          return content;
     }
}
