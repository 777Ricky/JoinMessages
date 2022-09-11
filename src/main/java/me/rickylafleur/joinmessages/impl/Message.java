package me.rickylafleur.joinmessages.impl;

import io.github.johnnypixelz.utilizer.itemstack.Items;
import io.github.johnnypixelz.utilizer.shade.xseries.XMaterial;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Message {
    private final String identifier;
    private final String permission;
    private final List<String> message;
    private final Material material;
    private final String name;
    private final List<String> lore;
    private final int customModelData;

    public Message(String identifier, String permission, List<String> message, Material material, String name, List<String> lore, int customModelData) {
        this.identifier = identifier;
        this.permission = permission;
        this.message = message;
        this.material = material;
        this.name = name;
        this.lore = lore;
        this.customModelData = customModelData;
    }

    public String getIdentifier() {
        return identifier;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getMessage() {
        return message;
    }

    public String getName() {
        return name;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getCustomModelData() {
        return customModelData;
    }

    public XMaterial getMaterial() {
        if (material == null) return XMaterial.BOOK;

        return XMaterial.matchXMaterial(material);
    }

    public ItemStack getGUIStack(Player player) {
        return Items.edit(getMaterial().parseItem())
                .setDisplayName(name)
                .setLore(lore.stream().map(s -> s.replace("{player}", player.getName())).toList())
                .meta(meta -> meta.setCustomModelData(customModelData))
                .getItem();
    }
}
