/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author jacobsimonsen
 */
public class CategoryDTO {
List<ProductDTO> products = new ArrayList();

    public CategoryDTO(List<ProductDTO> productsDTO) {
       this.products = productsDTO;
    }

    public List<ProductDTO> getAll() {
        return products;
    }
    
}