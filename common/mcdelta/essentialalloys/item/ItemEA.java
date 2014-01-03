package mcdelta.essentialalloys.item;

import mcdelta.core.item.ItemDelta;
import mcdelta.essentialalloys.EssentialAlloys;

public class ItemEA extends ItemDelta
{
<<<<<<< HEAD
     public ItemEA (final String name)
     {
          super(EssentialAlloys.instance, name);
     }
=======
    public static ItemIngot ingotCopper = new ItemIngot("copper");
    public static ItemIngot ingotTin = new ItemIngot("tin");
    public static ItemIngot ingotBronze = new ItemIngot("bronze");
    public static ItemIngot ingotMagic = new ItemIngot("magic", true);
    public static ItemIngot ingotSteel = new ItemIngot("steel");

    public static ItemDust dustIron = new ItemDust("iron");
    public static ItemDust dustGold = new ItemDust("gold");
    public static ItemDust dustCopper = new ItemDust("copper");
    public static ItemDust dustTin = new ItemDust("tin");
    public static ItemDust dustBronze = new ItemDust("bronze");
    public static ItemDust dustMagic = new ItemDust("magic", true);

    public static ItemEA nuggetSteel = (ItemEA) new ItemEA("nugget.steel").setCreativeTab(CreativeTabs.tabMaterials);

    public ItemEA(final String name)
    {
        super(EssentialAlloys.instance, name);
    }
>>>>>>> 5ad34493d08f0ba6f35302fdcfc63d2da091fe08
}
