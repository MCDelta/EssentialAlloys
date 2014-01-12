package mcdelta.essentialalloys.block;

import java.util.Random;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.Position;
import mcdelta.essentialalloys.EAContent;
import mcdelta.essentialalloys.EssentialAlloys;
import mcdelta.essentialalloys.block.tileentity.TileHotPlate;
import mcdelta.essentialalloys.gui.GuiHandler;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHotPlate extends BlockEA implements ITileEntityProvider
{
     @SideOnly (Side.CLIENT)
     private Icon blockIconTop;
     @SideOnly (Side.CLIENT)
     private Icon blockIconBottom;
     
     
     
     
     public BlockHotPlate (final String s)
     {
          super(s, Material.iron);
          this.setHardness(3.0F);
          this.setResistance(8.0F);
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public Icon getIcon (int side, int meta)
     {
          return side == 1 ? blockIconTop : side == 0 ? blockIconBottom : blockIcon;
     }
     
     
     
     
     @SideOnly (Side.CLIENT)
     public void registerIcons (IconRegister register)
     {
          this.blockIcon = doRegister("hotPlate_side", register);
          this.blockIconTop = doRegister("hotPlate_top", register);
          this.blockIconBottom = Block.netherBrick.getIcon(0, 0);
     }
     
     
     
     
     public int[] getDimensions (final Position pos)
     {
          // { 2x2, 3x3 }
          final boolean[] dimensions = new boolean[]
          { false, false };
          
          // { y+1, y+2, y+3, y+4 }
          final boolean[] heights = new boolean[]
          { false, false, false, false };
          
          for (int radius = 1; radius < dimensions.length + 1; radius++)
          {
               boolean stop = false;
               
               if (!stop)
               {
                    for (int x = radius; x > -radius - 1; x--)
                    {
                         if (!stop)
                         {
                              for (int z = radius; z > -radius - 1; z--)
                              {
                                   final Position target = pos.move(x, 0, z);
                                   
                                   if (target.getBlock() != Block.netherBrick && target.getBlock() != Block.netherFence && target.getBlock() != Block.stairsNetherBrick && ((target.getBlock() != Block.stoneSingleSlab || target.getBlock() != Block.stoneDoubleSlab) && target.getMeta() != 6 && target.getMeta() != 14) && target.getBlock() != EAContent.hotPlate)
                                   {
                                        stop = true;
                                        break;
                                   }
                              }
                         }
                    }
               }
               
               if (!stop)
               {
                    dimensions[radius - 1] = true;
               }
          }
          final int radius = (dimensions[1] ? 2 : dimensions[0] ? 1 : 0) + 1;
          
          boolean stop = false;
          
          for (int y = 1; y < heights.length + 1; y++)
          {
               if (!stop)
               {
                    for (int x = radius; x > -radius - 1; x--)
                    {
                         if (!stop)
                         {
                              for (int z = radius; z > -radius - 1; z--)
                              {
                                   if (!(Math.abs(x) == radius && Math.abs(z) == radius))
                                   {
                                        final Position target = pos.move(x, y, z);
                                        
                                        if (Math.abs(x) == radius || Math.abs(z) == radius)
                                        {
                                             if (target.getBlock() != Block.netherBrick && target.getBlock() != Block.netherFence && target.getBlock() != Block.stairsNetherBrick && ((target.getBlock() != Block.stoneSingleSlab || target.getBlock() != Block.stoneDoubleSlab) && target.getMeta() != 6 && target.getMeta() != 14))
                                             {
                                                  stop = true;
                                                  break;
                                             }
                                        }
                                   }
                              }
                         }
                    }
               }
               if (!stop)
               {
                    heights[y - 1] = true;
               }
          }
          final int x = (dimensions[1] ? 2 : dimensions[0] ? 1 : 0) * 2 + 1;
          final int y = heights[3] ? 4 : heights[2] ? 3 : heights[1] ? 2 : heights[0] ? 1 : 0;
          final int z = x;
          
          return new int[]
          { x, y, z };
     }
     
     
     
     
     public int getFuel (final Position pos, final int[] dimensions)
     {
          int i = 0;
          
          for (int y = 1; y < dimensions[1] + 1; y++)
          {
               for (int x = dimensions[0]; x > -dimensions[0] - 1; x--)
               {
                    for (int z = dimensions[2]; z > -dimensions[2] - 1; z--)
                    {
                         if (!(Math.abs(x) == dimensions[0] && Math.abs(z) != dimensions[2]))
                         {
                              final Position target = pos.move(x, y, z);
                              
                              if (target.getBlock() == Block.lavaStill && target.getMeta() == 0)
                              {
                                   i += 1;
                              }
                         }
                    }
               }
          }
          return i;
     }
     
     
     
     
     public boolean removeRandomFuel (final Position pos, final int[] dimensions)
     {
          if (Assets.isServer())
          {
               final Random rand = new Random();
               final int fuel = getFuel(pos, dimensions);
               
               if (fuel != 0)
               {
                    for (int i = 0; i < 100; i++)
                    {
                         final int x = dimensions[0] == 1 ? 0 : Math.round((float) rand.nextInt(dimensions[0] - 1) - Math.round(rand.nextFloat()) * ((dimensions[0] - 1) / 2));
                         final int y = rand.nextInt(dimensions[1]) + 1;
                         final int z = dimensions[2] == 1 ? 0 : Math.round((float) rand.nextInt(dimensions[2] - 1) - Math.round(rand.nextFloat()) * ((dimensions[2] - 1) / 2));
                         
                         final Position target = pos.move(x, y, z);
                         
                         if (target.getBlock() == Block.lavaStill && target.getMeta() == 0)
                         {
                              Assets.setToAir(target);
                              return true;
                         }
                    }
               }
          }
          return false;
     }
     
     
     
     
     public boolean onBlockActivated (World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
     {
          ((TileHotPlate) world.getBlockTileEntity(x, y, z)).refreshDimensions();
          ((TileHotPlate) world.getBlockTileEntity(x, y, z)).blastFurnaceBB = ((TileHotPlate) world.getBlockTileEntity(x, y, z)).getFurnaceBB();
          
          if (Assets.isClient())
          {
               return true;
          }
          else
          {
               player.openGui(EssentialAlloys.instance, GuiHandler.hotPlateGuiID, world, x, y, z);
               
               return true;
          }
     }
     
     
     
     
     @Override
     public TileEntity createNewTileEntity (World world)
     {
          return new TileHotPlate();
     }
}
