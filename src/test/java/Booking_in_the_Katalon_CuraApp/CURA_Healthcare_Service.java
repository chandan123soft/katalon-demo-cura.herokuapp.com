package Booking_in_the_Katalon_CuraApp;

import java.time.Duration;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class CURA_Healthcare_Service {

	WebDriver driver;

	@BeforeSuite
	public void navigateToTheURL() {

		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		driver.get("https://katalon-demo-cura.herokuapp.com/");
		String title=driver.getTitle();
		Assert.assertEquals(title, "CURA Healthcare Service", "Unable to open the App URL");

	}

	@Test
	public void loginWithValidCrendetials() throws InterruptedException {

		WebElement makeAppointmentButton = driver.findElement(By.xpath("//a[@id='btn-make-appointment']"));
		makeAppointmentButton.click();
		WebElement userName = driver.findElement(By.xpath("//input[@id='txt-username']"));
		userName.sendKeys("John Doe");
		WebElement password = driver.findElement(By.xpath("//input[@id='txt-password']"));
		password.sendKeys("ThisIsNotAPassword");
		WebElement loginButton = driver.findElement(By.xpath("//button[@id='btn-login']"));
		loginButton.click();

	}

	@Test
	public void makeAppointment() throws InterruptedException {

		Select facilityDropdown = new Select(driver.findElement(By.xpath("//select[@id='combo_facility']")));
		facilityDropdown.selectByVisibleText("Seoul CURA Healthcare Center");

		WebElement medicaidRadioButton = driver.findElement(By.xpath("//input[@value='Medicaid']"));
		medicaidRadioButton.click();
		WebElement bookAppointmentButton = driver.findElement(By.xpath("//button[@id='btn-book-appointment']"));

		Date currentDate = new Date();
		WebElement visitDate = driver.findElement(By.xpath("//input[@id='txt_visit_date']"));
		visitDate.sendKeys(currentDate.toString());

		WebElement comment = driver.findElement(By.xpath("//*[@id='txt_comment']"));
		comment.sendKeys("Please be informed that your appointment has been booked !!!! ");
        bookAppointmentButton.click();

	}

	@Test(dependsOnMethods = "makeAppointment")
	public void verifyAppointmentConfirmation() {

		WebElement goToHomePageButton = driver.findElement(By.xpath("//a[@class='btn btn-default']"));
		Assert.assertEquals(true, goToHomePageButton.isDisplayed(), "Go to HomepageButton is not present");

	}

	@AfterSuite
	public void tearDown() {
		driver.quit();
	}

}
