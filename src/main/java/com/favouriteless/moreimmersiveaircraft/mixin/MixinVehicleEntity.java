package com.favouriteless.moreimmersiveaircraft.mixin;

import com.favouriteless.moreimmersiveaircraft.MIACommonConfig;
import immersive_aircraft.config.Config;
import immersive_aircraft.entity.EngineAircraft;
import immersive_aircraft.entity.VehicleEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level.ExplosionInteraction;
import net.minecraft.world.level.gameevent.GameEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VehicleEntity.class)
public class MixinVehicleEntity {

	@Inject(method = "hurt", at=@At(value = "INVOKE",
			target = "Limmersive_aircraft/entity/VehicleEntity;gameEvent(Lnet/minecraft/world/level/gameevent/GameEvent;Lnet/minecraft/world/entity/Entity;)V",
			shift = Shift.AFTER),
			cancellable = true)
	private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		VehicleEntity asVehicle = (VehicleEntity)(Object)this;

		if(source.getEntity() instanceof Player player) {
			if(!player.getAbilities().instabuild)
				asVehicle.spawnAtLocation(asVehicle.asItem());
			asVehicle.discard();
		}
		else if(asVehicle.getDamageWobbleStrength() > 40.0F) {
			if(MIACommonConfig.ENABLE_CRASH_EXPLOSION.get() && ((VehicleEntity)(Object)this) instanceof EngineAircraft aircraft) {
				aircraft.level().explode(aircraft, aircraft.getX(), aircraft.getY(), aircraft.getZ(),
						MIACommonConfig.CRASH_EXPLOSION_RADIUS.get().floatValue(),
						MIACommonConfig.CRASH_EXPLOSION_FIRE.get(),
						MIACommonConfig.CRASH_BLOCK_DESTRUCTION.get() ? ExplosionInteraction.MOB : ExplosionInteraction.NONE);
			}
			if(MIACommonConfig.DROP_VEHICLES.get())
				asVehicle.spawnAtLocation(asVehicle.asItem());

			asVehicle.discard();
		}

		cir.setReturnValue(true);
	}

}
