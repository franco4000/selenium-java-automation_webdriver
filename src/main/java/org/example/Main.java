package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Main {

    // =====================
    // FLUJO CARRITO (MÉTODO)
    // =====================
    public static void flujoCarrito(ChromeDriver driver, WebDriverWait wait) {

        driver.findElement(By.linkText("Books")).click();

        driver.findElement(By.cssSelector("input[value='Add to cart']")).click();

        driver.findElement(By.linkText("Shopping cart")).click();

        wait.until(ExpectedConditions.presenceOfElementLocated(
                By.cssSelector(".cart-item-row .product a")
        ));

        String producto = driver.findElement(By.cssSelector(".cart-item-row .product a")).getText();
        System.out.println("Producto en carrito: " + producto);

        String esperado = "Computing and Internet";

        if (producto.equals(esperado)) {
            System.out.println("TEST CARRITO ✔ producto correcto");
        } else {
            System.out.println("TEST CARRITO ❌ producto incorrecto");
        }

        // limpiar carrito


        driver.findElement(By.cssSelector(".remove-from-cart input")).click();
        driver.findElement(By.name("updatecart")).click();

        wait.until(ExpectedConditions.textToBePresentInElementLocated(
                By.tagName("body"),
                "Your Shopping Cart is empty"
        ));

        if (driver.getPageSource().contains("Your Shopping Cart is empty")) {
            System.out.println("TEST CARRITO ✔ carrito vacío");
        } else {
            System.out.println("TEST CARRITO ❌ todavía hay productos");
        }
    }

    // =====================
    // MAIN
    // =====================
    public static void main(String[] args) throws Exception {

        ChromeDriver driver = new ChromeDriver();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        driver.manage().window().maximize();
        driver.get("https://demowebshop.tricentis.com/");

        // =====================
        // REGISTRO
        // =====================
        driver.findElement(By.linkText("Register")).click();

        driver.findElement(By.id("FirstName")).sendKeys("Franco");
        driver.findElement(By.id("LastName")).sendKeys("Dimitri");
        driver.findElement(By.id("gender-male")).click();

        String email = "franco" + System.currentTimeMillis() + "@test.com";

        driver.findElement(By.id("Email")).sendKeys(email);
        driver.findElement(By.id("Password")).sendKeys("123456");
        driver.findElement(By.id("ConfirmPassword")).sendKeys("123456");

        driver.findElement(By.id("register-button")).click();

        System.out.println("TEST PASO");

        driver.findElement(By.cssSelector(".button-1.register-continue-button")).click();

        System.out.println(driver.getCurrentUrl());
        System.out.println(driver.getTitle());

        // =====================
        // BÚSQUEDA
        // =====================
        driver.findElement(By.id("small-searchterms")).sendKeys("computer");
        driver.findElement(By.cssSelector("input[type='submit']")).click();

        String resultadoBusqueda = driver.getTitle();
        System.out.println("Titulo de búsqueda: " + resultadoBusqueda);

        if (resultadoBusqueda.contains("Demo Web Shop")) {
            System.out.println("TEST PASO ✔ búsqueda correcta");
        } else {
            System.out.println("TEST FALLÓ ❌ búsqueda incorrecta");
        }

        // =====================
        // CARRITO
        // =====================
        flujoCarrito(driver, wait);

        driver.quit();
    }
}