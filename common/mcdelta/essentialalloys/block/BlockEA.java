package mcdelta.essentialalloys.block;

import mcdelta.core.block.BlockDelta;
import mcdelta.essentialalloys.EssentialAlloys;
import net.minecraft.block.material.Material;

public class BlockEA extends BlockDelta
{
     public BlockEA (final String s, final Material mat)
     {
          super(EssentialAlloys.instance, s, mat);
     }
}
