package model;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class NameGeneratorTest {

    @Test
    void testConstructor() {
        final List<String> firstNames = List.of("a", "b", "c");
        final List<String> lastNames = List.of("d", "e", "f");
        final NameGenerator generator =
                new NameGenerator(firstNames, lastNames);

        assertArrayEquals(firstNames.toArray(new String[0]), generator.myFirstNames);
        assertArrayEquals(lastNames.toArray(new String[0]), generator.myLastNames);
    }

    @Test
    void testGenerate() {
        final List<String> firstNames = List.of("a", "b", "c");
        final List<String> lastNames = List.of("d", "e", "f");
        final NameGenerator generator =
                new NameGenerator(firstNames, lastNames);

        final String[] name = generator.generate().split(" ");
        assertTrue(firstNames.contains(name[0]));
        assertTrue(lastNames.contains(name[1]));
    }
}
