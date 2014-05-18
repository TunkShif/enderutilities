package fi.dy.masa.minecraft.mods.enderutilities.render;

import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;

public class RenderEnderBow implements IItemRenderer
{
	/** 
	 * Checks if this renderer should handle a specific item's render type
	 * @param item The item we are trying to render
	 * @param type A render type to check if this renderer handles
	 * @return true if this renderer should handle the given render type,
	 * otherwise false
	 */
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type)
	{
		return type == ItemRenderType.EQUIPPED || type == ItemRenderType.EQUIPPED_FIRST_PERSON;
	}
	
	/**
	 * Checks if certain helper functionality should be executed for this renderer.
	 * See ItemRendererHelper for more info
	 * 
	 * @param type The render type
	 * @param item The ItemStack being rendered
	 * @param helper The type of helper functionality to be ran
	 * @return True to run the helper functionality, false to not.
	 */
	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper)
	{
		return true;
	}
	
	/**
	 * Called to do the actual rendering, see ItemRenderType for details on when specific 
	 * types are run, and what extra data is passed into the data parameter.
	 * 
	 * @param type The render type
	 * @param item The ItemStack being rendered
	 * @param data Extra Type specific data
	 */
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data)
	{
		EntityLivingBase living = (EntityLivingBase) data[1];
		ItemRenderer renderer = RenderManager.instance.itemRenderer;
		for (int i = 0; i < item.getItem().getRenderPasses(item.getItemDamage()) + 1; i++)
		{
			this.renderItem(living, item, i, type);
		}
	}

	public void renderItem(EntityLivingBase living, ItemStack stack, int pass, ItemRenderType type)
	{
		
	}
}
