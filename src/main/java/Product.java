/*
 * Joshua Lynn - Zappos Java Take Home Test for Software Engineer Position
 * 3/27/2013
 * 
 * This is a Product class used to create Product objects from data that is 
 * obtained from a JSON string in the default constructor. It also allows 
 * for the images associated with this product to be downloaded from the 
 * API call. 
 * 
 * 
 */

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import javax.imageio.*;


class Product {
    private String productId;
    private String brandId;
    private String brandName;
    private String productName;
    private String defaultProductUrl;
    private String defaultImageUrl;

    Product() {
        // initialize all fields to null
        productId = brandId = brandName = productName = defaultProductUrl = defaultImageUrl = null;
    }

    Product(String in) {
        JSONObject jsonObjectIn = new JSONObject(in); // convert the input string to a JSON object
        JSONArray jsonArray = jsonObjectIn.getJSONArray("product"); // get the "product" array from the JSON object

        // this is the cheating way to do this for this particular problem....it strips the array parts off of the JSON
        // object and leaves a JSON object with all the data goodies that we want to work with
        JSONObject jsonObject = new JSONObject(jsonArray.toString().replace("[", "").replace("]", ""));

        // parse out the JSON fields.
        this.productId = jsonObject.isNull("productId") ? "" : jsonObject.getString("productId");
        this.brandId = jsonObject.isNull("brandId") ? "" : jsonObject.getString("brandId");
        this.brandName = jsonObject.isNull("brandName") ? "" : jsonObject.getString("brandName");
        this.productName = jsonObject.isNull("productName") ? "" : jsonObject.getString("productName");
        this.defaultProductUrl = jsonObject.isNull("defaultProductUrl") ? "" : jsonObject.getString("defaultProductUrl");
        this.defaultImageUrl = jsonObject.isNull("defaultImageUrl") ? "" : jsonObject.getString("defaultImageUrl");

        // print out the product id to the console
        System.out.println(this.productId);
    }

    // Accessor methods.
    public void setProductId(String pid) {
        this.productId = pid;
    }

    public void setBrandId(String bid) {
        this.brandId = bid;
    }

    public void setBrandName(String bn) {
        this.brandName = bn;
    }

    public void setProductName(String pn) {
        this.productName = pn;
    }

    public void setDefaultProductUrl(String dfurl) {
        this.defaultProductUrl = dfurl;
    }

    public void setDefaultImageUrl(String dfurl) {
        this.defaultImageUrl = dfurl;
    }

    public String getProductId() {
        return this.productId;
    }

    public String getBrandId() {
        return this.brandId;
    }

    public String getBrandName() {
        return this.brandName;
    }

    public String getProductName() {
        return this.productName;
    }

    public String getDefaultProductUrl() {
        return this.defaultProductUrl;
    }

    public String getDefaultImageUrl() {
        return this.defaultImageUrl;
    }

    // This method downloads the image associated with this product and puts in a 
    // directory (/images) within the current working directory (it creates the directory if 
    // if doesn't already exist)
    // 
    public void downloadImage() {
        // create new directory in current working directory
        File imageDir = new File(System.getProperty("user.dir") + "/images");

        // if the directory doesn't exist then create it! 
        if (!imageDir.exists()) {
            imageDir.mkdirs();
        }

        BufferedImage pic = null;
        try {
            URL url = new URL(this.defaultImageUrl.replace("\\", ""));  // create url for image 

            pic = ImageIO.read(url); // read image from url 

            // write image to file and name it by the product ID
            ImageIO.write(pic, "jpg", new File(System.getProperty("user.dir") + "/images/" + this.productId + ".jpg"));
        } catch (MalformedURLException mue) {
            // this exception is for a bad url
            System.err.println("Error opening url.");
        } catch (IOException e) {
            // this exception is for a problem with reading or writing the image. 
            System.err.println("Error reading/writing image. ");
        }
    }

    // This method prints the fields in the object.
    public void printProduct() {
        System.out.println("Product ID: " + this.productId + "\n" +
                "brandId: " + this.brandId + "\n" +
                "brandName: " + this.brandName + "\n" +
                "productName: " + this.productName + "\n" +
                "defaultProductUrl: " + this.defaultProductUrl + "\n" +
                "defaultImageUrl: " + this.defaultImageUrl + "\n");
    }

}
