package mcdelta.essentialalloys.block;

import net.minecraft.block.material.Material;

public class BlockEAOre extends BlockEA
{
     public BlockEAOre (final String s)
     {
          super("ore." + s, Material.rock);
          this.setHardness(3.0F);
          this.setResistance(5.0F);
          this.setStepSound(soundStoneFootstep);
     }
}
