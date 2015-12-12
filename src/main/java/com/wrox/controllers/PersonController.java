package com.wrox.controllers;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.wrox.controllers.forms.WidgetDataSource.Node;
import com.wrox.entities.Person;
import com.wrox.entities.Product;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;
import java.util.List;

/**
 * @author dengb
 */
@Controller
@RequestMapping("/hr")
public class PersonController {

    private static Gson gson = new GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.UPPER_CAMEL_CASE)
            .serializeNulls()
            .create();
    public static List<Product> products = gson.fromJson("[{\"ProductID\":1,\"ProductName\":\"Chai\",\"UnitPrice\":18,\"UnitsInStock\":39,\"Discontinued\":false},{\"ProductID\":2,\"ProductName\":\"Chang\",\"UnitPrice\":19,\"UnitsInStock\":17,\"Discontinued\":false},{\"ProductID\":3,\"ProductName\":\"Aniseed Syrup\",\"UnitPrice\":10,\"UnitsInStock\":13,\"Discontinued\":false},{\"ProductID\":4,\"ProductName\":\"Chef Anton\\u0027s Cajun Seasoning\",\"UnitPrice\":22,\"UnitsInStock\":53,\"Discontinued\":false},{\"ProductID\":5,\"ProductName\":\"Chef Anton\\u0027s Gumbo Mix\",\"UnitPrice\":21.35,\"UnitsInStock\":0,\"Discontinued\":true},{\"ProductID\":6,\"ProductName\":\"Grandma\\u0027s Boysenberry Spread\",\"UnitPrice\":25,\"UnitsInStock\":120,\"Discontinued\":false},{\"ProductID\":7,\"ProductName\":\"Uncle Bob\\u0027s Organic Dried Pears\",\"UnitPrice\":30,\"UnitsInStock\":15,\"Discontinued\":false},{\"ProductID\":8,\"ProductName\":\"Northwoods Cranberry Sauce\",\"UnitPrice\":40,\"UnitsInStock\":6,\"Discontinued\":false},{\"ProductID\":9,\"ProductName\":\"Mishi Kobe Niku\",\"UnitPrice\":97,\"UnitsInStock\":29,\"Discontinued\":true},{\"ProductID\":10,\"ProductName\":\"Ikura\",\"UnitPrice\":31,\"UnitsInStock\":31,\"Discontinued\":false},{\"ProductID\":11,\"ProductName\":\"Queso Cabrales\",\"UnitPrice\":21,\"UnitsInStock\":22,\"Discontinued\":false},{\"ProductID\":12,\"ProductName\":\"Queso Manchego La Pastora\",\"UnitPrice\":38,\"UnitsInStock\":86,\"Discontinued\":false},{\"ProductID\":13,\"ProductName\":\"Konbu\",\"UnitPrice\":6,\"UnitsInStock\":24,\"Discontinued\":false},{\"ProductID\":14,\"ProductName\":\"Tofu\",\"UnitPrice\":23.25,\"UnitsInStock\":35,\"Discontinued\":false},{\"ProductID\":15,\"ProductName\":\"Genen Shouyu\",\"UnitPrice\":15.5,\"UnitsInStock\":39,\"Discontinued\":false},{\"ProductID\":16,\"ProductName\":\"Pavlova\",\"UnitPrice\":17.45,\"UnitsInStock\":29,\"Discontinued\":false},{\"ProductID\":17,\"ProductName\":\"Alice Mutton\",\"UnitPrice\":39,\"UnitsInStock\":0,\"Discontinued\":true},{\"ProductID\":18,\"ProductName\":\"Carnarvon Tigers\",\"UnitPrice\":62.5,\"UnitsInStock\":42,\"Discontinued\":false},{\"ProductID\":19,\"ProductName\":\"Teatime Chocolate Biscuits\",\"UnitPrice\":9.2,\"UnitsInStock\":25,\"Discontinued\":false},{\"ProductID\":20,\"ProductName\":\"Sir Rodney\\u0027s Marmalade\",\"UnitPrice\":81,\"UnitsInStock\":40,\"Discontinued\":false},{\"ProductID\":21,\"ProductName\":\"Sir Rodney\\u0027s Scones\",\"UnitPrice\":10,\"UnitsInStock\":3,\"Discontinued\":false},{\"ProductID\":22,\"ProductName\":\"Gustaf\\u0027s Knäckebröd\",\"UnitPrice\":21,\"UnitsInStock\":104,\"Discontinued\":false},{\"ProductID\":23,\"ProductName\":\"Tunnbröd\",\"UnitPrice\":9,\"UnitsInStock\":61,\"Discontinued\":false},{\"ProductID\":24,\"ProductName\":\"Guaraná Fantástica\",\"UnitPrice\":4.5,\"UnitsInStock\":20,\"Discontinued\":true},{\"ProductID\":25,\"ProductName\":\"NuNuCa Nuß-Nougat-Creme\",\"UnitPrice\":14,\"UnitsInStock\":76,\"Discontinued\":false},{\"ProductID\":26,\"ProductName\":\"Gumbär Gummibärchen\",\"UnitPrice\":31.23,\"UnitsInStock\":15,\"Discontinued\":false},{\"ProductID\":27,\"ProductName\":\"Schoggi Schokolade\",\"UnitPrice\":43.9,\"UnitsInStock\":49,\"Discontinued\":false},{\"ProductID\":28,\"ProductName\":\"Rössle Sauerkraut\",\"UnitPrice\":45.6,\"UnitsInStock\":26,\"Discontinued\":true},{\"ProductID\":29,\"ProductName\":\"Thüringer Rostbratwurst\",\"UnitPrice\":123.79,\"UnitsInStock\":0,\"Discontinued\":true},{\"ProductID\":30,\"ProductName\":\"Nord-Ost Matjeshering\",\"UnitPrice\":25.89,\"UnitsInStock\":10,\"Discontinued\":false},{\"ProductID\":31,\"ProductName\":\"Gorgonzola Telino\",\"UnitPrice\":12.5,\"UnitsInStock\":0,\"Discontinued\":false},{\"ProductID\":32,\"ProductName\":\"Mascarpone Fabioli\",\"UnitPrice\":32,\"UnitsInStock\":9,\"Discontinued\":false},{\"ProductID\":33,\"ProductName\":\"Geitost\",\"UnitPrice\":2.5,\"UnitsInStock\":112,\"Discontinued\":false},{\"ProductID\":34,\"ProductName\":\"Sasquatch Ale\",\"UnitPrice\":14,\"UnitsInStock\":111,\"Discontinued\":false},{\"ProductID\":35,\"ProductName\":\"Steeleye Stout\",\"UnitPrice\":18,\"UnitsInStock\":20,\"Discontinued\":false},{\"ProductID\":36,\"ProductName\":\"Inlagd Sill\",\"UnitPrice\":19,\"UnitsInStock\":112,\"Discontinued\":false},{\"ProductID\":37,\"ProductName\":\"Gravad lax\",\"UnitPrice\":26,\"UnitsInStock\":11,\"Discontinued\":false},{\"ProductID\":38,\"ProductName\":\"Côte de Blaye\",\"UnitPrice\":263.5,\"UnitsInStock\":17,\"Discontinued\":false},{\"ProductID\":39,\"ProductName\":\"Chartreuse verte\",\"UnitPrice\":18,\"UnitsInStock\":69,\"Discontinued\":false},{\"ProductID\":40,\"ProductName\":\"Boston Crab Meat\",\"UnitPrice\":18.4,\"UnitsInStock\":123,\"Discontinued\":false},{\"ProductID\":41,\"ProductName\":\"Jack\\u0027s New England Clam Chowder\",\"UnitPrice\":9.65,\"UnitsInStock\":85,\"Discontinued\":false},{\"ProductID\":42,\"ProductName\":\"Singaporean Hokkien Fried Mee\",\"UnitPrice\":14,\"UnitsInStock\":26,\"Discontinued\":true},{\"ProductID\":43,\"ProductName\":\"Ipoh Coffee\",\"UnitPrice\":46,\"UnitsInStock\":17,\"Discontinued\":false},{\"ProductID\":44,\"ProductName\":\"Gula Malacca\",\"UnitPrice\":19.45,\"UnitsInStock\":27,\"Discontinued\":false},{\"ProductID\":45,\"ProductName\":\"Rogede sild\",\"UnitPrice\":9.5,\"UnitsInStock\":5,\"Discontinued\":false},{\"ProductID\":46,\"ProductName\":\"Spegesild\",\"UnitPrice\":12,\"UnitsInStock\":95,\"Discontinued\":false},{\"ProductID\":47,\"ProductName\":\"Zaanse koeken\",\"UnitPrice\":9.5,\"UnitsInStock\":36,\"Discontinued\":false},{\"ProductID\":48,\"ProductName\":\"Chocolade\",\"UnitPrice\":12.75,\"UnitsInStock\":15,\"Discontinued\":false},{\"ProductID\":49,\"ProductName\":\"Maxilaku\",\"UnitPrice\":20,\"UnitsInStock\":10,\"Discontinued\":false},{\"ProductID\":50,\"ProductName\":\"Valkoinen suklaa\",\"UnitPrice\":16.25,\"UnitsInStock\":65,\"Discontinued\":false},{\"ProductID\":51,\"ProductName\":\"Manjimup Dried Apples\",\"UnitPrice\":53,\"UnitsInStock\":20,\"Discontinued\":false},{\"ProductID\":52,\"ProductName\":\"Filo Mix\",\"UnitPrice\":7,\"UnitsInStock\":38,\"Discontinued\":false},{\"ProductID\":53,\"ProductName\":\"Perth Pasties\",\"UnitPrice\":32.8,\"UnitsInStock\":0,\"Discontinued\":true},{\"ProductID\":54,\"ProductName\":\"Tourtière\",\"UnitPrice\":7.45,\"UnitsInStock\":21,\"Discontinued\":false},{\"ProductID\":55,\"ProductName\":\"Pâté chinois\",\"UnitPrice\":24,\"UnitsInStock\":115,\"Discontinued\":false},{\"ProductID\":56,\"ProductName\":\"Gnocchi di nonna Alice\",\"UnitPrice\":38,\"UnitsInStock\":21,\"Discontinued\":false},{\"ProductID\":57,\"ProductName\":\"Ravioli Angelo\",\"UnitPrice\":19.5,\"UnitsInStock\":36,\"Discontinued\":false},{\"ProductID\":58,\"ProductName\":\"Escargots de Bourgogne\",\"UnitPrice\":13.25,\"UnitsInStock\":62,\"Discontinued\":false},{\"ProductID\":59,\"ProductName\":\"Raclette Courdavault\",\"UnitPrice\":55,\"UnitsInStock\":79,\"Discontinued\":false},{\"ProductID\":60,\"ProductName\":\"Camembert Pierrot\",\"UnitPrice\":34,\"UnitsInStock\":19,\"Discontinued\":false},{\"ProductID\":61,\"ProductName\":\"Sirop d\\u0027érable\",\"UnitPrice\":28.5,\"UnitsInStock\":113,\"Discontinued\":false},{\"ProductID\":62,\"ProductName\":\"Tarte au sucre\",\"UnitPrice\":49.3,\"UnitsInStock\":17,\"Discontinued\":false},{\"ProductID\":63,\"ProductName\":\"Vegie-spread\",\"UnitPrice\":43.9,\"UnitsInStock\":24,\"Discontinued\":false},{\"ProductID\":64,\"ProductName\":\"Wimmers gute Semmelknödel\",\"UnitPrice\":33.25,\"UnitsInStock\":22,\"Discontinued\":false},{\"ProductID\":65,\"ProductName\":\"Louisiana Fiery Hot Pepper Sauce\",\"UnitPrice\":21.05,\"UnitsInStock\":76,\"Discontinued\":false},{\"ProductID\":66,\"ProductName\":\"Louisiana Hot Spiced Okra\",\"UnitPrice\":17,\"UnitsInStock\":4,\"Discontinued\":false},{\"ProductID\":67,\"ProductName\":\"Laughing Lumberjack Lager\",\"UnitPrice\":14,\"UnitsInStock\":52,\"Discontinued\":false},{\"ProductID\":68,\"ProductName\":\"Scottish Longbreads\",\"UnitPrice\":12.5,\"UnitsInStock\":6,\"Discontinued\":false},{\"ProductID\":69,\"ProductName\":\"Gudbrandsdalsost\",\"UnitPrice\":36,\"UnitsInStock\":26,\"Discontinued\":false},{\"ProductID\":70,\"ProductName\":\"Outback Lager\",\"UnitPrice\":15,\"UnitsInStock\":15,\"Discontinued\":false},{\"ProductID\":71,\"ProductName\":\"Flotemysost\",\"UnitPrice\":21.5,\"UnitsInStock\":26,\"Discontinued\":false},{\"ProductID\":72,\"ProductName\":\"Mozzarella di Giovanni\",\"UnitPrice\":34.8,\"UnitsInStock\":14,\"Discontinued\":false},{\"ProductID\":73,\"ProductName\":\"Röd Kaviar\",\"UnitPrice\":15,\"UnitsInStock\":101,\"Discontinued\":false},{\"ProductID\":74,\"ProductName\":\"Longlife Tofu\",\"UnitPrice\":10,\"UnitsInStock\":4,\"Discontinued\":false},{\"ProductID\":75,\"ProductName\":\"Rhönbräu Klosterbier\",\"UnitPrice\":7.75,\"UnitsInStock\":125,\"Discontinued\":false},{\"ProductID\":76,\"ProductName\":\"Lakkalikööri\",\"UnitPrice\":18,\"UnitsInStock\":57,\"Discontinued\":false},{\"ProductID\":77,\"ProductName\":\"Original Frankfurter grüne Soße\",\"UnitPrice\":13,\"UnitsInStock\":32,\"Discontinued\":false}]", new TypeToken<List<Product>>(){}.getType());

    @RequestMapping(value = {"/", "list"}, method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("id", "123");
        return "person/employee";
    }

    @RequestMapping("/init/{widget}")
    @ResponseBody
    public Object init(HttpServletResponse response, @PathVariable("widget") String widget) throws IOException {
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache_Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("application/json; charset=UTF-8");

        switch (widget) {
            case "gender":
                Node male = new Node("男", "M");
                Node female = new Node("女", "W");
                return new Node[] {male, female};
            case "grid":
                return products;
        }
        return null;
    }

    @RequestMapping(value = "/load")
    @ResponseBody
    public Person load(HttpServletResponse response) throws IOException {
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache_Control", "no-cache");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("application/json; charset=UTF-8");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 1991);
        calendar.set(Calendar.MONTH, Calendar.SEPTEMBER);
        calendar.set(Calendar.DATE, 16);
        Person person = new Person();
        person.setName("圆圆");
        person.setAge(24);
        person.setGender("W");
        person.setBirthday(calendar.getTime());

        return person;
    }

    @RequestMapping(value = "update")
    public String update(HttpServletResponse response, Person person) throws IOException {
        response.setDateHeader("Expires", -1);
        response.setHeader("Cache_Control", "no-cache");
        response.setHeader("Pragma", "no-cache");

        System.out.println("Person => " + person);

        response.getWriter().write("{status: \"更新成功\"}");
        return "person/employee";
    }
}
