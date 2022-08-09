package test.task.PIS.config;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.postgresql.util.PGobject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.TypeDescriptor;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;
import org.springframework.data.convert.WritingConverter;
import org.springframework.data.jdbc.core.convert.JdbcCustomConversions;
import org.springframework.data.jdbc.repository.config.AbstractJdbcConfiguration;
import org.springframework.lang.NonNull;
import test.task.PIS.dto.ContractualParty;
import test.task.PIS.entity.ContractualParties;

@Configuration
public class JdbcConfiguration extends AbstractJdbcConfiguration {

    private final ObjectMapper objectMapper;

    public JdbcConfiguration(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    @Override
    public JdbcCustomConversions jdbcCustomConversions() {
        return new JdbcCustomConversions(Arrays.asList(
                new EntityReadingConverter(objectMapper),
                new EntityWritingConverter(objectMapper)
        ));
    }

    @SuppressWarnings("CdiInjectionPointsInspection")
    @WritingConverter
    public static class EntityWritingConverter implements Converter<ContractualParties, PGobject> {

        private final ObjectMapper objectMapper;

        EntityWritingConverter(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public PGobject convert(@NonNull ContractualParties source) {
            PGobject json = new PGobject();
            json.setType("json");
            try {
                json.setValue(objectMapper.writeValueAsString(source.getContractualParties()));
            } catch (Exception e) {
                return null;
            }
            return json;
        }
    }

    @SuppressWarnings("CdiInjectionPointsInspection")
    @ReadingConverter
    public static class EntityReadingConverter implements Converter<PGobject, ContractualParties> {

        private final ObjectMapper objectMapper;

        EntityReadingConverter(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public ContractualParties convert(@NonNull PGobject pgObject) {
            String source = pgObject.getValue();
            try {
                ContractualParties contractualParties = new ContractualParties();
                contractualParties.setContractualParties(objectMapper.readValue(source, new TypeReference<List<ContractualParty>>(){}));
                return contractualParties;
            } catch (Exception e) {
                return null;
            }
        }
    }
}
