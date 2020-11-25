package applitools.HolidayShoppingHackathon;

import org.testng.annotations.Test;

import com.applitools.eyes.selenium.fluent.Target;

import base.BaseTests;

public class AllTests extends BaseTests {

	@Test
	public void testMainPage() {
		setConfiguration("Test 1");
		eyes.check(Target.window().fully().withName("main page"));
	}

	@Test
	public void testFilteredProductGrid() {
		setConfiguration("Test 2");
		webDriver.findElement(blackColor).click();
		webDriver.findElement(filterButton).click();
		eyes.check(Target.region(shoesGrid).withName("filter by color"));
	}

	@Test
	public void testProductDetails() {
		setConfiguration("Test 3");
		webDriver.findElement(firstShoe).click();
		eyes.check(Target.window().fully().withName("product details"));
	}
}
