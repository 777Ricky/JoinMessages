package me.rickylafleur.joinmessages.listener;

import io.github.johnnypixelz.utilizer.config.Configs;
import io.github.johnnypixelz.utilizer.text.Colors;
import me.rickylafleur.joinmessages.JoinMessagePlugin;
import me.rickylafleur.joinmessages.impl.Message;
import me.rickylafleur.joinmessages.impl.User;
import me.rickylafleur.joinmessages.manager.MessageManager;
import me.rickylafleur.joinmessages.manager.UserManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerListener implements Listener {

    private final JoinMessagePlugin plugin;

    public PlayerListener(JoinMessagePlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (Configs.get("config").getBoolean("settings.override-all-join-messages", true)) {
            e.setJoinMessage(null);
        }

        final User user = UserManager.getUser(e.getPlayer());

        if (user.getMessage().isEmpty()) return;

        final Message message = MessageManager.getMessage(user.getMessage());

        if (message == null) return;

        message.getMessage().stream()
                .map(Colors::color)
                .map(s -> s.replace("{player}", e.getPlayer().getName()))
                .forEach(s -> plugin.getServer().getOnlinePlayers().forEach(player -> player.sendMessage(s)));
    }
}
