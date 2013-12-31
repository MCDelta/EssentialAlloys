package mcdelta.essentialalloys.world;

import java.util.Random;

import mcdelta.essentialalloys.block.BlockEAOre;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;
import cpw.mods.fml.common.IWorldGenerator;

public class GeneratorOres implements IWorldGenerator
{
     
     private WorldGenerator copperGen;
     private WorldGenerator tinGen;
     
     
     
     
     public GeneratorOres ()
     {
          this.copperGen = new WorldGenMinable(BlockEAOre.oreCopper.blockID, 8);
          this.tinGen = new WorldGenMinable(BlockEAOre.oreTin.blockID, 8);
     }
     
     
     
     
     @Override
     public void generate (Random rand, int chunkX, int chunkZ, World world, IChunkProvider generator, IChunkProvider provider)
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
     
     
     
     
     private void generateSurface (World world, Random rand, int chunkX, int chunkZ)
     {
          genStandardOre(15, copperGen, 0, 64, world, rand, chunkX, chunkZ);
          genStandardOre(15, tinGen, 0, 64, world, rand, chunkX, chunkZ);
     }
     
     
     
     
     protected void genStandardOre (int veinsPerChunk, WorldGenerator worldGen, int lowestLayer, int highestLayer, World world, Random rand, int chunkX, int chunkZ)
     {
          for (int l = 0; l < veinsPerChunk; ++l)
          {
               int i1 = chunkX + rand.nextInt(16);
               int j1 = rand.nextInt(highestLayer - lowestLayer) + lowestLayer;
               int k1 = chunkZ + rand.nextInt(16);
               worldGen.generate(world, rand, i1, j1, k1);
          }
     }
}
