package mcdelta.essentialalloys.client.gui;

import mcdelta.essentialalloys.EssentialAlloys;
import mcdelta.essentialalloys.block.tileentity.TileHotPlate;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly (Side.CLIENT)
public class GuiHotPlate extends GuiContainer
{
     private static final ResourceLocation furnaceGuiTextures = new ResourceLocation(EssentialAlloys.MOD_ID.toLowerCase(), "textures/gui/hotPlate.png");
     private TileHotPlate                  hotPlateInv;
     protected int                         ySize              = 178;
     
     
     
     
     public GuiHotPlate (InventoryPlayer inventory, TileHotPlate tile)
     {
          super(new ContainerHotPlate(inventory, tile));
          hotPlateInv = tile;
     }
     
     
     
     
     @Override
     protected void drawGuiContainerForegroundLayer (int par1, int par2)
     {
          String s = "" + ((hotPlateInv.dimensions[0] * hotPlateInv.dimensions[1] * hotPlateInv.dimensions[2] == 0) ? EnumChatFormatting.DARK_RED : EnumChatFormatting.DARK_GREEN) + hotPlateInv.dimensions[0] + "x" + hotPlateInv.dimensions[1] + "x" + hotPlateInv.dimensions[2] + EnumChatFormatting.RESET + " " + I18n.getString(hotPlateInv.getInvName());
          this.fontRenderer.drawString(s, this.xSize / 2 - this.fontRenderer.getStringWidth(s) / 2, 1, 4210752);
          this.fontRenderer.drawString(I18n.getString("container.inventory"), 8, this.ySize - 99, 4210752);
     }
     
     
     
     
     @Override
     protected void drawGuiContainerBackgroundLayer (float par1, int par2, int par3)
     {
          GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
          this.mc.getTextureManager().bindTexture(furnaceGuiTextures);
          int k = (this.width - this.xSize) / 2;
          int l = (this.height - this.ySize) / 2;
          this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
          
          for (int i = 0; i < 5; i++)
          {
               if (hotPlateInv.smeltingTotalTimes[i] != 0)
               {
                    double d = 1 - (((double) this.hotPlateInv.smeltingTimes[i]) / hotPlateInv.smeltingTotalTimes[i]);
                    this.drawTexturedModalRect(k + 33 + (i * 23), l + 28, 176, 0, (int) (d * 17), 16);
               }
          }
     }
}
