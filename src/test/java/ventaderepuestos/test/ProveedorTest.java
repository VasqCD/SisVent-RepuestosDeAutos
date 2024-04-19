package ventaderepuestos.test;

import static java.time.Duration.ofSeconds;
import static org.openqa.selenium.support.ui.ExpectedConditions.titleIs;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class ProveedorTest {
	
	@Test
	public void testGuardarRepuesto() throws InterruptedException {
		
		// Inicializa el WebDriver para Chrome
		//WebDriver driver = new ChromeDriver();
		//WebDriver driver = new FirefoxDriver();
		WebDriver driver = new EdgeDriver();
		
		try {
			
		
			// Abre la página web de inicio de sesión
			driver.get("http://localhost:8080/proveedor");
			
			new WebDriverWait (driver, ofSeconds(30), ofSeconds(1)).until(titleIs("Proveedor"));
			
			//Espera 3 segundos 
			Thread.sleep(3000);
			
			// Localiza el campo de entrada de proveedor
			WebElement cNombre = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_nomProveedor']/input"));
			WebElement cDireccion = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_Direccion']"));
			WebElement cCorreo = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_correo']/input"));
			WebElement cTelefono = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_telefono']/input"));
			WebElement cPais = driver.findElement(By.xpath("//vaadin-text-field[@id='txt_pais']"));

			
			WebElement btnGuardar = driver.findElement(By.xpath("//vaadin-button[@id='btn_gurdar']"));
			
			// Ingresa un proveedor
			cNombre.sendKeys("Super repuestos");
			cDireccion.sendKeys("San Pedro Sula");
			cCorreo.sendKeys("contacto@srepuesto.com");
			cTelefono.sendKeys("8888-8989");
			cPais.sendKeys("Honduras");
			
			
			
			//Espera 3 segundos para dar click en el boton guardar
			Thread.sleep(3000);
			
			btnGuardar.click();
		
		}finally {
			driver.close();
		}
	}

}
