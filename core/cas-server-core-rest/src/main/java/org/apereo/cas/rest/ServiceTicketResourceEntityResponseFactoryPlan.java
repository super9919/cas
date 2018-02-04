package org.apereo.cas.rest;

import java.util.Collection;

/**
 * This is {@link ServiceTicketResourceEntityResponseFactoryPlan}.
 *
 * @author Misagh Moayyed
 * @since 5.3.0
 */
public interface ServiceTicketResourceEntityResponseFactoryPlan {
    /**
     * Register factory.
     *
     * @param factory the factory
     */
    void registerFactory(ServiceTicketResourceEntityResponseFactory factory);

    /**
     * Gets factories.
     *
     * @return the factories
     */
    Collection<ServiceTicketResourceEntityResponseFactory> getFactories();

}
