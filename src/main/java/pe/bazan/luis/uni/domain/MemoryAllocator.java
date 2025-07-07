package pe.bazan.luis.uni.domain;

import java.util.ArrayList;
import java.util.List;

public class MemoryAllocator {
	private byte[] memory;
	private boolean[] used;
	private int free;
	private List<Process> processes;
	private int pidCounter;

	public MemoryAllocator(int memorySize) {
		this.processes = new ArrayList<>();
		this.memory = new byte[memorySize];
		this.used = new boolean[memorySize];
		this.free = memorySize;
		this.pidCounter = 1;
	}

	public void runProcess(Process process) {
		processes.add(process);
		if(process.load(this)){
			process.run(pidCounter++);
		}
	}

	private void retryLoadProcess() {
		for (int i = 0; i < processes.size(); i++) {
			Process process = processes.get(i);
			if (process.getState() != ProcessState.WAIT) continue;
			if(process.load(this)){
				process.run(pidCounter++);
			}
		}
	}

	public int[] getMemory(int byteSize) throws MemoryOutException {
		int[] memoryDirections = new int[byteSize];
		int memoryIndex = 0;

		if(byteSize > this.free) throw new MemoryOutException();

		for (int i = 0; i < used.length; i++) {
			if(!this.used[i]) {
				memoryDirections[memoryIndex] = i;
				this.used[i] = true;
				this.free--;
				memoryIndex++;
				if(memoryIndex >= byteSize) break;
			}
		}

		return memoryDirections;
	}

	public void clearMemory(int[] memoryDirections) {
		for (int memoryDirection : memoryDirections) {
			used[memoryDirection] = false;
			free++;
		}
		retryLoadProcess();
	}

	public void clearMemory(Process process) {
		if(processes.remove(process)) {
			clearMemory(process.getBlockMemory().getMemoryDirections());
		}
	}

	public void printState(int lineSize, boolean state) {
		int lineCounter = 0;

		System.out.printf("%nMemory state - Used(%db / %db | %.2f%%)%n", this.memory.length - this.free, this.memory.length, (float) (this.memory.length - this.free) * 100 / this.memory.length);

		for (int i = 0; i < used.length; i++) {
			boolean b = used[i];
			if(lineCounter >= lineSize) {
				System.out.println("");
				lineCounter = 0;
			}
			if(b) {
				if(state) {
					System.out.print("|█| ");
				} else {
					System.out.printf("|%03d| ", memory[i]);
				}
			} else {
				if(state) {
					System.out.print("|_| ");
				} else {
					System.out.printf("|%03d| ", memory[i]);
				}
			}
			lineCounter++;
		}
		System.out.println();
	}

	public void printState() {
		printState(20, true);
	}

	public void printValues() {
		printState(20, false);
	}

	public boolean isFree(int size) {
		return this.free >= size;
	}

	public void setByte(int index, byte data) {
		memory[index] = data;
	}

	public byte getByte(int index) {
		return memory[index];
	}

	public int getFree() {
		return free;
	}

	public static class MemoryOutException extends Exception{
		@Override
		public String getMessage() {
			return "MemoryOutException";
		}
	}

	/**
	 * Solo existe este get por cuestiones de la api, no es muy seguro que se ofresca la
	 * memoria directamente de forma publica, por eso hay blockMemories que adminitran la memoria
	 * @return memory array
	 */
	public byte[] getMemory() {
		return memory;
	}

	/**
	 * Solo existe este get por cuestiones de la api, no es muy seguro que se ofresca la
	 * memoria directamente de forma publica, por eso hay blockMemories que adminitran la memoria
	 * @return memory used array
	 */
	public boolean[] getUsed() {
		return used;
	}

	public List<Process> getProcesses() {
		return processes;
	}
}
