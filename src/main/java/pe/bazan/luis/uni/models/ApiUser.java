package pe.bazan.luis.uni.models;

import pe.bazan.luis.uni.domain.MemoryAllocator;

import java.util.Date;

public class ApiUser {
    private Date lastRequest;
    private String uuid;
    private MemoryAllocator memoryAllocator;

    public ApiUser(Date lastRequest, String uuid, MemoryAllocator memoryAllocator) {
        this.lastRequest = lastRequest;
        this.uuid = uuid;
        this.memoryAllocator = memoryAllocator;
    }

    public Date getLastRequest() {
        return lastRequest;
    }

    public String getUuid() {
        return uuid;
    }

    public MemoryAllocator getMemoryAllocator() {
        return memoryAllocator;
    }

    public void setLastRequest(Date lastRequest) {
        this.lastRequest = lastRequest;
    }
}
