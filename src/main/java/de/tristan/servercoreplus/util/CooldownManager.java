package de.tristan.servercoreplus.util;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class CooldownManager {

    private final Map<UUID, Long> cooldowns = new HashMap<>();

    public boolean isOnCooldown(UUID uuid, int cooldownSeconds) {
        return getRemainingMillis(uuid, cooldownSeconds) > 0;
    }

    public long getRemainingMillis(UUID uuid, int cooldownSeconds) {
        long now = System.currentTimeMillis();
        long end = cooldowns.getOrDefault(uuid, 0L) + (cooldownSeconds * 1000L);
        return Math.max(0L, end - now);
    }

    public void setUsedNow(UUID uuid) {
        cooldowns.put(uuid, System.currentTimeMillis());
    }
}
