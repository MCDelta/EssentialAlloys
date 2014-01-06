package mcdelta.essentialalloys.block;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.BlockShapes;
import mcdelta.core.assets.world.Position;
import mcdelta.essentialalloys.block.tileentity.TileCrusher;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockCrusherExtMoving extends BlockCrusherExt
{
     public BlockCrusherExtMoving (final String s)
     {
          super(s);
          
          this.setBlockUnbreakable();
     }
     
     
     
     
     @Override
     public void setBlockBoundsBasedOnState (final IBlockAccess world, final int x, final int y, final int z)
     {
          final Position pos = new Position(world, x, y, z);
          final EnumFacing face = Assets.getFacing(pos.getMeta());
          
          final TileCrusher source = (TileCrusher) pos.move(Assets.invertFace(Assets.getFacing(pos.getMeta()))).getTile();
          
          final int extend = source.extend;
          final int extendTotal = source.extendTotal;
          
          final float f = (float) Math.abs(extend) / (float) Math.abs(extendTotal) * (extendTotal <= 0 ? 1 : -1) + (extendTotal <= 0 ? -1 : 0);
          
          this.setBlockBounds(BlockShapes.crusherExtension(face, f));
     }
     
     
     
     
     @Override
     public void addCollisionBoxesToList (final World world, final int x, final int y, final int z, final AxisAlignedBB axis, final List list, final Entity entity)
     {
          final Position pos = new Position(world, x, y, z);
          final EnumFacing face = Assets.getFacing(pos.getMeta());
          
          final TileCrusher source = (TileCrusher) pos.move(Assets.invertFace(Assets.getFacing(pos.getMeta()))).getTile();
          
          if (source != null)
          {
               final int extend = source.extend;
               final int extendTotal = source.extendTotal;
               
               final float f = (float) Math.abs(extend) / (float) Math.abs(extendTotal) * (extendTotal <= 0 ? 1 : -1) + (extendTotal <= 0 ? -1 : 0);
               
               float shift = 0.438F;
               
               switch (face)
               {
                    case UP:
                    case SOUTH:
                    case WEST:
                         shift *= -1;
                         break;
                    case DOWN:
                         break;
                    case EAST:
                         break;
                    case NORTH:
                         break;
                    default:
                         break;
               }
               this.setBlockBoundsBasedOnState(world, x, y, z);
               super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
               
               this.setBlockBounds(BlockShapes.extensionShaft(face, shift, f));
               super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
          }
     }
}
