package com.favouriteless.moreimmersiveaircraft;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import immersive_aircraft.item.UpgradeItem;
import immersive_aircraft.item.upgrade.AircraftStat;
import immersive_aircraft.item.upgrade.AircraftUpgrade;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class UpgradeReloadListener extends SimpleJsonResourceReloadListener {

	private static final Gson GSON = new Gson();
	private static final Map<String, AircraftStat> statKeys = new HashMap<>();

	static {
		statKeys.put("strength", AircraftStat.STRENGTH);
		statKeys.put("acceleration", AircraftStat.ACCELERATION);
		statKeys.put("durability", AircraftStat.DURABILITY);
		statKeys.put("wind", AircraftStat.WIND);
		statKeys.put("friction", AircraftStat.FRICTION);
		statKeys.put("fuel", AircraftStat.FUEL);
	}

	public UpgradeReloadListener(String directory) {
		super(GSON, directory);
	}

	@Override
	protected void apply(Map<ResourceLocation, JsonElement> jsonMap, ResourceManager resourceManager, ProfilerFiller profiler) {
		jsonMap.forEach((resourceLocation, jsonElement) -> {
			try {
				JsonObject jsonObject = jsonElement.getAsJsonObject();

				if(ForgeRegistries.ITEMS.getValue(resourceLocation) instanceof UpgradeItem item) {
					AircraftUpgrade upgrade = item.getUpgrade();
					upgrade.getAll().clear();
					for(String key : jsonObject.keySet()) {
						AircraftStat stat = statKeys.get(key);
						if(stat != null)
							upgrade.set(stat, jsonObject.get(key).getAsFloat());
					}
				}

			} catch (IllegalArgumentException | JsonParseException jsonparseexception) {
				MoreImmersiveAircraft.LOGGER.error("Parsing error on aircraft upgrade {}: {}", resourceLocation, jsonparseexception.getMessage());
			}
		});
	}

}