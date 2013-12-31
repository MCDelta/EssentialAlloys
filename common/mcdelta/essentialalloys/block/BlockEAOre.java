package mcdelta.essentialalloys.block;

import net.minecraft.block.material.Material;
import net.minecraft.world.gen.feature.WorldGenMinable;

public class BlockEAOre extends BlockEA
{
     public BlockEAOre (String s)
     {
          super("ore." + s, Material.rock);
          this.setHardness(3.0F);
          this.setResistance(5.0F);
          this.setStepSound(soundStoneFootstep);
     }
}
