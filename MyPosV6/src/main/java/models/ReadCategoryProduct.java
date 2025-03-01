package models;
//PosV3
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import models.Product;

public class ReadCategoryProduct {
    
     // 產品寫在此處
    public static String[][] product_array = {
            {"p-n-101", "麵食", "起司牛奶炒泡麵", "75", "PotNoodle.jpg", "產品描述"},
            {"p-n-102", "麵食", "排骨酥麵", "90", "Ribs.jpg", "產品描述"},
            {"p-n-103", "麵食", "肉燥乾麵", "60", "DriedMeat.jpg", "產品描述"},
            {"p-n-104", "麵食", "香辣炒麵", "70", "HotFried.jpg", "產品描述"},
            {"p-n-105", "麵食", "手工豚骨拉麵", "100", "handNoodle.jpg", "產品描述"},
            {"p-d-201", "飲品", "柳橙汁", "30", "Orange.jpg", "產品描述"},
            {"p-d-202", "飲品", "白玉珍珠奶茶", "50", "WmilkTea.jpg", "產品描述"},
            {"p-d-203", "飲品", "宇治抹茶鮮奶茶", "60", "Matcha.jpg", "產品描述"},
            {"p-d-204", "飲品", "手沖咖啡", "65", "coffee.jpg", "產品描述"},
            {"p-d-205", "飲品", "大杯冰美式", "50", "iceCoffee.jpg", "產品描述"},
            {"p-f-301", "炸物", "起司牛肉可樂餅", "45", "croquette.jpg", "產品描述"},
            {"p-f-302", "炸物", "酥脆天婦羅", "40", "Shrimp.jpg", "產品描述"},
            {"p-f-303", "炸物", "酥炸飯糰", "35", "riceBall.png", "產品描述"}
        };

    //準備好產品清單  
    public static TreeMap<String, Product> readProduct() {
        //read_product_from_file(); //從檔案或資料庫讀入產品菜單資訊
        //放所有產品  產品編號  產品物件
        TreeMap<String, Product> product_dict = new TreeMap();

        //一筆放入字典變數product_dict中
        for (String[] item : product_array) {
            Product product = new Product(
                    item[0],
                    item[1],
                    item[2],
                    Integer.parseInt(item[3]), //價格轉為int
                    item[4],
                    item[5]);
            //將這一筆放入字典變數product_dict中 
            product_dict.put(product.getProduct_id(), product);
        }
        return product_dict;
    }
    //準備好產品清單  
    public static List<Product> readProductList() {
        //read_product_from_file(); //從檔案或資料庫讀入產品菜單資訊
        //放所有產品  產品編號  產品物件
        List<Product> product_dict = new ArrayList<>();

        //一筆放入字典變數product_dict中
        for (String[] item : product_array) {
            Product product = new Product(
                    item[0],
                    item[1],
                    item[2],
                    Integer.parseInt(item[3]), //價格轉為int
                    item[4],
                    item[5]);
            //將這一筆放入字典變數product_dict中 
            product_dict.add(product);
        }
        return product_dict;
    }
}
