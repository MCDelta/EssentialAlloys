package mcdelta.essentialalloys;

import java.util.HashMap;
import java.util.Map;

import mcdelta.core.ModDelta;
import mcdelta.core.special.enchant.EnchantmentDelta;
import mcdelta.essentialalloys.block.BlockEA;
import mcdelta.essentialalloys.event.EventBlockBreakEA;
import mcdelta.essentialalloys.item.ItemEA;
import mcdelta.essentialalloys.proxy.EACommonProxy;
import mcdelta.essentialalloys.special.enchant.EnchEnchanted;
import mcdelta.essentialalloys.world.GeneratorOres;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
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

@Mod(modid = EssentialAlloys.MOD_ID, useMetadata = true)
@NetworkMod(clientSideRequired = true, serverSideRequired = false)
public class EssentialAlloys extends ModDelta
{
    // TODO
    // - make items enchantable
    // - work on armor

    public static final String MOD_ID = "essentialalloys";
    
    @Instance(MOD_ID)
    public static EssentialAlloys instance;

    @SidedProxy(clientSide = "mcdelta.essentialalloys.proxy.EAClientProxy", serverSide = "mcdelta.essentialalloys.proxy.EACommonProxy")
    public static EACommonProxy proxy;

    public static Map<Block, ItemStack> crusherRecipes = new HashMap<Block, ItemStack>();
    public static Map<Block, ItemStack> explosionRecipes = new HashMap<Block, ItemStack>();

    public static EnchantmentDelta enchant;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {}

    @EventHandler
    public void load(FMLInitializationEvent event)
    {
        OreDictionary.registerOre("ingotCopper", ItemEA.ingotCopper);
        OreDictionary.registerOre("ingotTin", ItemEA.ingotTin);
        OreDictionary.registerOre("ingotBronze", ItemEA.ingotBronze);
        OreDictionary.registerOre("ingotMagic", ItemEA.ingotMagic);
        OreDictionary.registerOre("ingotSteel", ItemEA.ingotSteel);

        OreDictionary.registerOre("storageCopper", BlockEA.blockCopper);
        OreDictionary.registerOre("storageTin", BlockEA.blockTin);
        OreDictionary.registerOre("storageBronze", BlockEA.blockBronze);
        OreDictionary.registerOre("storageMagic", BlockEA.blockMagic);

        OreDictionary.registerOre("dustCopper", ItemEA.dustCopper);
        OreDictionary.registerOre("dustTin", ItemEA.dustTin);
        OreDictionary.registerOre("dustBronze", ItemEA.dustBronze);
        OreDictionary.registerOre("dustMagic", ItemEA.dustMagic);
        OreDictionary.registerOre("dustIron", ItemEA.dustIron);
        OreDictionary.registerOre("dustGold", ItemEA.dustGold);

        OreDictionary.registerOre("nuggetSteel", ItemEA.nuggetSteel);

        explosionRecipes.put(Block.oreIron, new ItemStack(ItemEA.dustIron, 3));
        explosionRecipes.put(Block.oreGold, new ItemStack(ItemEA.dustGold, 3));
        explosionRecipes.put(BlockEA.oreCopper, new ItemStack(ItemEA.dustCopper, 3));
        explosionRecipes.put(BlockEA.oreTin, new ItemStack(ItemEA.dustTin, 3));
        explosionRecipes.put(Block.oreNetherQuartz, new ItemStack(Item.netherQuartz, 6));

        crusherRecipes.put(Block.oreIron, new ItemStack(ItemEA.dustIron, 2));
        crusherRecipes.put(Block.oreGold, new ItemStack(ItemEA.dustGold, 2));
        crusherRecipes.put(BlockEA.oreCopper, new ItemStack(ItemEA.dustCopper, 2));
        crusherRecipes.put(BlockEA.oreTin, new ItemStack(ItemEA.dustTin, 2));

        crusherRecipes.put(Block.blockIron, new ItemStack(ItemEA.dustIron, 9));
        crusherRecipes.put(Block.blockGold, new ItemStack(ItemEA.dustGold, 9));
        crusherRecipes.put(BlockEA.blockCopper, new ItemStack(ItemEA.dustCopper, 9));
        crusherRecipes.put(BlockEA.blockTin, new ItemStack(ItemEA.dustTin, 9));
        crusherRecipes.put(BlockEA.blockBronze, new ItemStack(ItemEA.dustBronze, 9));

        crusherRecipes.put(Block.oreLapis, new ItemStack(Item.dyePowder, 8, 4));
        crusherRecipes.put(Block.oreRedstone, new ItemStack(Item.redstone, 8));
        crusherRecipes.put(Block.oreDiamond, new ItemStack(Item.diamond, 2));
        crusherRecipes.put(Block.oreCoal, new ItemStack(Item.coal, 4));
        crusherRecipes.put(Block.oreEmerald, new ItemStack(Item.emerald, 2));
        crusherRecipes.put(Block.oreNetherQuartz, new ItemStack(Item.netherQuartz, 5));

        crusherRecipes.put(Block.stone, new ItemStack(Block.gravel, 2));
        crusherRecipes.put(Block.gravel, new ItemStack(Item.flint, 2));
        crusherRecipes.put(Block.cobblestone, new ItemStack(Block.sand));

        FurnaceRecipes.smelting().addSmelting(BlockEA.blockCopper.blockID, new ItemStack(ItemEA.ingotCopper), 0.7F);
        FurnaceRecipes.smelting().addSmelting(BlockEA.blockTin.blockID, new ItemStack(ItemEA.ingotTin), 0.7F);

        FurnaceRecipes.smelting().addSmelting(ItemEA.dustBronze.itemID, new ItemStack(ItemEA.ingotBronze), 1.0F);
        FurnaceRecipes.smelting().addSmelting(ItemEA.dustGold.itemID, new ItemStack(Item.ingotGold), 0.5F);
        FurnaceRecipes.smelting().addSmelting(ItemEA.dustCopper.itemID, new ItemStack(ItemEA.ingotCopper), 0.35F);
        FurnaceRecipes.smelting().addSmelting(ItemEA.dustTin.itemID, new ItemStack(ItemEA.ingotTin), 0.35F);
        FurnaceRecipes.smelting().addSmelting(ItemEA.dustIron.itemID, new ItemStack(Item.ingotIron), 0.35F);
        FurnaceRecipes.smelting().addSmelting(ItemEA.dustMagic.itemID, new ItemStack(ItemEA.ingotMagic), 2.0F);

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemEA.dustBronze), new Object[]
        { "dustTin", "dustCopper" }));

        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemEA.nuggetSteel, 9), new Object[]
        { "ingotSteel" }));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemEA.ingotBronze, 9), new Object[]
        { "storageBronze" }));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemEA.ingotCopper, 9), new Object[]
        { "storageCopper" }));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemEA.ingotTin, 9), new Object[]
        { "storageTin" }));
        GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(ItemEA.ingotMagic, 9), new Object[]
        { "storageMagic" }));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockEA.blockBronze), "xxx", "xxx", "xxx", 'x', "ingotBronze"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockEA.blockCopper), "xxx", "xxx", "xxx", 'x', "ingotCopper"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockEA.blockTin), "xxx", "xxx", "xxx", 'x', "ingotTin"));
        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(BlockEA.blockMagic), "xxx", "xxx", "xxx", 'x', "ingotMagic"));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemEA.ingotSteel), "xxx", "xxx", "xxx", 'x', "nuggetSteel"));

        enchant = new EnchEnchanted("enchant", 999, EnumEnchantmentType.all);

        GameRegistry.registerWorldGenerator(new GeneratorOres());

        MinecraftForge.EVENT_BUS.register(new EventBlockBreakEA());

        proxy.registerRenderers();
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {}
}