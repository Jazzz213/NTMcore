package by.quaks.ntm.files;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class WarnList {
    private static File file;
    private static FileConfiguration fileConfiguration;

    public static void setup() {
        file = new File(Bukkit.getServer().getPluginManager().getPlugin("NTM").getDataFolder(), "warnlist.yml");

        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }

    public static FileConfiguration get(){
        return fileConfiguration;
    }

    public static void save(){
        try {
            fileConfiguration.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void reload(){
        fileConfiguration = YamlConfiguration.loadConfiguration(file);
    }
}
