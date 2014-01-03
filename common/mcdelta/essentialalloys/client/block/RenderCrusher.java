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

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderCrusher implements ISimpleBlockRenderingHandler
{
     @Override
     public void renderInventoryBlock (final Block block, final int metadata, final int modelID, final RenderBlocks renderer)
     {
          final float[] arr = BlockShapes.crusherExtended(EnumFacing.UP);
          renderer.setRenderBounds(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
          this.renderBlockItem(renderer, block, 10);
          
          renderer.setOverrideBlockTexture(EAContent.crusher.studIcon);
          
          final float[] arr1 = BlockShapes.crusherHeadThingies(EnumFacing.UP, 0);
          renderer.setRenderBounds(arr1[0], arr1[1], arr1[2], arr1[3], arr1[4], arr1[5]);
          this.renderBlockItem(renderer, block, 1);
          
          final float[] arr2 = BlockShapes.crusherHeadThingies(EnumFacing.UP, 1);
          renderer.setRenderBounds(arr2[0], arr2[1], arr2[2], arr2[3], arr2[4], arr2[5]);
          this.renderBlockItem(renderer, block, 1);
          
          final float[] arr3 = BlockShapes.crusherHeadThingies(EnumFacing.UP, 2);
          renderer.setRenderBounds(arr3[0], arr3[1], arr3[2], arr3[3], arr3[4], arr3[5]);
          this.renderBlockItem(renderer, block, 1);
          
          final float[] arr4 = BlockShapes.crusherHeadThingies(EnumFacing.UP, 3);
          renderer.setRenderBounds(arr4[0], arr4[1], arr4[2], arr4[3], arr4[4], arr4[5]);
          this.renderBlockItem(renderer, block, 1);
          
          renderer.clearOverrideBlockTexture();
     }
     
     
     
     
     private void renderBlockItem (final RenderBlocks renderer, final Block block, final int metadata)
     {
          final Tessellator tessellator = Tessellator.instance;
          GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
          tessellator.startDrawingQuads();
          tessellator.setNormal(0.0F, -1.0F, 0.0F);
          renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 0, metadata));
          tessellator.draw();
          tessellator.startDrawingQuads();
          tessellator.setNormal(0.0F, 1.0F, 0.0F);
          renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 1, metadata));
          tessellator.draw();
          tessellator.startDrawingQuads();
          tessellator.setNormal(0.0F, 0.0F, -1.0F);
          renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 2, metadata));
          tessellator.draw();
          tessellator.startDrawingQuads();
          tessellator.setNormal(0.0F, 0.0F, 1.0F);
          renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 3, metadata));
          tessellator.draw();
          tessellator.startDrawingQuads();
          tessellator.setNormal(-1.0F, 0.0F, 0.0F);
          renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 4, metadata));
          tessellator.draw();
          tessellator.startDrawingQuads();
          tessellator.setNormal(1.0F, 0.0F, 0.0F);
          renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, renderer.getBlockIconFromSideAndMetadata(block, 5, metadata));
          tessellator.draw();
          GL11.glTranslatef(0.5F, 0.5F, 0.5F);
     }
     
     
     
     
     @Override
     public boolean renderWorldBlock (final IBlockAccess world, final int x, final int y, final int z, final Block block, final int modelId, final RenderBlocks renderer)
     {
          final Position pos = new Position(world, x, y, z);
          
          final EnumFacing face = Assets.getFacing(pos.getMeta());
          
          RenderAssets.rotateSidedRenderer(renderer, face);
          
          if (!EAContent.crusher.isExtended(pos) && ((TileEntityCrusher) pos.getTile()).extend == 0)
          {
               if (renderer.hasOverrideBlockTexture())
               {
                    final float[] arr = BlockShapes.crusherExtended(face);
                    renderer.setRenderBounds(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
                    renderer.renderStandardBlock(block, x, y, z);
                    
                    return true;
               }
               final float[] arr = BlockShapes.crusherExtended(face);
               renderer.setRenderBounds(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
               renderer.renderStandardBlock(block, x, y, z);
               
               renderer.renderAllFaces = true;
               
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
          }
          else
          {
               if (renderer.hasOverrideBlockTexture())
               {
                    final float[] arr = BlockShapes.crusherNonExtended(face);
                    renderer.setRenderBounds(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
                    renderer.renderStandardBlock(block, x, y, z);
                    
                    return true;
               }
               final float[] arr = BlockShapes.crusherNonExtended(face);
               renderer.setRenderBounds(arr[0], arr[1], arr[2], arr[3], arr[4], arr[5]);
               renderer.renderStandardBlock(block, x, y, z);
          }
          renderer.uvRotateEast = 0;
          renderer.uvRotateWest = 0;
          renderer.uvRotateSouth = 0;
          renderer.uvRotateNorth = 0;
          renderer.uvRotateTop = 0;
          
          return false;
     }
     
     
     
     
     @Override
     public boolean shouldRender3DInInventory ()
     {
          return true;
     }
     
     
     
     
     @Override
     public int getRenderId ()
     {
          return EAClientProxy.typeCrusher;
     }
}
