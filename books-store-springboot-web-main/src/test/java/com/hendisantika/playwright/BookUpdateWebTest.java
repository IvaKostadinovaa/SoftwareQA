package com.hendisantika.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BookUpdateWebTest {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
        TestHelpers.login(page, "testing@gmail.com", "test1234");
        TestHelpers.createBook(page);
    }

    @AfterEach
    public void cleanup() {
        TestHelpers.deleteBook(page);
        browser.close();
        playwright.close();
    }

    @Test
    public void testUpdateBook() {
        Locator row = page.locator("tr").filter(new Locator.FilterOptions().setHasText("The Little Prince"));
        row.locator("text=Edit").click();
        page.fill("input[name='price']", "500");
        page.click("button[type='submit']");

        row = page.locator("tr").filter(new Locator.FilterOptions().setHasText("The Little Prince"));
        assertThat(row).containsText("500");
    }
}