import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class HomeWork18 {

    private final static String URL = "http://demowebshop.tricentis.com/";

    @Test
    @DisplayName("Добавление товара в список желаемых")
    public void addToWishList() {
        Specifications.installSpecification(Specifications.requestSpec(URL), Specifications.responseSpecOK200());

        String str = given()
                .contentType("application/x-www-form-urlencoded")
                .cookie("ARRAffinity=a1e87db3fa424e3b31370c78def315779c40ca259e77568bef2bb9544f63134e; __utma=78382081.259764520.1646372164.1646372164.1646372164.1; __utmc=78382081; __utmz=78382081.1646372164.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __RequestVerificationToken=vvW8XgfuJKo5B60hJeJQ1HuQxRWXfz6EGKdMD3i0uQx1IByp4uSTXDdqXg8P3567gmZt94POMCFV2gkC8TaPWT1oSahW-Ach-6wZEI7Cl8M1; nop.CompareProducts=CompareProductIds=2; ASP.NET_SessionId=3bqkdyvndjz5lb34ddlruzqa; Nop.customer=b054f6ee-83dd-4367-9918-f4538b7bb5a2; NopCommerce.RecentlyViewedProducts=RecentlyViewedProductIds=2&RecentlyViewedProductIds=75&RecentlyViewedProductIds=72&RecentlyViewedProductIds=13; __utmb=78382081.79.10.1646372164; __atuvc=22%7C9; __atuvs=6221a5fe25ba3c95015")
                .when()
                .get("wishlist")
                .then()
                .log().all().extract().asString();

        String st = ((st = str.substring(0, str.lastIndexOf(")</span>"))).substring(st.lastIndexOf("(") + 1));
        Integer whishListValue = Integer.valueOf(st);


        given()
                .contentType("application/x-www-form-urlencoded; charset=UTF-8")
                .cookie("ARRAffinity=a1e87db3fa424e3b31370c78def315779c40ca259e77568bef2bb9544f63134e; __utma=78382081.259764520.1646372164.1646372164.1646372164.1; __utmc=78382081; __utmz=78382081.1646372164.1.1.utmcsr=(direct)|utmccn=(direct)|utmcmd=(none); __RequestVerificationToken=vvW8XgfuJKo5B60hJeJQ1HuQxRWXfz6EGKdMD3i0uQx1IByp4uSTXDdqXg8P3567gmZt94POMCFV2gkC8TaPWT1oSahW-Ach-6wZEI7Cl8M1; nop.CompareProducts=CompareProductIds=2; ASP.NET_SessionId=3bqkdyvndjz5lb34ddlruzqa; Nop.customer=b054f6ee-83dd-4367-9918-f4538b7bb5a2; NopCommerce.RecentlyViewedProducts=RecentlyViewedProductIds=2&RecentlyViewedProductIds=75&RecentlyViewedProductIds=72&RecentlyViewedProductIds=13; __utmb=78382081.79.10.1646372164; __atuvc=22%7C9; __atuvs=6221a5fe25ba3c95015")
                .body("giftcard_2.RecipientName=viktor&giftcard_2." +
                        "RecipientEmail=viktornuts%40gmail.com&giftcard_2." +
                        "SenderName=viktor&giftcard_2." +
                        "SenderEmail=viktornuts%40gmail.com&giftcard_2." +
                        "Message=21312&addtocart_2." +
                        "EnteredQuantity=1")
                .when()
                .post("addproducttocart/details/2/2")
                .then()
                .log().all().body("success", is(true))
                .body("message", is("The product has been added to your <a href=\"/wishlist\">wishlist</a>"))
                .body("updatetopwishlistsectionhtml", is( "(" + Integer.toString(whishListValue +1) + ")"));


    }

}
