package mcdelta.essentialalloys.block;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.BlockShapes;
import mcdelta.core.assets.world.Position;
import mcdelta.essentialalloys.block.tileentity.TileEntityCrusher;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrusherExtMoving extends BlockCrusherExt
{
    public BlockCrusherExtMoving(String s)
    {
        super(s);

        setBlockUnbreakable();
    }

    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z)
    {
        Position pos = new Position(world, x, y, z);
        EnumFacing face = Assets.getFacing(pos.getMeta());

        TileEntityCrusher source = (TileEntityCrusher) pos.move(Assets.invertFace(Assets.getFacing(pos.getMeta()))).getTile();

        int extend = source.extend;
        int extendTotal = source.extendTotal;

        float f = ((((float) Math.abs(extend)) / ((float) Math.abs(extendTotal))) * (extendTotal <= 0 ? 1 : -1)) + (extendTotal <= 0 ? -1 : 0);

        setBlockBounds(BlockShapes.crusherExtension(face, f));
    }

    @Override
    public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity)
    {
        Position pos = new Position(world, x, y, z);
        EnumFacing face = Assets.getFacing(pos.getMeta());

        TileEntityCrusher source = (TileEntityCrusher) pos.move(Assets.invertFace(Assets.getFacing(pos.getMeta()))).getTile();

        if (source != null)
        {
            int extend = source.extend;
            int extendTotal = source.extendTotal;

            float f = ((((float) Math.abs(extend)) / ((float) Math.abs(extendTotal))) * (extendTotal <= 0 ? 1 : -1)) + (extendTotal <= 0 ? -1 : 0);

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

            setBlockBounds(BlockShapes.extensionShaft(face, shift, f));
            super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
        }
    }
}
