package mcdelta.essentialalloys.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.Position;
import mcdelta.core.logging.Logger;
import mcdelta.essentialalloys.block.tileentity.TileHotPlate;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockHotPlate extends BlockEA
{
     public static List<Block> bottom = new ArrayList<Block>();
     public static List<Block> filler = new ArrayList<Block>();
     
     
     
     
     public BlockHotPlate (final String s)
     {
          super(s, Material.iron);
          
          if (bottom.isEmpty())
          {
               bottom.clear();
               bottom.add(Block.bedrock);
               bottom.add(this);
               
               filler.clear();
               filler.add(Block.lavaMoving);
               filler.add(Block.lavaStill);
          }
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
                                   
                                   if (!bottom.contains(target.getBlock()))
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
                                             if (target.getBlock() != Block.netherBrick && target.getBlock() != Block.netherFence && target.getBlock() != Block.stairsNetherBrick && (target.getBlock() != Block.stoneSingleSlab || target.getBlock() != Block.stoneDoubleSlab) && target.getMeta() != 6 && target.getMeta() != 14)
                                             {
                                                  stop = true;
                                                  break;
                                             }
                                        }
                                        else
                                        {
                                             if (!Assets.isAirBlock(target) && !filler.contains(target.getBlock()))
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
               final int fuel = this.getFuel(pos, dimensions);
               
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
     
     
     
     
     @Override
     public boolean onBlockActivated (final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float offsetX, final float offsetY, final float offsetZ)
     {
          final Position pos = new Position(world, x, y, z);
          final int[] dimensions = this.getDimensions(pos);
          final int fuel = this.getFuel(pos, dimensions);
          final boolean removed = this.removeRandomFuel(pos, dimensions);
          
          if (Assets.isServer())
          {
               Logger.log("area: " + dimensions[0] * dimensions[1] * dimensions[2], dimensions[0] + "x" + dimensions[1] + "x" + dimensions[2]);
               Logger.log("fuel: " + fuel + ", removed: " + removed);
               Logger.blank();
          }
          return true;
     }
     
     
     
     
     /**@Override
     public TileEntity createNewTileEntity (World world)
     {
          return new TileHotPlate();
     }*/
}
