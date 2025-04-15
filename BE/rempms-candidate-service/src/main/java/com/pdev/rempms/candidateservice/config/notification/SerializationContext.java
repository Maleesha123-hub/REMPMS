package com.pdev.rempms.candidateservice.config.notification;

public class SerializationContext {
    private static final ThreadLocal<Boolean> useCustomSerialization = ThreadLocal.withInitial(() -> false);
    private static final ThreadLocal<Integer> notificationID = ThreadLocal.withInitial(() -> -1);

    public static void enableCustomSerialization() {
        useCustomSerialization.set(true);
    }

    public static void disableCustomSerialization() {
        useCustomSerialization.set(false);
    }

    public static boolean isCustomSerializationEnabled() {
        return useCustomSerialization.get();
    }

}
