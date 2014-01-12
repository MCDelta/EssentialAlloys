package mcdelta.essentialalloys.gui;

import mcdelta.essentialalloys.block.tileentity.TileHotPlate;
import mcdelta.essentialalloys.client.gui.ContainerHotPlate;
import mcdelta.essentialalloys.client.gui.GuiHotPlate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler
{
     public static final int hotPlateGuiID = 0;
     
     
     
     
     public void registerRenderers ()
     {
     }
     
     
     
     
     @Override
     public Object getServerGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
     {
          switch (ID)
          {
               case hotPlateGuiID:
                    return new ContainerHotPlate(player.inventory, (TileHotPlate) world.getBlockTileEntity(x, y, z));
               default:
                    return null;
          }
     }
     
     
     
     
     @Override
     public Object getClientGuiElement (int ID, EntityPlayer player, World world, int x, int y, int z)
     {
          switch (ID)
          {
               case hotPlateGuiID:
                    return new GuiHotPlate(player.inventory, (TileHotPlate) world.getBlockTileEntity(x, y, z));
               default:
                    return null;
          }
     }
}
