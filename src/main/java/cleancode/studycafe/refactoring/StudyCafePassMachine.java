package cleancode.studycafe.refactoring;

import cleancode.studycafe.refactoring.exception.AppException;
import cleancode.studycafe.refactoring.io.*;
import cleancode.studycafe.refactoring.model.StudyCafeLockerPass;
import cleancode.studycafe.refactoring.model.StudyCafePass;
import cleancode.studycafe.refactoring.model.StudyCafePassType;
import cleancode.studycafe.refactoring.model.LockerPasses;
import cleancode.studycafe.refactoring.model.ReadLockerPasses;
import cleancode.studycafe.refactoring.model.ReadStudyCafePasses;
import cleancode.studycafe.refactoring.model.StudyCafePasses;

import java.util.List;

public class StudyCafePassMachine {

    private final InputHandler inputHandler;
    private final OutputHandler outputHandler;

    private final ReadLockerPasses readLockerPasses = new LockerPasses();
    private final ReadStudyCafePasses readStudyCafePasses = new StudyCafePasses();

    private StudyCafePass selectedPass;

    public StudyCafePassMachine(InputHandler inputHandler, OutputHandler outputHandler) {
        this.inputHandler = inputHandler;
        this.outputHandler = outputHandler;
    }

    public void run() {
        outputHandler.showWelcomeMessage();
        outputHandler.showAnnouncement();

        try {
            outputHandler.askPassTypeSelection();

            StudyCafePassType studyCafePassType = inputHandler.getPassTypeSelectingUserAction();

            if (studyCafePassType == StudyCafePassType.HOURLY) { // 시간권 선택
                doesStudyCafePassHourly();
                outputHandler.showPassOrderSummary(selectedPass, null);
            }
            if (studyCafePassType == StudyCafePassType.WEEKLY) { // 주간권 선택
                doesStudyCafePassWeekly();
                outputHandler.showPassOrderSummary(selectedPass, null);
            }
            if (studyCafePassType == StudyCafePassType.FIXED) { // 고정권 선택
                doesStudyCafePassFixed();
            }
        } catch (AppException e) {
            outputHandler.showSimpleMessage(e.getMessage());
        } catch (Exception e) {
            outputHandler.showSimpleMessage("알 수 없는 오류가 발생했습니다.");
        }

    }

    private void doesStudyCafePassHourly() {
        List<StudyCafePass> hourlyPasses = readStudyCafePasses.readStudyCafePasses().stream()
            .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.HOURLY)
            .toList();
        outputHandler.showPassListForSelection(hourlyPasses);
        selectedPass = inputHandler.getSelectPass(hourlyPasses);
    }

    private void doesStudyCafePassWeekly() {
        List<StudyCafePass> weeklyPasses = readStudyCafePasses.readStudyCafePasses().stream()
            .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.WEEKLY)
            .toList();
        outputHandler.showPassListForSelection(weeklyPasses);
        selectedPass = inputHandler.getSelectPass(weeklyPasses);
    }

    private void doesStudyCafePassFixed() {
        List<StudyCafePass> fixedPasses = readStudyCafePasses.readStudyCafePasses().stream()
            .filter(studyCafePass -> studyCafePass.getPassType() == StudyCafePassType.FIXED)
            .toList();
        outputHandler.showPassListForSelection(fixedPasses);
        selectedPass = inputHandler.getSelectPass(fixedPasses);

        lockerPass();
    }

    private void lockerPass() {
        List<StudyCafeLockerPass> lockerPasses = readLockerPasses.readLockerPasses();
        StudyCafeLockerPass lockerPass = lockerPasses.stream()
            .filter(option ->
                option.getPassType() == selectedPass.getPassType()
                    && option.getDuration() == selectedPass.getDuration()
            )
            .findFirst()
            .orElse(null);

        lockerSelection(lockerPass);
    }

    private void lockerSelection(StudyCafeLockerPass lockerPass) {
        boolean lockerSelection = false;
        if (lockerPass != null) {
            outputHandler.askLockerPass(lockerPass);
            lockerSelection = inputHandler.getLockerSelection();
        }

        if (lockerSelection) {
            outputHandler.showPassOrderSummary(selectedPass, lockerPass);
        } else {
            outputHandler.showPassOrderSummary(selectedPass, null);
        }
    }
}
