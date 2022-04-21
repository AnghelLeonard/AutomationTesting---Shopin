package org.shopin.admin;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import java.util.Optional;
import org.shopin.dao.CategoryRepository;
import org.shopin.dao.DetailRepository;
import org.shopin.dao.ProductRepository;
import org.shopin.dao.UserRepository;
import org.shopin.model.Detail;
import org.shopin.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.core.annotation.Order;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Order(1001) 
public class AdminProductService implements IAdminProduct {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private DetailRepository detailRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    public void modifyProduct(JsonNode node) throws IOException {

        productRepository.deleteOneProduct(node.get("sku").asText());
        detailRepository.deleteOneDetail(node.get("sku").asText());
        addProduct(node);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    public void addProduct(JsonNode node) throws IOException {

        if ("true".equals(node.get("live").asText())) {
            updateCategoryTable(node);
        }
        updateProductTable(node);
        updateDetailTable(node);
    }

    @Override
    @Transactional
    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    public void updateUser(final String email, final boolean status) {
        if (status) {
            userRepository.enableUserAccount(email);
        } else {
            userRepository.disableUserAccount(email);
            userRepository.deleteRememberMe(email);

            sessionRegistry.getAllPrincipals().stream().filter((principal)
                    -> (principal instanceof User)).map((principal)
                    -> (UserDetails) principal).filter((userDetails)
                    -> (userDetails.getUsername().equals(email))).forEachOrdered((userDetails)
                    -> {
                          sessionRegistry.getAllSessions(userDetails, true).forEach((information)
                          -> {
                                information.expireNow();
                             });
                    });
        }
    }

    @Override
    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    public String loadProduct(String sku) {

        String p = "";
        HeavyProductDto product = adminRepository.fetchHeavyBySku(sku);

        if (product != null) {

            String mcat = product.getImage().substring(1, 2);
            String scat = product.getImage().substring(3, 5);
            p = p + "{\"product\":{\"name\":\"" + product.getName() + "\",\"sku\":\"" + product.getSku() + "\",\"price\":\"" + product.getPrice() + "\",\"categoryid\":\"" + product.getCategory_id() + "\",\"mcat\":\"" + mcat + "\",\"scat\":\"" + scat + "\",\"discount\":\"" + product.getDiscount() + "\",\"firstpage\":\"" + product.getFirstpage() + "\",\"live\":\"" + product.getLive() + "\",\"color\":\"" + product.getColor() + "\",\"sizes\":\"" + product.getSizes() + "\",\"description\":\"" + product.getDescription() + "\"}}";
        }

        return p;
    }

    @Override
    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    @CachePut(cacheNames = "count_all", key = "'count_all'")
    public long incFetchAllCount(long now) throws IOException {
        return ++now;
    }

    @Override
    @CachePut(cacheNames = "count_all", key = "'count_'+#key")
    public long incFetchCategoryCount(final String key, long now) throws IOException {
        return ++now;
    }

    @Override
    @CachePut(cacheNames = "count_all", key = "'count_'+#key1+'_'+#key2")
    public long incFetchCategoryRangeCount(final String key1, final String key2, long now) throws IOException {
        return ++now;
    }

    @Override
    @CachePut(cacheNames = "count_all", key = "'count_subcategory_'+#key")
    public long incFetchSubcategoryCount(final long key, long now) throws IOException {
        return ++now;
    }

    @Override
    @CachePut(cacheNames = "count_all", key = "'price_all'")
    public Float incFetchAllTopPrice(final Float now, final Float old) throws IOException {
        if (old != null && old > now) {
            return old;
        }

        return now;
    }

    @Override
    //@PreAuthorize("hasRole('FULL_AUTH_ADMIN')")
    @CachePut(cacheNames = "count_all", key = "'count_all'")
    public long decFetchAllCount(long now) throws IOException {
        if (now - 1 > 0) {
            return --now;
        }
        return 0;
    }

    @Override
    @CachePut(cacheNames = "count_all", key = "'count_'+#key")
    public long decFetchCategoryCount(final String key, long now) throws IOException {
        if (now - 1 > 0) {
            return --now;
        }
        return 0;
    }

    @Override
    @CachePut(cacheNames = "count_all", key = "'count_'+#key1+'_'+#key2")
    public long decFetchCategoryRangeCount(final String key1, final String key2, long now) throws IOException {
        if (now - 1 > 0) {
            return --now;
        }
        return 0;
    }

    @Override
    @CachePut(cacheNames = "count_all", key = "'count_subcategory_'+#key")
    public long decFetchSubcategoryCount(final long key, long now) throws IOException {
        if (now - 1 > 0) {
            return --now;
        }
        return 0;
    }

    @Override
    @CachePut(cacheNames = "count_all", key = "'price_all'")
    public Float decFetchAllTopPrice(final Float now, final Float old) throws IOException {
        if (old != null && old > now) {
            return old;
        }

        return productRepository.fetchAllPrice();
    }

    @Override
    @CacheEvict(cacheNames = "categories", allEntries = true)
    public void evictAllCategories() {
        // evict all categories
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void updateCategoryTable(final JsonNode node) {
        long id = Long.valueOf(node.get("categoryid").asText());

        Optional<String> ocolors = categoryRepository.fetchCategoryColors(id);
        String colors = ocolors.orElse("");

        if (!colors.contains(node.get("color").asText())) {
            if (colors.isEmpty()) {
                categoryRepository.updateCategoryColors(node.get("color").asText(), id);
            } else {
                categoryRepository.updateCategoryColors(colors + "," + node.get("color").asText(), id);
            }
        }

        categoryRepository.updateCategoryPrice(Float.valueOf(node.get("price").asText()), id);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void updateProductTable(final JsonNode node) {

        Product newp = new Product();
        newp.setCategory_id(Long.valueOf(node.get("categoryid").asText()));
        newp.setColor(node.get("color").asText());
        newp.setDiscount(Integer.valueOf(node.get("discount").asText()));
        newp.setFirstpage("true".equals(node.get("firstpage").asText()) ? Boolean.TRUE : Boolean.FALSE);
        newp.setImage("/" + node.get("mcat").asText() + "/" + node.get("scat").asText() + "/");
        newp.setLive("true".equals(node.get("live").asText()) ? Boolean.TRUE : Boolean.FALSE);
        newp.setName(node.get("name").asText());
        newp.setPrice(Float.valueOf(node.get("price").asText()));
        newp.setSizes(node.get("sizes").asText());
        newp.setSku(node.get("sku").asText());

        productRepository.save(newp);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void updateDetailTable(final JsonNode node) {

        Detail newd = new Detail();

        newd.setDescription(node.get("description").asText());
        newd.setSku(node.get("sku").asText());

        detailRepository.save(newd);
    }
}
