package mcdelta.essentialalloys.enchant;

import mcdelta.core.enchant.EnchantmentDelta;
import mcdelta.essentialalloys.EAContent;
import mcdelta.essentialalloys.EssentialAlloys;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchEnchanted extends EnchantmentDelta
{
     public EnchEnchanted (final String s, final int rarity, final EnumEnchantmentType type)
     {
          super(EssentialAlloys.instance, s, rarity, type);
     }
     
     
     
     
     @Override
     public int getMinEnchantability (final int enchLevel)
     {
          return 30;
     }
     
     
     
     
     @Override
     public int getMaxEnchantability (final int enchLevel)
     {
          return 100;
     }
     
     
     
     
     @Override
     public int getMinLevel ()
     {
          return 0;
     }
     
     
     
     
     @Override
     public int getMaxLevel ()
     {
          return 0;
     }
     
     
     
     
     @Override
     public boolean isAllowedOnBooks ()
     {
          return false;
     }
     
     
     
     
     @Override
     public boolean canApply (final ItemStack stack)
     {
          return stack.getItem() == EAContent.dustGold;
     }
}
