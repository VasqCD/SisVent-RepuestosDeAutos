package ventaderepuestos.test;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.firefox.FirefoxDriver;

import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

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
			
			//Espera 3 segundos 
			Thread.sleep(3000);
			
			// Localiza el campo de entrada de repuesto
			WebElement campoNombreRepuesto = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_nombreRepuesto']/input"));
			WebElement campoPrecio = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_precioUnitario']/input"));
			WebElement campoFecha = driver.findElement(By.xpath("//vaadin-date-picker[@id='txt_fechaIngreso']/input"));
			WebElement campoUnidades = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_unidades']/input"));
			WebElement campoEstado = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_estado']/input"));
			
			WebElement btnGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_guardar']"));
			
			// Ingresa un repuesto
			campoNombreRepuesto.sendKeys("Pastillas de freno");
			campoPrecio.sendKeys("1498");
			campoFecha.sendKeys("15/3/2024");
			campoUnidades.sendKeys("8");
			campoEstado.sendKeys("Activo");
			
			//Espera 3 segundos para dar click en el boton guardar
			Thread.sleep(3000);
			
			btnGuardar.click();
		
		}finally {
			driver.close();
		}
	}

}
