package pe.bazan.luis.uni.programs;

import pe.bazan.luis.uni.domain.Process;

import java.util.Scanner;

public class GenericProcess extends Process {
    public GenericProcess(String programName, int sizeMemory) {
        super(programName, sizeMemory);
    }

    @Override
    public void run(int pid) {
        super.run(pid);

        getBlockMemory().getMemoryAllocator().printState();
        Scanner scanner = new Scanner(System.in);
        System.out.println("Preciona cualquier tecla para eliminar el programa.");
        scanner.nextLine();
        clear();
    }
}
