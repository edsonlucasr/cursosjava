import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;




public class TesteCampoTreinamento {
	private WebDriver driver;
	
	@Before
		public void antes() {
		System.setProperty("webdriver.chrome.driver", "/Users/edson/eclipse/java-2020-03/chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
	
	}
	
	@After
	public void depois() {
		//Fechar navegador
		driver.quit();
	}
	
	@Test
		public void teste() {
		
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Edson Lucas");
		Assert.assertEquals("Edson Lucas", driver.findElement(By.id("elementosForm:nome")).getAttribute("value"));
		
	}

	@Test
	public void deveInteragircomTextArea() {
		driver.findElement(By.id("elementosForm:sugestoes")).sendKeys("Teste automatizado");
		Assert.assertEquals("Teste automatizado", driver.findElement(By.id("elementosForm:sugestoes")).getAttribute("value"));
		
	}
	
	@Test
	public void deveInteragircomRadioButton() {
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		Assert.assertTrue(driver.findElement(By.id("elementosForm:sexo:0")).isSelected());
		
	}	
	
	@Test
	public void deveInteragircomCheckbox() {
		driver.findElement(By.id("elementosForm:comidaFavorita:2")).click();
		Assert.assertTrue(driver.findElement(By.id("elementosForm:comidaFavorita:2")).isSelected());
		
	}
	
	@Test
	public void deveInteragircomCombo() {
		//Selecionar uma opção no combo
		WebElement element =  driver.findElement(By.id("elementosForm:escolaridade"));
		Select combo = new Select(element);
		combo.selectByIndex(3);
		
		//outra forma de selecionar a opção do combo:
		//combo.selectByValue("superior");
		
		//outra forma de selecionar a opção do combo:
		//combo.selectByVisibleText("doutorado");
		
		Assert.assertEquals("2o grau completo", combo.getFirstSelectedOption().getText()); 
		
		
	}
	
	@Test
	public void deveInteragircomComboMultiplo() {
		WebElement element =  driver.findElement(By.id("elementosForm:esportes"));
		Select combo = new Select(element);
		combo.selectByVisibleText("Natacao");
		combo.selectByVisibleText("Corrida");
		combo.selectByVisibleText("O que eh esporte?");
		
	}
	
	@Test
	public void deveInteragircomBotoes() {
		WebElement botao =  driver.findElement(By.id("buttonSimple"));
		botao.click();
		
		Assert.assertEquals("Obrigado!", botao.getAttribute("value"));
		
		
	}
	
	@Test
	public void deveInteragircomLinks() {
		driver.findElement(By.linkText("Voltar")).click();
		
		Assert.assertEquals("Voltou!", driver.findElement(By.id("resultado")).getText());
		
		
	}
	
	@Test
	public void deveBuscarTextosNaPagina() {
		Assert.assertEquals("Campo de Treinamento", driver.findElement(By.tagName("h3")).getText()); 
		
		Assert.assertEquals("Cuidado onde clica, muitas armadilhas...", driver.findElement(By.className("facilAchar")).getText()); 
		
	}
	
	@Test
	public void deveInteragirComAlertSimples() {
		
		driver.findElement(By.id("alert")).click();
		
		//Alterando o foco para o alert
		Alert alert = driver.switchTo().alert();
		String texto = alert.getText();
		Assert.assertEquals("Alert Simples", texto);
		alert.accept();
		
		driver.findElement(By.id("elementosForm:nome")).sendKeys(texto);
		
		
	}
	
	@Test
	public void deveInteragirComAlertConfirm() {
		
		driver.findElement(By.id("confirm")).click();
		
		Alert confirm = driver.switchTo().alert();	
		Assert.assertEquals("Confirm Simples", confirm.getText());
		
		//Aceitar o alert
		confirm.accept();
		
		//Cancelar o alert
		//confirm.dismiss();
				
		//Validar o texto quando confirma o alert		
		Assert.assertEquals("Confirmado", confirm.getText());
		
		////Validar o texto quando cancela o alert
		//Assert.assertEquals("Negado", confirm.getText());
		
		
	}
	
	@Test
	public void deveInteragirComAlertPrompt() {
	
		driver.findElement(By.id("prompt")).click();
		
		Alert prompt = driver.switchTo().alert();	
		Assert.assertEquals("Digite um numero", prompt.getText());
		
		prompt.sendKeys("123");
		prompt.accept();
		
		Assert.assertEquals("Era 123?", prompt.getText());
		prompt.accept();
		
		Assert.assertEquals(":D", prompt.getText());
		prompt.accept();
		
		
	}
	
	@Test
	public void desafioCadastro() {
		
		//Inserindo nome e sobrenome
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Edson Lucas");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Roncador");
		
		//Selecionando sexo
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		
		//Qual a sua comida favorita?
		driver.findElement(By.id("elementosForm:comidaFavorita:2")).click();
		
		//Escolaridade
		WebElement element =  driver.findElement(By.id("elementosForm:escolaridade"));
		Select combo = new Select(element);
		combo.selectByValue("superior");
		
		//Esportes
		WebElement esporte =  driver.findElement(By.id("elementosForm:esportes"));
		Select combo1 = new Select(esporte);
		combo1.selectByVisibleText("Futebol");
		
		//Sugestoes
		driver.findElement(By.id("elementosForm:sugestoes")).sendKeys("Teste cadastro");
				
		//Clicar em cadastrar
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		
		Assert.assertTrue(driver.findElement(By.id("resultado")).getText().startsWith("Cadastrado!"));
		Assert.assertEquals("Nome: Edson Lucas", driver.findElement(By.id("descNome")).getText());
		Assert.assertEquals("Sobrenome: Roncador", driver.findElement(By.id("descSobrenome")).getText());
		Assert.assertEquals("Sexo: Masculino", driver.findElement(By.id("descSexo")).getText());
		Assert.assertEquals("Comida: Pizza", driver.findElement(By.id("descComida")).getText());
		Assert.assertEquals("Escolaridade: superior", driver.findElement(By.id("descEscolaridade")).getText());
		Assert.assertEquals("Esportes: Futebol", driver.findElement(By.id("descEsportes")).getText());
		Assert.assertEquals("Sugestoes: Teste cadastro", driver.findElement(By.id("descSugestoes")).getText());
		
	}	
		
	@Test
	public void interagirComFrame() {
		
		driver.switchTo().frame("frame1");
		driver.findElement(By.id("frameButton")).click();
		Alert alert = driver.switchTo().alert();
		String msg = alert.getText();
		Assert.assertEquals("Frame OK!", msg);
		alert.accept();
		
		driver.switchTo().defaultContent();
		driver.findElement(By.id("elementosForm:nome")).sendKeys(msg);
		
	}
	
	@Test
	public void interagirComFrameEscondido() {
		driver.switchTo().frame("frame2");
		driver.findElement(By.id("frameButton")).click();
		Alert alert = driver.switchTo().alert();
		String msg = alert.getText();
		Assert.assertEquals("Frame OK!", msg);
		alert.accept();
	}
	
	@Test
	public void interagirComJanelas() {
		
		driver.findElement(By.id("buttonPopUpEasy")).click();
		driver.switchTo().window("Popup");
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
		driver.close();
		
		driver.switchTo().window("");
		driver.findElement(By.tagName("textarea")).sendKeys("E agora?");
	
	
	}
	
	@Test
	public void interagirComJanelasSemTitulo() {
		
		driver.findElement(By.id("buttonPopUpHard")).click();
		System.out.println(driver.getWindowHandle());
		System.out.println(driver.getWindowHandles());		
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[1]);
		driver.findElement(By.tagName("textarea")).sendKeys("Deu certo?");
		
		driver.switchTo().window((String) driver.getWindowHandles().toArray()[0]);
		driver.findElement(By.tagName("textarea")).sendKeys("E agora?");
	
		
	}

	@Test
	public void deveValidarNomeObrigatorio() {
		
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Nome eh obrigatorio", alert.getText());
	}
	
	@Test
	public void deveValidarSobrenomeObrigatorio() {
		
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Lucas");
		
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sobrenome eh obrigatorio", alert.getText());
		
	}
	
	@Test
	public void deveValidarSexoObrigatorio() {
		
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Teste");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Teste");
				
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sexo eh obrigatorio", alert.getText());
		
		
	}

	@Test
	public void deveValidarComidaObrigatorio() {
		
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Teste");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Teste");
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:3")).click();
		
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Tem certeza que voce eh vegetariano?", alert.getText());
				
	}

	@Test
	public void deveValidarEsporteObrigatorio() {
		
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Teste");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Teste");
		driver.findElement(By.id("elementosForm:sexo:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
		WebElement esporte =  driver.findElement(By.id("elementosForm:esportes"));
		Select combo1 = new Select(esporte);
		combo1.selectByVisibleText("Futebol");
		combo1.selectByVisibleText("O que eh esporte?");
		
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Voce faz esporte ou nao?", alert.getText());
		
	}
	
}
