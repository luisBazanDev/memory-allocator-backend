package pe.bazan.luis.uni.models;

import pe.bazan.luis.uni.domain.MemoryAllocator;

import java.util.List;

public class MemoryState {
    private int[] values;
    private boolean[] used;
    private List<ProcessStateApi> process;

    public MemoryState(MemoryAllocator memoryAllocator) {
        this.values = new int[memoryAllocator.getMemory().length];
        this.used = memoryAllocator.getUsed();
        this.process = memoryAllocator.getProcesses().stream().map(ProcessStateApi::new).toList();

        for (int i = 0; i < memoryAllocator.getMemory().length; i++) {
            this.values[i] = memoryAllocator.getMemory()[i];
        }
    }

    public int[] getValues() {
        return values;
    }

    public boolean[] getUsed() {
        return used;
    }

    public List<ProcessStateApi> getProcessStateApis() {
        return process;
    }
}
