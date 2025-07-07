package pe.bazan.luis.uni.domain;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Process {
	private ProcessState state;
	private BlockMemory blockMemory;
	private int sizeMemory;
	private int pid;
	private String programName;
	private List<String> logs;

	public Process(String programName, int sizeMemory) {
		this.state = ProcessState.WAIT;
		this.sizeMemory = sizeMemory;
		this.programName = programName;
		this.logs = new ArrayList<>();
	}

	public void run(int pid) {
		this.pid = pid;
	}

	public boolean load(MemoryAllocator memoryAllocator) {
		if(!memoryAllocator.isFree(this.sizeMemory)) return false;

		try {
			this.blockMemory = new BlockMemory(memoryAllocator, this.sizeMemory);
			this.state = ProcessState.EXECUTING;
			return true;
		} catch (MemoryAllocator.MemoryOutException e) {
			return false;
		}
	}

	public void clear() {
		this.state = ProcessState.EXIT;
		blockMemory.getMemoryAllocator().clearMemory(this);
	}

	public int getSizeMemory() {
		return sizeMemory;
	}

	public BlockMemory getBlockMemory() {
		return blockMemory;
	}

	public void setState(ProcessState state) {
		this.state = state;
		if(state == ProcessState.EXIT) clear();
	}

	public String getProgramName() {
		return programName;
	}

	public ProcessState getState() {
		return state;
	}

	public int getPid() {
		return pid;
	}

	public List<String> getLogs() {
		return logs;
	}

	public void log(String log) {
		this.logs.add(getFormattedTime() + log);
		System.out.println(log);
	}

	private static String getFormattedTime() {
		Date currentTime = new Date();

		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		String formattedTime = "[" + format.format(currentTime) + "] ";

		return formattedTime;
	}
}
