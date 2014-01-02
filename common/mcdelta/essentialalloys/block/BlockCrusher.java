package mcdelta.essentialalloys.block;

import java.util.List;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.BlockData;
import mcdelta.core.assets.world.BlockShapes;
import mcdelta.core.assets.world.Position;
import mcdelta.core.block.BlockSided;
import mcdelta.essentialalloys.EssentialAlloys;
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
     @SideOnly (Side.CLIENT)
     public Icon    studIcon;
     
     @SideOnly (Side.CLIENT)
     public Icon    shaftIcon;
     
     @SideOnly (Side.CLIENT)
     protected Icon insideIcon;
     
     @SideOnly (Side.CLIENT)
     protected Icon sideIconOn;
     
     
     
     
     public BlockCrusher (final String s)
     {
          super(EssentialAlloys.instance, s, Material.piston);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public void registerIcons (final IconRegister register)
     {
          super.registerIcons(register);
          
          sideIcon = doRegister(name + "_side_off", register);
          this.sideIconOn = doRegister(name + "_side_on", register);
          this.insideIcon = doRegister(name + "_inside", register);
          this.studIcon = Block.blockNetherQuartz.getIcon(0, 0);
          this.shaftIcon = Block.cobblestone.getIcon(2, 0);
          blockIcon = Block.brick.getIcon(0, 0);
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public Icon getIcon (final int side, final int meta)
     {
          return this.getIcon(side, meta, true);
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public Icon getIcon (final int side, final int meta, final boolean crusher)
     {
          Icon icon = super.getIcon(side, meta);
          
          if (meta != 10)
          {
               final EnumFacing face = Assets.getFacing(meta);
               
               final boolean b1 = minX == BlockShapes.crusherNonExtended(face)[0];
               final boolean b2 = minY == BlockShapes.crusherNonExtended(face)[1];
               final boolean b3 = minZ == BlockShapes.crusherNonExtended(face)[2];
               
               final boolean b4 = maxX == BlockShapes.crusherNonExtended(face)[3];
               final boolean b5 = maxY == BlockShapes.crusherNonExtended(face)[4];
               final boolean b6 = maxZ == BlockShapes.crusherNonExtended(face)[5];
               
               final boolean b = !b1 || !b2 || !b3 || !b4 || !b5 || !b6;
               
               if (icon == frontIcon && !b)
               {
                    icon = this.insideIcon;
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
               if (icon == this.insideIcon)
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
     public TileEntity createNewTileEntity (final World world)
     {
          return new TileEntityCrusher();
     }
     
     
     
     
     @Override
     public void setBlockBoundsBasedOnState (final IBlockAccess world, final int x, final int y, final int z)
     {
          final Position pos = new Position(world, x, y, z);
          
          final EnumFacing face = Assets.getFacing(pos.getMeta());
          
          if ((TileEntityCrusher) pos.getTile() != null && (BlockEA.crusher.isExtended(pos) || ((TileEntityCrusher) pos.getTile()).extend != 0))
          {
               setBlockBounds(BlockShapes.crusherNonExtended(face));
          }
          else
          {
               setBlockBounds(BlockShapes.crusherExtended(face));
          }
     }
     
     
     
     
     @Override
     public boolean onBlockActivated (final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float offsetX, final float offsetY, final float offsetZ)
     {
          return false;
     }
     
     
     
     
     @Override
     public void onPostBlockPlaced (final World world, final int x, final int y, final int z, final int metadata)
     {
          final Position pos = new Position(world, x, y, z);
          final TileEntityCrusher tile = (TileEntityCrusher) pos.getTile();
          
          tile.checkForPower = true;
          
          this.doThings(pos);
     }
     
     
     
     
     @Override
     public void onNeighborBlockChange (final World world, final int x, final int y, final int z, final int neighborID)
     {
          final Position pos = new Position(world, x, y, z);
          final TileEntityCrusher tile = (TileEntityCrusher) pos.getTile();
          
          tile.checkForPower = true;
          
          this.doThings(pos);
     }
     
     
     
     
     public void doThings (final Position pos)
     {
          final TileEntityCrusher tile = (TileEntityCrusher) pos.getTile();
          
          if (tile.extend != 0 || tile.cooldown != 0)
          {
               return;
          }
          boolean flag = false;
          
          if (!this.isExtended(pos) && Assets.isPoweredIndirectly(pos))
          {
               if (!(tile.power >= 100))
               {
                    tile.checkForPower = true;
                    
                    return;
               }
               if (this.extend(pos))
               {
                    tile.power -= 100;
                    flag = true;
                    
                    tile.cooldown = 10;
                    
                    if (flag && Assets.isServer())
                    {
                         PacketDispatcher.sendPacketToAllAround(pos.x, pos.y, pos.z, 20, ((World) pos.world).provider.dimensionId, Assets.populatePacket(new PacketCrusherExtend(tile.extend, pos.x, pos.y, pos.z)));
                    }
                    return;
               }
          }
          else if (!Assets.isPoweredIndirectly(pos))
          {
               flag = this.retract(pos);
               
               tile.cooldown = 10;
               
               if (flag && Assets.isServer())
               {
                    PacketDispatcher.sendPacketToAllAround(pos.x, pos.y, pos.z, 20, ((World) pos.world).provider.dimensionId, Assets.populatePacket(new PacketCrusherExtend(tile.extend, pos.x, pos.y, pos.z)));
               }
               return;
          }
     }
     
     
     
     
     public boolean isExtended (final Position pos)
     {
          if (pos.move(Assets.getFacing(pos.getMeta())).getBlockData() == null)
          {
               return false;
          }
          return pos.move(Assets.getFacing(pos.getMeta())).getBlockData().equals(new BlockData(BlockEA.crusherExt, pos.getMeta())) || pos.move(Assets.getFacing(pos.getMeta())).getBlockData().equals(new BlockData(BlockEA.crusherExtMoving, pos.getMeta()));
     }
     
     
     
     
     private boolean extend (final Position pos)
     {
          final Position target = pos.move(Assets.getFacing(pos.getMeta()));
          final World world = (World) pos.world;
          
          if (!Assets.isAirBlock(target) && EssentialAlloys.crusherRecipes.containsKey(target.getBlockData().block))
          {
               final EntityItem item = new EntityItem(world, target.x, target.y, target.z, EssentialAlloys.crusherRecipes.get(target.getBlockData().block).copy());
               
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
     
     
     
     
     public void finishExtension (final Position pos)
     {
          Assets.placeBlock(pos.move(Assets.getFacing(pos.getMeta())), new BlockData(BlockEA.crusherExt, pos.getMeta()));
     }
     
     
     
     
     private boolean retract (final Position pos)
     {
          if (this.isExtended(pos))
          {
               ((TileEntityCrusher) pos.getTile()).extend = -8;
               
               Assets.placeBlock(pos.move(Assets.getFacing(pos.getMeta())), new BlockData(BlockEA.crusherExtMoving, pos.getMeta()));
               
               return true;
          }
          return false;
     }
     
     
     
     
     public void finishRetraction (final Position pos)
     {
          Assets.setToAir(pos.move(Assets.getFacing(pos.getMeta())));
     }
     
     
     
     
     @Override
     public boolean isOpaqueCube ()
     {
          return false;
     }
     
     
     
     
     @Override
     public int getRenderType ()
     {
          return EAClientProxy.typeCrusher;
     }
     
     
     
     
     @Override
     @SideOnly (Side.CLIENT)
     public Icon getBlockTexture (final IBlockAccess world, final int x, final int y, final int z, final int side)
     {
          final Position pos = new Position(world, x, y, z);
          
          Icon icon = this.getIcon(side, world.getBlockMetadata(x, y, z));
          
          if (icon == sideIcon && ((TileEntityCrusher) pos.getTile()).power >= 98)
          {
               icon = this.sideIconOn;
          }
          return icon;
     }
     
     
     
     
     @Override
     public void addCollisionBoxesToList (final World world, final int x, final int y, final int z, final AxisAlignedBB axis, final List list, final Entity entity)
     {
          this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
          super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
     }
     
     
     
     
     @Override
     public AxisAlignedBB getCollisionBoundingBoxFromPool (final World world, final int x, final int y, final int z)
     {
          this.setBlockBoundsBasedOnState(world, x, y, z);
          return super.getCollisionBoundingBoxFromPool(world, x, y, z);
     }
     
     
     
     
     @Override
     public boolean renderAsNormalBlock ()
     {
          return false;
     }
}
