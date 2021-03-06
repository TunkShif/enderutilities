package fi.dy.masa.enderutilities.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fi.dy.masa.enderutilities.init.EnderUtilitiesItems;
import fi.dy.masa.enderutilities.item.tool.ItemEnderSword;

public class LivingDropsEventHandler
{
    @SubscribeEvent
    public void onLivingDrops(LivingDropsEvent event)
    {
        if (event.source != null && event.source.damageType != null && event.source.damageType.equals("player")
            && event.source.getSourceOfDamage() instanceof EntityPlayer)
        {
            ItemStack stack = ((EntityPlayer)event.source.getSourceOfDamage()).getCurrentEquippedItem();
            if (stack != null && stack.getItem() == EnderUtilitiesItems.enderSword)
            {
                ((ItemEnderSword) stack.getItem()).handleLivingDropsEvent(stack, event);
            }
        }
    }
}
