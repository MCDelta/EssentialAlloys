package mcdelta.essentialalloys.block;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.world.Position;
import mcdelta.core.logging.Logger;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockHotPlate extends BlockEA
{
    public static List<Block> bottom = new ArrayList<Block>();
    public static List<Block> filler = new ArrayList<Block>();

    public BlockHotPlate(String s)
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

    public int[] getDimensions(Position pos)
    {
        // { 2x2, 3x3 }
        boolean[] dimensions = new boolean[]
        { false, false };

        // { y+1, y+2, y+3, y+4 }
        boolean[] heights = new boolean[]
        { false, false, false, false };

        for (int radius = 1; radius < (dimensions.length + 1); radius++)
        {
            boolean stop = false;

            if (!stop)
            {
                for (int x = radius; x > (-radius - 1); x--)
                {
                    if (!stop)
                    {
                        for (int z = radius; z > (-radius - 1); z--)
                        {
                            Position target = pos.move(x, 0, z);

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
        int radius = (dimensions[1] ? 2 : dimensions[0] ? 1 : 0) + 1;

        boolean stop = false;

        for (int y = 1; y < (heights.length + 1); y++)
        {
            if (!stop)
            {
                for (int x = radius; x > (-radius - 1); x--)
                {
                    if (!stop)
                    {
                        for (int z = radius; z > (-radius - 1); z--)
                        {
                            if (!((Math.abs(x) == radius) && (Math.abs(z) == radius)))
                            {
                                Position target = pos.move(x, y, z);

                                if (((Math.abs(x) == radius) || (Math.abs(z) == radius)))
                                {
                                    if ((target.getBlock() != Block.netherBrick)
                                            && (target.getBlock() != Block.netherFence)
                                            && (target.getBlock() != Block.stairsNetherBrick)
                                            && (((target.getBlock() != Block.stoneSingleSlab) || (target.getBlock() != Block.stoneDoubleSlab)) && ((target.getMeta() != 6) && (target
                                                    .getMeta() != 14))))
                                    {
                                        stop = true;
                                        break;
                                    }
                                } else
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
        int x = ((dimensions[1] ? 2 : dimensions[0] ? 1 : 0) * 2) + 1;
        int y = heights[3] ? 4 : heights[2] ? 3 : heights[1] ? 2 : heights[0] ? 1 : 0;
        int z = x;

        return new int[]
        { x, y, z };
    }

    public int getFuel(Position pos, int[] dimensions)
    {
        int i = 0;

        for (int y = 1; y < (dimensions[1] + 1); y++)
        {
            for (int x = dimensions[0]; x > (-dimensions[0] - 1); x--)
            {
                for (int z = dimensions[2]; z > (-dimensions[2] - 1); z--)
                {
                    if (!((Math.abs(x) == dimensions[0]) && (Math.abs(z) != dimensions[2])))
                    {
                        Position target = pos.move(x, y, z);

                        if ((target.getBlock() == Block.lavaStill) && (target.getMeta() == 0))
                        {
                            i += 1;
                        }
                    }
                }
            }
        }
        return i;
    }

    public boolean removeRandomFuel(Position pos, int[] dimensions)
    {
        if (Assets.isServer())
        {
            Random rand = new Random();
            int fuel = getFuel(pos, dimensions);

            if (fuel != 0)
            {
                for (int i = 0; i < 100; i++)
                {
                    int x = dimensions[0] == 1 ? 0 : Math.round((float) rand.nextInt(dimensions[0] - 1) - ((Math.round(rand.nextFloat())) * ((dimensions[0] - 1) / 2)));
                    int y = rand.nextInt(dimensions[1]) + 1;
                    int z = dimensions[2] == 1 ? 0 : Math.round((float) rand.nextInt(dimensions[2] - 1) - ((Math.round(rand.nextFloat())) * ((dimensions[2] - 1) / 2)));

                    Position target = pos.move(x, y, z);

                    if ((target.getBlock() == Block.lavaStill) && (target.getMeta() == 0))
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
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float offsetX, float offsetY, float offsetZ)
    {
        Position pos = new Position(world, x, y, z);
        int[] dimensions = getDimensions(pos);
        int fuel = getFuel(pos, dimensions);
        boolean removed = removeRandomFuel(pos, dimensions);

        if (Assets.isServer())
        {
            Logger.log("area: " + (dimensions[0] * dimensions[1] * dimensions[2]), dimensions[0] + "x" + dimensions[1] + "x" + dimensions[2]);
            Logger.log("fuel: " + fuel + ", removed: " + removed);
            Logger.blank();
        }
        return true;
    }
}