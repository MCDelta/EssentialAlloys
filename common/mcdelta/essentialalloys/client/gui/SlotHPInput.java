package mcdelta.essentialalloys.client.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotHPInput extends Slot
{
     public SlotHPInput (IInventory inv, int slot, int x, int y)
     {
          super(inv, slot, x, y);
     }
     
     
     
     
     public int getSlotStackLimit ()
     {
          return 1;
     }
     
     
     
     
     public boolean canTakeStack (EntityPlayer player)
     {
          return false;
     }
     
     
     
     
     public boolean isItemValid (ItemStack stack)
     {
          return false;
     }
}
