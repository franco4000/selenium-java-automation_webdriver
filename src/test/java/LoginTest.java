import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public class LoginTest {

    @Test
    public void primerTest() {

        WebDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        try {
            driver.manage().window().maximize();
            driver.get("https://demowebshop.tricentis.com/");

            // =========================
            // REGISTER
            // =========================
            driver.findElement(By.className("ico-register")).click();

            String emailAleatorio = "franco.d" + System.currentTimeMillis() + "@gmail.com";

            driver.findElement(By.id("gender-male")).click();
            driver.findElement(By.id("FirstName")).sendKeys("Franco");
            driver.findElement(By.id("LastName")).sendKeys("Dimitri");
            driver.findElement(By.id("Email")).sendKeys(emailAleatorio);
            driver.findElement(By.id("Password")).sendKeys("Test1234");
            driver.findElement(By.id("ConfirmPassword")).sendKeys("Test1234");
            driver.findElement(By.id("register-button")).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.className("ico-logout")));

            driver.findElement(By.className("ico-logout")).click();

            // =========================
            // LOGIN
            // =========================
            driver.findElement(By.className("ico-login")).click();
            driver.findElement(By.id("Email")).sendKeys(emailAleatorio);
            driver.findElement(By.id("Password")).sendKeys("Test1234");
            driver.findElement(By.cssSelector("input[value='Log in']")).click();

            // =========================
            // PRODUCTO 1
            // =========================
            driver.findElement(By.id("small-searchterms")).sendKeys("laptop");
            driver.findElement(By.cssSelector("input[value='Search']")).click();

            driver.findElement(By.cssSelector(".product-title a")).click();
            driver.findElement(By.cssSelector("input[value='Add to cart']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-qty")));

            driver.findElement(By.className("header-logo")).click();

            // =========================
            // PRODUCTO 2
            // =========================
            driver.findElement(By.linkText("Books")).click();
            driver.findElement(By.cssSelector(".product-title a")).click();
            driver.findElement(By.cssSelector("input[value='Add to cart']")).click();

            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("cart-qty")));

            // =========================
            // CARRITO
            // =========================
            driver.findElement(By.className("cart-label")).click();

            // eliminar 1 producto
            driver.findElements(By.name("removefromcart")).get(0).click();

            wait.until(ExpectedConditions.elementToBeClickable(By.name("updatecart")));

            driver.findElement(By.name("updatecart")).click();

            // esperar a que el carrito se actualice
            wait.until(ExpectedConditions.numberOfElementsToBeLessThan(
                    By.cssSelector(".cart-item-row"), 2
            ));

            // =========================
            // VALIDACIÓN
            // =========================
            List<WebElement> productos = driver.findElements(By.cssSelector(".product-name"));

            for (WebElement producto : productos) {
                System.out.println("🛒 Producto en carrito: " + producto.getText());
            }

            boolean tieneLaptop = productos.stream()
                    .anyMatch(p -> p.getText().toLowerCase().contains("laptop"));

            boolean tieneBook = productos.stream()
                    .anyMatch(p -> p.getText().toLowerCase().contains("computing"));

            boolean tieneComputing = productos.stream()
                    .anyMatch(p -> p.getText().toLowerCase().contains("computing"));

            Assertions.assertTrue(tieneComputing);

            int cantidad = driver.findElements(By.cssSelector(".cart-item-row")).size();

            System.out.println("🛒 Productos luego de eliminar: " + cantidad);

            Assertions.assertEquals(1, cantidad);

        } finally {
            driver.quit();
        }
    }
}