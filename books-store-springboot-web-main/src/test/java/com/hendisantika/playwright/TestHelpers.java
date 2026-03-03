package com.hendisantika.playwright;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.WaitForSelectorState;

public class TestHelpers {

    public static void login(Page page, String email, String password) {
        page.navigate("http://localhost:8080/login");
        page.fill("input[name='username']", email);
        page.fill("input[name='password']", password);
        page.click("button[type='submit']");
        page.waitForURL("**/");
    }

    public static void createBook(Page page) {
        page.click("text=Add Book");
        page.waitForURL("**/create");

        page.fill("input[name='title']", "The Little Prince");
        page.fill("input[name='author']", "Antoine de Saint-Exupéry");
        page.fill("input[name='description']",
                "A whimsical tale of a little prince traveling from planet to planet, learning about love, friendship, and human values.");
        page.fill("input[name='price']", "350");
        page.fill("input[name='stock']", "10");

        page.click("button[type='submit']");
        page.waitForSelector("tr:has-text('The Little Prince')");
    }

    public static void deleteBook(Page page) {
        Locator row = page.locator("tr").filter(new Locator.FilterOptions().setHasText("The Little Prince"));
        if (row.count() > 0) {
            row.locator("a.btn-danger").click();
            page.waitForSelector("tr:has-text('The Little Prince')", new Page.WaitForSelectorOptions().setState(WaitForSelectorState.DETACHED));
        }
    }
}