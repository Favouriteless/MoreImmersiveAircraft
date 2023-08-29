package com.favouriteless.moreimmersiveaircraft.mixin;

import com.favouriteless.moreimmersiveaircraft.MoreImmersiveAircraftCommonConfig;
import immersive_aircraft.entity.EngineAircraft;
import immersive_aircraft.entity.VehicleEntity;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level.ExplosionInteraction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VehicleEntity.class)
public class MixinVehicleEntity {

	@Inject(method = "hurt", at=@At(value = "INVOKE", target = "Limmersive_aircraft/entity/VehicleEntity;discard()V"))
	private void hurt(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
		if(MoreImmersiveAircraftCommonConfig.ENABLE_CRASH_EXPLOSION.get() && !(source.getEntity() instanceof Player) && ((VehicleEntity)(Object)this) instanceof EngineAircraft aircraft) {
			aircraft.level().explode(aircraft, aircraft.getX(), aircraft.getY(), aircraft.getZ(),
					MoreImmersiveAircraftCommonConfig.CRASH_EXPLOSION_RADIUS.get().floatValue(),
					MoreImmersiveAircraftCommonConfig.CRASH_EXPLOSION_FIRE.get(),
					MoreImmersiveAircraftCommonConfig.CRASH_BLOCK_DESTRUCTION.get() ? ExplosionInteraction.MOB : ExplosionInteraction.NONE);

		}
	}

}
