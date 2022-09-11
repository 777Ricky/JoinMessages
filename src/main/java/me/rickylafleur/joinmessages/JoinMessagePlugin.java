package me.rickylafleur.joinmessages;

import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import io.github.johnnypixelz.utilizer.config.Configs;
import io.github.johnnypixelz.utilizer.plugin.UtilPlugin;
import me.rickylafleur.joinmessages.command.JoinMessageCommand;
import me.rickylafleur.joinmessages.listener.PlayerListener;
import me.rickylafleur.joinmessages.manager.MessageManager;
import me.rickylafleur.joinmessages.manager.UserManager;
import org.bukkit.ChatColor;

public class JoinMessagePlugin extends UtilPlugin {

    @Override
    public void onEnable() {
        Configs.load("config").watch().onReload(MessageManager::load);
        MessageManager.load();
        UserManager.load();

        registerCommands();

        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
    }

    @Override
    public void onDisable() {
        UserManager.save();
    }

    private void registerCommands() {
        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.setFormat(MessageType.SYNTAX, ChatColor.RED, ChatColor.WHITE);
        commandManager.registerCommand(new JoinMessageCommand());
    }
}
