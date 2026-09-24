package tomeko.hychatter.event;

//? if ornithe {
/*public final class ClientSendMessageEvents {
    private ClientSendMessageEvents() {}

    public static final Event<AllowChat> ALLOW_CHAT = Event.create(AllowChat.class, listeners -> message -> {
        for (AllowChat listener : listeners) {
            if (!listener.allowSendChatMessage(message)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<AllowCommand> ALLOW_COMMAND = Event.create(AllowCommand.class, listeners -> command -> {
        for (AllowCommand listener : listeners) {
            if (!listener.allowSendCommandMessage(command)) {
                return false;
            }
        }
        return true;
    });

    public static final Event<ModifyChat> MODIFY_CHAT = Event.create(ModifyChat.class, listeners -> message -> {
        for (ModifyChat listener : listeners) {
            message = listener.modifySendChatMessage(message);
            if (message == null) {
                return null;
            }
        }
        return message;
    });

    public static final Event<ModifyCommand> MODIFY_COMMAND = Event.create(ModifyCommand.class, listeners -> command -> {
        for (ModifyCommand listener : listeners) {
            command = listener.modifySendCommandMessage(command);
            if (command == null) {
                return null;
            }
        }
        return command;
    });

    public static final Event<Chat> CHAT = Event.create(Chat.class, listeners -> message -> {
        for (Chat listener : listeners) {
            listener.onSendChatMessage(message);
        }
    });

    public static final Event<Command> COMMAND = Event.create(Command.class, listeners -> command -> {
        for (Command listener : listeners) {
            listener.onSendCommandMessage(command);
        }
    });

    @FunctionalInterface
    public interface AllowChat {
        boolean allowSendChatMessage(String message);
    }

    @FunctionalInterface
    public interface AllowCommand {
        boolean allowSendCommandMessage(String command);
    }

    @FunctionalInterface
    public interface ModifyChat {
        String modifySendChatMessage(String message);
    }

    @FunctionalInterface
    public interface ModifyCommand {
        String modifySendCommandMessage(String command);
    }

    @FunctionalInterface
    public interface Chat {
        void onSendChatMessage(String message);
    }

    @FunctionalInterface
    public interface Command {
        void onSendCommandMessage(String command);
    }
}
*///?}