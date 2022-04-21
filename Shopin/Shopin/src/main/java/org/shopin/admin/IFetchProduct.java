package org.shopin.admin;

import java.io.IOException;
import java.util.List;
import org.shopin.dto.CategoryDto;
import org.springframework.stereotype.Service;

@Service
public interface IFetchProduct {

    public long incFetchAllCount(long now) throws IOException;

    public long incFetchCategoryCount(final String key, long now) throws IOException;

    public long incFetchCategoryRangeCount(final String key1, final String key2, long now) throws IOException;

    public long incFetchSubcategoryCount(final long key, long now) throws IOException;

    public Float incFetchAllTopPrice(final Float now, final Float old) throws IOException;

    public long decFetchAllCount(long now) throws IOException;

    public long decFetchCategoryCount(final String key, long now) throws IOException;

    public long decFetchCategoryRangeCount(final String key1, final String key2, long now) throws IOException;

    public long decFetchSubcategoryCount(final long key, long now) throws IOException;

    public Float decFetchAllTopPrice(final Float now, final Float old) throws IOException;

    public void evictAllCategories();
}
