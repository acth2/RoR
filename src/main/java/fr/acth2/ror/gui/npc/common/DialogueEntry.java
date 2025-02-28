package fr.acth2.ror.gui.npc.common;

import java.util.List;
public class DialogueEntry {
    private final String npcText;
    private final List<String> playerResponses;
    private final List<DialogueEntry> nextEntries;

    public DialogueEntry(String npcText, List<String> playerResponses, List<DialogueEntry> nextEntries) {
        this.npcText = npcText;
        this.playerResponses = playerResponses;
        this.nextEntries = nextEntries;
    }

    public String getNpcText() {
        return npcText;
    }

    public List<String> getPlayerResponses() {
        return playerResponses;
    }

    public DialogueEntry getNextEntry(int choiceIndex) {
        if (choiceIndex >= 0 && choiceIndex < nextEntries.size()) {
            return nextEntries.get(choiceIndex);
        }
        return null;
    }
}