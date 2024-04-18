package ventaderepuestos.test;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

import hn.ventaderepuestos.controller.InteractorRepuesto;
import hn.ventaderepuestos.views.repuestos.RepuestosView;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static java.time.Duration.ofSeconds;

public class RepuestoTest {
	
    private RepuestosView repuestosView;
    private InteractorRepuesto mockControlador;
    private WebDriver driver;
	
    @BeforeEach
    void setUp() {
        mockControlador = mock(InteractorRepuesto.class);
        repuestosView = new RepuestosView();
        driver = new ChromeDriver();
    }

    @Test
    void testGuardarRepuesto() throws InterruptedException {
        driver.get("http://localhost:8080/repuestos");
        new WebDriverWait(driver, ofSeconds(30)).until(ExpectedConditions.titleIs("Repuestos"));
        Thread.sleep(3000);
        WebElement campoNombreRepuesto = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_nombreRepuesto']/input"));
        WebElement campoMarca = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_marcaRepuesto']/input"));
        WebElement campoPrecio = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_precioUnitario']/input"));
        WebElement campoStock = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_unidades']/input"));
        WebElement campoEstado = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_estado']/input"));
        WebElement btnGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));

        campoNombreRepuesto.sendKeys("Pastillas de freno");
        campoMarca.sendKeys("Keinsei");
        campoPrecio.sendKeys("2000");
        campoStock.sendKeys("8");
        campoEstado.sendKeys("Activo");
        Thread.sleep(3000);
        btnGuardar.click();
      
    }


    @Test
    void testMostrarMensajeError() {
        repuestosView.mostrarMensajeError("Este es un mensaje de error");
    
    }
    
    @Test
    void testEliminarRepuesto() throws InterruptedException {
        driver.get("http://localhost:8080/repuestos");
        new WebDriverWait(driver, ofSeconds(30)).until(ExpectedConditions.titleIs("Repuestos"));
        Thread.sleep(3000);
        
        // Simular la selección de un repuesto en el grid
        WebElement btnEliminar = driver.findElement(By.xpath("//vaadin-button[@id='btn_eliminar']"));
        btnEliminar.click();
        
        // Verificar que se llame al método eliminarRepuesto en el controlador con el id del repuesto seleccionado
        verify(mockControlador).eliminarRepuesto("id_del_repuesto_seleccionado");
    }
    
    @AfterEach
    void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}