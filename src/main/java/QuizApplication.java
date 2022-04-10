import gui.QuizGUI;
import handlers.MemoryHandler;

public abstract class QuizApplication {

    public static void main(String[] args) {

        MemoryHandler.quizGUI = new QuizGUI("Quiz");

        System.out.println(MemoryHandler.accountsFile.getPath());

    }
}
