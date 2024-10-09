package cleancode.studycafe.refactoring.io;

import cleancode.studycafe.refactoring.model.StudyCafePass;
import cleancode.studycafe.refactoring.model.StudyCafePassType;

import java.util.List;

public interface InputHandler {

    StudyCafePassType getPassTypeSelectingUserAction();

    StudyCafePass getSelectPass(List<StudyCafePass> passes);

    boolean getLockerSelection();

}
