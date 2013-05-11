/*
 * Joshua Lynn
 * 3/27/2013
 * Zappos Java Test Program for Software Engineering Position.
 * 
 * This program is designed to take in a filename from the command line 
 * and then download the images associated with the input data and put them 
 * in a directory under the current working directory entitled "images". 
 * 
 * The sample file included in the Repo is "skus.txt".
 * 
 */


import java.io.*;
import java.net.*;

public class ZapposSkuProgram {

    // This is the API key that is used for making the API call to Zappos API. 
    final static String apiKey = "&key=52ddafbe3ee659bad97fcce7c53592916a6bfd73";

    public static void main(String[] args) {
        
        // Check for a valid number of command line arguments. If invalid display error message and halt execution.
        if (args.length != 1) { 
            System.err.println("Error: incorrect number of arguments passed to program.\n" +
                               "  Proper usage: 'java ZapposSKUProgram <input file>'");
            System.exit(1);
        } else {
            try {
                // Create a buffer to read the input file from. If the file cannot be opened 
                // an exception is thrown. 
                BufferedReader inFile = new BufferedReader(new FileReader(args[0]));
       
                // read in lines from the file until the end of the file.
                String line; 
                while ((line = inFile.readLine()) != null) {
                        Product apiReturn = APICaller(line); // call the API and process information
                        if (apiReturn != null) {
                            //apiReturn.printProduct();
                            apiReturn.downloadImage();
                        }
                }
            } catch (IOException e) {
                // Error thrown if file does not exist.
                System.out.println("Error reading file information:" + e ); 
            }    
        }     
    }
    
    static Product APICaller(String sku) {
        
        try {
            // create new URL object for handling Zappos API Call
            URL apiCall = new URL("http://api.zappos.com/Product?id=" + sku.replace(" ", "") + apiKey);
            
            // Create an HTTP Connection to handle reading data from the API call
            HttpURLConnection apiConnection = (HttpURLConnection)apiCall.openConnection();
            
            // Create a buffer to read in data from the API Call
            BufferedReader in = new BufferedReader(new InputStreamReader(apiConnection.getInputStream()));
            
            // Read in data generated from the API call
            String inputString = in.readLine();                
            Product pd = new Product(inputString);
                        
            // close the buffered reader connection.
            in.close();
            
            // Disconnect from the HTTP connection
            apiConnection.disconnect();
            return pd; 
            
        } catch (MalformedURLException mue) {
            // Error thrown if the URL is invalid. 
            System.err.println("  Invalid URL query.");  
        } catch (IOException e) {
            // Error thrown if there is a 400 message returned by the URL reader call
            System.err.println("Invalid SKU.");
        }
        
         return null;   
    }   
}

