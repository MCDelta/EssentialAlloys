package mcdelta.essentialalloys.block;

import java.util.List;

import mcdelta.core.EnumMCDMods;
import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.BlockData;
import mcdelta.core.assets.world.BlockShapes;
import mcdelta.core.assets.world.Position;
import mcdelta.core.block.BlockSided;
import mcdelta.essentialalloys.EssentialAlloysCore;
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
     
     
     
     
     public BlockCrusherExt (String s)
     {
          super(EnumMCDMods.ESSENTIAL_ALLOYS, s, Material.piston);
          this.setCreativeTab(null);
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void registerIcons (IconRegister register)
     {
          this.extensionIcon = doRegister("crusherExt", register);
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public Icon getIcon (int side, int meta)
     {
          return BlockEA.crusher.getIcon(side, meta, false);
     }
     
     
     
     
     @Override
     public void onNeighborBlockChange (World world, int x, int y, int z, int par5)
     {
          Position pos = new Position(world, x, y, z);
          
          if (!this.canBlockStay(world, x, y, z))
          {
               Assets.setToAir(pos);
          }
     }
     
     
     
     
     public boolean canBlockStay (World world, int x, int y, int z)
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
     
     
     
     
     public boolean canPlaceBlockAt (World world, int x, int y, int z)
     {
          return this.canBlockStay(world, x, y, z);
     }
     
     
     
     
     public void onBlockHarvested (World world, int x, int y, int z, int meta, EntityPlayer player)
     {
          super.onBlockHarvested(world, x, y, z, meta, player);
          
          Position pos = new Position(world, x, y, z);
          Position source = pos.move(Assets.invertFace(Assets.getFacing(pos.getMeta())));
          
          Assets.breakBlock(source, !player.capabilities.isCreativeMode);
     }
     
     
     
     
     public boolean isOpaqueCube ()
     {
          return false;
     }
     
     
     
     
     public int getRenderType ()
     {
          return EAClientProxy.typeCrusherExtension;
     }
     
     
     
     
     public void setBlockBoundsBasedOnState (IBlockAccess world, int x, int y, int z)
     {
          Position pos = new Position(world, x, y, z);
          EnumFacing face = Assets.getFacing(pos.getMeta());
          
          setBlockBounds(BlockShapes.crusherExtension(face, 0));
     }
     
     
     
     
     public void addCollisionBoxesToList (World world, int x, int y, int z, AxisAlignedBB axis, List list, Entity entity)
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
          
          this.setBlockBoundsBasedOnState(world, x, y, z);
          super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
          
          setBlockBounds(BlockShapes.extensionShaft(face, shift, 0));
          super.addCollisionBoxesToList(world, x, y, z, axis, list, entity);
     }
     
     
     
     
     public boolean renderAsNormalBlock ()
     {
          return false;
     }
     
     
     
     
     public int idPicked (World world, int x, int y, int z)
     {
          return BlockEA.crusher.blockID;
     }
}
