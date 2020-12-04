/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CategoryDTO;
import dto.OnSaleDTO;
import dto.SearchDTO;
import fetchers.ProductFetcher;
import java.io.IOException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author jacobsimonsen
 */

@Path("products")
public class ProductResource {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    
    
     @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{search}")
    public String getProduct(@PathParam("search") String search) throws IOException {
        ProductFetcher pf = new ProductFetcher();

        SearchDTO sDTO = pf.getProduct(search);

        return GSON.toJson(sDTO);
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("categories/{category}")
    public String getProducts(@PathParam("category") String category) throws IOException {
        ProductFetcher pf = new ProductFetcher();

        CategoryDTO cDTO = pf.getProducts(category);

        return GSON.toJson(cDTO);
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("offers")
    public String getProductsOnSale() throws IOException {
        ProductFetcher pf = new ProductFetcher();

        OnSaleDTO osDTO = pf.getProductsOnSale();

        return GSON.toJson(osDTO);
    }
    
}
