package com.example.jpajob;

import org.springframework.batch.item.database.orm.AbstractJpaQueryProvider;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.Query;

/**
 * Abstract JPA Query Provider to serve as a base class for all JPA Query providers.
 */
public class CustomerByCityQueryProvider extends AbstractJpaQueryProvider {
    private String cityName;

    public Query createQuery() {
        EntityManager manager = getEntityManager();
        Query query = manager.createQuery("select c from Customer c where c.city = :city");
        query.setParameter("city", cityName);
        return query;
    }

    public void afterPropertiesSet() throws Exception {
        Assert.notNull(cityName, "City name is required");
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}