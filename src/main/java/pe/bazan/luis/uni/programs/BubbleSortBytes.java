package pe.bazan.luis.uni.programs;

import pe.bazan.luis.uni.domain.BlockMemory;
import pe.bazan.luis.uni.domain.Process;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class BubbleSortBytes extends Process {
    private int size;
    private ScheduledExecutorService scheduler;

    public BubbleSortBytes(String programName, int size) {
        // size + 1 byte of pointer
        super(programName, size + 1);
        this.size = size;
    }

    @Override
    public void run(int pid) {
        super.run(pid);
        getBlockMemory().set(this.size, (byte) 1);
        fillMemory();
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        this.scheduler.scheduleAtFixedRate(() -> {
            nextStep();
        }, 0, 1, TimeUnit.SECONDS);
    }

    private void nextStep() {
        BlockMemory bm = getBlockMemory();
        byte pointer = bm.get(this.size);
        if(pointer >= this.size) {
            pointer = 1;
        }

        if(bm.getInt(pointer - 1) > bm.getInt(pointer)) {
            swap(pointer, pointer - 1);
            log(String.format("Swap %d to %d", pointer, pointer + 1));
        }


        bm.set(this.size, (byte) (pointer + 1));
    }

    private void fillMemory() {
        for (int i = 0; i < this.size; i++) {
            getBlockMemory().set(i, (byte) i);
        }

        Random random = new Random();
        for (int i = 0; i < this.size; i++) {
            swap(i, random.nextInt(0, this.size));
        }
    }

    private void swap(int i, int j) {
        byte temp = getBlockMemory().get(i);
        getBlockMemory().set(i, getBlockMemory().get(j));
        getBlockMemory().set(j, temp);
    }
}
