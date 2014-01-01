package mcdelta.essentialalloys.special.enchant;

import mcdelta.core.EnumMCDMods;
import mcdelta.core.special.enchant.EnchantmentDelta;
import mcdelta.essentialalloys.item.ItemEA;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.ItemStack;

public class EnchEnchanted extends EnchantmentDelta
{

    public EnchEnchanted(String s, int rarity, EnumEnchantmentType type)
    {
        super(EnumMCDMods.ESSENTIAL_ALLOYS, s, rarity, type);
    }

    @Override
    public int getMinEnchantability(int enchLevel)
    {
        return 30;
    }

    @Override
    public int getMaxEnchantability(int enchLevel)
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
    public boolean canApply(ItemStack stack)
    {
        return stack.getItem() == ItemEA.dustGold;
    }
}
