package pe.bazan.luis.uni.controllers;

import org.springframework.web.bind.annotation.*;
import pe.bazan.luis.uni.domain.MemoryAllocator;
import pe.bazan.luis.uni.domain.Process;
import pe.bazan.luis.uni.models.ApiMessage;
import pe.bazan.luis.uni.models.ApiUser;
import pe.bazan.luis.uni.models.MemoryState;
import pe.bazan.luis.uni.models.ProcessStateApi;
import pe.bazan.luis.uni.programs.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

@RestController
@CrossOrigin
public class APIController {
    private static HashMap<String, ApiUser> users = new HashMap<>();

    @GetMapping(value = "/api")
    public ApiMessage apiWelcome() {
        return new ApiMessage("Welcome to the api");
    }

    // /api/new?size=100
    @GetMapping(value = "/api/new")
    public String apiNewClient(@RequestParam int size) {
        if(size > 10 * 1000) return null;

        String uuid = UUID.randomUUID().toString();
        ApiUser apiUser = new ApiUser(new Date(), uuid, new MemoryAllocator(size));

        users.put(uuid, apiUser);

        return uuid;
    }

    // /api/state?uuid=8a63f9d2-0652-41dd-9b7d-4362a19c557f
    @GetMapping(value = "/api/state")
    public MemoryState memoryState(@RequestParam String uuid) {
        ApiUser user = users.get(uuid);
        if(user == null) return null;

        user.setLastRequest(new Date());

        return new MemoryState(user.getMemoryAllocator());
    }

    @PostMapping(value = "/api/run")
    public ProcessStateApi runProgram(@RequestParam String uuid, @RequestParam ProgramList program) {
        ApiUser user = users.get(uuid);
        if(user == null) return null;

        user.setLastRequest(new Date());

        Process process = switch (program) {
            case SUM_NUMBERS -> new SumNumbers(program.name());
            case GENERIC_PROCESS -> new GenericProcess(program.name(), 12);
            case RANDOM_BYTES -> new RandomBytes(program.name());
            case RANDOM_VALUES -> new RandomValues(program.name());
            case BUBBLE_SORT -> new BubbleSortBytes(program.name(), new Random().nextInt(4, 20));
        };

        user.getMemoryAllocator().runProcess(process);

        return new ProcessStateApi(process);
    }

    @PostMapping(value = "/api/kill")
    public String kill(@RequestParam String uuid, @RequestParam int pid) {
        ApiUser user = users.get(uuid);
        if(user == null) return null;

        user.setLastRequest(new Date());

        for (int i = 0; i < user.getMemoryAllocator().getProcesses().size(); i++) {
            Process process = user.getMemoryAllocator().getProcesses().get(i);

            if(process.getPid() == pid && process.getBlockMemory() != null) {
                process.clear();
                break;
            }
        }

        return "OK";
    }

    @PostMapping(value = "/api/logout")
    public String logout(@RequestParam String uuid) {
        ApiUser user = users.get(uuid);
        if(user == null) return null;

        for (int i = 0; i < user.getMemoryAllocator().getProcesses().size(); i++) {
            Process process = user.getMemoryAllocator().getProcesses().get(i);

            if(process.getBlockMemory() != null) {
                process.clear();
                break;
            }
        }

        users.remove(user);

        user = null;

        return "OK";
    }
}
