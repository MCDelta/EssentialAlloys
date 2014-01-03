package mcdelta.essentialalloys.proxy;

import mcdelta.essentialalloys.client.block.RenderCrusher;
import mcdelta.essentialalloys.client.block.RenderCrusherExtension;
import cpw.mods.fml.client.registry.RenderingRegistry;

public class EAClientProxy extends EACommonProxy
{
     public static int typeCrusherExtension;
     public static int typeCrusher;
     
     
     
     
     @Override
     public void registerRenderers ()
     {
          typeCrusherExtension = RenderingRegistry.getNextAvailableRenderId();
          RenderingRegistry.registerBlockHandler(new RenderCrusherExtension());
          
          typeCrusher = RenderingRegistry.getNextAvailableRenderId();
          RenderingRegistry.registerBlockHandler(new RenderCrusher());
     }
}
