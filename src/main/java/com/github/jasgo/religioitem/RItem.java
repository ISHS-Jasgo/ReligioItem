package com.github.jasgo.religioitem;

import com.github.jasgo.religioitem.ench.Glow;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;
import java.util.Map;

public class RItem {
    private String name;
    private Material type;
    private String displayname;
    private List<String> lore;
    private List<ItemFlag> flags;
    private boolean glow;
    private Map<Enchantment, Integer> enchantments;
    private boolean isUnbreakable;
    private int durability;
    private String owner;

    public RItem(String name, Material type, String displayname, List<String> lore, List<ItemFlag> flags, boolean glow, Map<Enchantment, Integer> enchantments, boolean isUnbreakable, int durability, String owner) {
        this.name = name;
        this.type = type;
        this.displayname = displayname;
        this.lore = lore;
        this.flags = flags;
        this.glow = glow;
        this.enchantments = enchantments;
        this.isUnbreakable = isUnbreakable;
        this.durability = durability;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Material getType() {
        return type;
    }

    public void setType(Material type) {
        this.type = type;
    }

    public String getDisplayname() {
        return displayname;
    }

    public void setDisplayname(String displayname) {
        this.displayname = displayname;
    }

    public List<String> getLore() {
        return lore;
    }

    public void setLore(List<String> lore) {
        this.lore = lore;
    }

    public List<ItemFlag> getFlags() {
        return flags;
    }

    public void setFlags(List<ItemFlag> flags) {
        this.flags = flags;
    }

    public Map<Enchantment, Integer> getEnchantments() {
        return enchantments;
    }

    public void setEnchantments(Map<Enchantment, Integer> enchantments) {
        this.enchantments = enchantments;
    }

    public boolean isUnbreakable() {
        return isUnbreakable;
    }

    public void setUnbreakable(boolean unbreakable) {
        isUnbreakable = unbreakable;
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public boolean isGlow() {
        return glow;
    }

    public void setGlow(boolean glow) {
        this.glow = glow;
    }
}
