package mcdelta.essentialalloys.block;

import mcdelta.core.block.BlockDelta;
import net.minecraft.block.material.Material;

public class BlockStorage extends BlockEA
{
     public BlockStorage (String s)
     {
          super("storage." + s, Material.iron);
     }
}
