package test.task.PIS.config;

import java.time.LocalDateTime;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.relational.core.mapping.event.AfterSaveEvent;
import org.springframework.data.relational.core.mapping.event.BeforeSaveEvent;
import test.task.PIS.entity.Contract;

@Configuration
public class PersistenceConfiguration {

    @Bean
    public ApplicationListener<BeforeSaveEvent> setIdAndDate() {
        return event -> {
            Object entity = event.getEntity();
            if (entity instanceof Contract) {
                //((Contract) entity).setId(UUID.randomUUID());
                ((Contract) entity).setDateCreate(LocalDateTime.now());
            }
        };
    }

    @Bean
    public ApplicationListener<AfterSaveEvent> setNewToFalse() {
        return event -> {
            Object entity = event.getEntity();
            if (entity instanceof Contract) {
                ((Contract) entity).setNew(false);
            }
        };
    }
}
