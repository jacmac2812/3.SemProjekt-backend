/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fetchers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.CategoryDTO;
import dto.CombinedDTO;
import dto.ProductDTO;
import java.io.IOException;
import utils.HttpUtils;

/**
 *
 * @author jacobsimonsen
 */
public class ProductFetcher {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public ProductDTO getProduct() throws IOException {
        String product = HttpUtils.fetchData("https://api.bestbuy.com/v1/products/8880044.json?&apiKey=7xOnwr4pXXGdQXNUIIkqD6Jp");

        
        ProductDTO pDTO = GSON.fromJson(product, ProductDTO.class);
        
        return pDTO;
    }
    public CategoryDTO getProducts(String category) throws IOException {
        String products = HttpUtils.fetchData("https://api.bestbuy.com/v1/products(categoryPath.id%20=" + category + ")?format=json&pageSize=100&apiKey=7xOnwr4pXXGdQXNUIIkqD6Jp");

        
        CategoryDTO categoryDTO = GSON.fromJson(products, CategoryDTO.class);
        
        return categoryDTO;
    }
}
