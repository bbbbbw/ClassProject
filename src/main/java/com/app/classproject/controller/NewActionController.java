package com.app.classproject.controller;

import com.alibaba.fastjson.JSONObject;
import com.app.classproject.model.Computer;
import com.app.classproject.model.ComputerUI;
import com.app.classproject.model.Instructions;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Receive requests submitted by the front-end, execute the action, and return the result
 */
@RestController
public class NewActionController {
    Computer computer = new Computer();
    private int TEST_PROGRAM_ONE = 1;
    private int TEST_PROGRAM_TWO = 2;
    private int PART_FOUR_DEMO = 3;
    private int[] programList = {3, 1, 2, 4};
//    private int[] programList = {3};
    private int currentProgramIndex = 0;

    @RequestMapping(value = "/action/initialize")
    public String initialize(Model model) {
        JSONObject result = new JSONObject();
        ComputerUI computerUI = new ComputerUI(computer);
        result.put("status", 0);
        result.put("computer", computerUI);
        return result.toString();
    }

    /**
     * User changed contents of a register
     */
    @RequestMapping(value = "/action/submitRegister")
    public String alterRegister(Model model, String register, String value) {
        JSONObject result = new JSONObject();
        int intValue;
        if (value == "" || value == null) {
            intValue = 0;
        } else {
            intValue = Integer.parseInt(value);
        }
        switch (register) {
            case "R0":
                computer.gpr[0].setValue(intValue);
                break;
            case "R1":
                computer.gpr[1].setValue(intValue);
                break;
            case "R2":
                computer.gpr[2].setValue(intValue);
                break;
            case "R3":
                computer.gpr[3].setValue(intValue);
                break;
            case "X1":
                computer.idx[0].setValue(intValue);
                break;
            case "X2":
                computer.idx[1].setValue(intValue);
                break;
            case "X3":
                computer.idx[2].setValue(intValue);
                break;
            case "PC":
                computer.pc.setValue(intValue);
                break;
            case "MAR":
                computer.mar.setValue(intValue);
                break;
            case "MBR":
                computer.mbr.setValue(intValue);
                break;
            case "IR":
                computer.ir.setValue(intValue);
                break;
            default:
                result.put("status", -1);
                result.put("errorMessage", "Please select a register");
                return result.toString();
        }
        ComputerUI computerUI = new ComputerUI(computer);
        result.put("status", 0);
        result.put("computer", computerUI);
        return result.toString();
    }

    /**
     * User changed value of a memory location
     */
    @RequestMapping(value = "/action/submitMemory")
    public String alterMemory(Model model, String address, String value) {
        JSONObject result = new JSONObject();
        int intAddress, intValue;
        if (address == "" || address == null) {
            result.put("status", -1);
            result.put("errorMessage", "Please input a valid address");
            return result.toString();
        } else {
            intAddress = Integer.parseInt(address);
        }
        if (value == "" || value == null) {
            intValue = 0;
        } else {
            intValue = Integer.parseInt(value);
        }
        computer.RAM[intAddress].mem = intValue;
        computer.RAM[intAddress].loadval();
        result.put("status", 0);
        return result.toString();
    }

    /**
     * User changed value of instruction
     */
    @RequestMapping(value = "/action/submitInstruction")
    public String alterInstruction(Model model, String instruction) {
        JSONObject result = new JSONObject();
        return result.toString();
    }

    /**
     * IPL button pressed
     */
    @RequestMapping(value = "/action/IPL")
    public String IPL(Model model) {
        JSONObject result = new JSONObject();
        if (programList[currentProgramIndex] == TEST_PROGRAM_ONE) {
            computer.loadTestProgramOne();
        } else if (programList[currentProgramIndex] == TEST_PROGRAM_TWO) {
            computer.loadTestProgramTwo();
        } else if (programList[currentProgramIndex] == PART_FOUR_DEMO) {
            computer.loadPartFourDemo();
        }
        ComputerUI computerUI = new ComputerUI(computer);
        result.put("status", 0);
        result.put("computer", computerUI);
        return result.toString();
    }

    /**
     * Run button pressed
     */
    @RequestMapping(value = "/action/run")
    public String run(Model model) {
        JSONObject result = new JSONObject();
        computer.runProgram();
        ComputerUI computerUI = new ComputerUI(computer);
        if (computerUI.status == 0) {
            if (nextProgram()) {
                return run(model);
            }
        }
        result.put("status", 0);
        result.put("computer", computerUI);
        return result.toString();
    }

//    @RequestMapping(value = "/action/halt")
//    public String halt(Model model) {
//        return "Halt";
//    }

    @RequestMapping(value = "/action/singleStep")
    public String singleStep(Model model) {
        JSONObject result = new JSONObject();
        computer.singleStep();
        ComputerUI computerUI = new ComputerUI(computer);
        if (computerUI.status == 0) {
            nextProgram();
        }
        result.put("status", 0);
        result.put("computer", computerUI);
        return result.toString();
    }

    @RequestMapping(value = "/action/input")
    public String deviceIn(Model model, String input) {
        JSONObject result = new JSONObject();
        try {
            // Input is an integer
            int temp = Integer.parseInt(input);
            if (temp > 65535 || temp < 0) {
                result.put("status", -1);
                result.put("errorMessage", "Please input a valid number (0 ~ 65535) or one valid character");
            } else {
                computer.continueIn(temp);
                result.put("status", 0);
            }
        } catch (NumberFormatException e) {
            // Input is a string
            if (input == null || input.matches("\\s*")) {
                computer.continueIn(0);
                result.put("status", 0);
            } else if (input.length() > 1) {
                result.put("status", -1);
                result.put("errorMessage", "Please input a valid number (0 ~ 65535) or one valid character");
            } else {
                computer.continueIn((int) input.charAt(0));
                result.put("status", 0);
            }
        }
        ComputerUI computerUI = new ComputerUI(computer);
        result.put("computer", computerUI);
        return result.toString();
    }

    @RequestMapping(value = "/action/reStart")
    public String reStart(Model model) {
        JSONObject result = new JSONObject();
        computer = new Computer();
        this.currentProgramIndex = 0;
        ComputerUI computerUI = new ComputerUI(computer);
        result.put("status", 0);
        result.put("computer", computerUI);
        return result.toString();
    }

    @RequestMapping(value = "/test/oneInstruction")
    public String oneInstruction(Model model, String instructionStr) {
        JSONObject result = new JSONObject();
        int[] instruction = new int[16];
        for (int i = 0; i < 16; i++) {
            instruction[i] = instructionStr.charAt(i) - 48;
        }
        Instructions curInstruction = new Instructions(instruction, computer);
        int executionResult = curInstruction.execute();
        ComputerUI computerUI = new ComputerUI(computer);
        result.put("status", executionResult);
        result.put("computer", computerUI);
        return result.toString();
    }

    // load and execute next program
    private boolean nextProgram() {
        if (currentProgramIndex <= programList.length - 1) {
            String pastPrinter = computer.printer + "\n\nA new program starts.\n\n";
            computer = new Computer();
            computer.printer = pastPrinter;
            currentProgramIndex += 1;
            if (programList[currentProgramIndex] == TEST_PROGRAM_ONE) {
                computer.loadTestProgramOne();
            } else if (programList[currentProgramIndex] == TEST_PROGRAM_TWO) {
                computer.loadTestProgramTwo();
            } else if (programList[currentProgramIndex] == PART_FOUR_DEMO) {
                computer.loadPartFourDemo();
            } else {
                return false;
            }
            return true;
        }
        return false;
    }
}
