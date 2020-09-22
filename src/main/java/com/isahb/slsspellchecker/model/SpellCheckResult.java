package com.isahb.slsspellchecker.model;


import org.languagetool.rules.CorrectExample;
import org.languagetool.rules.IncorrectExample;

import java.util.ArrayList;
import java.util.List;

/**
 * isahb
 */
public class SpellCheckResult {
    private String error;
    private List<SpellCheckSuggestion> spellCheckSuggestions = new ArrayList<>();

    public String getError() {
        return error;
    }

    public List<SpellCheckSuggestion> getSpellCheckSuggestions() {
        return spellCheckSuggestions;
    }

    public void addCorrectionSuggestion(SpellCheckSuggestion spellCheckSuggestion) {
        this.spellCheckSuggestions.add(spellCheckSuggestion);
    }

    private SpellCheckResult(String errorMsg) {
        this.error = errorMsg;
    }

    public static SpellCheckResult withError(String errorMsg) {
        return new SpellCheckResult(errorMsg);
    }

    public SpellCheckResult() {
    }

    public static class SpellCheckSuggestion {
        private int startPos;
        private int endPos;
        private String message;
        private String shortMessage;
        private String type;
        private List<String> suggestedReplacements;
        private SuggestionMeta suggestionMeta;

        // required by Jackson
        public SpellCheckSuggestion() {
        }

        public SpellCheckSuggestion(int startPos, int endPos, String message, String shortMessage, String type, SuggestionMeta suggestionMeta, List<String> suggestedReplacements) {
            this.startPos = startPos;
            this.endPos = endPos;
            this.message = message;
            this.shortMessage = shortMessage;
            this.type = type;
            this.suggestionMeta = suggestionMeta;
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

        public String getShortMessage() {
            return shortMessage;
        }

        public String getType() {
            return type;
        }

        public SuggestionMeta getSuggestionMeta() {
            return suggestionMeta;
        }
    }

    public static class SuggestionMeta {
        private String categoryId;
        private String categoryName;
        private List<CorrectExample> correctExamples = new ArrayList<>();
        private List<IncorrectExample> incorrectExamples = new ArrayList<>();

        public SuggestionMeta() {
        }

        public SuggestionMeta(String categoryId, String categoryName, List<CorrectExample> correctExamples, List<IncorrectExample> incorrectExamples) {
            this.categoryId = categoryId;
            this.categoryName = categoryName;
            this.correctExamples = correctExamples;
            this.incorrectExamples = incorrectExamples;
        }

        public String getCategoryId() {
            return categoryId;
        }

        public String getCategoryName() {
            return categoryName;
        }

        public List<CorrectExample> getCorrectExamples() {
            return correctExamples;
        }

        public List<IncorrectExample> getIncorrectExamples() {
            return incorrectExamples;
        }
    }
}
