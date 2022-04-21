package org.shopin.dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.jpa.QueryHints;
import org.shopin.dto.FilterProductDto;
import org.shopin.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional(readOnly = false)
public class ProductDaoImpl implements IProductDao<Product, Long> {

    private static final Logger LOG = Logger.getLogger(ProductDaoImpl.class.getName());

    @Value("${products.per.page}")
    private byte pagesize;

    @Value("${products.page.cache}")
    private int pagecache;

    private final EntityManager em;
    private final CriteriaBuilder cb;

    @Autowired
    public ProductDaoImpl(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(Product.class);
        this.cb = em.getCriteriaBuilder();
    }

    @Override
    public List<FilterProductDto> fetchFilteredProducts(final Optional<String> fd, final Optional<String> fp, final Optional<String> fc, final Optional<String> fm,
            final Optional<String> category, final Optional<String> range, final Optional<String> id, final Optional<String> name, int page) {

        List<Predicate> predicates = new ArrayList<>();

        CriteriaQuery<FilterProductDto> criteriaQuery = cb.createQuery(FilterProductDto.class);
        Root<Product> root = criteriaQuery.from(Product.class);

        criteriaQuery.orderBy(cb.asc(root.get("price")));
        criteriaQuery.multiselect(root.get("sku"), root.get("category_id"), root.get("discount"), root.get("image"), root.get("price"), root.get("name"));

        predicates.add(cb.equal(root.get("live"), Boolean.TRUE));

        if (category.isPresent() && !range.isPresent() && !id.isPresent() && !name.isPresent()) {
            predicates.add(cb.like(root.get("image"), "/" + category.get() + "/%"));
        } else if (category.isPresent() && range.isPresent() && !id.isPresent() && !name.isPresent()) {
            predicates.add(cb.like(root.get("image"), "/" + category.get() + "/" + range.get() + "/"));
        } else if (category.isPresent() && !range.isPresent() && id.isPresent() && name.isPresent()) {
            predicates.add(cb.equal(root.get("category_id"), Long.valueOf(id.get())));
        }

        if (fd.isPresent()) {
            predicates.add(
                    cb.and(
                            cb.ge(root.get("discount"), Integer.valueOf(fd.get().substring(0, fd.get().indexOf("_")))),
                            cb.le(root.get("discount"), Integer.valueOf(fd.get().substring(fd.get().indexOf("_") + 1)))
                    )
            );
        }

        if (fp.isPresent()) {
            predicates.add(
                    cb.and(
                            cb.ge(cb.diff(root.get("price"), cb.quot(cb.prod(root.get("price"), root.get("discount")), 100)), Integer.valueOf(fp.get().substring(0, fp.get().indexOf("_")))),
                            cb.le(cb.diff(root.get("price"), cb.quot(cb.prod(root.get("price"), root.get("discount")), 100)), Integer.valueOf(fp.get().substring(fp.get().indexOf("_") + 1)))
                    )
            );
        }

        if (fc.isPresent()) {
            if (!fc.get().contains("_")) {
                predicates.add(
                        cb.equal(root.get("color"), fc.get())
                );
            } else {
                predicates.add(
                        cb.or(
                                cb.equal(root.get("color"), fc.get().substring(0, fc.get().indexOf("_"))),
                                cb.equal(root.get("color"), fc.get().substring(fc.get().indexOf("_") + 1))
                        )
                );
            }
        }

        if (fm.isPresent()) {
            String fmr = fm.get().replace("q", "/");
            if (!fmr.contains("_")) {
                predicates.add(
                        cb.gt(cb.locate(root.get("sizes"), "_" + fmr + "_"), 0)
                );
            } else {
                predicates.add(
                        cb.or(
                                cb.gt(cb.locate(root.get("sizes"), "_" + fmr.substring(0, fmr.indexOf("_")) + "_"), 0),
                                cb.gt(cb.locate(root.get("sizes"), "_" + fmr.substring(fmr.indexOf("_") + 1) + "_"), 0)
                        )
                );
            }
        }

        int first = page == 1 ? 0 : pagesize * (page - 1);
        Query query = em.createQuery(criteriaQuery.where(predicates.toArray(new Predicate[]{}))).
                setFirstResult(first).
                setMaxResults(pagesize + 1).
                setHint(QueryHints.HINT_READONLY, true);

        return query.getResultList();
    }

    @Override
    public List<FilterProductDto> fetchSearchedProducts(String like, final int page) {

        final Session session = em.unwrap(Session.class);
        session.setDefaultReadOnly(true);

        like = like.replaceAll(" si ", " ");
        like = like.replaceAll(" de ", " ");
        like = like.replaceAll(" in ", " ");
        like = like.replaceAll(" cu ", " ");
        like = like.replaceAll(" sau ", " ");

        // OFFSET pagination
        int first = page == 1 ? 0 : pagesize * (page - 1);
        Query query = em.createNativeQuery("SELECT p.sku AS sku, p.category_id AS category_id, "
                + "p.discount AS discount, p.image AS image, p.price AS price, p.name AS name "
                + "FROM products AS p WHERE p.live = TRUE "
                + "AND MATCH (p.name) AGAINST (:like IN BOOLEAN MODE)", "FetchSearchedProductsMapping").
                setHint(QueryHints.HINT_READONLY, true).
                setFirstResult(first).
                setMaxResults(pagesize + 1);
        query.setParameter("like", "'" + like + "'");

        return query.getResultList();
    }

    @Override
    public List<Product> fetchFirstPageProducts() {

        final Session session = em.unwrap(Session.class);
        session.setDefaultReadOnly(true);

        Query query = em.createQuery("SELECT p FROM Product AS p "
                + "WHERE p.live = TRUE AND p.firstpage = TRUE", Product.class)
                .setHint(QueryHints.HINT_READONLY, true)
                .setHint("org.hibernate.cacheable", true)
                .setMaxResults(20);

        return query.getResultList();

        // printCacheRegionStatistics(Product.class.getName());
        // return p;
    }

    @Override
    public List<Product> fetchAllProducts(final int page) {

        final Session session = em.unwrap(Session.class);
        session.setDefaultReadOnly(true);

        // OFFSET pagination
        int first = page == 1 ? 0 : pagesize * (page - 1);
        final TypedQuery<Product> query = em.createQuery("SELECT p FROM Product AS p "
                + "WHERE p.live = TRUE ORDER BY p.id ASC", Product.class).
                setFirstResult(first).
                setMaxResults(pagesize);
        query.setHint(QueryHints.HINT_READONLY, true);

        if (page <= pagecache) {
            query.setHint("org.hibernate.cacheable", true);
        }

        return query.getResultList();

        // printCacheRegionStatistics(Product.class.getName());
        // return p;
    }

    @Override
    public List<Product> fetchCategoryProducts(final int page, final String category) {

        final Session session = em.unwrap(Session.class);
        session.setDefaultReadOnly(true);

        // OFFSET pagination
        int first = page == 1 ? 0 : pagesize * (page - 1);
        final TypedQuery<Product> query = em.createQuery("SELECT p FROM Product AS p "
                + "WHERE p.live = TRUE AND p.image LIKE :image ORDER BY p.id ASC", Product.class).
                setFirstResult(first).
                setMaxResults(pagesize);
        query.setParameter("image", "/" + category + "/%");
        query.setHint(QueryHints.HINT_READONLY, true);

        if (page <= pagecache) {
            query.setHint("org.hibernate.cacheable", true);
        }

        return query.getResultList();

        // printCacheRegionStatistics(Product.class.getName());
        // return p;
    }

    @Override
    public List<Product> fetchCategoryRangeProducts(final int page, final String category, final String range) {

        final Session session = em.unwrap(Session.class);
        session.setDefaultReadOnly(true);

        // OFFSET pagination
        int first = page == 1 ? 0 : pagesize * (page - 1);
        final TypedQuery<Product> query = em.createQuery("SELECT p FROM Product AS p "
                + "WHERE p.live = TRUE AND p.image LIKE :image ORDER BY p.id ASC", Product.class).
                setFirstResult(first).
                setMaxResults(pagesize);
        query.setParameter("image", "/" + category + "/" + range + "/");
        query.setHint(QueryHints.HINT_READONLY, true);

        if (page <= pagecache) {
            query.setHint("org.hibernate.cacheable", true);
        }

        return query.getResultList();

        // printCacheRegionStatistics(Product.class.getName());
        // return p;
    }

    @Override
    public List<Product> fetchSubcategoryProducts(final int page, final long id) {
        final Session session = em.unwrap(Session.class);
        session.setDefaultReadOnly(true);

        // OFFSET pagination
        int first = page == 1 ? 0 : pagesize * (page - 1);
        final TypedQuery<Product> query = em.createQuery("SELECT p FROM Product AS p "
                + "WHERE p.live = TRUE AND p.category_id = :id ORDER BY p.id ASC", Product.class).
                setFirstResult(first).
                setMaxResults(pagesize);
        query.setParameter("id", id);
        query.setHint(QueryHints.HINT_READONLY, true);

        if (page <= pagecache) {
            query.setHint("org.hibernate.cacheable", true);
        }

        return query.getResultList();

        // printCacheRegionStatistics(Product.class.getName());
        // return p;
    }

    /*
    public void printCacheRegionStatistics(String region) {

        SecondLevelCacheStatistics statistics
                = em.getEntityManagerFactory().unwrap(SessionFactory.class).getStatistics().getSecondLevelCacheStatistics(region);
        LOG.info("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
        //LOG.info("Details: "+region + ": " + statistics + statistics.getEntries());
        // LOG.info("\nFlush:" + session.getFlushMode());
        LOG.log(Level.INFO, "\nRegion:{0}\nStatistics:{1}\nEntries:{2}", new Object[]{region, statistics, statistics.getEntries()});       
        LOG.info("\n$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
    }
     */
}
