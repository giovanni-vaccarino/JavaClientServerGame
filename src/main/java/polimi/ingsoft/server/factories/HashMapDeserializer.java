package polimi.ingsoft.server.factories;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.KeyDeserializer;

import java.io.IOException;

public class HashMapDeserializer extends KeyDeserializer {
    @Override
    public Object deserializeKey(final String key, final DeserializationContext ctxt ) throws IOException, JsonProcessingException
    {
        System.out.print(key);
        return null;
    }
}