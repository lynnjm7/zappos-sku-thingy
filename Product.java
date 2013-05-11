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

import java.io.*;
import java.net.*;
import java.awt.image.BufferedImage;
import java.util.StringTokenizer;
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
        // This constructor takes a string from the API call and parses it to populate the fields. 
        in = in.replace("{", "").replace(":", "").replace("[", "").replace("}", "").replace("]", "").replace(",", "");
        in = in.replace("http", "http:");
                
        StringTokenizer tokens = new StringTokenizer(in, "\"");
        
        // This loop proceeds through the tokens and populates the field of the object.
        tokens.nextToken();
        while(tokens.hasMoreTokens()) {
            //System.out.println(tokens.nextToken());
            String currentToken = tokens.nextToken();
            //System.out.println(currentToken);
            if (currentToken.equals("productId")) {
                System.out.println(this.productId = tokens.nextToken());
            } else if (currentToken.equals("brandId")) {
                this.brandId = tokens.nextToken();
            } else if (currentToken.equals("brandName")) {
                this.brandName = tokens.nextToken();
            } else if (currentToken.equals("productName")) {
                this.productName = tokens.nextToken();
            } else if (currentToken.equals("defaultProductUrl")) {
                this.defaultProductUrl = tokens.nextToken();
            } else if (currentToken.equals("defaultImageUrl")) {
                this.defaultImageUrl = tokens.nextToken();
            } else {
                //System.err.println("Error: Invalid tokens in string.");
            }
        }
        return;
        
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
        } catch(MalformedURLException mue) {
            // this exception is for a bad url
            System.err.println("Error opening url.");
        } catch (IOException e) {
            // this exception is for a problem with reading or writing the image. 
            System.err.println("Error reading/writing image. ");
        }
        return;
   }
    
    // This method prints the fields in the object.
    public void printProduct() {
        System.out.println("Product ID: " + this.productId + "\n" + 
                           "brandId: " + this.brandId + "\n" + 
                           "brandName: " + this.brandName + "\n" + 
                           "productName: " + this.productName + "\n" + 
                           "defaultProductUrl: " + this.defaultProductUrl + "\n" +
                           "defaultImageUrl: " + this.defaultImageUrl + "\n");
        return;
    }
    
}
