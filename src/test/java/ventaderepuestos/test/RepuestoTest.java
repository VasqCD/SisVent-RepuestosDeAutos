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
    public void testGuardarRepuesto() throws InterruptedException {

        // Inicializa el WebDriver para Chrome
        WebDriver driver = new ChromeDriver();
        //WebDriver driver = new FirefoxDriver();

        try {


            // Abre la página web de inicio de sesión
            driver.get("http://localhost:8080/repuestos");

            new WebDriverWait (driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Repuestos"));

            //Espera 3mil segundos
            Thread.sleep(3000);
        WebElement campoNombreRepuesto = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_nombreRepuesto']/input"));
        WebElement campoMarca = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_marcaRepuesto']/input"));
        WebElement campoPrecio = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_precioUnitario']/input"));
        //WebElement campoStock = driver.findElement(By.xpath("//vaadin-number-field[@id='txt_unidades']/input"));
        WebElement campoProveedor = driver.findElement(By.xpath("//vaadin-combo-box[@id='txt_proveedor']/input"));
        //WebElement campoEstado = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_estado']/input"));
        WebElement btnGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));

        campoNombreRepuesto.sendKeys("Pastillas de freno");
        campoMarca.sendKeys("Continental");
        campoPrecio.sendKeys("2000");
        //campoStock.sendKeys("8");
        campoProveedor.sendKeys("Continental");
        //campoEstado.sendKeys("Activo");

        Thread.sleep(3000);

        //btnGuardar.click();
        // Aquí puedes agregar aserciones o verificaciones adicionales según sea necesario
    }finally {
            driver.close();
        }



//    @Test
//    void testMostrarMensajeError() {
//        repuestosView.mostrarMensajeError("Este es un mensaje de error");
//        // Aquí puedes agregar aserciones o verificaciones adicionales según sea necesario
//    }
//
//    @Test
//    void testEliminarRepuesto() throws InterruptedException {
//        driver.get("http://localhost:8080/repuestos");
//        new WebDriverWait(driver, ofSeconds(30)).until(ExpectedConditions.titleIs("Repuestos"));
//        Thread.sleep(3000);
//
//        // Simular la selección de un repuesto en el grid
//        WebElement btnEliminar = driver.findElement(By.xpath("//vaadin-button[@id='btn_eliminar']"));
//        btnEliminar.click();
//
//        // Verificar que se llame al método eliminarRepuesto en el controlador con el id del repuesto seleccionado
//        verify(mockControlador).eliminarRepuesto("id_del_repuesto_seleccionado");
//    }
//
//    @AfterEach
//    void tearDown() {
//        if (driver != null) {
//            driver.quit();
//        }
//    }
}
}