package mcdelta.essentialalloys.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIngot extends ItemEA
{
     private final boolean magic;
     
     
     
     
     public ItemIngot (final String name)
     {
          this(name, false);
     }
     
     
     
     
     public ItemIngot (final String s, final boolean magic)
     {
          super("ingot." + s);
          
          this.magic = magic;
          this.setCreativeTab(CreativeTabs.tabMaterials);
          
          if (magic)
          {
               this.maxStackSize = 1;
          }
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public boolean hasEffect (final ItemStack par1ItemStack, final int pass)
     {
          return this.magic;
     }
}
