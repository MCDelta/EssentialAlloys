package mcdelta.essentialalloys.event;

import mcdelta.essentialalloys.EAContent;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.world.BlockEvent.HarvestDropsEvent;

public class EventBlockBreakEA
{
     @ForgeSubscribe
     public void breakBlock (final HarvestDropsEvent event)
     {
          if (event.dropChance < 1)
          {
               if (EAContent.explosionRecipes.containsKey(event.block))
               {
                    event.dropChance = 1;
                    event.drops.clear();
                    event.drops.add(EAContent.explosionRecipes.get(event.block).copy());
               }
          }
          
          else if (event.block == Block.oreNetherQuartz)
          {
               event.drops.clear();
               event.drops.add(new ItemStack(Item.netherQuartz, 4).copy());
          }
     }
}
