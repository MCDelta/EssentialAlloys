package mcdelta.essentialalloys;

import java.util.HashMap;
import java.util.Map;

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
import mcdelta.essentialalloys.block.tileentity.TileCrusher;
import mcdelta.essentialalloys.block.tileentity.TileHotPlate;
import mcdelta.essentialalloys.enchant.EnchEnchanted;
import mcdelta.essentialalloys.item.ItemDust;
import mcdelta.essentialalloys.item.ItemEA;
import mcdelta.essentialalloys.item.ItemIngot;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
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
     
     public static Map<Block, ItemStack> crusherRecipes   = new HashMap<Block, ItemStack>();
     public static Map<Block, ItemStack> explosionRecipes = new HashMap<Block, ItemStack>();
     
     
     
     
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
          
          GameRegistry.registerTileEntity(TileCrusher.class, "crusher");
          GameRegistry.registerTileEntity(TileHotPlate.class, "hotPlate");
          
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
          ItemStack result = new ItemStack(crusher);
          GameRegistry.addRecipe(new ShapedOreRecipe(result, "oxo", "yzy", "yfy", 'x', Item.netherQuartz, 'o', Block.cobblestone, 'y', Block.brick, 'z', Item.ingotIron, 'f', Block.furnaceIdle));
          
          result = new ItemStack(hotPlate);
          GameRegistry.addRecipe(new ShapedOreRecipe(result, "xxx", "oco", "oho", 'x', "ingotBronze", 'o', Block.netherBrick, 'c', Block.coalBlock, 'h', Block.hopperBlock));
          
          explosionRecipes.put(Block.oreIron, new ItemStack(EAContent.dustIron, 3));
          explosionRecipes.put(Block.oreGold, new ItemStack(EAContent.dustGold, 3));
          explosionRecipes.put(EAContent.oreCopper, new ItemStack(EAContent.dustCopper, 3));
          explosionRecipes.put(EAContent.oreTin, new ItemStack(EAContent.dustTin, 3));
          explosionRecipes.put(Block.oreNetherQuartz, new ItemStack(Item.netherQuartz, 6));
          
          crusherRecipes.put(Block.oreIron, new ItemStack(EAContent.dustIron, 2));
          crusherRecipes.put(Block.oreGold, new ItemStack(EAContent.dustGold, 2));
          crusherRecipes.put(EAContent.oreCopper, new ItemStack(EAContent.dustCopper, 2));
          crusherRecipes.put(EAContent.oreTin, new ItemStack(EAContent.dustTin, 2));
          
          crusherRecipes.put(Block.blockIron, new ItemStack(EAContent.dustIron, 9));
          crusherRecipes.put(Block.blockGold, new ItemStack(EAContent.dustGold, 9));
          crusherRecipes.put(EAContent.blockCopper, new ItemStack(EAContent.dustCopper, 9));
          crusherRecipes.put(EAContent.blockTin, new ItemStack(EAContent.dustTin, 9));
          crusherRecipes.put(EAContent.blockBronze, new ItemStack(EAContent.dustBronze, 9));
          
          crusherRecipes.put(Block.oreLapis, new ItemStack(Item.dyePowder, 8, 4));
          crusherRecipes.put(Block.oreRedstone, new ItemStack(Item.redstone, 8));
          crusherRecipes.put(Block.oreDiamond, new ItemStack(Item.diamond, 2));
          crusherRecipes.put(Block.oreCoal, new ItemStack(Item.coal, 4));
          crusherRecipes.put(Block.oreEmerald, new ItemStack(Item.emerald, 2));
          crusherRecipes.put(Block.oreNetherQuartz, new ItemStack(Item.netherQuartz, 5));
          
          crusherRecipes.put(Block.stone, new ItemStack(Block.gravel, 2));
          crusherRecipes.put(Block.gravel, new ItemStack(Item.flint, 2));
          crusherRecipes.put(Block.cobblestone, new ItemStack(Block.sand));
          
          FurnaceRecipes.smelting().addSmelting(EAContent.oreCopper.blockID, new ItemStack(EAContent.ingotCopper), 0.7F);
          FurnaceRecipes.smelting().addSmelting(EAContent.oreTin.blockID, new ItemStack(EAContent.ingotTin), 0.7F);
          
          FurnaceRecipes.smelting().addSmelting(EAContent.dustBronze.itemID, new ItemStack(EAContent.ingotBronze), 1.0F);
          FurnaceRecipes.smelting().addSmelting(EAContent.dustGold.itemID, new ItemStack(Item.ingotGold), 0.5F);
          FurnaceRecipes.smelting().addSmelting(EAContent.dustCopper.itemID, new ItemStack(EAContent.ingotCopper), 0.35F);
          FurnaceRecipes.smelting().addSmelting(EAContent.dustTin.itemID, new ItemStack(EAContent.ingotTin), 0.35F);
          FurnaceRecipes.smelting().addSmelting(EAContent.dustIron.itemID, new ItemStack(Item.ingotIron), 0.35F);
          FurnaceRecipes.smelting().addSmelting(EAContent.dustMagic.itemID, new ItemStack(EAContent.ingotMagic), 2.0F);
          
          GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(EAContent.dustBronze), new Object[]
          { "dustTin", "dustCopper" }));
          
          GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(EAContent.nuggetSteel, 9), new Object[]
          { "ingotSteel" }));
          GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(EAContent.ingotBronze, 9), new Object[]
          { "storageBronze" }));
          GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(EAContent.ingotCopper, 9), new Object[]
          { "storageCopper" }));
          GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(EAContent.ingotTin, 9), new Object[]
          { "storageTin" }));
          GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(EAContent.ingotMagic, 9), new Object[]
          { "storageMagic" }));
          
          GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(EAContent.blockBronze), "xxx", "xxx", "xxx", 'x', "ingotBronze"));
          GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(EAContent.blockCopper), "xxx", "xxx", "xxx", 'x', "ingotCopper"));
          GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(EAContent.blockTin), "xxx", "xxx", "xxx", 'x', "ingotTin"));
          GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(EAContent.blockMagic), "xxx", "xxx", "xxx", 'x', "ingotMagic"));
          GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(EAContent.ingotSteel), "xxx", "xxx", "xxx", 'x', "nuggetSteel"));
     }
     
     
     
     
     @Override
     public void addMaterialBasedRecipes (final ItemMaterial tmp)
     {
          
     }
     
}
