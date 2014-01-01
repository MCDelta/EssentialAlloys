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

    public ItemDust(String s)
    {
        this(s, false);
    }

    public ItemDust(String s, boolean b)
    {
        super("dust." + s);

        magic = b;
        setCreativeTab(CreativeTabs.tabMaterials);

        if (magic)
        {
            maxStackSize = 1;
        }
    }

    @Override
    public int getItemEnchantability()
    {
        return 1;
    }

    @Override
    public boolean isItemTool(ItemStack stack)
    {
        return stack.stackSize == 1;
    }

    @Override
    public void onUpdate(ItemStack stack, World world, Entity entity, int i, boolean b)
    {
        if ((this == dustGold) && stack.isItemEnchanted() && (entity instanceof EntityPlayer))
        {
            EntityPlayer player = (EntityPlayer) entity;

            int slot = Arrays.asList(player.inventory.mainInventory).lastIndexOf(stack);

            player.inventory.setInventorySlotContents(slot, new ItemStack(dustGold, stack.stackSize));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack par1ItemStack, int pass)
    {
        return magic;
    }
}
