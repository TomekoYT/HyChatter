package tomeko.hychatter.event;

//? if 1.8.9 {
/*
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.NetworkManager;

public final class ClientPlayConnectionEvents {
    private ClientPlayConnectionEvents() {}

    public static final Event<Init> INIT = Event.create(
            Init.class,
            listeners -> (handler, connection, client) -> {
                for (Init listener : listeners) {
                    listener.onInit(handler, connection, client);
                }
            }
    );

    public static final Event<Join> JOIN = Event.create(
            Join.class,
            listeners -> (handler, client) -> {
                for (Join listener : listeners) {
                    listener.onJoin(handler, client);
                }
            }
    );

    public static final Event<Disconnect> DISCONNECT = Event.create(
            Disconnect.class,
            listeners -> (handler, client) -> {
                for (Disconnect listener : listeners) {
                    listener.onDisconnect(handler, client);
                }
            }
    );

    public interface Init {
        void onInit(NetHandlerPlayClient handler, NetworkManager connection, Minecraft client);
    }

    public interface Join {
        void onJoin(NetHandlerPlayClient handler, Minecraft client);
    }

    public interface Disconnect {
        void onDisconnect(NetHandlerPlayClient handler, Minecraft client);
    }
}
*///?}