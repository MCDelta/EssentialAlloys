package mcdelta.essentialalloys.item;

import java.util.Arrays;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import mcdelta.core.assets.Assets;
import mcdelta.core.item.ItemDelta;
import mcdelta.essentialalloys.EssentialAlloysCore;

public class ItemDust extends ItemEA
{
     private boolean        magic;
     
     
     
     
     public ItemDust (String s)
     {
          this(s, false);
     }
     
     
     
     
     public ItemDust (String s, boolean b)
     {
          super("dust." + s);
          
          this.magic = b;
          this.setCreativeTab(CreativeTabs.tabMaterials);
          
          if (magic)
          {
               this.maxStackSize = 1;
          }
     }
     
     
     
     
     public int getItemEnchantability ()
     {
          return 1;
     }
     
     
     
     
     public boolean isItemTool (ItemStack stack)
     {
          return stack.stackSize == 1;
     }
     
     
     
     
     public void onUpdate (ItemStack stack, World world, Entity entity, int i, boolean b)
     {
          if (this == dustGold && stack.isItemEnchanted() && entity instanceof EntityPlayer)
          {
               EntityPlayer player = (EntityPlayer) entity;
               
               int slot = Arrays.asList(player.inventory.mainInventory).lastIndexOf(stack);
               
               player.inventory.setInventorySlotContents(slot, new ItemStack(dustGold, stack.stackSize));
          }
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public boolean hasEffect (ItemStack par1ItemStack, int pass)
     {
          return magic;
     }
}
