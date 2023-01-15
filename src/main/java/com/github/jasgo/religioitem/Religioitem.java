package com.github.jasgo.religioitem;

import com.github.jasgo.religioitem.ench.Glow;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public final class Religioitem extends JavaPlugin {

    public static FileConfiguration config;
    public List<RItem> rItemList = new ArrayList<>();
    NamespacedKey key = new NamespacedKey(this, "glow");
    public Glow glow = new Glow(key);

    @Override
    public void onEnable() {
        if (!new File(getDataFolder() + "/Data/").exists()) new File(getDataFolder() + "/Data/").mkdirs();
        registerGlow();
        loadData();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (label.equalsIgnoreCase("ri") && sender.isOp()) {
            sender.sendMessage(((Player) sender).getInventory().getItemInMainHand().getItemMeta().hasEnchant(glow) ? "true" : "false");
            if (args[0].equalsIgnoreCase("reload")) {
                loadData();
            }
            else if (args[0].equalsIgnoreCase("get")) {
                if (args.length == 2) {
                    ((Player) sender).getInventory().addItem(getItem(args[1]));
                }
            }
        }
        return false;
    }

    public void loadData() {
        File[] files = new File(getDataFolder() + "/Data/").listFiles();
        if (files.length > 0) {
            for (File f : files) {
                FileConfiguration config = YamlConfiguration.loadConfiguration(f);
                String name = (String) config.getKeys(false).toArray()[0];
                Material type = Material.getMaterial(Objects.requireNonNull(config.getString(name + ".type")));
                String displayname = config.getString(name + ".display-name");
                List<String> lore = config.getStringList(name + ".lore");
                List<ItemFlag> flags = new ArrayList<>();
                config.getStringList(name + ".flags").forEach(flag -> {
                    flags.add(ItemFlag.valueOf(flag.toUpperCase()));
                });
                boolean glow = config.getBoolean(name + ".glow");
                Map<Enchantment, Integer> enchantments = new HashMap<>();
                config.getMapList(name + ".enchantments").forEach((enchantment) -> {
                    enchantments.put(Enchantment.getByName((String) enchantment.keySet().toArray()[0]), (Integer) enchantment.get(enchantment.keySet().toArray()[0]));
                });
                boolean unbreakable = config.getBoolean(name + ".unbreakable");
                int durability = config.getInt(name + ".durability");
                String owner = null;
                if (type.equals(Material.PLAYER_HEAD)) {
                    owner = config.getString(name + ".owner");
                }
                RItem ritem = new RItem(name, type, displayname, lore, flags, glow, enchantments, unbreakable, durability, owner);
                rItemList.add(ritem);
            }
        }
    }

    public void registerGlow() {
        try {
            Field f = Enchantment.class.getDeclaredField("acceptingNew");
            f.setAccessible(true);
            f.set(null, true);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        try {
            NamespacedKey key = new NamespacedKey(Religioitem.getPlugin(Religioitem.class), "glow");
            Glow glow = new Glow(key);
            Enchantment.registerEnchantment(glow);
        }
        catch (IllegalArgumentException ignored){
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
    public ItemStack getItem(String name) {
        AtomicReference<ItemStack> itemStack = new AtomicReference<>();
        rItemList.forEach(rItem -> {
            if(Objects.equals(rItem.getName(), name)) {
                ItemStack item = new ItemStack(rItem.getType());
                ItemMeta meta = item.getItemMeta();
                if (rItem.getDisplayname() != null) meta.setDisplayName(rItem.getDisplayname());
                if (rItem.getLore() != null) meta.setLore(rItem.getLore());
                if (rItem.getFlags().size() > 0) meta.addItemFlags(rItem.getFlags().toArray(ItemFlag[]::new));
                if (rItem.isGlow()) item.addEnchantment(glow, 1);
                if (rItem.getEnchantments().size() > 0) item.addEnchantments(rItem.getEnchantments());
                meta.setUnbreakable(rItem.isUnbreakable());
                ((Damageable) meta).setDamage(rItem.getType().getMaxDurability() - rItem.getDurability());
                item.setItemMeta(meta);
                itemStack.set(item);
            }
        });
        return itemStack.get();
    }
}
