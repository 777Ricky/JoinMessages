package me.rickylafleur.joinmessages.manager;

import io.github.johnnypixelz.utilizer.file.storage.Storage;
import io.github.johnnypixelz.utilizer.file.storage.StorageContainer;
import me.rickylafleur.joinmessages.impl.User;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class UserManager {

    private UserManager() {}

    private static StorageContainer<Map<UUID, User>> users;

    public static void load() {
         users = Storage.map(UUID.class, User.class)
                .json("users")
                .container(HashMap::new);

         users.autoSave(1, TimeUnit.MINUTES);
    }

    public static void save() {
        users.save();
    }

    public static User getUser(UUID uniqueId) {
        return users.get().computeIfAbsent(uniqueId, t -> new User(uniqueId));
    }

    public static User getUser(Player player) {
        return getUser(player.getUniqueId());
    }

}
