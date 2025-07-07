package pe.bazan.luis.uni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;
import pe.bazan.luis.uni.domain.*;
import pe.bazan.luis.uni.programs.GenericProcess;
import pe.bazan.luis.uni.programs.RandomBytes;
import pe.bazan.luis.uni.programs.SumNumbers;

@SpringBootApplication
public class Main {

	public static void main(String[] args) {
//		memoryallocator memoryallocator = new memoryallocator(100);
//
//		randombytes randombytes = new randombytes("random byte");
//
//		memoryallocator.runprocess(randombytes);
//		memoryallocator.printvalues();
//		sumnumbers sumnumbers = new sumnumbers("sum numbers 1");
//		memoryallocator.runprocess(sumnumbers); // internal printstate()
//		memoryallocator.printstate();
//		genericprocess genericprocess = new genericprocess("generic", 30);
//		memoryallocator.runprocess(genericprocess);

		System.out.printf("hi");
		ConfigurableApplicationContext context = SpringApplication.run(Main.class, args);
		Environment env = context.getEnvironment();
		String port = env.getProperty("local.server.port");
		System.out.printf("front in http://127.0.0.1:%s/", port);
	}
}