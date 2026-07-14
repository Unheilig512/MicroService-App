import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceRegisteredEvent;
import org.springframework.cloud.netflix.eureka.server.event.EurekaInstanceCanceledEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Component
public class EurekaStateLogger {

	private static final Logger LOGGER = LoggerFactory.getLogger(YourClassName.class);

	// 1. Ловим момент, когда любой сервис УСПЕШНО ПОДКЛЮЧИЛСЯ
	@EventListener
	public void onInstanceRegistered(EurekaInstanceRegisteredEvent event) {
		log.info("✅ Сервис {} зарегистрирован по адресу {}:{}",
				event.getInstanceInfo().getAppName(),
				event.getInstanceInfo().getIPAddr(),
				event.getInstanceInfo().getPort());
	}

	// 2. Ловим момент, когда сервис ОТКЛЮЧИЛСЯ (или упал)
	@EventListener
	public void onInstanceCanceled(EurekaInstanceCanceledEvent event) {
		log.info("❌  Сервис {} отлючился",
				event.getInstanceInfo().getAppName();
	}
}