package mcdelta.essentialalloys.client.gui;

import mcdelta.essentialalloys.block.tileentity.TileHotPlate;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotFurnace;
import net.minecraft.item.ItemStack;

public class ContainerHotPlate extends Container
{
     private TileHotPlate hotPlate;
     
     
     
     
     public ContainerHotPlate (InventoryPlayer inventory, TileHotPlate tile)
     {
          hotPlate = tile;
          this.addSlotToContainer(new SlotHPInput(tile, 0, 34, 13));
          this.addSlotToContainer(new SlotHPInput(tile, 1, 57, 13));
          this.addSlotToContainer(new SlotHPInput(tile, 2, 80, 13));
          this.addSlotToContainer(new SlotHPInput(tile, 3, 103, 13));
          this.addSlotToContainer(new SlotHPInput(tile, 4, 126, 13));
          
          this.addSlotToContainer(new SlotFurnace(inventory.player, tile, 5, 34, 57));
          this.addSlotToContainer(new SlotFurnace(inventory.player, tile, 6, 57, 57));
          this.addSlotToContainer(new SlotFurnace(inventory.player, tile, 7, 80, 57));
          this.addSlotToContainer(new SlotFurnace(inventory.player, tile, 8, 103, 57));
          this.addSlotToContainer(new SlotFurnace(inventory.player, tile, 9, 126, 57));
          int i;
          
          for (i = 0; i < 3; ++i)
          {
               for (int j = 0; j < 9; ++j)
               {
                    this.addSlotToContainer(new Slot(inventory, j + i * 9 + 9, 8 + j * 18, 90 + i * 18));
               }
          }
          
          for (i = 0; i < 9; ++i)
          {
               this.addSlotToContainer(new Slot(inventory, i, 8 + i * 18, 148));
          }
     }
     
     
     
     
     public ItemStack transferStackInSlot (EntityPlayer player, int i)
     {
          return null;
     }
     
     
     
     
     @Override
     public boolean canInteractWith (EntityPlayer player)
     {
          return hotPlate.isUseableByPlayer(player);
     }
}
