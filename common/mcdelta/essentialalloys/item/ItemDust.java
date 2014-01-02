package mcdelta.essentialalloys.item;

import java.util.Arrays;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemDust extends ItemEA
{
     private final boolean magic;
     
     
     
     
     public ItemDust (final String name)
     {
          this(name, false);
     }
     
     
     
     
     public ItemDust (final String name, final boolean magic)
     {
          super("dust." + name);
          
          this.magic = magic;
          setCreativeTab(CreativeTabs.tabMaterials);
          
          if (magic)
          {
               maxStackSize = 1;
          }
     }
     
     
     
     
     @Override
     public int getItemEnchantability ()
     {
          return 1;
     }
     
     
     
     
     @Override
     public boolean isItemTool (final ItemStack stack)
     {
          return stack.stackSize == 1;
     }
     
     
     
     
     @Override
     public void onUpdate (final ItemStack stack, final World world, final Entity entity, final int i, final boolean b)
     {
          if (this == dustGold && stack.isItemEnchanted() && entity instanceof EntityPlayer)
          {
               final EntityPlayer player = (EntityPlayer) entity;
               
               final int slot = Arrays.asList(player.inventory.mainInventory).lastIndexOf(stack);
               
               player.inventory.setInventorySlotContents(slot, new ItemStack(dustGold, stack.stackSize));
          }
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public boolean hasEffect (final ItemStack par1ItemStack, final int pass)
     {
          return this.magic;
     }
}
