package mcdelta.essentialalloys.block;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.BlockData;
import mcdelta.core.assets.world.BlockShapes;
import mcdelta.core.assets.world.Position;
import mcdelta.core.block.BlockSided;
import mcdelta.essentialalloys.EssentialAlloys;
import mcdelta.essentialalloys.proxy.EAClientProxy;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrusherExt extends BlockSided
{
    @SideOnly(Side.CLIENT)
    public Icon extensionIcon;

    public BlockCrusherExt(String s)
    {
        super(EssentialAlloys.instance, s, Material.piston);
        setCreativeTab(null);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {
        extensionIcon = doRegister("crusherExt", register);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        return BlockEA.crusher.getIcon(side, meta, false);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int par5)
    {
        Position pos = new Position(world, x, y, z);

        if (!canBlockStay(world, x, y, z))
        {
            Assets.setToAir(pos);
        }
    }

    @Override
    public boolean canBlockStay(World world, int x, int y, int z)
    {
        Position pos = new Position(world, x, y, z);

        if (pos.copy().move(Assets.invertFace(getFacing(pos.getMeta()))).getBlockData() == null)
        {
            return false;
        }

        if (pos.copy().move(Assets.invertFace(getFacing(pos.getMeta()))).getBlockData().equals(new BlockData(BlockEA.crusher, pos.getMeta())))
        {
            return true;
        }

        return false;
    }

    @Override
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return canBlockStay(world, x, y, z);
    }

    @Override
    public void onBlockHarvested(World world, int x, int y, int z, int meta, EntityPlayer player)
    {
        super.onBlockHarvested(world, x, y, z, meta, player);

        Position pos = new Position(world, x, y, z);
        Position source = pos.move(Assets.invertFace(Assets.getFacing(pos.getMeta())));

        Assets.breakBlock(source, !player.capabilities.isCreativeMode);
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return EAClientProxy.typeCrusherExtension;
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        Position pos = new Position(world, x, y, z);
        EnumFacing face = Assets.getFacing(pos.getMeta());

        setBlockBounds(BlockShapes.crusherExtension(face, 0));
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity)
    {
        Position pos = new Position(world, x, y, z);
        EnumFacing face = Assets.getFacing(pos.getMeta());

        float shift = 0.438F;

        switch (face)
        {
            case UP:
            case SOUTH:
            case WEST:
                shift *= -1;
                break;
        }

        setBlockBoundsBasedOnState(world, x, y, z);
        super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);

        setBlockBounds(BlockShapes.extensionShaft(face, shift, 0));
        super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }

    @Override
    public int idPicked(World world, int x, int y, int z)
    {
        return BlockEA.crusher.blockID;
    }
}
