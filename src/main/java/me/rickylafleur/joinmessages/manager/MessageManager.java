package me.rickylafleur.joinmessages.manager;

import io.github.johnnypixelz.utilizer.config.Configs;
import me.rickylafleur.joinmessages.impl.Message;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageManager {

    private MessageManager() {}
    private static Map<String, Message> messages;

    public static void load() {
        if (messages != null) {
            messages.clear();
        } else {
            messages = new HashMap<>();
        }

        ConfigurationSection config = Configs.get("config").getConfigurationSection("join-messages");

        for (String key : config.getKeys(false)) {
            final ConfigurationSection messageSection = config.getConfigurationSection(key);

            final String identifier = messageSection.getString("identifier", key);
            final String permission = messageSection.getString("permission", "");
            final List<String> joinMessage = messageSection.getStringList("message");
            final String material = messageSection.getString("material", "BOOK");
            final String name = messageSection.getString("name", identifier);
            final List<String> lore = messageSection.getStringList("lore");
            final int customModelData = messageSection.getInt("customModelData", 0);

            final Message message = new Message(identifier, permission, joinMessage, Material.matchMaterial(material), name, lore, customModelData);
            messages.put(key, message);
        }
    }

    public static Message getMessage(String identifier) {
        return messages.getOrDefault(identifier, null);
    }

    public static Collection<Message> getMessages() {
        return messages.values();
    }
}
