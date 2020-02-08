package com.serverless.model;

import java.util.ArrayList;
import java.util.List;

/**
 * isahb
 */
public class SpellCheckResult {
    private List<SpellCheckSuggestion> spellCheckSuggestions = new ArrayList<>();

    public List<SpellCheckSuggestion> getSpellCheckSuggestions() {
        return spellCheckSuggestions;
    }

    public void addCorrectionSuggestion(SpellCheckSuggestion spellCheckSuggestion) {
        this.spellCheckSuggestions.add(spellCheckSuggestion);
    }

    public SpellCheckResult() {
    }

    public static class SpellCheckSuggestion {
        private int startPos;
        private int endPos;
        private String message;
        private List<String> suggestedReplacements;

        public SpellCheckSuggestion() {
        }

        public SpellCheckSuggestion(int startPos, int endPos, String message, List<String> suggestedReplacements) {
            this.startPos = startPos;
            this.endPos = endPos;
            this.message = message;
            this.suggestedReplacements = suggestedReplacements;
        }

        public int getStartPos() {
            return startPos;
        }

        public int getEndPos() {
            return endPos;
        }

        public String getMessage() {
            return message;
        }

        public List<String> getSuggestedReplacements() {
            return suggestedReplacements;
        }
    }
}
