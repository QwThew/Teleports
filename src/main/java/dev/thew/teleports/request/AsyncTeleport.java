//
//
//import dev.thew.teleports.model.User;
//import lombok.Setter;
//import org.bukkit.Bukkit;
//import org.bukkit.Location;
//import org.bukkit.entity.Player;
//import org.bukkit.event.player.PlayerRespawnEvent;
//import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
//
//import java.math.BigDecimal;
//import java.util.Calendar;
//import java.util.GregorianCalendar;
//import java.util.concurrent.CompletableFuture;
//import java.util.concurrent.ExecutionException;
//
//public class AsyncTeleport {
//    private final User teleportOwner;
//    private AsyncTimedTeleport timedTeleport;
//
//    @Setter
//    private TeleportType tpType;
//
//    public AsyncTeleport(final User user) {
//        this.teleportOwner = user;
//        tpType = TeleportType.NORMAL;
//    }
//
//    public void cooldown(final boolean check) throws Throwable {
//        final CompletableFuture<Boolean> exceptionFuture = new CompletableFuture<>();
//        if (cooldown(check, exceptionFuture)) {
//            try {
//                exceptionFuture.get();
//            } catch (final ExecutionException e) {
//                throw e.getCause();
//            }
//        }
//    }
//
//    public boolean cooldown(final boolean check, final CompletableFuture<Boolean> future) {
//        final Calendar time = new GregorianCalendar();
//        if (teleportOwner.getTimeLastTeleport() > 0) {
//            // Take the current time, and remove the delay from it.
//            final double cooldown = ess.getSettings().getTeleportCooldown();
//            final Calendar earliestTime = new GregorianCalendar();
//            earliestTime.add(Calendar.SECOND, -(int) cooldown);
//            earliestTime.add(Calendar.MILLISECOND, -(int) ((cooldown * 1000.0) % 1000.0));
//            // This value contains the most recent time a teleportPlayer could have been used that would allow another use.
//            final long earliestLong = earliestTime.getTimeInMillis();
//
//            // When was the last teleportPlayer used?
//            final long lastTime = teleportOwner.getTimeLastTeleport();
//
//            if (lastTime > time.getTimeInMillis()) {
//                // This is to make sure time didn't get messed up on last teleportPlayer use.
//                // If this happens, let's give the user the benifit of the doubt.
//                teleportOwner.setTimeLastTeleport(time.getTimeInMillis());
//                return false;
//            } else if (lastTime > earliestLong
//                    && cooldownApplies()) {
//                time.setTimeInMillis(lastTime);
//                time.add(Calendar.SECOND, (int) cooldown);
//                time.add(Calendar.MILLISECOND, (int) ((cooldown * 1000.0) % 1000.0));
//                future.completeExceptionally(new TranslatableException("timeBeforeTeleport", DateUtil.formatDateDiff(time.getTimeInMillis())));
//                return true;
//            }
//        }
//        // if justCheck is set, don't update lastTeleport; we're just checking
//        if (!check) {
//            teleportOwner.setTimeLastTeleport(time.getTimeInMillis());
//        }
//        return false;
//    }
//
//    private boolean cooldownApplies() {
//        boolean applies = true;
//        final String globalBypassPerm = "essentials.teleport.cooldown.bypass";
//        switch (tpType) {
//            case NORMAL:
//                applies = !teleportOwner.isAuthorized(globalBypassPerm);
//                break;
//            case BACK:
//                applies = !(teleportOwner.isAuthorized(globalBypassPerm) &&
//                        teleportOwner.isAuthorized("essentials.teleport.cooldown.bypass.back"));
//                break;
//            case TPA:
//                applies = !(teleportOwner.isAuthorized(globalBypassPerm) &&
//                        teleportOwner.isAuthorized("essentials.teleport.cooldown.bypass.tpa"));
//                break;
//        }
//        return applies;
//    }
//
//    public void teleportPlayer(final User otherUser, final Location loc, final TeleportCause cause, final CompletableFuture<Boolean> future) {
//        teleport(otherUser, loc, cause, future);
//    }
//
//
//    private void teleport(final User teleportee, Location location, final TeleportCause cause, final CompletableFuture<Boolean> future) {
//        double delay = ess.getSettings().getTeleportDelay();
//
//        if (cooldown(true, future)) {
//            future.complete(false);
//            return;
//        }
//
//        cancel();
//        initTimer((long) (delay * 1000.0), teleportee, location, cause, future);
//    }
//
//    //If we need to cancelTimer a pending teleportPlayer call this method
//    private void cancel() {
//        if (timedTeleport != null) {
//            timedTeleport.cancelTimer(false);
//            timedTeleport = null;
//        }
//    }
//
//    private void initTimer(final long delay, final User teleportUser, final Location target, final TeleportCause cause, CompletableFuture<Boolean> future) {
//        timedTeleport = new AsyncTimedTeleport(teleportOwner, this, delay, future, teleportUser, target, cause);
//    }
//
//    public enum TeleportType {
//        TPA,
//        BACK,
//        NORMAL
//    }
//}
//
