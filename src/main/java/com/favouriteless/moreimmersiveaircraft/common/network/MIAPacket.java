package com.favouriteless.moreimmersiveaircraft.common.network;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public interface MIAPacket {

	void encode(FriendlyByteBuf buffer);
	void handle(Supplier<Context> context);

}
