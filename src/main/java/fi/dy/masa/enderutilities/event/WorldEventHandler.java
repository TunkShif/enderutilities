package fi.dy.masa.enderutilities.event;

import net.minecraftforge.event.world.WorldEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import fi.dy.masa.enderutilities.util.EnergyBridgeTracker;

public class WorldEventHandler
{
    @SubscribeEvent
    public void onWorldSaveEvent(WorldEvent.Save event)
    {
        EnergyBridgeTracker.writeToDisk();
    }
}
