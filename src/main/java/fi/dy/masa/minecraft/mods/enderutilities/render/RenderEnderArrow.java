package fi.dy.masa.minecraft.mods.enderutilities.render;

import net.minecraft.client.renderer.entity.RenderArrow;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import fi.dy.masa.minecraft.mods.enderutilities.entity.EntityEnderArrow;
import fi.dy.masa.minecraft.mods.enderutilities.reference.Reference;

public class RenderEnderArrow extends RenderArrow
{
	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityEnderArrow par1EntityArrow)
	{
		return new ResourceLocation(Reference.getEntityTextureName(Reference.NAME_ENTITY_ENDER_ARROW));
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getEntityTexture((EntityEnderArrow)par1Entity);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.doRender((EntityEnderArrow)par1Entity, par2, par4, par6, par8, par9);
	}
}
