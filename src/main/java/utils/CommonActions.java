package utils;

import java.util.Random;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class CommonActions {

	Page page;

	public CommonActions(Page page) {
		this.page = page;
	}

	public void click(Locator locator) {
		locator.click();
	}

	public void hover(Locator locator) {
		locator.hover();
	}

	public void clearFieldAndEnterText(Locator locator, String value) {
		locator.clear();
		locator.fill(value);
	}

	public void navigateTo(String url) {
		page.navigate(url);
	}

	public String getURL() {
		return page.url();
	}

	public boolean checkElementIsDisplayed(Locator locator) {
		return locator.isVisible();
	}

	public void waitForSelector(String value) {
		page.waitForSelector(value);
	}
	public boolean compareText(Locator locator, String message) {
		return locator.textContent().equalsIgnoreCase(message.trim());
	}

	public void clickOnNthElement(Locator locator, int index) {
		locator.nth(index).click();
	}

	public String getText(Locator locator) {
		return locator.textContent();
	}

	public String randomEmailGenerator() {
		String name = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
		StringBuilder nameBuild = new StringBuilder();
		Random rnd = new Random();
		while (nameBuild.length() < 10) { // length of the random string.
			int index = (int) (rnd.nextFloat() * name.length());
			nameBuild.append(name.charAt(index));
		}
		String saltStr = nameBuild.toString();
		saltStr = saltStr + "@yopmail.com";
		return saltStr;

	}
}
