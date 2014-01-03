package mcdelta.essentialalloys.world;

import java.util.Random;

import mcdelta.essentialalloys.block.BlockEA;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import cpw.mods.fml.common.IWorldGenerator;

public class GeneratorOres implements IWorldGenerator
{

    private final WorldGenerator copperGen;
    private final WorldGenerator tinGen;

    public GeneratorOres()
    {
        copperGen = new WorldGenMinable(BlockEA.oreCopper.blockID, 8);
        tinGen = new WorldGenMinable(BlockEA.oreTin.blockID, 8);
    }

    @Override
    public void generate(final Random rand, final int chunkX, final int chunkZ, final World world, final IChunkProvider generator, final IChunkProvider provider)
    {
        switch (world.provider.dimensionId)
        {
            case 0:
                generateSurface(world, rand, chunkX * 16, chunkZ * 16);
                break;
            default:
                break;
        }
    }

    private void generateSurface(final World world, final Random rand, final int chunkX, final int chunkZ)
    {
        genStandardOre(15, copperGen, 0, 64, world, rand, chunkX, chunkZ);
        genStandardOre(15, tinGen, 0, 64, world, rand, chunkX, chunkZ);
    }

    protected void genStandardOre(final int veinsPerChunk, final WorldGenerator worldGen, final int lowestLayer, final int highestLayer, final World world, final Random rand,
            final int chunkX, final int chunkZ)
    {
        for (int vein = 0; vein < veinsPerChunk; ++vein)
        {
            final int x = chunkX + rand.nextInt(16);
            final int y = rand.nextInt(highestLayer - lowestLayer) + lowestLayer;
            final int z = chunkZ + rand.nextInt(16);
            worldGen.generate(world, rand, x, y, z);
        }
    }
}
