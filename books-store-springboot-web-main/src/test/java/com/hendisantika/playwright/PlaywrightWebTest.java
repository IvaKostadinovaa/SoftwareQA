package com.hendisantika.playwright;

import com.microsoft.playwright.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlaywrightWebTest {

    @Test
    public void testFullBookWorkflow() {
        try (Playwright playwright = Playwright.create()) {
            Browser browser = playwright.chromium().launch(new BrowserType.LaunchOptions().setHeadless(false));
            Page page = browser.newPage();

            page.navigate("http://localhost:8080/");

            // Sign Up
            page.click("text=Signup");
            page.waitForURL("**/signup");

            page.fill("input[name='firstName']", "TestFirst");
            page.fill("input[name='lastName']", "TestLast");
            page.fill("input[name='email']", "testing@gmail.com");
            page.fill("input[name='password']", "test1234");

            page.waitForTimeout(1500);

            page.click("text=Signin");
            page.waitForTimeout(1500);

            // Login

            page.fill("input[name='username']", "testing@gmail.com");
            page.fill("input[name='password']", "test1234");

            page.waitForTimeout(1500);
            page.click("button[type='submit']");

            page.waitForTimeout(1500);


            //  Create Book
            page.click("text=Add Book");
            page.waitForURL("**/create");

            page.waitForTimeout(1500);

            page.fill("input#title", "The Little Prince");
            // Form
            page.fill("input[name='title']", "The Little Prince");
            page.fill("input[name='author']", "Antoine de Saint-Exupéry");
            page.fill("input[name='description']",
                    "A whimsical tale of a little prince traveling from planet to planet, learning about love, friendship, and human values.");
            page.fill("input[name='price']", "350");
            page.fill("input[name='stock']", "10");

            page.click("button[type='submit']");
            page.waitForTimeout(1500);

            // Edit Book
            Locator bookRow = page.locator("tr").filter(new Locator.FilterOptions().setHasText("The Little Prince")).first();

            bookRow.locator("text=Edit").click();
            page.waitForURL("**/edit/**");
            page.fill("input[name='price']", "500");
            page.waitForTimeout(1500);
            page.click("button[type='submit']");
            page.waitForTimeout(1500);

            page.navigate("http://localhost:8080");

            bookRow.locator("text=Detail").click();
            page.waitForURL("**/detail/**");
            page.waitForTimeout(1500);

            page.navigate("http://localhost:8080");

            //  Delete Book
            Locator row = page.locator("tr").filter(new Locator.FilterOptions().setHasText("The Little Prince"));
            row.locator("a.btn-danger").click();
            assertTrue(!page.content().contains("The Little Prince"));
            page.waitForTimeout(1500);

            // Clean user
            page.navigate("http://localhost:8080/admin/users");
            Locator userRow = page.locator("tr").filter(new Locator.FilterOptions().setHasText("testing@gmail.com"));
            if (userRow.isVisible()) {
                userRow.locator("a.btn-danger").click();
                page.waitForTimeout(1000);
            }

            browser.close();
        }
    }
}