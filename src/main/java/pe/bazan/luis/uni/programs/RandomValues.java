package pe.bazan.luis.uni.programs;

import pe.bazan.luis.uni.domain.Process;
import pe.bazan.luis.uni.domain.ProcessState;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class RandomValues extends Process {
    private ScheduledExecutorService scheduler;

    public RandomValues(String programName) {
        super(programName, 11);
    }

    @Override
    public void run(int pid) {
        super.run(pid);
        this.scheduler = Executors.newSingleThreadScheduledExecutor();

        this.scheduler.scheduleAtFixedRate(() -> {
            generateValues();
        }, 0, 1, TimeUnit.SECONDS);
    }

    public void generateValues() {
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("Random values: ");

        for (int i = 0; i < getSizeMemory(); i++) {
            byte randomByte = (byte) random.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE + 1);
            getBlockMemory().set(i, randomByte);
            stringBuilder.append(randomByte);
            if(i != getSizeMemory() - 1) stringBuilder.append(", ");
        }

        log(stringBuilder.toString());
    }

    @Override
    public void clear() {
        scheduler.shutdown();
        super.clear();
    }
}
