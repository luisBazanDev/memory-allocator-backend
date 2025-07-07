package pe.bazan.luis.uni.programs;

import pe.bazan.luis.uni.domain.BlockMemory;
import pe.bazan.luis.uni.domain.Process;

public class SumNumbers extends Process {

    public SumNumbers(String programName) {
        super(programName, 8);
    }

    @Override
    public void run(int pid) {
        super.run(pid);
        BlockMemory bm = getBlockMemory();

        bm.setInt(0, 12932);
        bm.setInt(4, 1239120);

        log(String.format("%d + %d = %d", bm.getInt(0), bm.getInt(4), bm.getInt(0) + bm.getInt(4)));
    }
}
