/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soloskyblock.dailybonus;

import java.util.logging.Logger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.entity.Player;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;
import java.util.logging.Level;
import static java.lang.Integer.parseInt;

import org.bukkit.OfflinePlayer;
import static java.lang.Long.parseLong;
import me.badbones69.crazyauctions.currency.Vault;

/**
 *
 * @author Fraspace5
 */
public class DailyBonus extends JavaPlugin implements Listener {

    Logger Logger = Bukkit.getLogger();
    ConsoleCommandSender clogger = this.getServer().getConsoleSender();

    public static HashMap<String, BonusData> bonus = new HashMap<>();

    public static HashMap<UUID, Integer> players = new HashMap<>();

    private File data;

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults();
        Bukkit.getPluginManager().registerEvents(this, this);
        try {
            onInitiateFile();
        } catch (IOException ex) {
            Logger.getLogger(DailyBonus.class.getName()).log(Level.SEVERE, null, ex);
        }
        clogger.sendMessage(ChatColor.GREEN + "---------------------------------------");
        clogger.sendMessage(ChatColor.YELLOW + "Dailybonus Plugin v" + this.getDescription().getVersion() + " Enabled");
        clogger.sendMessage(ChatColor.GREEN + "---------------------------------------");
        loadFromConfig();
        try {
            loadData(data);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DailyBonus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void onDisable() {

        clogger.sendMessage(ChatColor.RED + "---------------------------------------");
        clogger.sendMessage(ChatColor.YELLOW + "Dailybonus Plugin v" + this.getDescription().getVersion() + " Disabled");
        clogger.sendMessage(ChatColor.RED + "---------------------------------------");
        try {
            saveData(data);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(DailyBonus.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        Player pl = e.getPlayer();
//tempo passato in ore
        Long tempopassato = (System.currentTimeMillis() - pl.getFirstPlayed()) / 36000000;

        if (tempopassato < 24) {
            BonusData b = bonus.get("primo");

            if (players.containsKey(pl.getUniqueId())) {
                if (players.get(pl.getUniqueId()).equals(0)) {

                    if (b.tipo.equalsIgnoreCase("chiave")) {
                        Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 1);
                    } else {

                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                        OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                        Vault.addMoney(p, parseLong(b.argomento));

                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 1);
                    }

                }

            } else {
                if (b.tipo.equalsIgnoreCase("chiave")) {
                    Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 1);
                } else {

                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                    OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                    Vault.addMoney(p, parseLong(b.argomento));
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 1);
                }
            }

//primo giorno
        } else if (tempopassato >= 24 && tempopassato < 48) {
            BonusData b = bonus.get("secondo");

            if (players.containsKey(pl.getUniqueId())) {
                if (players.get(pl.getUniqueId()).equals(1)) {

                    if (b.tipo.equalsIgnoreCase("chiave")) {
                        Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 2);
                    } else {

                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                        OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                        Vault.addMoney(p, parseLong(b.argomento));
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 2);
                    }

                }

            } else {
                if (b.tipo.equalsIgnoreCase("chiave")) {
                    Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 2);
                } else {

                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                    OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                    Vault.addMoney(p, parseLong(b.argomento));
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 1);
                }
            }
            //secondo giorno
        } else if (tempopassato >= 48 && tempopassato < 72) {
//terzo giorno
            BonusData b = bonus.get("terzo");

            if (players.containsKey(pl.getUniqueId())) {
                if (players.get(pl.getUniqueId()).equals(2)) {

                    if (b.tipo.equalsIgnoreCase("chiave")) {
                        Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 3);
                    } else {

                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                        OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                        Vault.addMoney(p, parseLong(b.argomento));
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 3);
                    }

                }

            } else {
                if (b.tipo.equalsIgnoreCase("chiave")) {
                    Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 3);
                } else {

                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                    OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                    Vault.addMoney(p, parseLong(b.argomento));
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 3);
                }
            }
        } else if (tempopassato >= 72 && tempopassato < 96) {
//quarto giorno
            BonusData b = bonus.get("quarto");

            if (players.containsKey(pl.getUniqueId())) {
                if (players.get(pl.getUniqueId()).equals(3)) {

                    if (b.tipo.equalsIgnoreCase("chiave")) {
                        Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 4);
                    } else {

                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                        OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                        Vault.addMoney(p, parseLong(b.argomento));
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 4);
                    }

                }

            } else {
                if (b.tipo.equalsIgnoreCase("chiave")) {
                    Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 4);
                } else {

                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                    OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                    Vault.addMoney(p, parseLong(b.argomento));
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 4);
                }
            }
        } else if (tempopassato >= 96 && tempopassato < 120) {
//quinto giorno
            BonusData b = bonus.get("quinto");

            if (players.containsKey(pl.getUniqueId())) {
                if (players.get(pl.getUniqueId()).equals(4)) {

                    if (b.tipo.equalsIgnoreCase("chiave")) {
                        Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 5);
                    } else {

                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                        OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                        Vault.addMoney(p, parseLong(b.argomento));
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 5);
                    }

                }

            } else {
                if (b.tipo.equalsIgnoreCase("chiave")) {
                    Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 5);
                } else {

                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                    OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                    Vault.addMoney(p, parseLong(b.argomento));
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 5);
                }
            }
        } else if (tempopassato >= 120 && tempopassato < 144) {
//sesto giorno
            BonusData b = bonus.get("sesto");

            if (players.containsKey(pl.getUniqueId())) {
                if (players.get(pl.getUniqueId()).equals(5)) {

                    if (b.tipo.equalsIgnoreCase("chiave")) {
                        Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 6);
                    } else {

                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                        OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                        Vault.addMoney(p, parseLong(b.argomento));
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 6);
                    }

                }

            } else {
                if (b.tipo.equalsIgnoreCase("chiave")) {
                    Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 6);
                } else {

                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                    OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                    Vault.addMoney(p, parseLong(b.argomento));
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 6);
                }
            }
        } else if (tempopassato >= 144 && tempopassato < 168) {
//settimo giorno
            BonusData b = bonus.get("settimo");

            if (players.containsKey(pl.getUniqueId())) {
                if (players.get(pl.getUniqueId()).equals(6)) {

                    if (b.tipo.equalsIgnoreCase("chiave")) {
                        Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 7);
                    } else {

                        pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                        OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                        Vault.addMoney(p, parseLong(b.argomento));
                        players.remove(pl.getUniqueId());
                        players.put(pl.getUniqueId(), 7);
                    }

                }

            } else {
                if (b.tipo.equalsIgnoreCase("chiave")) {
                    Bukkit.dispatchCommand(pl, b.argomento.replace("%player%", pl.getName()));
                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato una nuova chiave!");
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 7);
                } else {

                    pl.sendMessage(ChatColor.YELLOW + "[DailyBonus]: Ciao, oggi hai guadagnato" + b.argomento.toString() + " soldi!");
                    OfflinePlayer p = Bukkit.getOfflinePlayer(pl.getUniqueId());
                    Vault.addMoney(p, parseLong(b.argomento));
                    players.remove(pl.getUniqueId());
                    players.put(pl.getUniqueId(), 7);
                }
            }

        }

    }

    public void loadFromConfig() {
        bonus.clear();
        String primo_tipo = this.getConfig().getString("bonus.primo.tipo");
        String primo_argomento = this.getConfig().getString("bonus.primo.argomento");

        bonus.put("primo", new BonusData(primo_tipo, primo_argomento));

        String secondo_tipo = this.getConfig().getString("bonus.secondo.tipo");
        String secondo_argomento = this.getConfig().getString("bonus.secondo.argomento");
        bonus.put("secondo", new BonusData(secondo_tipo, secondo_argomento));

        String terzo_tipo = this.getConfig().getString("bonus.terzo.tipo");
        String terzo_argomento = this.getConfig().getString("bonus.terzo.argomento");
        bonus.put("terzo", new BonusData(terzo_tipo, terzo_argomento));

        String quarto_tipo = this.getConfig().getString("bonus.quarto.tipo");
        String quarto_argomento = this.getConfig().getString("bonus.quarto.argomento");
        bonus.put("quarto", new BonusData(quarto_tipo, quarto_argomento));
        String quinto_tipo = this.getConfig().getString("bonus.quinto.tipo");
        String quinto_argomento = this.getConfig().getString("bonus.quinto.argomento");
        bonus.put("quinto", new BonusData(quinto_tipo, quinto_argomento));
        String sesto_tipo = this.getConfig().getString("bonus.sesto.tipo");
        String sesto_argomento = this.getConfig().getString("bonus.sesto.argomento");
        bonus.put("sesto", new BonusData(sesto_tipo, sesto_argomento));
        String settimo_tipo = this.getConfig().getString("bonus.settimo.tipo");
        String settimo_argomento = this.getConfig().getString("bonus.settimo.argomento");
        bonus.put("settimo", new BonusData(settimo_tipo, settimo_argomento));
    }

    public static void saveData(File file) throws FileNotFoundException {
        for (UUID data : players.keySet()) {

            String storageData = data.toString() + ";" + players.get(data).toString();
            try (PrintWriter out = new PrintWriter(new FileOutputStream(file))) {
                out.println(storageData);
            }
        }
    }

    public static void loadData(File file) throws FileNotFoundException {
        players.clear();
        try (Scanner scanner = new Scanner(file)) {
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] s = line.split(";");

                players.put(UUID.fromString(s[0]), parseInt(s[1]));
            }
        }
    }

    public void onInitiateFile() throws IOException {

        data = new File(Bukkit.getServer().getPluginManager().getPlugin("DailyBonus").getDataFolder(), "data.yml");

        if (!data.exists()) {

            data.createNewFile();
        }

    }

}
