package mcdelta.essentialalloys;

import cpw.mods.fml.common.registry.GameRegistry;
import mcdelta.core.block.BlockDelta;
import mcdelta.core.material.ToolMaterial;
import mcdelta.essentialalloys.block.BlockCrusher;
import mcdelta.essentialalloys.block.BlockCrusherExt;
import mcdelta.essentialalloys.block.BlockCrusherExtMoving;
import mcdelta.essentialalloys.block.BlockEAOre;
import mcdelta.essentialalloys.block.BlockHotPlate;
import mcdelta.essentialalloys.block.BlockStorage;
import mcdelta.essentialalloys.block.tileentity.TileEntityCrusher;
import mcdelta.essentialalloys.item.ItemDust;
import mcdelta.essentialalloys.item.ItemEA;
import mcdelta.essentialalloys.item.ItemIngot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;

public class EAContent
{
     public static ItemIngot             ingotCopper;
     public static ItemIngot             ingotTin;
     public static ItemIngot             ingotBronze;
     public static ItemIngot             ingotMagic;
     public static ItemIngot             ingotSteel;
     
     public static ItemDust              dustIron;
     public static ItemDust              dustGold;
     public static ItemDust              dustCopper;
     public static ItemDust              dustTin;
     public static ItemDust              dustBronze;
     public static ItemDust              dustMagic;
     
     public static ItemEA                nuggetSteel;
     
     public static BlockCrusher          crusher;
     public static BlockCrusherExt       crusherExt;
     public static BlockCrusherExtMoving crusherExtMoving;
     
     public static BlockHotPlate         hotPlate;
     
     public static BlockEAOre            oreCopper;
     public static BlockEAOre            oreTin;
     
     public static BlockDelta            blockCopper;
     public static BlockDelta            blockTin;
     public static BlockDelta            blockBronze;
     public static BlockDelta            blockMagic;
     
     public static ToolMaterial          BRONZE;
     public static ToolMaterial          MAGIC;
     public static ToolMaterial          STEEL;
     
     
     
     
     public static void load ()
     {
          /**BRONZE = new ToolMaterial(new Object[]
          { "bronze", 0xd3b838, "ingotBronze", true, true, false }, new Object[]
          { 2, 418, 9.0F, 2.0F, 22 }, null);
          MAGIC = new ToolMaterial(new Object[]
          { "magic", 0x7340ad, "ingotMagic", true, true, true }, new Object[]
          { 3, 205, 12.0F, 1.0F, 44 }, null);
          STEEL = new ToolMaterial(new Object[]
          { "steel", 0x637080, "ingotSteel", true, true, false }, new Object[]
          { 4, 1111, 4.0F, 3.0F, 12 }, new Object[]
          { 33, new int[]
          { 3, 8, 6, 3 }, 10 });*/
          
          ingotCopper = new ItemIngot("copper");
          ingotTin = new ItemIngot("tin");
          ingotBronze = new ItemIngot("bronze");
          ingotMagic = new ItemIngot("magic", true);
          ingotSteel = new ItemIngot("steel");
          
          dustIron = new ItemDust("iron");
          dustGold = new ItemDust("gold");
          dustCopper = new ItemDust("copper");
          dustTin = new ItemDust("tin");
          dustBronze = new ItemDust("bronze");
          dustMagic = new ItemDust("magic", true);
          
          nuggetSteel = (ItemEA) new ItemEA("nugget.steel").setCreativeTab(CreativeTabs.tabMaterials);
          
          crusher = new BlockCrusher("crusher");
          crusherExt = new BlockCrusherExt("crusherExt");
          crusherExtMoving = new BlockCrusherExtMoving("crusherExtMoving");
          
          hotPlate = new BlockHotPlate("hotPlate");
          
          oreCopper = new BlockEAOre("copper");
          oreTin = new BlockEAOre("tin");
          blockCopper = new BlockStorage("copper");
          blockTin = new BlockStorage("tin");
          blockBronze = new BlockStorage("bronze");
          blockMagic = new BlockStorage("magic");
          
          GameRegistry.registerTileEntity(TileEntityCrusher.class, "crusher");
          
          MinecraftForge.setBlockHarvestLevel(oreCopper, "pickaxe", 1);
          MinecraftForge.setBlockHarvestLevel(oreTin, "pickaxe", 1);
          
          MinecraftForge.setBlockHarvestLevel(blockCopper, "pickaxe", 1);
          MinecraftForge.setBlockHarvestLevel(blockTin, "pickaxe", 1);
          MinecraftForge.setBlockHarvestLevel(blockBronze, "pickaxe", 1);
          MinecraftForge.setBlockHarvestLevel(blockMagic, "pickaxe", 1);
          
          OreDictionary.registerOre("ingotCopper", EAContent.ingotCopper);
          OreDictionary.registerOre("ingotTin", EAContent.ingotTin);
          OreDictionary.registerOre("ingotBronze", EAContent.ingotBronze);
          OreDictionary.registerOre("ingotMagic", EAContent.ingotMagic);
          OreDictionary.registerOre("ingotSteel", EAContent.ingotSteel);
          
          OreDictionary.registerOre("storageCopper", EAContent.blockCopper);
          OreDictionary.registerOre("storageTin", EAContent.blockTin);
          OreDictionary.registerOre("storageBronze", EAContent.blockBronze);
          OreDictionary.registerOre("storageMagic", EAContent.blockMagic);
          
          OreDictionary.registerOre("dustCopper", EAContent.dustCopper);
          OreDictionary.registerOre("dustTin", EAContent.dustTin);
          OreDictionary.registerOre("dustBronze", EAContent.dustBronze);
          OreDictionary.registerOre("dustMagic", EAContent.dustMagic);
          OreDictionary.registerOre("dustIron", EAContent.dustIron);
          OreDictionary.registerOre("dustGold", EAContent.dustGold);
          
          OreDictionary.registerOre("nuggetSteel", EAContent.nuggetSteel);
     }
     
}
