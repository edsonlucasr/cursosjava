import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import net.sf.cglib.beans.BeanCopier.Generator;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit; 

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InformacoesUsuarioTest {
	private WebDriver driver;
	
	@Before
	public void setup() {
		System.setProperty("webdriver.chrome.driver", "/Users/edson/eclipse/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		
		//Abrir navegador
		driver.get("http://www.juliodelima.com.br/taskit");
		
		//Clicar no link com texto Sign In
				driver.findElement(By.linkText("Sign in")).click();
						
				//Identificando o formulário de login	
				WebElement formSignIn = driver.findElement(By.id("signinbox"));
				
				//Digitar no campo login	
				formSignIn.findElement(By.name("login")).sendKeys("julio0001");
				
				//Digitar no campo password
				formSignIn.findElement(By.name("password")).sendKeys("123456");
				
				//Clicar no botão SIGN IN
				driver.findElement(By.linkText("SIGN IN")).click();
				
				//Clicar em um link que possui a class "me"
				driver.findElement(By.className("me")).click();
				
				//Clicar em um link que possui o texto "MORE DATA ABOUT YOU"
				driver.findElement(By.linkText("MORE DATA ABOUT YOU")).click();
		
	}
	
	//@Test
	public void testAdicionarUmaInformacaoAdicionalDoUsuario() {
		//Clicar no botão através do seu xpath //button[@data-target="addmoredata"]
		driver.findElement(By.xpath("//button[@data-target=\"addmoredata\"]")).click();
		
		//Identificar o pop-up onde está o formulário de id addmoredata
		WebElement popupAddMoreData = driver.findElement(By.id("addmoredata"));
		
		//No combo de name type, escolher a opção Phone. Interagindo com combobox
		WebElement campoType = popupAddMoreData.findElement(By.name("type"));
		new Select(campoType).selectByVisibleText("Phone");
		
		//No campo de name contact, digitar +5511987654321
		popupAddMoreData.findElement(By.name("contact")).sendKeys("+5511987654321");
		
		//Clicar no botão Save
		popupAddMoreData.findElement(By.linkText("SAVE")).click();
		
		//Na mensagem de id toast-container, validar que o texto é Your contact has been added!
		WebElement mensagemPop = driver.findElement(By.id("toast-container"));
		String mensagem = mensagemPop.getText();
		assertEquals("Your contact has been added!", mensagem);
		
		
		
		//Validar se aparece o texto Hi, Julio		
		//WebElement me = driver.findElement(By.className("me"));
		//String textoNoElementoMe = me.getText();
		//Assert.assertEquals("Hi, Julio", textoNoElementoMe);
				
	}
	
	@Test
	public void removerUmContatoDeUmUsuario() {
		//Clicar no elemento xpath //span[text()="+5511987654321"]/following-sibling::a
		driver.findElement(By.xpath("//span[text()=\"+5511989891132\"]/following-sibling::a")).click();
		
		//Confirmar a exclusão clicando em OK
		driver.switchTo().alert().accept();
		
		// Validar que a mensagem apresentada foi Rest in peace, dear phone!
		WebElement mensagemPop = driver.findElement(By.id("toast-container"));
		String mensagem = mensagemPop.getText();
		assertEquals("Rest in peace, dear phone!", mensagem);
		
			
		//Aguardar até 10 segundos para que a janela desapareça
		WebDriverWait espera = new WebDriverWait(driver, 10);
		espera.until(ExpectedConditions.stalenessOf(mensagemPop));
		
		//Fazer "Logout"
		driver.findElement(By.linkText("Logout")).click();
		
		
	}
	
	@After
	public void tearDown() {
		//Fechar navegador
		driver.quit();
	}
}
