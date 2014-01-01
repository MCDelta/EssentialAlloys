package mcdelta.essentialalloys.block;

import java.util.List;

import mcdelta.core.EnumMCDMods;
import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.BlockData;
import mcdelta.core.assets.world.BlockShapes;
import mcdelta.core.assets.world.Position;
import mcdelta.core.block.BlockSided;
import mcdelta.essentialalloys.EssentialAlloysCore;
import mcdelta.essentialalloys.block.tileentity.TileEntityCrusher;
import mcdelta.essentialalloys.network.PacketCrusherExtend;
import mcdelta.essentialalloys.proxy.EAClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrusher extends BlockSided implements ITileEntityProvider
{
    @SideOnly(Side.CLIENT)
    public Icon studIcon;

    @SideOnly(Side.CLIENT)
    public Icon shaftIcon;

    @SideOnly(Side.CLIENT)
    protected Icon insideIcon;

    @SideOnly(Side.CLIENT)
    protected Icon sideIconOn;

    public BlockCrusher(String s)
    {
        super(EnumMCDMods.ESSENTIAL_ALLOYS, s, Material.piston);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister register)
    {
        super.registerIcons(register);

        sideIcon = doRegister(name + "_side_off", register);
        sideIconOn = doRegister(name + "_side_on", register);
        insideIcon = doRegister(name + "_inside", register);
        studIcon = Block.blockNetherQuartz.getIcon(0, 0);
        shaftIcon = Block.cobblestone.getIcon(2, 0);
        blockIcon = Block.brick.getIcon(0, 0);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta)
    {
        return getIcon(side, meta, true);
    }

    @SideOnly(Side.CLIENT)
    public Icon getIcon(int side, int meta, boolean crusher)
    {
        Icon icon = super.getIcon(side, meta);

        if (meta != 10)
        {
            EnumFacing face = Assets.getFacing(meta);

            boolean b1 = minX == BlockShapes.crusherNonExtended(face)[0];
            boolean b2 = minY == BlockShapes.crusherNonExtended(face)[1];
            boolean b3 = minZ == BlockShapes.crusherNonExtended(face)[2];

            boolean b4 = maxX == BlockShapes.crusherNonExtended(face)[3];
            boolean b5 = maxY == BlockShapes.crusherNonExtended(face)[4];
            boolean b6 = maxZ == BlockShapes.crusherNonExtended(face)[5];

            boolean b = !b1 || !b2 || !b3 || !b4 || !b5 || !b6;

            if ((icon == frontIcon) && !b)
            {
                icon = insideIcon;
            }
        }

        else if (side == 1)
        {
            return frontIcon;
        }

        else if (side == 0)
        {
            return blockIcon;
        }

        if (!crusher)
        {
            if (icon == insideIcon)
            {
                icon = frontIcon;
            }

            if (icon == blockIcon)
            {
                icon = frontIcon;
            }
        }

        return icon;
    }

    @Override
    public TileEntity createNewTileEntity(World world)
    {
        return new TileEntityCrusher();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        Position pos = new Position(world, x, y, z);

        EnumFacing face = Assets.getFacing(pos.getMeta());

        if ((((TileEntityCrusher) pos.getTile()) != null) && (BlockEA.crusher.isExtended(pos) || (((TileEntityCrusher) pos.getTile()).extend != 0)))
        {
            setBlockBounds(BlockShapes.crusherNonExtended(face));
        }

        else
        {
            setBlockBounds(BlockShapes.crusherExtended(face));
        }
    }

    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float offsetX, float offsetY, float offsetZ)
    {
        return false;
    }

    @Override
    public void onPostBlockPlaced(World world, int x, int y, int z, int metadata)
    {
        Position pos = new Position(world, x, y, z);
        TileEntityCrusher tile = (TileEntityCrusher) pos.getTile();

        tile.checkForPower = true;

        doThings(pos);
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, int neighborID)
    {
        Position pos = new Position(world, x, y, z);
        TileEntityCrusher tile = (TileEntityCrusher) pos.getTile();

        tile.checkForPower = true;

        doThings(pos);
    }

    public void doThings(Position pos)
    {
        Position target = pos.move(Assets.getFacing(pos.getMeta()));
        TileEntityCrusher tile = (TileEntityCrusher) pos.getTile();

        if ((tile.extend != 0) || (tile.cooldown != 0))
        {
            return;
        }

        boolean flag = false;

        if (!isExtended(pos) && Assets.isPoweredIndirectly(pos))
        {
            if (!(tile.power >= 100))
            {
                tile.checkForPower = true;

                return;
            }

            if (extend(pos))
            {
                tile.power -= 100;
                flag = true;

                tile.cooldown = 10;

                if (flag && Assets.isServer())
                {
                    PacketDispatcher.sendPacketToAllAround(pos.x, pos.y, pos.z, 20, ((World) pos.blockAccess).provider.dimensionId,
                            Assets.populatePacket(new PacketCrusherExtend(tile.extend, pos.x, pos.y, pos.z)));
                }

                return;
            }
        }

        else if (!Assets.isPoweredIndirectly(pos))
        {
            flag = retract(pos);

            tile.cooldown = 10;

            if (flag && Assets.isServer())
            {
                PacketDispatcher.sendPacketToAllAround(pos.x, pos.y, pos.z, 20, ((World) pos.blockAccess).provider.dimensionId,
                        Assets.populatePacket(new PacketCrusherExtend(tile.extend, pos.x, pos.y, pos.z)));
            }

            return;
        }
    }

    public boolean isExtended(Position pos)
    {
        if (pos.move(Assets.getFacing(pos.getMeta())).getBlockData() == null)
        {
            return false;
        }

        return pos.move(Assets.getFacing(pos.getMeta())).getBlockData().equals(new BlockData(BlockEA.crusherExt, pos.getMeta()))
                || pos.move(Assets.getFacing(pos.getMeta())).getBlockData().equals(new BlockData(BlockEA.crusherExtMoving, pos.getMeta()));
    }

    private boolean extend(Position pos)
    {
        Position target = pos.move(Assets.getFacing(pos.getMeta()));
        World world = (World) pos.blockAccess;

        if (!Assets.isAirBlock(target) && EssentialAlloysCore.crusherRecipes.containsKey(target.getBlockData().block))
        {
            EntityItem item = new EntityItem(world, target.x, target.y, target.z, EssentialAlloysCore.crusherRecipes.get(target.getBlockData().block).copy());

            if (Assets.isServer())
            {
                world.spawnEntityInWorld(item);
            }

            Assets.setToAir(target);
        }

        if (pos.move(Assets.getFacing(pos.getMeta())).getBlockData() != null)
        {
            return false;
        }

        ((TileEntityCrusher) pos.getTile()).extend = 3;

        Assets.placeBlock(pos.move(Assets.getFacing(pos.getMeta())), new BlockData(BlockEA.crusherExtMoving, pos.getMeta()));

        return true;
    }

    public void finishExtension(Position pos)
    {
        Position target = pos.move(Assets.getFacing(pos.getMeta()));
        World world = (World) target.blockAccess;

        Assets.placeBlock(pos.move(Assets.getFacing(pos.getMeta())), new BlockData(BlockEA.crusherExt, pos.getMeta()));
    }

    private boolean retract(Position pos)
    {
        if (isExtended(pos))
        {
            World world = (World) pos.blockAccess;

            ((TileEntityCrusher) pos.getTile()).extend = -8;

            Assets.placeBlock(pos.move(Assets.getFacing(pos.getMeta())), new BlockData(BlockEA.crusherExtMoving, pos.getMeta()));

            return true;
        }

        return false;
    }

    public void finishRetraction(Position pos)
    {
        Assets.setToAir(pos.move(Assets.getFacing(pos.getMeta())));
    }

    @Override
    public boolean isOpaqueCube()
    {
        return false;
    }

    @Override
    public int getRenderType()
    {
        return EAClientProxy.typeCrusher;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Icon getBlockTexture(IBlockAccess world, int x, int y, int z, int side)
    {
        Position pos = new Position(world, x, y, z);

        Icon icon = this.getIcon(side, world.getBlockMetadata(x, y, z));

        if ((icon == sideIcon) && (((TileEntityCrusher) pos.getTile()).power >= 98))
        {
            icon = sideIconOn;
        }

        return icon;
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity)
    {
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        setBlockBoundsBasedOnState(world, x, y, z);
        return super.getCollisionBoundingBoxFromPool(world, x, y, z);
    }

    @Override
    public boolean renderAsNormalBlock()
    {
        return false;
    }
}
