import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;

public class Challenge {

	@Test
	public void addCartForMaxAmountProduct() {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.saucedemo.com/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofMillis(2000));
		driver.findElement(By.id("user-name")).sendKeys("standard_user");
		driver.findElement(By.id("password")).sendKeys("secret_sauce" + Keys.ENTER);
		List<WebElement> prices = driver.findElements(By.xpath("//div[@class='pricebar']/div"));
		List<Double> list = new ArrayList<>();
		for (WebElement price : prices) {
			list.add(Double.parseDouble(price.getText().replaceAll("[$]", "").trim()));
		}

		Double maxPrice = Collections.max(list);

		WebElement addToCart = driver.findElement(
				By.xpath("//div[@class='pricebar']/div[text()='" + maxPrice + "']/following-sibling::button"));

		((JavascriptExecutor) driver).executeScript("arguments[0].click();", addToCart);

		driver.findElement(By.id("shopping_cart_container")).click();
		Assert.assertTrue(driver.findElement(By.xpath("//div[@class='inventory_item_price']")).getText()
				.contains(maxPrice.toString()));
	}

}
