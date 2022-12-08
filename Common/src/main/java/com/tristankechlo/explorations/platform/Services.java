package com.tristankechlo.explorations.platform;

import com.tristankechlo.explorations.Explorations;

import java.util.ServiceLoader;

public final class Services {

    public static final IPlatformHelper PLATFORM = Services.load(IPlatformHelper.class);

    public static <T> T load(Class<T> clazz) {
        final T loadedService = ServiceLoader.load(clazz)
                .findFirst()
                .orElseThrow(() -> new NullPointerException("Failed to load service for " + clazz.getName()));
        Explorations.LOGGER.debug("Loaded {} for service {}", loadedService, clazz);
        return loadedService;
    }
}