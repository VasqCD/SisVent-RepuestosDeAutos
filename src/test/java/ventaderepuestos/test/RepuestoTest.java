package ventaderepuestos.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;
import static java.time.Duration.ofSeconds;

public class RepuestoTest {

    @Test
    public void testGuardarRepuesto() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("http://localhost:8080/repuestos");
            new WebDriverWait(driver, ofSeconds(30)).until(titleIs("Repuestos"));

            WebElement campoNombreRepuesto = driver.findElement(By.id("txt_nombreRepuesto"));
            WebElement campoMarca = driver.findElement(By.id("txt_marcaRepuesto"));
            WebElement campoPrecio = driver.findElement(By.id("txt_precioUnitario"));
            WebElement campoStock = driver.findElement(By.id("txt_unidades"));
            WebElement campoProveedor = driver.findElement(By.id("txt_proveedor"));
            WebElement campoEstado = driver.findElement(By.id("txt_estado"));
            WebElement btnGuardar = driver.findElement(By.id("btn_guardar"));

            campoNombreRepuesto.sendKeys("Pastillas de freno");
            campoMarca.sendKeys("Continental");
            campoPrecio.sendKeys("2000");
            campoStock.sendKeys("8");
            campoProveedor.sendKeys("Continental");
            campoEstado.sendKeys("Activo");

            btnGuardar.click();

            new WebDriverWait(driver, ofSeconds(10)).until(titleIs("Repuestos"));
        } finally {
            driver.quit();
        }
    }

    @Test
    void testEliminarRepuesto() {
        WebDriver driver = new ChromeDriver();
        try {
            driver.get("http://localhost:8080/repuestos");
            new WebDriverWait(driver, ofSeconds(30)).until(titleIs("Repuestos"));

            WebElement btnEliminar = driver.findElement(By.id("btn_eliminar"));
            btnEliminar.click();

            // Verificar que se llame al método eliminarRepuesto en el controlador con el id del repuesto seleccionado
 
        } finally {
            driver.quit();
        }
    }

    // TestMostrarMensajeError
    @Test
    void testMostrarMensajeError() {
        // Simular el método mostrarMensajeError en la vista y verificar que se muestre correctamente el mensaje de error
    }
}