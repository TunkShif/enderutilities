package fi.dy.masa.enderutilities.entity;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import fi.dy.masa.enderutilities.init.EnderUtilitiesItems;
import fi.dy.masa.enderutilities.item.ItemEnderBow;
import fi.dy.masa.enderutilities.setup.Configs;
import fi.dy.masa.enderutilities.util.EntityUtils;
import fi.dy.masa.enderutilities.util.nbt.NBTHelperTarget;
import fi.dy.masa.enderutilities.util.teleport.TeleportEntity;

public class EntityEnderArrow extends EntityArrow
{
    public int blockX = -1;
    public int blockY = -1;
    public int blockZ = -1;
    public Block inBlock;
    public int inData;
    public boolean inGround;
    public int canBePickedUp;
    public int arrowShake;
    public EntityLivingBase shootingEntity;
    public int ticksInGround;
    public int ticksInAir;
    // "TP target" mode target location
    public NBTHelperTarget tpTarget;
    public byte tpMode;
    public boolean applyPersistence;
    public UUID shooterUUID;
    public float teleportDamage = 2.0f;

    public EntityEnderArrow(World par1World)
    {
        super(par1World);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
        this.shooterUUID = UUID.randomUUID();
    }

    public EntityEnderArrow(World par1World, double par2, double par4, double par6)
    {
        super(par1World);
        this.renderDistanceWeight = 10.0D;
        this.setSize(0.5F, 0.5F);
        this.setPosition(par2, par4, par6);
        this.yOffset = 0.0F;
    }

    public EntityEnderArrow(World par1World, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase, float par4, float par5)
    {
        super(par1World);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = par2EntityLivingBase;
        this.shooterUUID = par2EntityLivingBase.getUniqueID();

        if (par2EntityLivingBase instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;

            if (((EntityPlayer)par2EntityLivingBase).capabilities.isCreativeMode == true)
            {
                this.canBePickedUp = 2;
            }
        }

        this.posY = par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight() - 0.10000000149011612D;
        double d0 = par3EntityLivingBase.posX - par2EntityLivingBase.posX;
        double d1 = par3EntityLivingBase.boundingBox.minY + (double)(par3EntityLivingBase.height / 3.0F) - this.posY;
        double d2 = par3EntityLivingBase.posZ - par2EntityLivingBase.posZ;
        double d3 = (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2);

        if (d3 >= 1.0E-7D)
        {
            float f2 = (float)(Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            float f3 = (float)(-(Math.atan2(d1, d3) * 180.0D / Math.PI));
            double d4 = d0 / d3;
            double d5 = d2 / d3;
            this.setLocationAndAngles(par2EntityLivingBase.posX + d4, this.posY, par2EntityLivingBase.posZ + d5, f2, f3);
            this.yOffset = 0.0F;
            float f4 = (float)d3 * 0.2F;
            this.setThrowableHeading(d0, d1 + (double)f4, d2, par4, par5);
        }
    }

    public EntityEnderArrow(World par1World, EntityLivingBase par2EntityLivingBase, float par3)
    {
        super(par1World);
        this.renderDistanceWeight = 10.0D;
        this.shootingEntity = par2EntityLivingBase;
        this.shooterUUID = par2EntityLivingBase.getUniqueID();

        if (par2EntityLivingBase instanceof EntityPlayer)
        {
            this.canBePickedUp = 1;

            if (((EntityPlayer)par2EntityLivingBase).capabilities.isCreativeMode == true)
            {
                this.canBePickedUp = 2;
            }
        }

        this.setSize(0.5F, 0.5F);
        this.setLocationAndAngles(par2EntityLivingBase.posX, par2EntityLivingBase.posY + (double)par2EntityLivingBase.getEyeHeight(), par2EntityLivingBase.posZ, par2EntityLivingBase.rotationYaw, par2EntityLivingBase.rotationPitch);
        double x, y, z;
        x = this.posX - (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);
        z = this.posZ - (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.16f);

        x -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.74f) * (double)(MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI));
        x -= (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.1f);
        y = this.posY - 0.10000000149011612d;
        z += (double)(MathHelper.cos(this.rotationYaw / 180.0f * (float)Math.PI) * 0.74f) * (double)(MathHelper.cos(this.rotationPitch / 180.0f * (float)Math.PI));
        z -= (double)(MathHelper.sin(this.rotationYaw / 180.0f * (float)Math.PI) * 0.1f);
        if (par1World.getBlock((int)MathHelper.floor_double(x), (int)y, (int)MathHelper.floor_double(z)) == Blocks.air)
        {
            this.posX = x;
            this.posZ = z;
        }
        this.setPosition(this.posX, this.posY, this.posZ);
        this.yOffset = 0.0F;
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI));
        this.motionY = (double)(-MathHelper.sin(this.rotationPitch / 180.0F * (float)Math.PI));
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, par3 * 1.8F, 1.0F);
    }

    public void setTpMode(byte mode)
    {
        this.tpMode = mode;
    }

    public void setPersistence(boolean enabled)
    {
        this.applyPersistence = enabled;
    }

    public void setTpTarget(NBTHelperTarget target)
    {
        this.tpTarget = target;
    }


    public void dropAsItem(boolean doDrop)
    {
        if (this.canBePickedUp != 1 || doDrop == false)
        {
            return;
        }

        EntityItem entityitem = new EntityItem(this.worldObj, this.posX, this.posY, this.posZ, new ItemStack(EnderUtilitiesItems.enderArrow, 1, 0));
        Random r = new Random();

        entityitem.motionX = 0.01d * r.nextGaussian();
        entityitem.motionY = 0.01d * r.nextGaussian() + 0.05d;
        entityitem.motionZ = 0.01d * r.nextGaussian();
        entityitem.delayBeforeCanPickup = 10;

        this.worldObj.spawnEntityInWorld(entityitem);
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        this.onEntityUpdate();

        if (this.prevRotationPitch == 0.0F && this.prevRotationYaw == 0.0F)
        {
            float f = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
            this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);
            this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f) * 180.0D / Math.PI);
        }

        Block block = this.worldObj.getBlock(this.blockX, this.blockY, this.blockZ);

        if (block.getMaterial() != Material.air)
        {
            block.setBlockBoundsBasedOnState(this.worldObj, this.blockX, this.blockY, this.blockZ);
            AxisAlignedBB axisalignedbb = block.getCollisionBoundingBoxFromPool(this.worldObj, this.blockX, this.blockY, this.blockZ);

            if (axisalignedbb != null && axisalignedbb.isVecInside(Vec3.createVectorHelper(this.posX, this.posY, this.posZ)))
            {
                this.inGround = true;
            }
        }

        if (this.arrowShake > 0)
        {
            --this.arrowShake;
        }

        if (this.inGround)
        {
            int j = this.worldObj.getBlockMetadata(this.blockX, this.blockY, this.blockZ);

            if (block == this.inBlock && j == this.inData)
            {
                ++this.ticksInGround;

                if (this.ticksInGround == 1200)
                {
                    this.setDead();
                }
            }
            else
            {
                this.inGround = false;
                this.motionX *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionY *= (double)(this.rand.nextFloat() * 0.2F);
                this.motionZ *= (double)(this.rand.nextFloat() * 0.2F);
                this.ticksInGround = 0;
                this.ticksInAir = 0;
            }

            return;
        }

        ++this.ticksInAir;
        Vec3 vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        Vec3 vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
        MovingObjectPosition movingobjectposition = this.worldObj.func_147447_a(vec31, vec3, false, true, false);
        vec31 = Vec3.createVectorHelper(this.posX, this.posY, this.posZ);
        vec3 = Vec3.createVectorHelper(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);

        if (movingobjectposition != null)
        {
            vec3 = Vec3.createVectorHelper(movingobjectposition.hitVec.xCoord, movingobjectposition.hitVec.yCoord, movingobjectposition.hitVec.zCoord);
        }

        Entity shooter = this.getShooter();
        Entity entity = null;
        List<Entity> list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
        double d0 = 0.0D;
        int i;
        float f1;

        for (i = 0; i < list.size(); ++i)
        {
            Entity entity1 = list.get(i);

            if (entity1.canBeCollidedWith() && EntityUtils.doesEntityStackContainEntity(shooter, entity1) == false)
            {
                f1 = 0.3F;
                AxisAlignedBB axisalignedbb1 = entity1.boundingBox.expand((double)f1, (double)f1, (double)f1);
                MovingObjectPosition movingobjectposition1 = axisalignedbb1.calculateIntercept(vec31, vec3);

                if (movingobjectposition1 != null)
                {
                    double d1 = vec31.distanceTo(movingobjectposition1.hitVec);

                    if (d1 < d0 || d0 == 0.0D)
                    {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        if (entity != null)
        {
            movingobjectposition = new MovingObjectPosition(entity);
        }

        float f2;
        float f4;

        // Hit something
        if (movingobjectposition != null)
        {
            // TP self mode
            if (this.tpMode == ItemEnderBow.BOW_MODE_TP_SELF)
            {
                // Valid shooter
                if (this.worldObj.isRemote == false && shooter != null)
                {
                    if (TeleportEntity.entityTeleportWithProjectile(shooter, this, movingobjectposition, this.teleportDamage, true, true) == true)
                    {
                        this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                    }
                    this.dropAsItem(false);
                    this.setDead();
                }
            }
            // TP target mode, hit an entity
            else if (this.tpMode == ItemEnderBow.BOW_MODE_TP_TARGET && movingobjectposition.entityHit != null)
            {
                if (shooter != null && EntityUtils.doesEntityStackContainEntity(movingobjectposition.entityHit, shooter) == false)
                {
                    this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));

                    if (EntityUtils.doesEntityStackHaveBlacklistedEntities(movingobjectposition.entityHit) == false &&
                        (EntityUtils.doesEntityStackHavePlayers(movingobjectposition.entityHit) == false
                        || Configs.enderBowAllowPlayers.getBoolean(false) == true))
                    {
                        if (this.worldObj.isRemote == false)
                        {
                            this.tpTarget = TeleportEntity.adjustTargetPosition(this.tpTarget, movingobjectposition.entityHit);
                            if (this.tpTarget != null)
                            {
                                Entity e = movingobjectposition.entityHit;
                                if (this.tpTarget.hasAngle == true && entity != null)
                                {
                                    entity.setPositionAndRotation(e.posX, e.posY, e.posZ, this.tpTarget.yaw, this.tpTarget.pitch);
                                }

                                if (this.applyPersistence == true && e instanceof EntityLiving)
                                {
                                    EntityUtils.applyMobPersistence((EntityLiving)e);
                                }

                                TeleportEntity.teleportEntity(e, this.tpTarget.dPosX, this.tpTarget.dPosY, this.tpTarget.dPosZ, this.tpTarget.dimension, true, true);
                            }

                            this.dropAsItem(false);
                            this.setDead();
                        }
                    }
                    // In vanilla: Could not damage the entity (aka. bouncing off an entity)
                    else
                    {
                        this.motionX *= -0.10000000149011612D;
                        this.motionY *= -0.10000000149011612D;
                        this.motionZ *= -0.10000000149011612D;
                        this.rotationYaw += 180.0F;
                        this.prevRotationYaw += 180.0F;
                        this.ticksInAir = 0;
                    }
                }
            }
            // hit something else, so a block
            else
            {
                this.blockX = movingobjectposition.blockX;
                this.blockY = movingobjectposition.blockY;
                this.blockZ = movingobjectposition.blockZ;
                this.inBlock = this.worldObj.getBlock(this.blockX, this.blockY, this.blockZ);
                this.inData = this.worldObj.getBlockMetadata(this.blockX, this.blockY, this.blockZ);
                this.motionX = (double)((float)(movingobjectposition.hitVec.xCoord - this.posX));
                this.motionY = (double)((float)(movingobjectposition.hitVec.yCoord - this.posY));
                this.motionZ = (double)((float)(movingobjectposition.hitVec.zCoord - this.posZ));
                f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
                this.posX -= this.motionX / (double)f2 * 0.05000000074505806D;
                this.posY -= this.motionY / (double)f2 * 0.05000000074505806D;
                this.posZ -= this.motionZ / (double)f2 * 0.05000000074505806D;
                this.playSound("random.bowhit", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
                this.inGround = true;
                this.arrowShake = 7;
                this.setIsCritical(false);

                if (this.inBlock.getMaterial() != Material.air)
                {
                    this.inBlock.onEntityCollidedWithBlock(this.worldObj, this.blockX, this.blockY, this.blockZ, this);
                }
            }
        }

        if (this.getIsCritical())
        {
            for (i = 0; i < 4; ++i)
            {
                this.worldObj.spawnParticle("crit", this.posX + this.motionX * (double)i / 4.0D, this.posY + this.motionY * (double)i / 4.0D, this.posZ + this.motionZ * (double)i / 4.0D, -this.motionX, -this.motionY + 0.2D, -this.motionZ);
            }
        }

        this.posX += this.motionX;
        this.posY += this.motionY;
        this.posZ += this.motionZ;
        f2 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        this.rotationYaw = (float)(Math.atan2(this.motionX, this.motionZ) * 180.0D / Math.PI);

        for (this.rotationPitch = (float)(Math.atan2(this.motionY, (double)f2) * 180.0D / Math.PI); this.rotationPitch - this.prevRotationPitch < -180.0F; this.prevRotationPitch -= 360.0F)
        {
            ;
        }

        while (this.rotationPitch - this.prevRotationPitch >= 180.0F) { this.prevRotationPitch += 360.0F; }
        while (this.rotationYaw - this.prevRotationYaw < -180.0F) { this.prevRotationYaw -= 360.0F; }
        while (this.rotationYaw - this.prevRotationYaw >= 180.0F) { this.prevRotationYaw += 360.0F; }
        this.rotationPitch = this.prevRotationPitch + (this.rotationPitch - this.prevRotationPitch) * 0.2F;
        this.rotationYaw = this.prevRotationYaw + (this.rotationYaw - this.prevRotationYaw) * 0.2F;
        float f3 = 0.99F;
        f1 = 0.05F;

        if (this.isInWater())
        {
            for (int l = 0; l < 4; ++l)
            {
                f4 = 0.25F;
                this.worldObj.spawnParticle("bubble", this.posX - this.motionX * (double)f4, this.posY - this.motionY * (double)f4, this.posZ - this.motionZ * (double)f4, this.motionX, this.motionY, this.motionZ);
            }
            f3 = 0.8F;
        }

        if (this.isWet())
        {
            this.extinguish();
        }

        this.motionX *= (double)f3;
        this.motionY *= (double)f3;
        this.motionZ *= (double)f3;
        this.motionY -= (double)f1;
        this.setPosition(this.posX, this.posY, this.posZ);
        this.func_145775_I();
    }

    /**
     * (abstract) Protected helper method to write subclass entity data to NBT.
     */
    public void writeEntityToNBT(NBTTagCompound tagCompound)
    {
        tagCompound.setInteger("xTile", (short)this.blockX);
        tagCompound.setInteger("yTile", (short)this.blockY);
        tagCompound.setInteger("zTile", (short)this.blockZ);
        tagCompound.setInteger("inTile", Block.getIdFromBlock(this.inBlock));
        tagCompound.setByte("inData", (byte)this.inData);
        tagCompound.setShort("life", (short)this.ticksInGround);
        tagCompound.setByte("shake", (byte)this.arrowShake);
        tagCompound.setByte("inGround", (byte)(this.inGround ? 1 : 0));
        tagCompound.setByte("pickup", (byte)this.canBePickedUp);;
        tagCompound.setLong("shooterUUIDMost", this.shooterUUID.getMostSignificantBits());
        tagCompound.setLong("shooterUUIDLeast", this.shooterUUID.getLeastSignificantBits());
        if (this.tpTarget != null)
        {
            this.tpTarget.writeToNBT(tagCompound);
        }
        tagCompound.setByte("tpMode", this.tpMode);
        tagCompound.setBoolean("Persistence", this.applyPersistence);
    }

    /**
     * (abstract) Protected helper method to read subclass entity data from NBT.
     */
    public void readEntityFromNBT(NBTTagCompound tagCompound)
    {
        this.blockX = tagCompound.getInteger("xTile");
        this.blockY = tagCompound.getInteger("yTile");
        this.blockZ = tagCompound.getInteger("zTile");
        this.inBlock = Block.getBlockById(tagCompound.getInteger("inTile"));
        this.inData = tagCompound.getByte("inData") & 0xF;
        this.ticksInGround = tagCompound.getShort("life");
        this.arrowShake = tagCompound.getByte("shake") & 255;
        this.inGround = tagCompound.getByte("inGround") == 1;
        if (tagCompound.hasKey("pickup", Constants.NBT.TAG_ANY_NUMERIC))
        {
            this.canBePickedUp = tagCompound.getByte("pickup");
        }
        else if (tagCompound.hasKey("player", Constants.NBT.TAG_ANY_NUMERIC))
        {
            this.canBePickedUp = tagCompound.getBoolean("player") ? 1 : 0;
        }
        if (tagCompound.hasKey("shooterUUIDMost", Constants.NBT.TAG_LONG) && tagCompound.hasKey("shooterUUIDLeast", Constants.NBT.TAG_LONG))
        {
            this.shooterUUID = new UUID(tagCompound.getLong("shooterUUIDMost"), tagCompound.getLong("shooterUUIDLeast"));
            this.shootingEntity = this.worldObj.func_152378_a(this.shooterUUID);
        }
        this.tpTarget = NBTHelperTarget.readTargetFromNBT(tagCompound);
        this.tpMode = tagCompound.getByte("tpMode");
        this.applyPersistence = tagCompound.getBoolean("Persistence");
    }

    /**
     * Called by a player entity when they collide with an entity
     */
    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer)
    {
        if (this.worldObj.isRemote == false && this.isDead == false && this.inGround == true && this.arrowShake <= 0 && this.canBePickedUp != 0)
        {
            // Normal pick up to inventory
            if (this.canBePickedUp == 1)
            {
                if (par1EntityPlayer.inventory.addItemStackToInventory(new ItemStack(EnderUtilitiesItems.enderArrow, 1)) == true)
                {
                    this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                    par1EntityPlayer.onItemPickup(this, 1);
                    this.setDead();
                }
            }
            // Creative mode fake pick up (no actual items given)
            else if (this.canBePickedUp == 2)
            {
                this.playSound("random.pop", 0.2F, ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F);
                this.setDead();
            }
        }
    }

    public EntityLivingBase getShooter()
    {
        if (this.shootingEntity == null && this.shooterUUID != null)
        {
            this.shootingEntity = this.worldObj.func_152378_a(this.shooterUUID);
        }

        return this.shootingEntity;
    }
}
