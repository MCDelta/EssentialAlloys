package mcdelta.essentialalloys.client.block;

import mcdelta.core.assets.Assets;
import mcdelta.core.assets.client.RenderAssets;
import mcdelta.core.assets.world.BlockShapes;
import mcdelta.core.assets.world.Position;
import mcdelta.essentialalloys.EAContent;
import mcdelta.essentialalloys.block.tileentity.TileEntityCrusher;
import mcdelta.essentialalloys.proxy.EAClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderCrusherExtension implements ISimpleBlockRenderingHandler
{
     @Override
     public boolean renderWorldBlock (final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer)
     {
          doRenderBlock(world, x, y, z, block, renderer, new Position(world, x, y, z).getMeta(), false);
          
          return true;
     }
     
     
     
     
     public static void doRenderBlock (final IBlockAccess world, final int x, final int y, final int z, final Block block, final RenderBlocks renderer, final int meta, final boolean b)
     {
          final EnumFacing face = Assets.getFacing(meta);
          
          final boolean flag = block == EAContent.crusherExtMoving;
          
          if (renderer.hasOverrideBlockTexture() && !flag)
          {
               final float[] arr = BlockShapes.crusherExtension(face, 0);
               renderer.setRenderBounds(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
               renderer.renderStandardBlock(block, x, y, z);
               
               return;
          }
          final Position pos = new Position(world, x, y, z);
          
          final double xOffset = Tessellator.instance.xOffset;
          final double yOffset = Tessellator.instance.yOffset;
          final double zOffset = Tessellator.instance.zOffset;
          
          float f = 0;
          
          if (flag)
          {
               final TileEntityCrusher source = (TileEntityCrusher) pos.move(Assets.invertFace(Assets.getFacing(pos.getMeta()))).getTile();
               
               final int extend = source.extend;
               final int extendTotal = source.extendTotal;
               
               f = (float) Math.abs(extend) / (float) Math.abs(extendTotal) * (extendTotal <= 0 ? 1 : -1) + (extendTotal <= 0 ? -1 : 0);
               
               switch (Assets.getFacing(pos.getMeta()))
               {
                    case UP:
                         Tessellator.instance.setTranslation(xOffset, yOffset + f, zOffset);
                         break;
                    case DOWN:
                         Tessellator.instance.setTranslation(xOffset, yOffset + f * -1, zOffset);
                         break;
                    case SOUTH:
                         Tessellator.instance.setTranslation(xOffset, yOffset, zOffset + f);
                         break;
                    case NORTH:
                         Tessellator.instance.setTranslation(xOffset, yOffset, zOffset + f * -1);
                         break;
                    case WEST:
                         Tessellator.instance.setTranslation(xOffset + f, yOffset, zOffset);
                         break;
                    case EAST:
                         Tessellator.instance.setTranslation(xOffset + f * -1, yOffset, zOffset);
                         break;
               }
          }
          renderer.renderAllFaces = true;
          RenderAssets.rotateSidedRenderer(renderer, face);
          
          final float[] arr = BlockShapes.crusherExtension(face, 0);
          renderer.setRenderBounds(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
          renderer.renderStandardBlock(block, x, y, z);
          
          float shift = 0.438F;
          
          switch (face)
          {
               case UP:
               case SOUTH:
               case WEST:
                    shift *= -1;
               default:
                    break;
          }
          renderer.uvRotateEast = 0;
          renderer.uvRotateWest = 0;
          renderer.uvRotateSouth = 0;
          renderer.uvRotateNorth = 0;
          renderer.uvRotateTop = 0;
          
          renderer.setOverrideBlockTexture(EAContent.crusher.shaftIcon);
          
          final float[] arr1 = BlockShapes.extensionShaft(face, shift, f);
          renderer.setRenderBounds(arr1[0], arr1[1], arr1[2], arr1[3], arr1[4], arr1[5]);
          renderer.renderStandardBlock(block, x, y, z);
          
          renderer.setOverrideBlockTexture(EAContent.crusher.studIcon);
          
          final float[] arr2 = BlockShapes.crusherHeadThingies(face, 0);
          renderer.setRenderBounds(arr2[0], arr2[1], arr2[2], arr2[3], arr2[4], arr2[5]);
          renderer.renderStandardBlock(block, x, y, z);
          
          final float[] arr3 = BlockShapes.crusherHeadThingies(face, 1);
          renderer.setRenderBounds(arr3[0], arr3[1], arr3[2], arr3[3], arr3[4], arr3[5]);
          renderer.renderStandardBlock(block, x, y, z);
          
          final float[] arr4 = BlockShapes.crusherHeadThingies(face, 2);
          renderer.setRenderBounds(arr4[0], arr4[1], arr4[2], arr4[3], arr4[4], arr4[5]);
          renderer.renderStandardBlock(block, x, y, z);
          
          final float[] arr5 = BlockShapes.crusherHeadThingies(face, 3);
          renderer.setRenderBounds(arr5[0], arr5[1], arr5[2], arr5[3], arr5[4], arr5[5]);
          renderer.renderStandardBlock(block, x, y, z);
          
          renderer.clearOverrideBlockTexture();
          
          renderer.renderAllFaces = false;
          
          renderer.uvRotateEast = 0;
          renderer.uvRotateWest = 0;
          renderer.uvRotateSouth = 0;
          renderer.uvRotateNorth = 0;
          renderer.uvRotateTop = 0;
          
          Tessellator.instance.setTranslation(xOffset, yOffset, zOffset);
     }
     
     
     
     
     @Override
     public boolean shouldRender3DInInventory ()
     {
          return false;
     }
     
     
     
     
     @Override
     public int getRenderId ()
     {
          return EAClientProxy.typeCrusherExtension;
     }
     
     
     
     
     @Override
     public void renderInventoryBlock (final Block block, final int metadata, final int modelID, final RenderBlocks renderer)
     {
     }
}
