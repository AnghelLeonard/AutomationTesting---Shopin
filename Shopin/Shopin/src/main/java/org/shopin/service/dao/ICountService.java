package org.shopin.service.dao;

import org.springframework.stereotype.Service;

@Service
public interface ICountService {

    public long fetchAllCount();

    public long fetchCategoryCount(String key);

    public long fetchCategoryRangeCount(String key1, String key2);

    public long fetchSubcategoryCount(long key);

    public Float fetchAllTopPrice();

    public void fetchAllColorsByCategory();

    public void fetchAllPricesCategory();
}
