package fr.acth2.mod.gui.diary;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.nbt.CompressedStreamTools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiaryManager {

    private static final List<DiaryEntry> ENTRIES = new ArrayList<>();
    private static final File DIARY_FILE = new File("config/diary.dat");

    public static void addEntry(DiaryEntry entry) {
        if (ENTRIES.stream().noneMatch(e -> e.getMonsterName().equals(entry.getMonsterName()))) {
            ENTRIES.add(entry);
            saveDiary();
        }
    }

    public static List<DiaryEntry> getEntries() {
        return ENTRIES;
    }

    public static void saveDiary() {
        ListNBT listNBT = new ListNBT();
        for (DiaryEntry entry : ENTRIES) {
            listNBT.add(entry.serializeNBT());
        }
        CompoundNBT root = new CompoundNBT();
        root.put("DiaryEntries", listNBT);

        try (FileOutputStream fos = new FileOutputStream(DIARY_FILE)) {
            CompressedStreamTools.writeCompressed(root, fos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void loadDiary() {
        if (!DIARY_FILE.exists()) {
            return;
        }
        try (FileInputStream fis = new FileInputStream(DIARY_FILE)) {
            CompoundNBT root = CompressedStreamTools.readCompressed(fis);
            ListNBT listNBT = root.getList("DiaryEntries", 10);
            ENTRIES.clear();
            for (int i = 0; i < listNBT.size(); i++) {
                CompoundNBT entryTag = listNBT.getCompound(i);
                DiaryEntry entry = DiaryEntry.deserializeNBT(entryTag);
                ENTRIES.add(entry);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String pickDescription(String monsterName) {
        return monsterName + " description...";
    }
}
