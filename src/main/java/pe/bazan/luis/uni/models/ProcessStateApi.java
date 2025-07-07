package pe.bazan.luis.uni.models;

import pe.bazan.luis.uni.domain.Process;
import pe.bazan.luis.uni.domain.ProcessState;

import java.util.List;

public class ProcessStateApi {
    private String processName;
    private ProcessState processState;
    private int[] memoryDirections;
    private int programSize;
    private int pid;
    private List<String> output;

    public ProcessStateApi(Process process) {
        this.processName = process.getProgramName();
        this.processState = process.getState();
        if(process.getBlockMemory() != null) {
            this.memoryDirections = process.getBlockMemory().getMemoryDirections();
        } else {
            this.memoryDirections = new int[0];
        }
        this.pid = process.getPid();
        this.programSize = process.getSizeMemory();
        this.output = process.getLogs();
    }

    public String getProcessName() {
        return processName;
    }

    public ProcessState getProcessState() {
        return processState;
    }

    public int[] getMemoryDirections() {
        return memoryDirections;
    }

    public int getPid() {
        return pid;
    }

    public int getProgramSize() {
        return programSize;
    }

    public List<String> getOutput() {
        return output;
    }
}
