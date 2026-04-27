package testCases;

import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;
import utilities.Data_Provides;

public class TC004_LoginTestDDT extends BaseClass {

    @Test(dataProvider = "excelData", dataProviderClass = Data_Provides.class)
    public void Verify_loginDDT(Map<String, String> data) {

        logger.info("**** Started TC003_LoginTestDDT ****");
        // 🔥 Why: logs test start in report/log file
        // Benefit: helps debugging + execution tracking

        // ✅ Extract data from Map
        String email = data.get("email");
        // 🔥 Why: Map uses column name instead of index
        // Benefit: no dependency on column order in Excel
        // Example: "email" → a@test.com

        String pwd = data.get("password");
        // 🔥 Why: gets password from Excel
        // Benefit: supports multiple users dynamically
        // Example: "password" → 12345

        String res = data.get("res");
        // 🔥 Why: expected result (valid/invalid)
        // Benefit: helps decide PASS/FAIL logic dynamically
        // Example: "valid" or "invalid"

        System.out.println("Data: " + email + " | " + pwd + " | " + res);
        // 🔥 Why: console debugging
        // Benefit: quick verification of input data

        try {
            HomePage hm = new HomePage(driver);
            // 🔥 Why: Page Object Model (POM)
            // Benefit: separates UI logic from test logic

            hm.clickMyAccount();
            // 🔥 Why: navigates to login section
            // Example: user clicks "My Account"

            hm.clickLoginlnk();
            // 🔥 Why: opens login page
            // Benefit: reusable method

            LoginPage lp = new LoginPage(driver);
            // 🔥 Why: Login page object created
            // Benefit: centralizes login actions

            lp.EnterMailId(email);
            // 🔥 Why: enters dynamic email from Excel
            // Example: a@test.com

            lp.EnterPassword(pwd);
            // 🔥 Why: enters dynamic password
            // Example: 12345

            lp.clickLoginBtn();
            // 🔥 Why: submits login form
            // Benefit: triggers authentication

            MyAccountPage myp = new MyAccountPage(driver);
            // 🔥 Why: checks post-login page
            // Benefit: verifies login success

            boolean targetPage = myp.MYAccountHeadingExists();
            // 🔥 Why: validation check
            // Benefit: confirms login success/failure
            // Example: true = login success

            // ===========================
            // 🔥 VALID LOGIN CASE
            // ===========================
            if (res.equalsIgnoreCase("valid")) {

                if (targetPage) {
                    // ✔ Expected: login should succeed
                    logger.info("Valid login - Passed");
                    myp.ClickLogout();
                    // 🔥 Why: clean session for next test
                    Assert.assertTrue(true);
                } else {
                    // ❌ Expected success but failed
                    logger.error("Valid login - Failed");
                    Assert.fail();
                }
            }

            // ===========================
            // 🔥 INVALID LOGIN CASE
            // ===========================
            else if (res.equalsIgnoreCase("invalid")) {

                if (targetPage) {
                    // ❌ login should NOT succeed but it did
                    myp.ClickLogout();
                    logger.error("Invalid login - Failed");
                    Assert.fail();
                } else {
                    // ✔ correctly rejected login
                    logger.info("Invalid login - Passed");
                    Assert.assertTrue(true);
                }
            }

        } catch (Exception e) {
            // 🔥 Why: handles unexpected failures
            // Benefit: prevents test crash
            logger.error("Test failed due to exception: " + e.getMessage());
            Assert.fail();
        }

        logger.info("**** Finished TC003_LoginTestDDT ****");
        // 🔥 Why: marks test end
        // Benefit: logs complete execution cycle
    }
}