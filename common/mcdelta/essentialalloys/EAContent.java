package mcdelta.essentialalloys;

import mcdelta.core.IContent;
import mcdelta.core.block.BlockDelta;
import mcdelta.core.enchant.EnchantmentDelta;
import mcdelta.core.material.ItemMaterial;
import mcdelta.essentialalloys.block.BlockCrusher;
import mcdelta.essentialalloys.block.BlockCrusherExt;
import mcdelta.essentialalloys.block.BlockCrusherExtMoving;
import mcdelta.essentialalloys.block.BlockEAOre;
import mcdelta.essentialalloys.block.BlockHotPlate;
import mcdelta.essentialalloys.block.BlockStorage;
import mcdelta.essentialalloys.block.tileentity.TileEntityCrusher;
import mcdelta.essentialalloys.enchant.EnchEnchanted;
import mcdelta.essentialalloys.item.ItemDust;
import mcdelta.essentialalloys.item.ItemEA;
import mcdelta.essentialalloys.item.ItemIngot;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import cpw.mods.fml.common.registry.GameRegistry;

public class EAContent implements IContent
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
     
     public static EnchantmentDelta      enchant;
     
     
     
     
     @Override
     public void addContent ()
     {
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
          
          enchant = new EnchEnchanted("enchant", 999, EnumEnchantmentType.all);
          
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
     
     
     
     
     @Override
     public void addMaterialBasedContent (final ItemMaterial mat)
     {
          
     }
     
     
     
     
     @Override
     public void addRecipes ()
     {
          
     }
     
}
