package mcdelta.essentialalloys.enchant;

import mcdelta.core.enchant.EnchantmentDelta;
<<<<<<< HEAD:common/mcdelta/essentialalloys/enchant/EnchEnchanted.java
import mcdelta.essentialalloys.EAContent;
=======
>>>>>>> 5ad34493d08f0ba6f35302fdcfc63d2da091fe08:common/mcdelta/essentialalloys/special/enchant/EnchEnchanted.java
import mcdelta.essentialalloys.EssentialAlloys;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchEnchanted extends EnchantmentDelta
{
<<<<<<< HEAD:common/mcdelta/essentialalloys/enchant/EnchEnchanted.java
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
=======
    public EnchEnchanted(final String s, final int rarity, final EnumEnchantmentType type)
    {
        super(EssentialAlloys.instance, s, rarity, type);
    }

    @Override
    public int getMinEnchantability(final int enchLevel)
    {
        return 30;
    }

    @Override
    public int getMaxEnchantability(final int enchLevel)
    {
        return 100;
    }

    @Override
    public int getMinLevel()
    {
        return 0;
    }

    @Override
    public int getMaxLevel()
    {
        return 0;
    }

    @Override
    public boolean isAllowedOnBooks()
    {
        return false;
    }

    @Override
    public boolean canApply(final ItemStack stack)
    {
        return stack.getItem() == ItemEA.dustGold;
    }
>>>>>>> 5ad34493d08f0ba6f35302fdcfc63d2da091fe08:common/mcdelta/essentialalloys/special/enchant/EnchEnchanted.java
}
