package mcdelta.essentialalloys.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIngot extends ItemEA
{
    private final boolean magic;

    public ItemIngot(String name)
    {
        this(name, false);
    }

    public ItemIngot(String s, boolean magic)
    {
        super("ingot." + s);

        this.magic = magic;
        setCreativeTab(CreativeTabs.tabMaterials);

        if (magic)
        {
            maxStackSize = 1;
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return magic;
    }
}