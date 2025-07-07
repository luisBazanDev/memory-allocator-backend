package pe.bazan.luis.uni.programs;

import pe.bazan.luis.uni.domain.BlockMemory;
import pe.bazan.luis.uni.domain.Process;

import java.util.Random;

public class RandomBytes extends Process {

    public RandomBytes(String programName) {
        super(programName, new Random().nextInt(3, 30));
    }

    @Override
    public void run(int pid) {
        super.run(pid);

        BlockMemory bm = getBlockMemory();

        Random random = new Random();

        for (int i = 0; i < getSizeMemory(); i++) {
            byte randomByte = (byte) random.nextInt(Byte.MIN_VALUE, Byte.MAX_VALUE + 1);
            log(String.format("Random byte generated: %d", randomByte));
            bm.set(i, randomByte);
        }
    }
}
