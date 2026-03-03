package com.hendisantika.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class BookDetailWebTest {

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
        page.navigate("http://localhost:8080/");
        TestHelpers.deleteBook(page);

        browser.close();
        playwright.close();
    }

    @Test
    public void testViewBookDetails() {
            Locator row = page.locator("tr").filter(new Locator.FilterOptions().setHasText("The Little Prince"));

            row.locator("text=Detail").first().click();

            page.waitForSelector("h3:has-text('The Little Prince')");


            assertThat(page.locator("h3.display-4")).hasText("The Little Prince");
            assertThat(page.locator("h5.text-muted")).hasText("By Antoine de Saint-Exupéry");
            assertThat(page.locator("h4.text-success")).containsText("Rp350.00");
            assertThat(page.locator("h4.text-danger")).hasText("10 pieces");
            assertThat(page.locator("p")).isVisible();
        }
}