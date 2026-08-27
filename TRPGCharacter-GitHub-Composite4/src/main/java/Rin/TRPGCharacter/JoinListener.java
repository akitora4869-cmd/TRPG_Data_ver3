package Rin.TRPGCharacter;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class JoinListener implements Listener {

    private final Plugin plugin;
    private final BookManager bookManager;

    public JoinListener(Plugin plugin, BookManager bookManager) {
        this.plugin = plugin;
        this.bookManager = bookManager;
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (event.getPlayer().hasPlayedBefore()) {
            return;
        }

        for (ItemStack item : event.getPlayer().getInventory().getContents()) {
            if (bookManager.isCharacterSheet(item)) {
                return;
            }
        }

        plugin.getServer().getScheduler().runTaskLater(
                plugin,
                () -> {
                    event.getPlayer().getInventory().addItem(bookManager.createSheet(event.getPlayer()));
                    event.getPlayer().sendMessage("§6[TRPG] §a探索者シートを配布しました。");
                },
                20L
        );
    }
}
