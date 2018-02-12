package gov.hud.lrs.common.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.BeanDescription;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationConfig;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializerFactory;
import com.fasterxml.jackson.databind.ser.BeanSerializerModifier;
import com.fasterxml.jackson.databind.ser.SerializerFactory;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;

public class Util {

	public static <T, K> Map<K, T> index(Iterable<T> iterable, Function<T, K> keyFunction) {
		Map<K, T> map = new HashMap<K, T>();
    	Iterator<T> iterator = iterable.iterator();
    	while (iterator.hasNext()) {
    		T t = iterator.next();
    		map.put(keyFunction.apply(t), t);
    	}
    	return map;
	}

	public static <T, K, V> Map<K, V> index(Iterable<T> iterable, Function<T, K> keyFunction, Function<T, V> valueFunction) {
    	Map<K, V> map = new HashMap<K, V>();
    	Iterator<T> iterator = iterable.iterator();
    	while (iterator.hasNext()) {
    		T t = iterator.next();
    		map.put(keyFunction.apply(t), valueFunction.apply(t));
    	}
    	return map;
	}

	public static <T, K> Multimap<K, T> multiindex(Iterable<T> iterable, Function<T, K> keyFunction) {
    	Multimap<K, T> multimap = ArrayListMultimap.create();
    	Iterator<T> iterator = iterable.iterator();
    	while (iterator.hasNext()) {
    		T t = iterator.next();
    		multimap.put(keyFunction.apply(t), t);
    	}
    	return multimap;
	}

	public static <T, K, V> Multimap<K, V> multiindex(Iterable<T> iterable, Function<T, K> keyFunction, Function<T, V> valueFunction) {
    	Multimap<K, V> multimap = ArrayListMultimap.create();
    	Iterator<T> iterator = iterable.iterator();
    	while (iterator.hasNext()) {
    		T t = iterator.next();
    		multimap.put(keyFunction.apply(t), valueFunction.apply(t));
    	}
    	return multimap;
	}

	public static <T> Iterable<T> emptyIfNull(Iterable<T> iterable) {
	    return iterable == null ? Collections.<T>emptyList() : iterable;
	}

	public static <T, R> List<R> map(Iterable<T> iterable, Function<T, R> function) {
		ArrayList<R> result = new ArrayList<R>();
		for (T t : iterable) {
			result.add(function.apply(t));
		}
		return result;
	}

	private static final Set<String> PII_FIELDS = ImmutableSet.of(
		"firstName",
		"middleInitial",
		"lastName",
		"phoneNumber",
		"taxIdNumber",
		"faxNumber",
		"streetAddress",
		"streetAddress2",
		"email",
		"secondaryEmail",
		"underwriterName",
		"borr_brth_dt",
		"borr_1_name",
		"borr_1_ssn",
		"borr_2_name",
		"borr_2_ssn",
		"gift_ltr_tin",
		"gift_ltr_2_tin",
		"prop_addr_1",
		"prop_addr_2",
		"loan_officer"
	);

    static class PiiCleansingSerializer extends JsonSerializer<Object> {
        @Override
        public void serialize(Object value, JsonGenerator jgen, SerializerProvider provider) throws IOException, JsonProcessingException {
            jgen.writeString("***");
        }
    }

    static class PiiCleansingBeanSerializerModifier extends BeanSerializerModifier {
		@Override
        public List<BeanPropertyWriter> changeProperties(SerializationConfig serializationConfig, BeanDescription beanDescription, List<BeanPropertyWriter> beanPropertyWriters) {
            for (int i = 0; i < beanPropertyWriters.size(); i++) {
                BeanPropertyWriter beanPropertyWriter = beanPropertyWriters.get(i);
                if (PII_FIELDS.contains(beanPropertyWriter.getName())) {
                    beanPropertyWriter.assignSerializer(new PiiCleansingSerializer());
                }
            }
            return beanPropertyWriters;
        }
    }

	public static String toCleansedJson(Object object) {
		SerializerFactory serializerFactory = BeanSerializerFactory.instance.withSerializerModifier(new PiiCleansingBeanSerializerModifier());
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.setSerializerFactory(serializerFactory);

		try {
			return objectMapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}

}
