package com.shine2share.common;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;

public class CommonUtil {
    private static ModelMapper mapper = null;
    public static <S, T> T toObject(S s, Class<T> targetClass) {
        getMapper();
        return mapper.map(s, targetClass);
    }
    private static void getMapper() {
        if (mapper == null) {
            mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        }
    }
    public static boolean isNullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
