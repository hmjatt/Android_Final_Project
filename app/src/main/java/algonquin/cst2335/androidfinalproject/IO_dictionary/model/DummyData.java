// DummyData.java
package algonquin.cst2335.androidfinalproject.IO_dictionary.model;

import java.util.ArrayList;
import java.util.List;

public class DummyData {

    public static List<Word> getDummyWords() {
        List<Word> words = new ArrayList<>();
        words.add(new Word("Test Word 1"));
        words.add(new Word("Test Word 2"));
        words.add(new Word("Test Word 3"));
        return words;
    }

    public static List<Definition> getDummyDefinitions() {
        List<Definition> definitions = new ArrayList<>();
        definitions.add(new Definition("Test Definition 1"));
        definitions.add(new Definition("Test Definition 2"));
        definitions.add(new Definition("Test Definition 3"));
        return definitions;
    }

    public static List<SavedWord> getDummySavedWords() {
        List<SavedWord> savedWords = new ArrayList<>();
        savedWords.add(new SavedWord("Saved Word 1"));
        savedWords.add(new SavedWord("Saved Word 2"));
        savedWords.add(new SavedWord("Saved Word 3"));
        return savedWords;
    }

    public static List<Definition> getDummySavedWordDefinitions() {
        List<Definition> savedWordDefinitions = new ArrayList<>();
        savedWordDefinitions.add(new Definition("Saved Word Definition 1"));
        savedWordDefinitions.add(new Definition("Saved Word Definition 2"));
        savedWordDefinitions.add(new Definition("Saved Word Definition 3"));
        return savedWordDefinitions;
    }
}
