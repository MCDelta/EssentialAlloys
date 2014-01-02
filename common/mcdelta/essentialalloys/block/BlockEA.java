package mcdelta.essentialalloys.block;

import mcdelta.core.block.BlockDelta;
import mcdelta.essentialalloys.EssentialAlloys;
import mcdelta.essentialalloys.block.tileentity.TileEntityCrusher;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.registry.GameRegistry;

public class BlockEA extends BlockDelta
{
     public static BlockCrusher          crusher          = new BlockCrusher("crusher");
     public static BlockCrusherExt       crusherExt       = new BlockCrusherExt("crusherExt");
     public static BlockCrusherExtMoving crusherExtMoving = new BlockCrusherExtMoving("crusherExtMoving");
     
     public static BlockHotPlate         hotPlate         = new BlockHotPlate("hotPlate");
     
     public static BlockEAOre            oreCopper        = new BlockEAOre("copper");
     public static BlockEAOre            oreTin           = new BlockEAOre("tin");
     
     public static BlockDelta            blockCopper      = new BlockStorage("copper");
     public static BlockDelta            blockTin         = new BlockStorage("tin");
     public static BlockDelta            blockBronze      = new BlockStorage("bronze");
     public static BlockDelta            blockMagic       = new BlockStorage("magic");
     
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
     
     
     
     
     public BlockEA (final String s, final Material mat)
     {
          super(EssentialAlloys.instance, s, mat);
          
          setCreativeTab(CreativeTabs.tabBlock);
     }
}
