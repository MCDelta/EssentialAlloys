package mcdelta.essentialalloys.item;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ItemIngot extends ItemEA
{ 
     private boolean         magic;
     
     
     
     
     public ItemIngot (String s)
     {
          this(s, false);
     }
     
     
     
     
     public ItemIngot (String s, boolean b)
     {
          super("ingot." + s);
          
          this.magic = b;
          this.setCreativeTab(CreativeTabs.tabMaterials);
          
          if (magic)
          {
               this.maxStackSize = 1;
          }
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public boolean hasEffect (ItemStack par1ItemStack, int pass)
     {
          return magic;
     }
}
