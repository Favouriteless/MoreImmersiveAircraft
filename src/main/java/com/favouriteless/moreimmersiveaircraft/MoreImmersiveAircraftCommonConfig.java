package com.favouriteless.moreimmersiveaircraft;

import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.ConfigValue;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber.Bus;

@EventBusSubscriber(modid=MoreImmersiveAircraft.MODID, bus= Bus.MOD)
public class MoreImmersiveAircraftCommonConfig {

	public static final ForgeConfigSpec SPEC;

	public static final ConfigValue<Boolean> ENABLE_CRASH_EXPLOSION;
	public static final ConfigValue<Boolean> CRASH_BLOCK_DESTRUCTION;
	public static final ConfigValue<Double> CRASH_EXPLOSION_RADIUS;
	public static final ConfigValue<Boolean> CRASH_EXPLOSION_FIRE;


	static {
		ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();

		builder.push("Engined Vehicle Crashes");
		ENABLE_CRASH_EXPLOSION = builder.comment("Enable engined vehicles exploding when they crash. #default true").define("enable_crash_explosion", true);
		CRASH_BLOCK_DESTRUCTION = builder.comment("Enable blocks being destroyed by the explosion. #default true").define("crash_block_explosion", true);
		CRASH_EXPLOSION_RADIUS = builder.comment("Explosion radius for crashing vehicles. #default 2.0").define("crash_explosion_radius", 2.0D);
		CRASH_EXPLOSION_FIRE = builder.comment("Crash explosions generate fire. #default true").define("crash_explosion_fire", true);
		builder.pop();

		SPEC = builder.build();
	}

}