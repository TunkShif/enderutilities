package fi.dy.masa.minecraft.mods.enderutilities.init;

import net.minecraft.client.renderer.entity.RenderSnowball;
import cpw.mods.fml.client.registry.RenderingRegistry;
import fi.dy.masa.minecraft.mods.enderutilities.entity.EntityEnderPearlReusable;

public class EnderUtilitiesRenderers
{
	public static void init()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityEnderPearlReusable.class, new RenderSnowball(EnderUtilitiesItems.enderPearlReusable));
	}
}
