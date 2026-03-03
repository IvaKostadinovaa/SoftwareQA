package com.hendisantika.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class SignUpWebTest {

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
    public void test_signup_success() {
        String email = "testing" + System.currentTimeMillis() + "@gmail.com";

        page.navigate("http://localhost:8080/signup");

        page.fill("input[placeholder='First Name']", "TestFirst");
        page.fill("input[placeholder='Last Name']", "TestLast");
        page.fill("input[placeholder='Email']", email);
        page.fill("input[placeholder='Password']", "test1234");

        page.click("button[type='submit']");
        page.waitForURL("**/signup");

        assertTrue(page.url().contains("/signup"));
        Locator successAlert = page.locator(".alert-success");
        assertThat(successAlert).isVisible();
        assertThat(successAlert).containsText("Your account has been created successfully");
    }
}