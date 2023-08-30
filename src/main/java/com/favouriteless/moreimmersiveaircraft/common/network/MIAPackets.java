package com.favouriteless.moreimmersiveaircraft.common.network;

import com.favouriteless.moreimmersiveaircraft.MoreImmersiveAircraft;
import com.favouriteless.moreimmersiveaircraft.common.network.packets.MIAClientboundUpgradePacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.chunk.LevelChunk;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class MIAPackets {

	private static final String PROTOCOL_VERSION = "1";
	public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(
			new ResourceLocation(MoreImmersiveAircraft.MODID, "main"),
			() -> PROTOCOL_VERSION,
			PROTOCOL_VERSION::equals,
			PROTOCOL_VERSION::equals
	);

	public static void registerPackets() {
		int id = 0;
		INSTANCE.registerMessage(id++, MIAClientboundUpgradePacket.class, MIAClientboundUpgradePacket::encode, MIAClientboundUpgradePacket::decode, MIAClientboundUpgradePacket::handle);

	}

	public static void sendToPlayer(MIAPacket packet, ServerPlayer player) {
		INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), packet);
	}

	public static void sendToAllPlayers(MIAPacket packet) {
		INSTANCE.send(PacketDistributor.ALL.noArg(), packet);
	}

	public static void sendToTrackingChunk(MIAPacket packet, LevelChunk chunk) {
		INSTANCE.send(PacketDistributor.TRACKING_CHUNK.with(() -> chunk), packet);
	}

	public static void sendToServer(MIAPacket packet) {
		INSTANCE.sendToServer(packet);
	}

	public static void sendToLevel(MIAPacket packet, ServerLevel level) {
		INSTANCE.send(PacketDistributor.DIMENSION.with(level::dimension), packet);
	}

}
