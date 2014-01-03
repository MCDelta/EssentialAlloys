package mcdelta.essentialalloys.block;

import mcdelta.core.block.BlockDelta;
import mcdelta.essentialalloys.EssentialAlloys;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockEA extends BlockDelta
{
<<<<<<< HEAD
     public BlockEA (final String s, final Material mat)
     {
          super(EssentialAlloys.instance, s, mat);
          
          setCreativeTab(CreativeTabs.tabBlock);
     }
=======
    public static BlockCrusher crusher = new BlockCrusher("crusher");
    public static BlockCrusherExt crusherExt = new BlockCrusherExt("crusherExt");
    public static BlockCrusherExtMoving crusherExtMoving = new BlockCrusherExtMoving("crusherExtMoving");

    public static BlockHotPlate hotPlate = new BlockHotPlate("hotPlate");

    public static BlockEAOre oreCopper = new BlockEAOre("copper");
    public static BlockEAOre oreTin = new BlockEAOre("tin");

    public static BlockDelta blockCopper = new BlockStorage("copper");
    public static BlockDelta blockTin = new BlockStorage("tin");
    public static BlockDelta blockBronze = new BlockStorage("bronze");
    public static BlockDelta blockMagic = new BlockStorage("magic");

    static
    {
        GameRegistry.registerTileEntity(TileEntityCrusher.class, "crusher");

        MinecraftForge.setBlockHarvestLevel(oreCopper, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(oreTin, "pickaxe", 1);

        MinecraftForge.setBlockHarvestLevel(blockCopper, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(blockTin, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(blockBronze, "pickaxe", 1);
        MinecraftForge.setBlockHarvestLevel(blockMagic, "pickaxe", 1);
    }

    public BlockEA(final String s, final Material mat)
    {
        super(EssentialAlloys.instance, s, mat);

        setCreativeTab(CreativeTabs.tabBlock);
    }
>>>>>>> 5ad34493d08f0ba6f35302fdcfc63d2da091fe08
}
