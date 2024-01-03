package projeto.api.utils.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.AttributeConverter;
import projeto.api.utils.dto.ItemDTO;

public class ItemConverter implements AttributeConverter<ItemDTO, String> {
    private static final ObjectMapper mapper = new ObjectMapper();
    @Override
    public String convertToDatabaseColumn(ItemDTO itemDTO) {
        if(itemDTO == null) return null;

        try {
            return mapper.writeValueAsString(itemDTO);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ItemDTO convertToEntityAttribute(String dbData) {
        try {
            return mapper.readValue(dbData,ItemDTO.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
