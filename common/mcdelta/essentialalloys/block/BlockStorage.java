package mcdelta.essentialalloys.block;

import net.minecraft.block.material.Material;

public class BlockStorage extends BlockEA
{
     public BlockStorage (final String s)
     {
          super("storage." + s, Material.iron);
          setHardness(5.0F);
          setResistance(10.0F);
          setStepSound(soundMetalFootstep);
     }
}
