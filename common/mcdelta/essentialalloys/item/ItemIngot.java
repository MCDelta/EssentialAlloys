package mcdelta.essentialalloys.item;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemIngot extends ItemEA
{
    private final boolean magic;

    public ItemIngot(String s)
    {
        this(s, false);
    }

    public ItemIngot(String s, boolean b)
    {
        super("ingot." + s);

        magic = b;
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
