package example.invisibleplayerglow;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.ClipContext;

import java.util.concurrent.ThreadLocalRandom;

public final class InvisiblePlayerGlowClient implements ClientModInitializer {
    private static int tickCounter = 0;

    @Override
    public void onInitializeClient() {
        ClientTickEvents.END_CLIENT_TICK.register(InvisiblePlayerGlowClient::onClientTick);
    }

    private static void onClientTick(Minecraft client) {
        if (client.level == null || client.player == null || client.options == null) {
            return;
        }

        // A small burst every few ticks keeps the effect visible without flooding particles.
        if (++tickCounter % 3 != 0) {
            return;
        }

        Player viewer = client.player;
        double maxDistance = client.options.renderDistance().get() * 16.0;
        ThreadLocalRandom random = ThreadLocalRandom.current();

        for (Player target : client.level.players()) {
            if (target == viewer || !target.isAlive() || !target.isInvisible()) {
                continue;
            }

            if (viewer.distanceToSqr(target) > maxDistance * maxDistance) {
                continue;
            }

            // Do not show the effect if a solid block is between the viewer and the player.
            Vec3 start = viewer.getEyePosition();
            Vec3 end = target.getBoundingBox().getCenter();
            BlockHitResult sight = client.level.clip(new ClipContext(
                    start, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.NONE, viewer
            ));
            if (sight.getType() != HitResult.Type.MISS) {
                continue;
            }

            double halfWidth = Math.max(0.22, target.getBbWidth() * 0.55);
            double x = target.getX() + random.nextDouble(-halfWidth, halfWidth);
            double y = target.getY() + random.nextDouble(0.15, Math.max(0.3, target.getBbHeight() - 0.1));
            double z = target.getZ() + random.nextDouble(-halfWidth, halfWidth);

            // End-rod particles are bright, sparkly, and use normal world depth testing.
            client.level.addParticle(ParticleTypes.END_ROD, x, y, z,
                    random.nextDouble(-0.005, 0.005),
                    random.nextDouble(0.005, 0.018),
                    random.nextDouble(-0.005, 0.005));
        }
    }
}
