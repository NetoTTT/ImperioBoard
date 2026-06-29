/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  net.minecraft.server.v1_8_R3.IChatBaseComponent
 *  net.minecraft.server.v1_8_R3.IChatBaseComponent$ChatSerializer
 *  net.minecraft.server.v1_8_R3.Packet
 *  net.minecraft.server.v1_8_R3.PacketPlayOutChat
 *  net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter
 *  net.minecraft.server.v1_8_R3.PacketPlayOutTitle
 *  net.minecraft.server.v1_8_R3.PacketPlayOutTitle$EnumTitleAction
 *  org.bukkit.Bukkit
 *  org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
 *  org.bukkit.entity.Player
 */
package com.hask.scoreboard.Utils;

import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PacketWrapper {
    public static void wrapPlayerList(Player player, String header, String footer) {
        IChatBaseComponent headerJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + header + "'}"));
        IChatBaseComponent footerJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + footer + "'}"));
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            Field a = packet.getClass().getDeclaredField("a");
            a.setAccessible(true);
            a.set(packet, headerJSON);
            Field b = packet.getClass().getDeclaredField("b");
            b.setAccessible(true);
            b.set(packet, footerJSON);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        PacketWrapper.sendPacket(packet, player);
    }

    public static void wrapTitle(Player player, String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + title + "'}"));
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + subtitle + "'}"));
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
        PacketWrapper.sendPacket(titlePacket, player);
        PacketWrapper.sendPacket(subtitlePacket, player);
    }

    public static void wrapActionBar(Player player, String msg) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + msg + "\"}"));
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
        PacketWrapper.sendPacket(ppoc, player);
    }

    public static void wrapTitle(String title, String subtitle, int fadeIn, int stay, int fadeOut) {
        IChatBaseComponent titleJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + title + "'}"));
        IChatBaseComponent subtitleJSON = IChatBaseComponent.ChatSerializer.a((String)("{'text': '" + subtitle + "'}"));
        PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, titleJSON, fadeIn, stay, fadeOut);
        PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, subtitleJSON);
        PacketWrapper.sendPacket(titlePacket);
        PacketWrapper.sendPacket(subtitlePacket);
    }

    public static void wrapActionBar(String msg) {
        IChatBaseComponent cbc = IChatBaseComponent.ChatSerializer.a((String)("{\"text\": \"" + msg + "\"}"));
        PacketPlayOutChat ppoc = new PacketPlayOutChat(cbc, (byte)2);
        PacketWrapper.sendPacket(ppoc);
    }

    private static void sendPacket(Packet<?> packet, Player player) {
        ((CraftPlayer)player).getHandle().playerConnection.sendPacket(packet);
    }

    private static void sendPacket(Packet<?> packet) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            PacketWrapper.sendPacket(packet, player);
        }
    }
}

