import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class EurekaStateLogger {

	// 1. Ловим момент, когда любой сервис УСПЕШНО ПОДКЛЮЧИЛСЯ
	@EventListener
	public void onInstanceRegistered(EurekaInstanceRegisteredEvent event) {
		String serviceName = event.getInstanceInfo().getAppName();
		String ipAddress = event.getInstanceInfo().getIPAddr();
		int port = event.getInstanceInfo().getPort();

		System.out.println("\n" + "✅ ".repeat(15));
		System.out.printf("[EUREKA] ПОДКЛЮЧИЛСЯ НОВЫЙ СЕРВИС: %s%n", serviceName);
		System.out.printf("[INFO] Адрес: http://%s:%d%n", ipAddress, port);
		System.out.println("✅ ".repeat(15) + "\n");
	}

	// 2. Ловим момент, когда сервис ОТКЛЮЧИЛСЯ (или упал)
	@EventListener
	public void onInstanceCanceled(EurekaInstanceCanceledEvent event) {
		String serviceName = event.getAppName();
		String serverId = event.getServerId();

		System.out.println("\n" + "❌ ".repeat(15));
		System.out.printf("[EUREKA] СЕРВИС ОТКЛЮЧИЛСЯ/ПРОПАЛ: %s%n", serviceName);
		System.out.printf("[INFO] ID сервера: %s%n", serverId);
		System.out.println("❌ ".repeat(15) + "\n");
	}
}