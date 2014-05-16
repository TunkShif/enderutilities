package fi.dy.masa.minecraft.mods.enderutilities.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.WorldServer;

public class TeleportEntity
{
	public boolean teleportEntityToDimension(EntityLiving entity, int dim)
	{
		this.teleportEntityToDimension(entity, dim, entity.posX, entity.posY, entity.posZ);
		return true;
	}

	public boolean teleportEntityToDimension(EntityLiving entitySrc, int dimDst, double x, double y, double z)
	{
        if (entitySrc.worldObj.isRemote == false && entitySrc.isDead == false)
        {
            entitySrc.worldObj.theProfiler.startSection("changeDimension");
            MinecraftServer minecraftserver = MinecraftServer.getServer();
            int dimSrc = entitySrc.dimension;
            WorldServer worldserverSrc = minecraftserver.worldServerForDimension(dimSrc);
            WorldServer worldserverDst = minecraftserver.worldServerForDimension(dimDst);
            entitySrc.dimension = dimDst;

            entitySrc.worldObj.removeEntity(entitySrc);
            entitySrc.isDead = false;
            entitySrc.worldObj.theProfiler.startSection("reposition");
            minecraftserver.getConfigurationManager().transferEntityToWorld(entitySrc, dimSrc, worldserverSrc, worldserverDst);
            entitySrc.worldObj.theProfiler.endStartSection("reloading");
            Entity entityDst = EntityList.createEntityByName(EntityList.getEntityString(entitySrc), worldserverDst);

            if (entityDst != null)
            {
            	entityDst.copyDataFrom(entitySrc, true);
                entitySrc.setLocationAndAngles(x, y, z, entitySrc.rotationYaw, entitySrc.rotationPitch);
                worldserverDst.spawnEntityInWorld(entityDst);
            }

            entitySrc.isDead = true;
            entitySrc.worldObj.theProfiler.endSection();
            worldserverSrc.resetUpdateEntityTick();
            worldserverDst.resetUpdateEntityTick();
            entitySrc.worldObj.theProfiler.endSection();

            return true;
        }
        return false;
	}
}
