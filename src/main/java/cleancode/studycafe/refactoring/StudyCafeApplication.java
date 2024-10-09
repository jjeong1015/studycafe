package cleancode.studycafe.refactoring;

import cleancode.studycafe.refactoring.io.ConsoleInputHandler;
import cleancode.studycafe.refactoring.io.ConsoleOutputHandler;
import cleancode.studycafe.refactoring.io.InputHandler;
import cleancode.studycafe.refactoring.io.OutputHandler;

public class StudyCafeApplication {

    public static void main(String[] args) {
        InputHandler inputHandler = new ConsoleInputHandler();
        OutputHandler outputHandler = new ConsoleOutputHandler();

        StudyCafePassMachine studyCafePassMachine = new StudyCafePassMachine(inputHandler, outputHandler);
        studyCafePassMachine.run();
    }

}
