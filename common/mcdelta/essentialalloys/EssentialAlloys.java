package mcdelta.essentialalloys;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.IContent;
import mcdelta.core.ModDelta;
import mcdelta.essentialalloys.event.EventBlockBreakEA;
import mcdelta.essentialalloys.proxy.EACommonProxy;
import mcdelta.essentialalloys.world.GeneratorOres;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.registry.GameRegistry;

@Mod (modid = EssentialAlloys.MOD_ID, useMetadata = true)
@NetworkMod (clientSideRequired = true, serverSideRequired = false)
public class EssentialAlloys extends ModDelta
{
     // TODO
     // - make items enchantable
     // - work on armor
     
     public static final String          MOD_ID           = "essentialalloys";
     
     @Instance (MOD_ID)
     public static EssentialAlloys       instance;
     
     @SidedProxy (clientSide = "mcdelta.essentialalloys.proxy.EAClientProxy", serverSide = "mcdelta.essentialalloys.proxy.EACommonProxy")
     public static EACommonProxy         proxy;
     
     public static Map<Block, ItemStack> crusherRecipes   = new HashMap<Block, ItemStack>();
     public static Map<Block, ItemStack> explosionRecipes = new HashMap<Block, ItemStack>();
     
     
     
     
     @Override
     public void deltaInit (FMLPreInitializationEvent event)
     {
          this.init(event);
     }
     
     
     
     
     @EventHandler
     public void preInit (final FMLPreInitializationEvent event)
     {
     }
     
     
     
     
     @EventHandler
     public void load (final FMLInitializationEvent event)
     {
          this.content().addRecipes();
          
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
          
          FurnaceRecipes.smelting().addSmelting(EAContent.blockCopper.blockID, new ItemStack(EAContent.ingotCopper), 0.7F);
          FurnaceRecipes.smelting().addSmelting(EAContent.blockTin.blockID, new ItemStack(EAContent.ingotTin), 0.7F);
          
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
          
          GameRegistry.registerWorldGenerator(new GeneratorOres());
          
          MinecraftForge.EVENT_BUS.register(new EventBlockBreakEA());
          
          proxy.registerRenderers();
     }
     
     
     
     
     @EventHandler
     public void postInit (final FMLPostInitializationEvent event)
     {
     }
     
     private final IContent content = new EAContent();
     
     
     
     
     @Override
     public IContent content ()
     {
          return this.content;
     }
}
