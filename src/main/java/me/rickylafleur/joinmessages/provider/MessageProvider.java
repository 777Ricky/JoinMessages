package me.rickylafleur.joinmessages.provider;

import io.github.johnnypixelz.utilizer.config.Configs;
import io.github.johnnypixelz.utilizer.config.Message;
import io.github.johnnypixelz.utilizer.config.Messages;
import io.github.johnnypixelz.utilizer.itemstack.Items;
import io.github.johnnypixelz.utilizer.itemstack.PaneType;
import io.github.johnnypixelz.utilizer.itemstack.PremadeItems;
import io.github.johnnypixelz.utilizer.shade.smartinvs.ClickableItem;
import io.github.johnnypixelz.utilizer.shade.smartinvs.SmartInventory;
import io.github.johnnypixelz.utilizer.shade.smartinvs.content.*;
import io.github.johnnypixelz.utilizer.shade.xseries.XMaterial;
import io.github.johnnypixelz.utilizer.text.Colors;
import me.rickylafleur.joinmessages.impl.User;
import me.rickylafleur.joinmessages.manager.MessageManager;
import me.rickylafleur.joinmessages.manager.UserManager;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.Objects;
import java.util.Optional;

public class MessageProvider implements InventoryProvider {

    private static final SlotPos PREVIOUS_PAGE_POS = SlotPos.of(4, 0);
    private static final SlotPos NEXT_PAGE_POS = SlotPos.of(4, 8);

    public static void open(Player player) {
        Messages.cfg("messages.commands.open").send(player);

        SmartInventory.builder()
                .provider(new MessageProvider())
                .size(5, 9)
                .title(Colors.color(Configs.get("config").getString("menu.title", "Select your join message")))
                .build()
                .open(player);
    }

    @Override
    public void init(Player player, InventoryContents contents) {
        if (Configs.get("config").getBoolean("menu.border-glass")) {
            contents.fillBorders(ClickableItem.empty(PremadeItems.getCustomPane(PaneType.BLACK)));
        }

        final Pagination pagination = contents.pagination();
        pagination.setItemsPerPage((contents.inventory().getRows() - 2) * 7);

        pagination.setItems(MessageManager.getMessages().stream()
                .map(message -> {
                    final ItemStack stack = message.getGUIStack(player);

                    return ClickableItem.of(stack, e -> {
                        final User user = UserManager.getUser(player);

                        if (!player.hasPermission(message.getPermission()) && !message.getPermission().isEmpty()) {
                            Messages.cfg("messages.menu.no-permission")
                                    .map(s -> s.replace("{message}", message.getIdentifier()))
                                    .send(player);
                            return;
                        }

                        user.setMessage(message.getIdentifier());
                        Messages.cfg("messages.menu.message-selected")
                                .map(s -> s.replace("{message}", message.getIdentifier()))
                                .send(player);
                    });
                })
                .toArray(ClickableItem[]::new));

        pagination.addToIterator(contents.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1).allowOverride(false));

        if (!pagination.isFirst()) {
            contents.set(PREVIOUS_PAGE_POS, ClickableItem.of(getItemStack("previous-page"), e -> {
                contents.inventory().open(player, pagination.previous().getPage());
            }));
        }

        if (!pagination.isLast()) {
            contents.set(NEXT_PAGE_POS, ClickableItem.of(getItemStack("next-page"), e -> {
                contents.inventory().open(player, pagination.next().getPage());
            }));
        }
    }

    private ItemStack getItemStack(String configStack) {
        ConfigurationSection guiConfig = Configs.get("config").getConfigurationSection("menu." + configStack);
        Optional<XMaterial> xMaterial = XMaterial.matchXMaterial(guiConfig.getString("material"));
        ItemStack stack = xMaterial.isPresent() ? xMaterial.get().parseItem() : new ItemStack(Material.BOOK);

        Objects.requireNonNull(stack);

        Items.setDisplayName(stack, guiConfig.getString("name"));
        Items.setLore(stack, guiConfig.getStringList("lore"));
        Items.meta(stack, meta -> meta.setCustomModelData(guiConfig.getInt("customModelData", 0)));

        return stack;
    }
}
