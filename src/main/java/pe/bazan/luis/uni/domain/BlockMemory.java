package pe.bazan.luis.uni.domain;

import java.nio.ByteBuffer;

public class BlockMemory {
	private int[] memoryDirections;
	private int sizeOfMemory;
	private MemoryAllocator memoryAllocator;

	public BlockMemory(MemoryAllocator memoryAllocator, int sizeOfMemory) throws
			MemoryAllocator.MemoryOutException {
		this.sizeOfMemory = sizeOfMemory;
		this.memoryDirections = memoryAllocator.getMemory(sizeOfMemory);
		this.memoryAllocator = memoryAllocator;
	}

	public int[] getMemoryDirections() {
		return memoryDirections;
	}

	public int getSizeOfMemory() {
		return sizeOfMemory;
	}

	public void set(int index, byte data) {
		if(index >= sizeOfMemory) return;
		memoryAllocator.setByte(memoryDirections[index], data);
	}

	public void setInt(int index, int number) {
		if (index + 4 > sizeOfMemory) return;

		byte[] data = ByteBuffer.allocate(4).putInt(number).array();

		for (int i = 0; i < 4; i++) {
			set(index + i, data[i]);
		}
	}

	public int getInt(int index) {
		if (index + 4 > sizeOfMemory) return 0;

		byte[] data = new byte[4];

		for (int i = 0; i < 4; i++) {
			data[i] = get(index + i);
		}

//		System.out.printf("%d, %d, %d, %d", data[0], data[1], data[2], data[3]);

		return ByteBuffer.wrap(data).getInt();
	}

	public byte get(int index) {
		return memoryAllocator.getByte(memoryDirections[index]);
	}

	public void clear() {
		memoryAllocator.clearMemory(memoryDirections);
	}

	public MemoryAllocator getMemoryAllocator() {
		return memoryAllocator;
	}
}
