import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TwentyQuestionsTest {

    private ByteArrayOutputStream baos;

    @Test
    void testQuestionNodeInner() {
        // Is Question node a private inner class?
        assertThrows(ClassNotFoundException.class, () -> Class.forName("QuestionNode"));
    }

    @Test
    void testGame() {
        outputStream();
        String expected = questions();
        System.setIn(getAnswerStream1());
        QuestionsGame questionsGame = new QuestionsGame();
        questionsGame.askQuestions();
        questionsGame.askQuestions();
        assertEquals(expected, baos.toString());
        System.setIn(System.in);
        System.setOut(System.out);
    }

    @Test
    void testRead() {
        System.setIn(getAnswerStream2());
        PrintStream outputStream = outputStream();
        QuestionsGame questionsGame = new QuestionsGame();
        questionsGame.read(new Scanner(expectedFileContents()));
        questionsGame.askQuestions();
        assertEquals(expectedOutput(),baos.toString());
    }

    @Test
    void testWrite() {
        System.setIn(getAnswerStream1());
        QuestionsGame questionsGame = new QuestionsGame();
        questionsGame.askQuestions();
        questionsGame.askQuestions();
        PrintStream outputStream = outputStream();
        questionsGame.write(outputStream);
        String expectedFileContents = expectedFileContents();
        assertEquals(expectedFileContents,baos.toString());
    }

    private String questions() {
        return String.join(" ",
                "Is it a computer? (y/n)",
                "What is the name of your object?",
                "Please give me a yes/no question that distinguishes between your object and mine-->",
                "And what is the answer for your object? (y/n)",
                "is it yellow? (y/n)",
                "Is it a banana? (y/n)",
                "Great! I got it right!\n"
        );
    }

    private ByteArrayInputStream getAnswerStream1() {
         return new ByteArrayInputStream(
                String.join("\n",
                        "n",
                        "banana",
                        "is it yellow",
                        "y",
                        "y",
                        "y"
                ).getBytes());
    }

    private ByteArrayInputStream getAnswerStream2() {
         return new ByteArrayInputStream(
                String.join("\n",
                        "y",
                        "y"
                ).getBytes());
    }

    private String expectedFileContents() {
        return "Q:\n" +
                "is it yellow\n" +
                "A:\n" +
                "banana\n" +
                "A:\n" +
                "computer\n";
    }

    private String expectedOutput() {
        return String.join(" ",
                "is it yellow? (y/n)",
                "Is it a banana? (y/n)",
                "Great! I got it right!"
        ) + "\n";
    }

    private PrintStream outputStream() {
        baos = new ByteArrayOutputStream();
        PrintStream outputStream = new PrintStream(baos);
        System.setOut(outputStream);
        return outputStream;
    }
}