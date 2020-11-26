/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

/**
 *
 * @author jacobsimonsen
 */
public class ProductDTO {
    private String sku;
    private String name;
    private String type;
    private String regularPrice;
    private String salePrice;
    private String url;
    private String mobileUrl;
    private String image;
    private String shortDesription;
    private String onSale;

    public ProductDTO(String sku, String name, String type, String regularPrice, String salePrice, String url, String mobileUrl, String image, String shortDesription, String onSale) {
        this.sku = sku;
        this.name = name;
        this.type = type;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
        this.url = url;
        this.mobileUrl = mobileUrl;
        this.image = image;
        this.shortDesription = shortDesription;
        this.onSale = onSale;
    }

}    