package com.hendisantika.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class LogInWebTest {

    Playwright playwright;
    Browser browser;
    Page page;

    @BeforeEach
    public void setup() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
        page = browser.newPage();
    }

    @AfterEach
    public void cleanup() {
        browser.close();
        playwright.close();
    }

    @Test
    public void test_login_success() {
        TestHelpers.login(page, "testing@gmail.com", "test1234");
        assertTrue(page.url().equals("http://localhost:8080/index"));
    }

    @Test
    public void test_login_failed() {
        page.navigate("http://localhost:8080/login");
        page.fill("input[name='username']", "wrong@gmail.com");
        page.fill("input[name='password']", "wrongpass");
        page.click("button[type='submit']");

        page.waitForURL("**/login?error");
        Locator errorAlert = page.locator(".alert-warning");

        assertTrue(errorAlert.isVisible());
        assertTrue(errorAlert.innerText().contains("Invalid username and password"));
    }
}