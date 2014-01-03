package mcdelta.essentialalloys.block;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.BlockData;
import mcdelta.core.assets.world.BlockShapes;
import mcdelta.core.assets.world.Position;
import mcdelta.core.block.BlockSided;
import mcdelta.essentialalloys.EAContent;
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
     @SideOnly (Side.CLIENT)
     public Icon extensionIcon;
     
     
     
     
     public BlockCrusherExt (final String s)
     {
          super(EssentialAlloys.instance, s, Material.piston);
          this.setCreativeTab(null);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void registerIcons (final IconRegister register)
     {
          this.extensionIcon = this.doRegister("crusherExt", register);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public Icon getIcon (final int side, final int meta)
     {
          return EAContent.crusher.getIcon(side, meta, false);
     }
     
     
     
     
     @Override
     public void onNeighborBlockChange (final World world, final int x, final int y, final int z, final int par5)
     {
          final Position pos = new Position(world, x, y, z);
          
          if (!this.canBlockStay(world, x, y, z))
          {
               Assets.setToAir(pos);
          }
     }
     
     
     
     
     @Override
     public boolean canBlockStay (final World world, final int x, final int y, final int z)
     {
          final Position pos = new Position(world, x, y, z);
          
          if (pos.copy().move(Assets.invertFace(getFacing(pos.getMeta()))).getBlockData() == null)
          {
               return false;
          }
          if (pos.copy().move(Assets.invertFace(getFacing(pos.getMeta()))).getBlockData().equals(new BlockData(EAContent.crusher, pos.getMeta())))
          {
               return true;
          }
          return false;
     }
     
     
     
     
     @Override
     public boolean canPlaceBlockAt (final World world, final int x, final int y, final int z)
     {
          return this.canBlockStay(world, x, y, z);
     }
     
     
     
     
     @Override
     public void onBlockHarvested (final World world, final int x, final int y, final int z, final int meta, final EntityPlayer player)
     {
          super.onBlockHarvested(world, x, y, z, meta, player);
          
          final Position pos = new Position(world, x, y, z);
          final Position source = pos.move(Assets.invertFace(Assets.getFacing(pos.getMeta())));
          
          Assets.breakBlock(source, !player.capabilities.isCreativeMode);
     }
     
     
     
     
     @Override
     public boolean isOpaqueCube ()
     {
          return false;
     }
     
     
     
     
     @Override
     public int getRenderType ()
     {
          return EAClientProxy.typeCrusherExtension;
     }
     
     
     
     
     @Override
     public void setBlockBoundsBasedOnState (final IBlockAccess world, final int x, final int y, final int z)
     {
          final Position pos = new Position(world, x, y, z);
          final EnumFacing face = Assets.getFacing(pos.getMeta());
          
          this.setBlockBounds(BlockShapes.crusherExtension(face, 0));
     }
     
     
     
     
     @Override
     public void addCollisionBoxesToList (final World world, final int x, final int y, final int z, final AxisAlignedBB axis, final List list, final Entity entity)
     {
          final Position pos = new Position(world, x, y, z);
          final EnumFacing face = Assets.getFacing(pos.getMeta());
          
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
          
          this.setBlockBounds(BlockShapes.extensionShaft(face, shift, 0));
          super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
     }
     
     
     
     
     @Override
     public boolean renderAsNormalBlock ()
     {
          return false;
     }
     
     
     
     
     @Override
     public int idPicked (final World world, final int x, final int y, final int z)
     {
          return EAContent.crusher.blockID;
     }
}
