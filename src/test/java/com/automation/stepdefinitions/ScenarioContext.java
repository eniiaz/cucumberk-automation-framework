package com.automation.stepdefinitions;

import java.util.HashMap;
import java.util.Map;

/**
 * Scenario Context - Shared State Container
 * Allows sharing data between step definitions within a scenario
 */
public class ScenarioContext {
    private static final ThreadLocal<Map<String, Object>> context = ThreadLocal.withInitial(HashMap::new);

    private ScenarioContext() {
        // Private constructor
    }

    /**
     * Store value in context
     * @param key context key
     * @param value value to store
     */
    public static void set(String key, Object value) {
        context.get().put(key, value);
    }

    /**
     * Retrieve value from context
     * @param key context key
     * @param <T> expected return type
     * @return stored value
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(String key) {
        return (T) context.get().get(key);
    }

    /**
     * Retrieve value from context with type casting
     * @param key context key
     * @param type expected class type
     * @param <T> expected return type
     * @return stored value
     */
    public static <T> T get(String key, Class<T> type) {
        return type.cast(context.get().get(key));
    }

    /**
     * Check if key exists in context
     * @param key context key
     * @return true if key exists
     */
    public static boolean contains(String key) {
        return context.get().containsKey(key);
    }

    /**
     * Remove value from context
     * @param key context key
     */
    public static void remove(String key) {
        context.get().remove(key);
    }

    /**
     * Clear all context data
     */
    public static void clear() {
        context.get().clear();
    }

    /**
     * Clear and remove thread local
     */
    public static void reset() {
        context.remove();
    }

    // Common context keys
    public static final String API_RESPONSE = "api_response";
    public static final String USER_ID = "user_id";
    public static final String AUTH_TOKEN = "auth_token";
    public static final String TEST_DATA = "test_data";
    public static final String DB_RESULT = "db_result";
}

