package fi.dy.masa.minecraft.mods.enderutilities.event;

import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fi.dy.masa.minecraft.mods.enderutilities.init.EnderUtilitiesItems;
import fi.dy.masa.minecraft.mods.enderutilities.util.TeleportEntity;

public class EntityAttack
{
	@SubscribeEvent
	public void onEntityAttackEvent(AttackEntityEvent event)
	{
		if (event.target instanceof EntityLiving)
		{
			ItemStack stack = event.entityPlayer.inventory.getCurrentItem();

			if (stack != null)
			{
				if (stack.getItem() == EnderUtilitiesItems.enderArrow)
				{
					TeleportEntity.teleportEntityRandomly((EntityLiving)event.target, 5.0d);
					event.setCanceled(true);
					return;
				}
			}
		}
	}
}
