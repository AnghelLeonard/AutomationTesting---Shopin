package org.shopin.admin;

import com.fasterxml.jackson.databind.JsonNode;
import java.io.IOException;
import javax.servlet.http.HttpSession;
import org.springframework.stereotype.Service;

@Service
public interface IAdminProduct extends IFetchProduct {

    public void addProduct(final JsonNode node) throws IOException;

    public void modifyProduct(JsonNode node) throws IOException;

    public String loadProduct(final String sku);

    public void updateUser(final String email, final boolean status);
}
