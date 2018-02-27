package org.aerogear.mobile.security;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.aerogear.mobile.core.executor.AppExecutors;
import org.aerogear.mobile.core.metrics.MetricsService;

import java.util.Collection;
import java.util.HashSet;

import static org.aerogear.mobile.core.utils.SanityCheck.nonNull;

/**
 * Entry point for the SecurityCheckExecutor.
 * This class provides the builders.
 */
public class SecurityCheckExecutor {
    private SecurityCheckExecutor() {}

    /**
     * Entry point for SecurityCheckExecutor builders.
     */
    public static class Builder {

        /**
         * Base class for SecurityCheckExecutor builders.
         *
         * @param <T> The type of this builder
         * @param <K> The type of the built object
         */
        static abstract class AbstractBuilder<T, K> {
            /**
             * {@link Context} of the device. Cannot be null.
             */
            private final Context ctx;

            /**
             * the {@link Collection<SecurityCheck>} of security checks to be tested. Can be null.
             */
            private final Collection<SecurityCheck> checks = new HashSet<>();

            /**
             * {@link MetricsService}. Can be null.
             */
            private MetricsService metricsService;

            /**
             * Creates AbstractBuilder object.
             *
             * @param ctx {@link Context} of the device
             * @throws IllegalArgumentException if {@param ctx} is null
             */
            public AbstractBuilder(@NonNull final Context ctx) {
                this.ctx = nonNull(ctx, "context");
            }

            /**
             * Adds a new security check by providing a security check instance.
             *
             * @param check the {@link SecurityCheck} to be added
             * @return {@link T} this
             * @throws IllegalArgumentException if {@param check} is null
             */
            public T withSecurityCheck(@NonNull final SecurityCheck check) {
                checks.add(nonNull(check, "check"));
                return (T) this;
            }

            /**
             * Adds a new security check.
             *
             * @param checkType {@link SecurityCheckType} to be added
             * @return {@link T} this
             * @throws IllegalArgumentException if {@param checkType} is null
             */
            public T withSecurityCheck(@NonNull final SecurityCheckType checkType) {
                checks.add(nonNull(checkType, "checkType").getSecurityCheck());
                return (T) this;
            }

            /**
             * Sets the metric service to be used.
             * The metric service should be a {@link AppExecutors#singleThreadService()}.
             *
             * @param metricsService the {@link MetricsService} {@link AppExecutors#singleThreadService()} to be used
             * @return this
             */
            public T withMetricsService(@Nullable final MetricsService metricsService) {
                this.metricsService = metricsService;
                return (T) this;
            }

            /**
             * Gets the context for the device.
             *
             * @return {@link Context}
             */
            protected Context getCtx() {
                return ctx;
            }

            /**
             * Gets the metric service being used.
             *
             * @return {@link MetricsService}
             */
            protected MetricsService getMetricsService() {
                return metricsService;
            }

            /**
             * Gets all the checks that are to be tested.
             *
             * @return {@link Collection<SecurityCheck>}
             */
            protected Collection<SecurityCheck> getChecks() {
                return checks;
            }

            /**
             * Builds the executor according to the passed in parameters.
             *
             * @return {@link K} the executor instance
             */
            public abstract K build();
        }

        /**
         * Creates a Builder for AsyncSecurityCheckExecutor.
         *
         * @param ctx {@link Context} for the device.
         * @return {@link AsyncSecurityCheckExecutor.Builder}.
         * @throws IllegalArgumentException if {@param ctx} is null.
         */
        private AsyncSecurityCheckExecutor.Builder newAsyncBuilder(@NonNull final Context ctx) {
            return new AsyncSecurityCheckExecutor.Builder(ctx);
        }

        /**
         * Creates a Builder for SyncSecurityCheckExecutor.
         *
         * @param ctx {@link Context} for the device.
         * @return {@link SyncSecurityCheckExecutor.Builder}.
         * @throws IllegalArgumentException if {@param ctx} is null.
         */
        private SyncSecurityCheckExecutor.Builder newSyncBuilder(@NonNull final Context ctx) {
            return new SyncSecurityCheckExecutor.Builder(ctx);
        }

        /**
         * Creates a new AsyncSecurityCheckExecutor Builder.
         *
         * @param ctx {@link Context} for the device.
         * @return {@link AsyncSecurityCheckExecutor.Builder}.
         * @throws IllegalArgumentException if {@param ctx} is null.
         */
        public static AsyncSecurityCheckExecutor.Builder newAsyncExecutor(@NonNull final Context ctx) {
            return new Builder().newAsyncBuilder(ctx);
        }

        /**
         * Creates a new SyncSecurityCheckExecutor Builder.
         *
         * @param ctx {@link Context} for the device.
         * @return {@link SyncSecurityCheckExecutor.Builder}.
         * @throws IllegalArgumentException if {@param ctx} is null.
         */
        public static SyncSecurityCheckExecutor.Builder newSyncExecutor(@NonNull final Context ctx) {
            return new Builder().newSyncBuilder(ctx);
        }
    }
}
